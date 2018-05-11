package com.yilaole.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.yilaole.R;
import com.yilaole.base.BaseLogActivity;
import com.yilaole.base.app.Constants;
import com.yilaole.base.app.MyApplication;
import com.yilaole.http.rx.RxBus;
import com.yilaole.http.rx.RxBusBaseMessage;
import com.yilaole.http.rx.RxCodeConstants;
import com.yilaole.inter_face.ilistener.OnForgetPostListener;
import com.yilaole.inter_face.ipresenter.IForgetAndChangePsPresenter;
import com.yilaole.presenter.LogPresenterImpl;
import com.yilaole.save.SPUtil;
import com.yilaole.utils.MLog;
import com.yilaole.utils.ToastUtil;
import com.yilaole.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 找回密码2界面
 */

public class GetPassWordTwoActivity extends BaseLogActivity implements OnForgetPostListener {

    @BindView(R.id.tv_user_ps)
    EditText tv_user_ps;

    @BindView(R.id.img_watch)
    ImageView img_watch;

    private String userPhone;
    private String code;
    private String password;
    private IForgetAndChangePsPresenter presenter;
    private boolean isWatch = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_getps2);
        ButterKnife.bind(this);
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

    /**
     *
     */
    private void initMyView() {
        getIntentData();
        //密码不可见初始化
        isWatch = false;
        tv_user_ps.setTransformationMethod(PasswordTransformationMethod.getInstance());

        presenter = new LogPresenterImpl(this, this);
    }

    /**
     * 获取跳转数据
     */
    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        userPhone = (String) bundle.get(Constants.USER_NAME);
        code = (String) bundle.get(Constants.USER_CODE);
    }

    private void initListener() {
        tv_user_ps.addTextChangedListener(new TextWatcher() {
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
    }

    @OnClick({R.id.btn_done, R.id.ps_for_back2, R.id.img_watch})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_done://完成

                if (isNull()) {
                    return;
                }

                if (!Utils.isPassword(password)) {
                    ToastUtil.toastLong(GetPassWordTwoActivity.this, "密码只支持6-16位的字母、数字、下划线格式！");
                    return;

                }
                loadingDialog.show();
                presenter.pForgetAndChangePost(userPhone, password, code);
                break;
            case R.id.ps_for_back2://关闭
                this.finish();
                break;
            case R.id.img_watch://
                if (!isWatch) {
                    //密码可见
                    tv_user_ps.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isWatch = true;
                } else {
                    //密码不可见
                    tv_user_ps.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isWatch = false;
                }
                break;

        }
    }

    private boolean isNull() {

        if (password.isEmpty()) {
            ToastUtil.ToastShort(this, "密码不能为空");
            return true;
        }

        return false;
    }

    @Override
    public void onForgetAndChangePostSuccess(Object obj) {
        loadingDialog.dismiss();
        //保存账号,为登录界面保存手机号
        SPUtil.setUserPhone(userPhone);
        //清空保存的密码
        SPUtil.setPassword("");

        ToastUtil.ToastShort(this, "密码修改成功,请重新登录！");

        //通知 我的界面，更新登录状态界面
        RxBus.getDefault().post(RxCodeConstants.FORGET2LOG, new RxBusBaseMessage());

        //跳转登录界面,遍历关闭打开的多个界面，回到登录界面
        startActivity(LoginActivity.class);
        MyApplication.getInstance().closeAllActOFSome();
    }

    @Override
    public void onForgetAndChangePostFailed(int code, String msg, Exception e) {
        loadingDialog.dismiss();
        if (code == -1) {
            ToastUtil.ToastShort(this, msg);
        } else {
            ToastUtil.ToastShort(this, msg);
            MLog.e(msg, e.toString());
        }
    }
}
