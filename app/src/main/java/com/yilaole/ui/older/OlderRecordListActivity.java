package com.yilaole.ui.older;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.adapter.mine.MineAppointVisitRecyclerViewAdapter2;
import com.yilaole.base.BaseActivity;
import com.yilaole.base.adapterbase.BaseQuickAdapter;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.mine.AppointVisitItemBean;
import com.yilaole.http.cache.ACache;
import com.yilaole.inter_face.ilistener.OnMoreAndRefreshListener;
import com.yilaole.presenter.MineAppointVisitListPresenterImpl;
import com.yilaole.ui.MineAppointVisitDetailActivity;
import com.yilaole.ui.MineCommentCreateActivity;
import com.yilaole.utils.MLog;
import com.yilaole.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的-长者档案list
 */

public class OlderRecordListActivity extends BaseActivity implements OnMoreAndRefreshListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    //
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.iti)
    RelativeLayout iti;

    @BindView(R.id.layout_right)
    RelativeLayout layout_right;
    @BindView(R.id.img_right)
    ImageView img_right;

    @BindView(R.id.layout_back)
    RelativeLayout layout_back;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    LinearLayoutManager layoutManager;
    private List<AppointVisitItemBean> listData;
    private MineAppointVisitRecyclerViewAdapter2 adapter;
    private MineAppointVisitListPresenterImpl presenter;
    private ACache aCache;
    private String token;
    private int total = 0;
    private int pageSize = 20;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mine_appoint);
        ButterKnife.bind(this);
        initMyView();
        initRecycler();
        loadData();
    }

    private void initMyView() {
        //top设置
        tv_title.setText(getResources().getString(R.string.mine_appointVisit));
        img_right.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_launcher));
        iti.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

        presenter = new MineAppointVisitListPresenterImpl(this, this);
        aCache = ACache.get(this);
        token = aCache.getAsString(Constants.TOKEN);
    }

    /**
     * 对swipeRefreshLayout 和 RecyclerView控件初始化操作
     */
    private void initRecycler() {

        //设置SwipeRefreshLayout样式
        swipeRefreshLayout.setOnRefreshListener(this);
        //颜色变化
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.commonColor)
                , ContextCompat.getColor(this, R.color.refresh_color2)
                , ContextCompat.getColor(this, R.color.refresh_color3)
                , ContextCompat.getColor(this, R.color.refresh_color2));
        //显示样式
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @OnClick({R.id.layout_back})
    public void onClickListenter(View view) {
        switch (view.getId()) {
            case R.id.layout_back://后退
                this.finish();
                break;
        }
    }

    /**
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     */

    private void initShow() {
        adapter = new MineAppointVisitRecyclerViewAdapter2(this, listData);
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this, recyclerView);

        //
        total = adapter.getData().size();
        pageSize = 10;//之后加载每次10条

        if (listData.size() <= 0) {
            //初始化就没有数据，可以设置空白背景
            //            recylerAdapter.setEmptyView();
            adapter.setEnableLoadMore(false);//不允许加载(没有footer提示)
        } else if (listData.size() < 20) {
            //            recylerAdapter.setEnableLoadMore(false);//不允许加载(没有footer提示)
            //数据全部加载完毕，显示 没有更多数据
            adapter.loadMoreEnd();
        } else {
            adapter.setEnableLoadMore(true);//允许加载
        }

        //跳转
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                AppointVisitItemBean bean = (AppointVisitItemBean) adapter.getItem(position);
                Bundle bundle = new Bundle();
                switch (view.getId()) {
                    case R.id.layout_item:

                        bundle.putInt("id", bean.getId());//订单id
                        startActivity(MineAppointVisitDetailActivity.class, bundle);

                        break;
                    case R.id.tv_assess:
                        AppointVisitItemBean bean1 = (AppointVisitItemBean) adapter.getItem(position);

                        bundle.putInt("bookId", bean.getId());//订单id
                        bundle.putInt("id", bean1.getAgencyid());//机构id
                        startActivity(MineCommentCreateActivity.class, bundle);
                        break;
                }
            }
        });
    }

    private void initRefreshShow() {
        adapter.setNewData(listData);
        total = adapter.getData().size();
        pageSize = 10;//之后加载每次10条

        //根据数据判断后续是否需要加载
        if (listData.size() <= 0) {
            //初始化就没有数据，可以设置空白背景
            //            recylerAdapter.setEmptyView();
            adapter.setEnableLoadMore(false);//不允许加载(没有footer提示)
        } else if (listData.size() < 20) {
            //            recylerAdapter.setEnableLoadMore(false);//不允许加载(没有footer提示)
            //数据全部加载完毕，显示 没有更多数据
            adapter.loadMoreEnd();
        } else {
            adapter.setEnableLoadMore(true);//允许加载
        }
    }

    private void initMoreShow() {
        //拼接数据
        adapter.addData(listData);
        total = adapter.getData().size();

        //根据数据判断后续是否需要加载
        if (listData.size() <= 0) {
            //数据全部加载完毕，再次加载无加载footer
            adapter.loadMoreEnd();
        } else if (listData.size() < 10 && listData.size() > 0) {
            //数据全部加载完毕，显示 没有更多数据
            adapter.loadMoreEnd();
        } else {
            //本次加载结束，再次加载仍可用
            adapter.loadMoreComplete();
        }
    }

    /**
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     */


    @Override
    public void onRefresh() {
        total = 0;
        pageSize = 20;
        refreshData();

    }

    @Override
    public void onLoadMoreRequested() {
        moreData();
    }

    /**
     * 初始加载
     */
    private void loadData() {
        // TODO: 2017/10/24 token问题
        if (token.isEmpty()) {
            ToastUtil.ToastShort(this, "token失效，请登录获取数据");
            return;
        }
        loadingDialog.show();
        presenter.pLoadData(token, total, pageSize);
    }

    //加载
    private void moreData() {
        // TODO: 2017/10/24 token问题
        if (token.isEmpty()) {
            ToastUtil.ToastShort(this, "token失效，请登录获取数据");
            return;
        }
        loadingDialog.show();
        presenter.pMoreData(token, total, pageSize);
    }

    //刷新
    private void refreshData() {
        // TODO: 2017/10/24 token问题
        if (token.isEmpty()) {
            ToastUtil.ToastShort(this, "token失效，请登录获取数据");
            return;
        }

        swipeRefreshLayout.setRefreshing(true);
        presenter.pRefreshData(token, total, pageSize);
    }

    /**
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     */
    @Override
    public void onListSuccess(Object bean) {
        loadingDialog.dismiss();
        listData = (List<AppointVisitItemBean>) bean;
        initShow();
    }

    @Override
    public void onListFailed(int code, String msg, Exception e) {
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

    @Override
    public void onRefreshSuccess(Object bean) {
        listData = (List<AppointVisitItemBean>) bean;
        swipeRefreshLayout.setRefreshing(false);
        initRefreshShow();
    }

    @Override
    public void onRefreshFailed(int code, String msg, Exception e) {
        swipeRefreshLayout.setRefreshing(false);
        if (code == 404) {
            listData = new ArrayList<>();
            initRefreshShow();
            ToastUtil.ToastShort(this, msg);
        } else {
            listData = new ArrayList<>();
            initRefreshShow();
            MLog.e(msg, e.toString());
        }

    }

    @Override
    public void onMoreSuccess(Object bean) {
        listData = (List<AppointVisitItemBean>) bean;
        initMoreShow();
    }

    @Override
    public void onMoreFailed(int code, String msg, Exception e) {
        if (code == 404) {
            listData = new ArrayList<>();
            initMoreShow();
            ToastUtil.ToastShort(this, msg);
        } else {
            listData = new ArrayList<>();
            initMoreShow();
            MLog.e(msg, e.toString());
        }

    }


}