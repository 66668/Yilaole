package com.yilaole.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.adapter.institution.DetailGalleryRecylerAdapter;
import com.yilaole.base.BaseActivity;
import com.yilaole.bean.institution.detail.FeesBean;
import com.yilaole.bean.institution.detail.InstituteDetailBean;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 更多图片
 * 功能：小图list预览，大图单张预览
 */

public class DetailGalleryActivity extends BaseActivity {

    //
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.layout_back)
    RelativeLayout layout_back;
    //
    @BindView(R.id.gallery_recyclerView)
    RecyclerView gallery_recyclerView;

    private FeesBean bean;
    private List<InstituteDetailBean.IMG> dataList;
    private DetailGalleryRecylerAdapter adapter;
    private int id;
    private InstituteDetailBean detailBean;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_detail_gallery);
        ButterKnife.bind(this);
        initMyView();
        initShow();
    }

    private void initShow() {
        adapter = new DetailGalleryRecylerAdapter(this, dataList);

        //适配 比较简单的适配
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        gallery_recyclerView.setLayoutManager(manager);
        gallery_recyclerView.setAdapter(adapter);

        //监听 跳转详情预览
        adapter.setOnItemClickListner(new DetailGalleryRecylerAdapter.OnItemClickListner() {
            @Override
            public void onItemClickListener(View view, int position) {

                Bundle bundle = new Bundle();
                bundle.putInt("selectInt",position);// 选中的图片position
                bundle.putSerializable("list", (Serializable) dataList);
                startActivity(DetailGalleryBigActivity.class, bundle);
            }
        });

    }

    private void initMyView() {
        tv_title.setText(getResources().getString(R.string.instite_detail_gallery));

        //获取跳转值
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        detailBean = (InstituteDetailBean) bundle.getSerializable("bean");
        dataList = detailBean.getImage();
    }

    @OnClick({R.id.layout_back})
    public void onItemClick(View view) {
        switch (view.getId()) {
            case R.id.layout_back:
                this.finish();
                break;
        }

    }


}
