package com.yilaole.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.base.BaseActivity;
import com.yilaole.bean.institution.detail.TravalLineBean;
import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.presenter.DetailTravalLinePresenterImpl;
import com.yilaole.utils.MLog;
import com.yilaole.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 360全景
 */

public class DetailFullViewActivity extends BaseActivity implements OnCommonListener {

    //
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.layout_back)
    RelativeLayout layout_back;


    DetailTravalLinePresenterImpl presenter;
    List<TravalLineBean> dataList;
    int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_detail_fullview);
        ButterKnife.bind(this);
        initMyView();
        //        getData();
    }

    private void initShow() {
        //

    }

    private void initMyView() {
        tv_title.setText(getResources().getString(R.string.instite_detail_quanjing));
        presenter = new DetailTravalLinePresenterImpl(DetailFullViewActivity.this, this);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
    }

    @OnClick({R.id.layout_back})
    public void onItemClick(View view) {
        switch (view.getId()) {
            case R.id.layout_back:
                this.finish();
                break;
        }

    }

    private void getData() {
        loadingDialog.show();
        presenter.pLoadData(id);
    }

    @Override
    public void onDataSuccess(Object obj) {
        loadingDialog.dismiss();
        dataList = (List<TravalLineBean>) obj;
        initShow();
    }

    @Override
    public void onDataFailed(int code,String msg, Exception e) {
        loadingDialog.dismiss();

        if (code == -1) {
            ToastUtil.ToastShort(this, msg);
        } else if (code == 500) {
            ToastUtil.ToastShort(this, "暂无该服务，请查看机构其他服务！");
            MLog.e(msg, e.toString());
        } else {
            ToastUtil.ToastShort(this, msg);
            MLog.e(msg, e.toString());
        }
    }
}
