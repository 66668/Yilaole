package com.yilaole.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.yilaole.R;
import com.yilaole.adapter.assess.AssessMainRecyclerAdapter;
import com.yilaole.base.BaseActivity;
import com.yilaole.base.adapterbase.BaseQuickAdapter;
import com.yilaole.bean.assess.AssessOldBean;
import com.yilaole.utils.MLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 在线评估主页
 */

public class AssessMainActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.assess_recyclerView)
    RecyclerView recyclerView;

    LinearLayoutManager assessLayoutManager;
    AssessMainRecyclerAdapter recyclerViewAdapter;
    List<AssessOldBean> ListData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_assess);
        ButterKnife.bind(this);

        initMyView();

        loadData();
    }

    /**
     * 初始化相关类
     */
    private void initMyView() {
        assessLayoutManager = new LinearLayoutManager(this);
        assessLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);//竖屏展示

    }

    /**
     * 数据加载
     */
    private void loadData() {
        Data();
        initListData();
    }

    private void initListData() {

        //获取到数据后的操作
        recyclerView.setLayoutManager(assessLayoutManager);
        recyclerViewAdapter = new AssessMainRecyclerAdapter(this, ListData);
        recyclerView.setAdapter(recyclerViewAdapter);

        //footer
        View footerView = getFooterView(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MLog.ToastShort(AssessMainActivity.this, "可以添加一条数据");
            }
        });
        recyclerViewAdapter.addFooterView(footerView, 0);

        //header
        View headerView = getHeaderView(0, null);
        recyclerViewAdapter.addHeaderView(headerView);

        recyclerViewAdapter.setOnItemChildClickListener(this);
    }


    /**
     * 添加footer布局
     *
     * @param type
     * @param listener
     * @return
     */
    private View getFooterView(int type, View.OnClickListener listener) {
        View view = getLayoutInflater().inflate(R.layout.act_assess_recycler_footer, (ViewGroup) recyclerView.getParent(), false);
        view.setOnClickListener(listener);
        return view;
    }

    /**
     * 添加header布局
     *
     * @param type
     * @param listener
     * @return
     */
    private View getHeaderView(int type, View.OnClickListener listener) {
        View view = getLayoutInflater().inflate(R.layout.act_assess_recycler_header, (ViewGroup) recyclerView.getParent(), false);
        view.setOnClickListener(listener);
        return view;
    }


    /**
     * 假数据
     */

    private void Data() {
        ListData = new ArrayList<>();
        //假数据
        ListData.add(new AssessOldBean("adffadsg", "男", "https://gw.alicdn.com/imgextra/i4/140/TB2lWFJgmFjpuFjSspbXXXagVXa_!!140-0-yamato.jpg_q50.jpg", "1990-10-05"));
        ListData.add(new AssessOldBean("kjagojgaojgdksag", "男", "https://gw.alicdn.com/imgextra/i4/140/TB2lWFJgmFjpuFjSspbXXXagVXa_!!140-0-yamato.jpg_q50.jpg", "1990-10-05"));
        ListData.add(new AssessOldBean("kjagojggaaojgdksag", "男", "https://gw.alicdn.com/imgextra/i4/140/TB2lWFJgmFjpuFjSspbXXXagVXa_!!140-0-yamato.jpg_q50.jpg", "1990-10-05"));
        ListData.add(new AssessOldBean("fdddfaf", "男", "https://gw.alicdn.com/imgextra/i4/140/TB2lWFJgmFjpuFjSspbXXXagVXa_!!140-0-yamato.jpg_q50.jpg", "1990-10-05"));
        ListData.add(new AssessOldBean("kjagojgaojgdksag", "男", "https://gw.alicdn.com/imgextra/i4/140/TB2lWFJgmFjpuFjSspbXXXagVXa_!!140-0-yamato.jpg_q50.jpg", "1990-10-05"));
        ListData.add(new AssessOldBean("cvgfdgsah", "男", "https://gw.alicdn.com/imgextra/i4/140/TB2lWFJgmFjpuFjSspbXXXagVXa_!!140-0-yamato.jpg_q50.jpg", "1990-10-05"));
        ListData.add(new AssessOldBean("fgaf", "女", "https://gw.alicdn.com/imgextra/i4/140/TB2lWFJgmFjpuFjSspbXXXagVXa_!!140-0-yamato.jpg_q50.jpg", "1990-10-05"));
    }


    /**
     * item点击监听
     *
     * @param view
     */
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.tv_assess_online:
                MLog.ToastShort(AssessMainActivity.this, "这是跳转");
                break;

            case R.id.img_assess:

                break;

        }
    }

}
