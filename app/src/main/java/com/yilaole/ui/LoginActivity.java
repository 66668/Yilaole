package com.yilaole.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yilaole.R;
import com.yilaole.base.BaseLogActivity;
import com.yilaole.base.app.Constants;
import com.yilaole.base.app.MyApplication;
import com.yilaole.bean.mine.LoginBean;
import com.yilaole.http.cache.ACache;
import com.yilaole.http.rx.RxBus;
import com.yilaole.http.rx.RxBusBaseMessage;
import com.yilaole.http.rx.RxCodeConstants;
import com.yilaole.inter_face.ilistener.OnLoginListener;
import com.yilaole.presenter.LogPresenterImpl;
import com.yilaole.save.SPUtil;
import com.yilaole.utils.MobileUtils;
import com.yilaole.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * 登录界面
 */

public class LoginActivity extends BaseLogActivity implements OnLoginListener {

    //手机号
    @BindView(R.id.tv_user)
    EditText tv_user;

    //密码
    @BindView(R.id.tv_ps)
    EditText tv_ps;

    //可见
    @BindView(R.id.img_watch)
    ImageView img_watch;

    private boolean isWatch = false;
    private String user_phone;
    private String password;
    private LogPresenterImpl presenter;
    private LoginBean bean;
    private ACache cache;
    private boolean isForget = false;//是否从忘记密码界面返回
    private boolean isRegist = false;//是否从注册界面接收返回

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        ButterKnife.bind(this);
        initMyView();
    }

    /**
     * 设置透明状态栏
     * //flag的详细用法见Readme讲解
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            //设置固定 透明状态栏
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    private void initMyView() {
        //密码不可见初始化
        isWatch = false;
        tv_ps.setTransformationMethod(PasswordTransformationMethod.getInstance());

        user_phone = SPUtil.getUserPhone();
        password = SPUtil.getPassword();
        cache = ACache.get(this);
        presenter = new LogPresenterImpl(this, this);
        initShow();
    }


    /**
     * 用户名 密码显示到界面
     */
    private void initShow() {
        tv_user.setText(user_phone);
        if (!isRegist && !isForget) {
            tv_ps.setText(password);
        } else {
            tv_ps.setText("");
        }
    }

    /**
     * 控件点击监听
     *
     * @param view
     */
    @OnClick({R.id.tv_forgetPs, R.id.btn_log, R.id.btn_reg, R.id.img_weichat, R.id.for_back, R.id.img_watch})
    public void onItemClick(View view) {
        switch (view.getId()) {
            case R.id.btn_reg://注册
                startActivity(RegistActivity.class);
                break;
            case R.id.tv_forgetPs://忘记密码
                startActivity(GetPassWordOneActivity.class);
                break;
            case R.id.btn_log:
                getVal();

                if (isNull()) {
                    return;
                }

                if (!MobileUtils.isMobile(user_phone)) {
                    ToastUtil.ToastShort(LoginActivity.this, "手机号无效！");
                    return;
                }

                //调接口登录
                loadingDialog.show();
                presenter.pLoginByPhoneAndPs(user_phone, password);
                break;

            case R.id.img_weichat://微信登陆
                //                ToastUtil.toastLong(LoginActivity.this, "即将开通，请用手机号登录！");
                wechatLog();
                break;

            case R.id.img_watch:
                if (!isWatch) {
                    //密码可见
                    tv_ps.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isWatch = true;
                } else {
                    //密码不可见
                    tv_ps.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isWatch = false;
                }
                break;
            case R.id.for_back:
                this.finish();
                break;
        }
    }

    /**
     * 微信登陆
     */
    private void wechatLog() {
        //非空
        if (MyApplication.api == null) {
            // application获取空，再注册一遍
            MyApplication.api = WXAPIFactory.createWXAPI(this, MyApplication.APP_ID, true);
        }

        //
        if (!MyApplication.api.isWXAppInstalled()) {
            ToastUtil.toastLong(LoginActivity.this, "您手机尚未安装微信，请先安装微信");
            return;
        }

        MyApplication.api.registerApp(MyApplication.APP_ID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        //官方说明：用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止csrf攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加session进行校验
        //diandi_wx_login / wechat_sdk_xb_live_state
        req.state = "wechat_sdk_xb_live_state";
        MyApplication.api.sendReq(req);
    }

    //获取界面参数
    private void getVal() {
        user_phone = tv_user.getText().toString().trim();
        password = tv_ps.getText().toString().trim();
    }

    //非空验证
    private boolean isNull() {
        if (TextUtils.isEmpty(user_phone)) {
            ToastUtil.ToastShort(LoginActivity.this, "手机号不能为空！");
            return true;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.ToastShort(LoginActivity.this, "密码不能为空！");
            return true;
        }
        return false;
    }
    /**
     * ===============================================================================
     * ========================================接口回调=======================================
     * ===============================================================================
     */
    /**
     * 登录成功 修改相关信息
     * <p>
     * （1）更新个人中心
     */

    @Override
    public void onLogSuccess(Object obj) {
        loadingDialog.dismiss();
        bean = (LoginBean) obj;
        ToastUtil.ToastShort(this, "登录成功！");

        //保存登录状态

        SPUtil.clearUserMessge();
        //保存后台参数
        SPUtil.setUserName(bean.getName());//保存用户名
        SPUtil.setUserImage(bean.getImage());//保存图片
        SPUtil.setToken(bean.getToken());//保存token（该处token不用，使用缓存的token操作）
        SPUtil.setTokenTime(bean.getExpires());//保存时间

        //保存手机用到的参数
        SPUtil.setPassword(password);//保存密码
        SPUtil.setUserPhone(user_phone);//保存手机
        SPUtil.setIsLogin(true);//

        //缓存保存 token等信息,关于token验证，都从缓存中获取
        cache.put(Constants.TOKEN, bean.getToken(), bean.getExpires());
        //修改
        MyApplication.getInstance().setLogin(true);

        //需要回调 修改个人登录状态 RxBus
        RxBus.getDefault().post(RxCodeConstants.LOG2LOG, new RxBusBaseMessage());

        //界面
        this.finish();

    }

    @Override
    public void onLogFailed(int code, String msg, Exception e) {
        loadingDialog.dismiss();
        ToastUtil.ToastShort(this, msg);
    }

    /**
     * 忘记密码 注册返回后走这个方法，所以接收 传值用该方法
     */
    @Override
    protected void onResume() {
        super.onResume();
        initRxBus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initRxBus() {
        RxBus.getDefault().toObservable(RxCodeConstants.FORGET2LOG, RxBusBaseMessage.class)
                .subscribe(new Action1<RxBusBaseMessage>() {
                    @Override
                    public void call(RxBusBaseMessage rxBusBaseMessage) {
                        isForget = true;
                        initMyView();
                    }
                });
        RxBus.getDefault().toObservable(RxCodeConstants.REG2LOG, RxBusBaseMessage.class)
                .subscribe(new Action1<RxBusBaseMessage>() {
                    @Override
                    public void call(RxBusBaseMessage rxBusBaseMessage) {
                        isRegist = true;
                        initMyView();
                    }
                });

    }
}
