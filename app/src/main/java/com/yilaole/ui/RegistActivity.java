package com.yilaole.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.yilaole.R;
import com.yilaole.base.BaseLogActivity;
import com.yilaole.base.app.MyApplication;
import com.yilaole.http.rx.RxBus;
import com.yilaole.http.rx.RxBusBaseMessage;
import com.yilaole.http.rx.RxCodeConstants;
import com.yilaole.inter_face.ilistener.OnRegListener;
import com.yilaole.presenter.LogPresenterImpl;
import com.yilaole.save.SPUtil;
import com.yilaole.utils.MLog;
import com.yilaole.utils.ToastUtil;
import com.yilaole.utils.Utils;
import com.yilaole.widget.CountDownButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册界面
 */

public class RegistActivity extends BaseLogActivity implements OnRegListener, CountDownButton.CountdownListener {
    @BindView(R.id.tv_user)
    EditText tv_user;

    //验证码需要做倒计时处理
    @BindView(R.id.et_code)
    EditText et_code;

    @BindView(R.id.tv_ps)
    EditText tv_ps;

    @BindView(R.id.img_watch)
    ImageView img_watch;

    //验证码倒计时
    @BindView(R.id.getIdenty)
    CountDownButton button;

    //同意按钮
    @BindView(R.id.ifAgreeProvisionCheckBox)
    CheckBox agreeCheckBox;

    private LogPresenterImpl presenter;
    private String user_phone;
    private String code;
    private String password;
    private boolean isWatch = false;
    private boolean isAgree = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_reg);
        ButterKnife.bind(RegistActivity.this);
        initMyView();
        initListener();
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

            //设置自动隐藏+手动显示的透明状态栏
            //            decorView.setSystemUiVisibility(
            //                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            //                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            //                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            //                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
            //                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
            //                            | View.SYSTEM_UI_FLAG_IMMERSIVE);


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

        presenter = new LogPresenterImpl(this, this);

        //默认统一条款协议
        isAgree = true;
        agreeCheckBox.setChecked(true);
        //开启按钮监听
        button.setOnCountDownListener(this);
    }

    private void initListener() {
        //手机号监听
        tv_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                user_phone = editable.toString().trim();
            }
        });
        //code监听
        et_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                code = editable.toString().trim();
            }
        });

        //密码
        tv_ps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password = editable.toString().trim();

            }
        });

        //协议监听
        agreeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAgree = isChecked;
            }
        });

    }

    /**
     * 控件点击监听
     *
     * @param view
     */

    @OnClick({R.id.reg_for_back, R.id.getIdenty, R.id.btn_reg, R.id.img_watch})
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.reg_for_back:
                this.finish();
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

            case R.id.getIdenty://获取验证码

                //                if (user_phone == null || user_phone.isEmpty()) {
                //                    ToastUtil.ToastShort(RegistActivity.this, "手机号不能为空");
                //                    return;
                //                }
                //
                //                if (!MobileUtils.isMobile(user_phone)) {
                //                    ToastUtil.ToastShort(RegistActivity.this, "手机号无效,无法注册！");
                //                    return;
                //                }
                //                loadingDialog.show();
                //                presenter.pGetCode(user_phone);
                break;

            case R.id.btn_reg://注册

                if (isNull()) {
                    return;
                }

                if (!Utils.isPassword(password)) {
                    ToastUtil.toastLong(RegistActivity.this, "密码只支持6-16位的字母、数字、下划线格式！");
                    return;

                }
                loadingDialog.show();

                //调接口登录
                presenter.pRegist(user_phone, password, code);
                break;
        }
    }

    /**
     * 非空排查
     *
     * @return
     */
    private boolean isNull() {

        if (user_phone == null || user_phone.isEmpty()) {
            ToastUtil.ToastShort(RegistActivity.this, "手机号不能为空");
            return true;
        }
        if (code == null || code.isEmpty()) {
            ToastUtil.ToastShort(RegistActivity.this, "验证码不能为空");
            return true;
        }
        if (code == null || password.isEmpty()) {
            ToastUtil.ToastShort(RegistActivity.this, "密码不能为空");
            return true;
        }

        if (!isAgree) {
            ToastUtil.ToastShort(RegistActivity.this, "请同意用户协议!");
            return true;
        }

        return false;
    }

    /**
     * =====================================================接口回调=====================================================
     *
     * @return
     */

    @Override
    public void onRegSuccess(Object obj) {
        loadingDialog.dismiss();
        MLog.ToastShort(this, (String) obj);
        //保存手机号，登录界面直接显示
        SPUtil.setUserPhone(user_phone);
        ToastUtil.ToastShort(this, "注册成功,请重新登录！");

        //为登录界面保存手机号
        RxBus.getDefault().post(RxCodeConstants.FORGET2LOG, new RxBusBaseMessage());
        startActivity(LoginActivity.class);
        //遍历关闭打开的多个界面，回到登录界面
        MyApplication.getInstance().closeAllActOFSome();

    }

    @Override
    public void onCodeFailed(int code, String msg, Exception e) {
        loadingDialog.dismiss();
        ToastUtil.ToastShort(RegistActivity.this, msg);
    }

    @Override
    public void onCodeSuccess(Object object) {
        loadingDialog.dismiss();
        // 验证码倒计时操作
        ToastUtil.ToastShort(RegistActivity.this, "发送验证码成功，请稍后！");
        button.start(this, 45);
    }

    @Override
    public void onRegFailed(int code, String msg, Exception e) {
        loadingDialog.dismiss();
        ToastUtil.ToastShort(RegistActivity.this, msg);
        MLog.e(msg, e.toString());
    }

    /**
     * =====================================================倒计时回调=====================================================
     *
     * @return
     */
    @Override
    public void timeCountdown(int time) {
        button.setText("倒计时:" + time);
    }

    @Override
    public void stop() {
        button.setText(getResources().getString(R.string.get_identy));
    }
}
