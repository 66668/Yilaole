package com.yilaole.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.base.BaseActivity;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.mine.DoorAssessDetailBean;
import com.yilaole.http.cache.ACache;
import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.presenter.MineDoorAssessDetailPresenterImpl;
import com.yilaole.utils.MLog;
import com.yilaole.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的-上门评估详情
 */

public class MineDoorAssessDetailActivity extends BaseActivity implements OnCommonListener {

    //
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.layout_back)
    RelativeLayout layout_back;

    private MineDoorAssessDetailPresenterImpl presenter;
    private ACache aCache;
    private String token;
    private int doorId;
    private DoorAssessDetailBean bean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mine_doorassess_detail);
        ButterKnife.bind(this);
        initMyView();
        loadDetailData();
    }

    private void initMyView() {
        getIntentData();
        tv_title.setText(getResources().getString(R.string.door_institute_title));
        presenter = new MineDoorAssessDetailPresenterImpl(this, this);
        aCache = ACache.get(this);
        token = aCache.getAsString(Constants.TOKEN);
    }

    private void getIntentData() {
        doorId = getIntent().getExtras().getInt("id");
        MLog.d("上门评估传参", doorId);
    }

    /**
     * 显示
     */
    private void initShow() {

    }

    private void loadDetailData() {
        loadingDialog.show();
        presenter.pLoadDetailData(token, doorId);

    }

    @OnClick({R.id.layout_back})
    public void onClickListenter(View view) {
        switch (view.getId()) {
            case R.id.layout_back://后退
                this.finish();
                break;
        }
    }


    @Override
    public void onDataSuccess(Object obj) {
        bean = (DoorAssessDetailBean) obj;
        loadingDialog.dismiss();
        initShow();
    }

    @Override
    public void onDataFailed(int code,String msg, Exception e) {
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