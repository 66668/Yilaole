package com.yilaole.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.adapter.institution.InstituteDetailQualificationRecylerAdapter;
import com.yilaole.base.BaseActivity;
import com.yilaole.bean.institution.detail.QualificationBean;
import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.presenter.DetailQualificationPresenterImpl;
import com.yilaole.utils.MLog;
import com.yilaole.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 机构资质
 */

public class DetailQualificationActivity extends BaseActivity implements OnCommonListener {

    //
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.layout_back)
    RelativeLayout layout_back;
    //
    @BindView(R.id.qualification_recyclerView)
    RecyclerView qualification_recyclerView;

    DetailQualificationPresenterImpl presenter;
    QualificationBean bean;
    InstituteDetailQualificationRecylerAdapter adapter;
    int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_detail_comment_list);
        ButterKnife.bind(this);
        initMyView();
        getData();
    }


    private void initShow() {
        //适配 比较简单的适配
        adapter.setDataList(bean.getImage());
        LinearLayoutManager manager = new LinearLayoutManager(DetailQualificationActivity.this);//设置3列
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        qualification_recyclerView.setLayoutManager(manager);
        qualification_recyclerView.setAdapter(adapter);

    }

    private void initMyView() {
        tv_title.setText(getResources().getString(R.string.instite_detail_jigouzizhi));
        presenter = new DetailQualificationPresenterImpl(DetailQualificationActivity.this, this);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        adapter = new InstituteDetailQualificationRecylerAdapter(this);
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
        bean = (QualificationBean) obj;
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
