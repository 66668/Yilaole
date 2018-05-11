package com.yilaole.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.adapter.institution.DetailBigImgRecylerAdapter;
import com.yilaole.base.BaseActivity;
import com.yilaole.bean.institution.detail.InstituteDetailBean;
import com.yilaole.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.yilaole.gallery.HackyViewPager;

/**
 * 更多图片
 * 大图单张预览
 */

public class DetailGalleryBigActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    //
    @BindView(R.id.tv_code)
    TextView tv_code;

    @BindView(R.id.layout_back)
    RelativeLayout layout_back;

    //
    @BindView(R.id.img_more)
    ImageView img_more;
    //
    @BindView(R.id.tv_detailContent)
    TextView tv_detailContent;
    //
    @BindView(R.id.viewPager)
    HackyViewPager viewPager;

    private List<InstituteDetailBean.IMG> dataList;
    private int selectInt;//当前选中页
    private int pageSize;//总页数
    private DetailBigImgRecylerAdapter adapter;//适配

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_detail_gallery_big);
        ButterKnife.bind(this);
        initMyView();
        initShow();
    }


    private void initShow() {
        adapter = new DetailBigImgRecylerAdapter(this, dataList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(selectInt);
        viewPager.addOnPageChangeListener(this);
        viewPager.setEnabled(false);

        tv_code.setText((selectInt + 1) + "/" + adapter.getCount());
        tv_detailContent.setText("机构图片预览");
    }

    private void initMyView() {
        //获取跳转值
        Bundle bundle = getIntent().getExtras();
        selectInt = bundle.getInt("selectInt");
        dataList = (List<InstituteDetailBean.IMG>) bundle.getSerializable("list");
    }

    @OnClick({R.id.layout_back, R.id.img_more})
    public void onItemClick(View view) {
        switch (view.getId()) {
            case R.id.layout_back:
                this.finish();
                break;
            case R.id.img_more:
                ToastUtil.ToastShort(this, "暂无功能");
                break;
        }

    }


    /**
     * viewpager滑动监听
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tv_code.setText((position + 1) + "/" + adapter.getCount());
        tv_detailContent.setText("机构图片预览");
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
