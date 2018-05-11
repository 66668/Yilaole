package com.yilaole.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.adapter.mine.MineCommentListRecylerAdapter;
import com.yilaole.base.BaseActivity;
import com.yilaole.base.adapterbase.BaseQuickAdapter;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.mine.MineCommentItemBean;
import com.yilaole.http.cache.ACache;
import com.yilaole.inter_face.ilistener.OnMoreAndRefreshListener;
import com.yilaole.presenter.MineCommentListPresenterImpl;
import com.yilaole.utils.MLog;
import com.yilaole.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人 我的评价list
 */

public class MineCommentListActivity extends BaseActivity implements OnMoreAndRefreshListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    //
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.layout_back)
    RelativeLayout layout_back;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private ACache aCache;
    private String token;
    private int total = 0;
    private int size = 20;
    private List<MineCommentItemBean> listData;
    private MineCommentListRecylerAdapter adapter;//适配
    private MineCommentListPresenterImpl presenter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mine_commentlist);
        ButterKnife.bind(this);
        initMyView();
        initRecycler();
        loadData();
        initListener();
    }


    private void initListener() {


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

    private void initMyView() {
        tv_title.setText(getResources().getString(R.string.mine_appraise));
        aCache = ACache.get(this);
        token = aCache.getAsString(Constants.TOKEN);
        presenter = new MineCommentListPresenterImpl(this, this);
    }

    /**
     * 监听
     *
     * @param view
     */
    @OnClick({R.id.layout_back})
    public void onItemClick(View view) {
        switch (view.getId()) {
            case R.id.layout_back:
                this.finish();
                break;

        }

    }
    /**
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     */

    /**
     * 数据显示
     */
    private void initShow() {

        adapter = new MineCommentListRecylerAdapter(this, listData);
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this, recyclerView);
        //
        total = adapter.getData().size();
        size = 10;//之后加载每次10条

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
        //机构跳转
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.layout_institution:

                        MineCommentItemBean bean = (MineCommentItemBean) adapter.getItem(position);
                        int id = bean.getAgencyid();
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", id);
                        startActivity(InstitutionDetailActivity.class, bundle);

                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initRefreshShow() {
        adapter.setNewData(listData);
        total = adapter.getData().size();
        size = 10;//之后加载每次10条

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

    /**
     * 修改数据源 后台没给加type判断，移动端自己加判断参数
     */

    private void setDataType() {
        for (MineCommentItemBean bean : listData) {
            if (bean.getChild() == null || bean.getChild().getContent() == null || bean.getChild().getContent().isEmpty()) {
                bean.setItemType(Constants.MultiInstituteType.TYPE1);
            } else {
                bean.setItemType(Constants.MultiInstituteType.TYPE2);
            }
        }
    }

    @Override
    public void onRefresh() {
        total = 0;
        size = 20;
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
        presenter.pLoadData(token, total, size);
    }

    //加载
    private void moreData() {
        // TODO: 2017/10/24 token问题
        if (token.isEmpty()) {
            ToastUtil.ToastShort(this, "token失效，请登录获取数据");
            return;
        }
        loadingDialog.show();
        presenter.pMoreData(token, total, size);
    }

    //刷新
    private void refreshData() {
        // TODO: 2017/10/24 token问题
        if (token.isEmpty()) {
            ToastUtil.ToastShort(this, "token失效，请登录获取数据");
            return;
        }

        swipeRefreshLayout.setRefreshing(true);
        presenter.pRefreshData(token, total, size);
    }

    /**
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     */


    @Override
    public void onListSuccess(Object bean) {
        loadingDialog.dismiss();
        listData = (List<MineCommentItemBean>) bean;
        setDataType();
        initShow();
    }

    @Override
    public void onListFailed(int code, String msg, Exception e) {
        loadingDialog.dismiss();
        if (code == -1) {
            ToastUtil.ToastShort(this, msg);
        } else if (code == 400) {
            ToastUtil.ToastShort(this, msg);
            MLog.e(msg, e.toString());
        } else {
            ToastUtil.ToastShort(this, msg);
            MLog.e(msg, e.toString());
        }
    }

    @Override
    public void onRefreshSuccess(Object bean) {
        listData = (List<MineCommentItemBean>) bean;
        setDataType();
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
        listData = (List<MineCommentItemBean>) bean;
        setDataType();
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
