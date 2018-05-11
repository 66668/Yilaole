package com.yilaole.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.adapter.institution.InstituteDetailServiceNotesRecylerAdapter;
import com.yilaole.base.BaseActivity;
import com.yilaole.bean.institution.detail.ServiceNotesBean;
import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.presenter.DetailServiceNotesPresenterImpl;
import com.yilaole.utils.MLog;
import com.yilaole.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 服务须知
 */

public class DetailServiceNotesActivity extends BaseActivity implements OnCommonListener {

    //
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.layout_back)
    RelativeLayout layout_back;

    //服务须知
    @BindView(R.id.tv_service_content)
    WebView tv_service_content;

    //服务须知
    @BindView(R.id.service_recyclerView)
    RecyclerView service_recyclerView;

    DetailServiceNotesPresenterImpl presenter;
    ServiceNotesBean bean;
    InstituteDetailServiceNotesRecylerAdapter adapter;
    int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_detail_service_notes);
        ButterKnife.bind(this);
        initMyView();
        getData();
    }


    private void initShow() {
        //适配 比较简单的适配
        adapter.setDataList(bean.getService());
        GridLayoutManager manager = new GridLayoutManager(DetailServiceNotesActivity.this, 3);//设置3列
        service_recyclerView.setLayoutManager(manager);
        service_recyclerView.setAdapter(adapter);
        //文本显示
        tv_service_content.loadDataWithBaseURL(null, bean.getLivenotice(), "text/html", "utf-8", null);

    }

    private void initMyView() {
        tv_title.setText(getResources().getString(R.string.instite_detail_notes));
        presenter = new DetailServiceNotesPresenterImpl(DetailServiceNotesActivity.this, this);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        adapter = new InstituteDetailServiceNotesRecylerAdapter(this);
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
        bean = (ServiceNotesBean) obj;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tv_service_content != null) {
            tv_service_content.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            tv_service_content.clearHistory();

            tv_service_content.removeView(tv_service_content);
            tv_service_content.destroy();
            tv_service_content = null;
        }
    }
}
