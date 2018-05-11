package com.yilaole.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.base.BaseLogActivity;
import com.yilaole.base.app.Constants;
import com.yilaole.inter_face.ilistener.OnForgetAndChangePsListener;
import com.yilaole.inter_face.ipresenter.IForgetAndChangePsPresenter;
import com.yilaole.presenter.LogPresenterImpl;
import com.yilaole.utils.MLog;
import com.yilaole.utils.MobileUtils;
import com.yilaole.utils.ToastUtil;
import com.yilaole.widget.CountDownButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 找回密码1界面
 */

public class GetPassWordOneActivity extends BaseLogActivity implements OnForgetAndChangePsListener, CountDownButton.CountdownListener {

    //手机号
    @BindView(R.id.tv_user)
    TextView tv_user;

    //验证码倒计时
    @BindView(R.id.getIdenty)
    CountDownButton button;

    //验证码
    @BindView(R.id.tv_identy)
    TextView et_code;

    private IForgetAndChangePsPresenter presenter;
    private String user_phone;
    private String code;
    private boolean isCodeClick = false;//发送短信按钮 是否点击

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_getps1);
        ButterKnife.bind(this);
        initMyView();
        initListener();
    }


    private void initMyView() {
        //开启按钮监听
        button.setOnCountDownListener(this);
        presenter = new LogPresenterImpl(this, this);
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
    }


    @OnClick({R.id.btn_next, R.id.ps_for_back1, R.id.getIdenty})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_next://下一步
                if (isNull()) {
                    return;
                }
                /**
                 * 传值跳转
                 */
                Bundle bundle = new Bundle();
                bundle.putString(Constants.USER_NAME, user_phone);
                bundle.putString(Constants.USER_CODE, code);
                startActivity(GetPassWordTwoActivity.class, bundle);
                break;

            case R.id.getIdenty://获取验证码

                if (user_phone == null || user_phone.isEmpty()) {
                    ToastUtil.ToastShort(GetPassWordOneActivity.this, "手机号不能为空");
                    return;
                }

                if (!MobileUtils.isMobile(user_phone)) {
                    ToastUtil.ToastShort(GetPassWordOneActivity.this, "手机号无效！");
                    return;
                }
                loadingDialog.show();
                //                presenter.pGetCode(user_phone);
                presenter.pGetChangeAndForgetCode(user_phone);
                break;

            case R.id.ps_for_back1://关闭
                this.finish();
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
            ToastUtil.ToastShort(GetPassWordOneActivity.this, "手机号不能为空");
            return true;
        }
        if (code == null || code.isEmpty()) {
            ToastUtil.ToastShort(GetPassWordOneActivity.this, "验证码不能为空");
            return true;
        }

        if (!isCodeClick) {
            ToastUtil.ToastShort(GetPassWordOneActivity.this, "验证码错误，请重新获取");
            return true;
        }
        return false;
    }

    @Override
    public void onForgetCodeSuccess(Object obj) {
        loadingDialog.dismiss();
        // 验证码倒计时操作
        ToastUtil.ToastShort(GetPassWordOneActivity.this, "发送成功，请稍后查看短信！");
        isCodeClick = true;
        button.start(this, 45);
    }

    @Override
    public void onForgetCodeFailed(int code, String msg, Exception e) {
        loadingDialog.dismiss();
        if (code == -1) {
            ToastUtil.ToastShort(this, msg);
        } else if (code == 401) {
            ToastUtil.ToastShort(this, msg);
            MLog.e(msg, e.toString());
        } else {
            ToastUtil.ToastShort(this, msg);
            MLog.e(msg, e.toString());
        }
    }

    @Override
    public void timeCountdown(int time) {
        button.setText("倒计时：" + time);
    }

    @Override
    public void stop() {
        button.setText(getResources().getString(R.string.get_identy));

    }
}
