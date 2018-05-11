package com.yilaole.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.adapter.institution.InstituteDetailCharacteristicRecylerAdapter;
import com.yilaole.base.BaseActivity;
import com.yilaole.bean.institution.detail.CharacteristicBean;
import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.presenter.DetailCharacteristicPresenterImpl;
import com.yilaole.utils.MLog;
import com.yilaole.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 医养特色
 */

public class DetailCharacteristicActivity extends BaseActivity implements OnCommonListener {

    //
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.layout_back)
    RelativeLayout layout_back;
    //
    @BindView(R.id.qualification_recyclerView)
    RecyclerView qualification_recyclerView;

    DetailCharacteristicPresenterImpl presenter;
    CharacteristicBean bean;
    List<CharacteristicBean.CharacteristicDetailBean> dataList;
    InstituteDetailCharacteristicRecylerAdapter adapter;
    int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_detail_characteristic);
        ButterKnife.bind(this);
        initMyView();
        getData();
    }

    private void initShow() {
        //适配 比较简单的适配
        adapter.setDataList(dataList);
        LinearLayoutManager manager = new LinearLayoutManager(DetailCharacteristicActivity.this);//设置3列
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        qualification_recyclerView.setLayoutManager(manager);
        qualification_recyclerView.setAdapter(adapter);

    }

    private void initMyView() {
        tv_title.setText(getResources().getString(R.string.instite_detail_yiyangtese));
        presenter = new DetailCharacteristicPresenterImpl(DetailCharacteristicActivity.this, this);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        adapter = new InstituteDetailCharacteristicRecylerAdapter(this);
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
        bean = (CharacteristicBean) obj;
        dataList = bean.getDetail();
        initShow();
    }

    @Override
    public void onDataFailed(int code, String msg, Exception e) {
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
