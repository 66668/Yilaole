package com.yilaole.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.base.BaseActivity;
import com.yilaole.base.app.Constants;
import com.yilaole.http.cache.ACache;
import com.yilaole.http.rx.RxBus;
import com.yilaole.http.rx.RxBusBaseMessage;
import com.yilaole.http.rx.RxCodeConstants;
import com.yilaole.inter_face.ilistener.OnChangeNameListener;
import com.yilaole.inter_face.ipresenter.IChangeNamePresenter;
import com.yilaole.presenter.MineNamePhotoPresenterImpl;
import com.yilaole.save.SPUtil;
import com.yilaole.utils.MLog;
import com.yilaole.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 个人中心-修改昵称
 */

public class MineChangeNameActivity extends BaseActivity implements OnChangeNameListener {

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

    //old昵称
    @BindView(R.id.tv_oldName)
    TextView tv_oldName;


    //昵称
    @BindView(R.id.et_name)
    EditText et_name;


    private ACache aCache;
    private String token;
    private String newName;
    private IChangeNamePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mine_changename);
        ButterKnife.bind(this);
        layout_input.setVisibility(View.VISIBLE);
        layout_success.setVisibility(View.INVISIBLE);
        initMyView();
    }


    private void initMyView() {
        tv_title.setText(getResources().getString(R.string.mine_center_name));
        tv_oldName.setText("昵称： "+SPUtil.getUserName());
        presenter = new MineNamePhotoPresenterImpl(this, this);
        aCache = ACache.get(this);
        token = aCache.getAsString(Constants.TOKEN);
    }

    /**
     * 监听
     *
     * @param view
     */
    @OnClick({R.id.layout_back, R.id.btn_sure})
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

        }

    }

    private void getPsData() {
        newName = et_name.getText().toString();
    }

    private boolean isNullOrIsError() {
        //非空
        if (newName.isEmpty()) {
            ToastUtil.ToastShort(this, "新昵称不能为空！");
            return true;
        }
        //相同

        if (SPUtil.getUserName().equals(newName)) {
            ToastUtil.ToastShort(this, "新昵称不能和旧昵称相同！");
            return true;
        }


        return false;

    }

    /**
     *
     */
    private void pPostData() {
        loadingDialog.show();
        presenter.pChangeNamePost(token, newName);
    }


    @Override
    public void onChangeNameSuccess(Object obj) {
        loadingDialog.dismiss();
        ToastUtil.ToastShort(this, "修改成功！");
        //保存新昵称
        SPUtil.setUserName(newName);
        //修改样式
        layout_input.setVisibility(View.INVISIBLE);
        layout_success.setVisibility(View.VISIBLE);
        btn_sure.setClickable(false);
        btn_sure.setEnabled(false);

        //通知我的界面修改昵称
        RxBus.getDefault().post(RxCodeConstants.NAME2CENTER, new RxBusBaseMessage());
    }

    @Override
    public void onChangeNameFailed(int code,String msg, Exception e) {
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
