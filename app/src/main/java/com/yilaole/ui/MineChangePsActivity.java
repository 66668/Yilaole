package com.yilaole.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.base.BaseActivity;
import com.yilaole.base.app.Constants;
import com.yilaole.http.cache.ACache;
import com.yilaole.inter_face.ilistener.OnChangePsListener;
import com.yilaole.inter_face.ipresenter.IChangePsPresenter;
import com.yilaole.presenter.LogPresenterImpl;
import com.yilaole.save.SPUtil;
import com.yilaole.utils.MLog;
import com.yilaole.utils.ToastUtil;
import com.yilaole.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 个人中心-修改密码
 */

public class MineChangePsActivity extends BaseActivity implements OnChangePsListener {

    //
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.layout_back)
    RelativeLayout layout_back;

    //输入布局
    @BindView(R.id.layout_input)
    LinearLayout layout_input;

    //成功界面
    @BindView(R.id.layout_success)
    RelativeLayout layout_success;

    //提交
    @BindView(R.id.btn_sure)
    Button btn_sure;


    @BindView(R.id.et_old)
    EditText et_old;
    @BindView(R.id.et_new)
    EditText et_new;

    @BindView(R.id.img_watch_old)
    ImageView img_watch_old;

    @BindView(R.id.img_watch_new)
    ImageView img_watch_new;


    private ACache aCache;
    private String token;
    private String oldPs;
    private String newPs;
    private IChangePsPresenter presenter;
    private boolean isWatchOld = false;
    private boolean isWatchNew = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mine_changeps);
        ButterKnife.bind(this);
        layout_input.setVisibility(View.VISIBLE);
        layout_success.setVisibility(View.INVISIBLE);
        initMyView();
    }


    private void initMyView() {
        tv_title.setText(getResources().getString(R.string.mine_center_ps));
        presenter = new LogPresenterImpl(this, this);
        aCache = ACache.get(this);
        token = aCache.getAsString(Constants.TOKEN);
        //
        isWatchOld = false;
        isWatchNew = false;
        et_old.setTransformationMethod(PasswordTransformationMethod.getInstance());
        et_new.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    /**
     * 监听
     *
     * @param view
     */
    @OnClick({R.id.layout_back, R.id.btn_sure, R.id.img_watch_new, R.id.img_watch_old})
    public void onItemClick(View view) {
        switch (view.getId()) {
            case R.id.layout_back:
                this.finish();
                break;
            case R.id.btn_sure:
                getPsData();
                if (isNullOrIsError()) {
                    return;
                }
                pPostData();
                break;
            case R.id.img_watch_old:
                if (!isWatchOld) {
                    //密码可见
                    et_old.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isWatchOld = true;
                } else {
                    //密码不可见
                    et_old.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isWatchOld = false;
                }
                break;
            case R.id.img_watch_new:
                if (!isWatchNew) {
                    //密码可见
                    et_new.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isWatchNew = true;
                } else {
                    //密码不可见
                    et_new.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isWatchNew = false;
                }
                break;
        }

    }

    private void getPsData() {
        oldPs = et_old.getText().toString();
        newPs = et_new.getText().toString();
    }

    private boolean isNullOrIsError() {
        //非空
        if (oldPs.isEmpty()) {
            ToastUtil.ToastShort(this, "原密码不能为空！");
            return true;
        }

        if (newPs.isEmpty()) {
            ToastUtil.ToastShort(this, "新密码不能为空！");
            return true;
        }

        if (!oldPs.equals(SPUtil.getPassword())) {
            ToastUtil.ToastShort(this, "原密码不正确！");
            return true;
        }

        if (oldPs.equals(newPs)) {
            ToastUtil.ToastShort(this, "密码不可以相同！");
            return true;
        }


        //密码规范
        if (!Utils.isPassword(newPs)) {
            ToastUtil.toastLong(this, "密码只支持6-16位的字母、数字、下划线格式！");
            return true;
        }
        return false;

    }

    /**
     *
     */
    private void pPostData() {
        loadingDialog.show();
        presenter.pChangePsPost(token, oldPs, newPs);
    }

    @Override
    public void onChangePsSuccess(Object obj) {
        loadingDialog.dismiss();
        ToastUtil.ToastShort(this, "修改成功！");
        //保存新密码
        SPUtil.setPassword(newPs);
        //修改样式
        layout_input.setVisibility(View.INVISIBLE);
        layout_success.setVisibility(View.VISIBLE);
        btn_sure.setClickable(false);
        btn_sure.setEnabled(false);
    }

    @Override
    public void onChangePsFailed(int code, String msg, Exception e) {
        loadingDialog.dismiss();
        if (code == -1) {
            ToastUtil.ToastShort(this, msg);
        } else if (code == 404) {
            ToastUtil.ToastShort(this, msg);
            MLog.e(msg, e.toString());
        } else {
            ToastUtil.ToastShort(this, msg);
            MLog.e(msg, e.toString());
        }
    }
}
