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
import com.yilaole.adapter.institution.InstitutionDetailCommentRecylerAdapter;
import com.yilaole.base.BaseActivity;
import com.yilaole.base.adapterbase.BaseQuickAdapter;
import com.yilaole.bean.institution.detail.DetailCommentBean;
import com.yilaole.inter_face.ilistener.OnMoreAndRefreshListener;
import com.yilaole.presenter.DetailUserCommentPresenterImpl;
import com.yilaole.utils.MLog;
import com.yilaole.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 机构-用户点评list
 */

public class DetailUserCommentActivity extends BaseActivity implements OnMoreAndRefreshListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    //
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.layout_back)
    RelativeLayout layout_back;
    //
    @BindView(R.id.qualification_recyclerView)
    RecyclerView qualification_recyclerView;

    @BindView(R.id.comment_swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private DetailUserCommentPresenterImpl presenter;
    private List<DetailCommentBean> commentData;
    private InstitutionDetailCommentRecylerAdapter commentRecylerAdapter;//评论适配
    LinearLayoutManager layoutManager;
    private int id;
    private int totol = 0;
    private int size = 20;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_detail_comment_list);
        ButterKnife.bind(this);

        initMyView();
        initRecycler();
        getData();
    }


    private void initMyView() {
        tv_title.setText(getResources().getString(R.string.instite_detail_yonghudianping));
        presenter = new DetailUserCommentPresenterImpl(DetailUserCommentActivity.this, this);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
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
        qualification_recyclerView.setLayoutManager(layoutManager);
    }

    @OnClick({R.id.layout_back})
    public void onItemClick(View view) {
        switch (view.getId()) {
            case R.id.layout_back:
                this.finish();
                break;
        }

    }

    /**
     * =============================================================================================================
     * =========================================================显示====================================================
     * =============================================================================================================
     */
    /**
     * 初始设置
     */
    private void initCommentShow() {
        commentRecylerAdapter = new InstitutionDetailCommentRecylerAdapter(this, commentData);
        commentRecylerAdapter.setOnLoadMoreListener(this, qualification_recyclerView);
        qualification_recyclerView.setAdapter(commentRecylerAdapter);

        //
        totol = commentRecylerAdapter.getData().size();
        size = 10;//之后加载每次10条

        if (commentData.size() <= 0) {
            //初始化就没有数据，可以设置空白背景
            //            recylerAdapter.setEmptyView();
            commentRecylerAdapter.setEnableLoadMore(false);//不允许加载(没有footer提示)
        } else if (commentData.size() < 20) {
            //            recylerAdapter.setEnableLoadMore(false);//不允许加载(没有footer提示)
            //数据全部加载完毕，显示 没有更多数据
            commentRecylerAdapter.loadMoreEnd();
        } else {
            commentRecylerAdapter.setEnableLoadMore(true);//允许加载
        }
    }

    /**
     * 刷新
     */
    private void initRefreshShow() {
        //
        commentRecylerAdapter.setNewData(commentData);
        totol = commentRecylerAdapter.getData().size();
        size = 10;//之后加载每次10条

        //根据数据判断后续是否需要加载
        if (commentData.size() <= 0) {
            //初始化就没有数据，可以设置空白背景
            //            recylerAdapter.setEmptyView();
            commentRecylerAdapter.setEnableLoadMore(false);//不允许加载(没有footer提示)
        } else if (commentData.size() < 20) {
            //            recylerAdapter.setEnableLoadMore(false);//不允许加载(没有footer提示)
            //数据全部加载完毕，显示 没有更多数据
            commentRecylerAdapter.loadMoreEnd();
        } else {
            commentRecylerAdapter.setEnableLoadMore(true);//允许加载
        }
    }

    private void initMoreShow() {

        //拼接数据
        commentRecylerAdapter.addData(commentData);
        totol = commentRecylerAdapter.getData().size();

        //根据数据判断后续是否需要加载
        if (commentData.size() <= 0) {
            //数据全部加载完毕，再次加载无加载footer
            commentRecylerAdapter.loadMoreEnd();
        } else if (commentData.size() < 10 && commentData.size() > 0) {
            //数据全部加载完毕，显示 没有更多数据
            commentRecylerAdapter.loadMoreEnd();
        } else {
            //本次加载结束，再次加载仍可用
            commentRecylerAdapter.loadMoreComplete();
        }
    }

    /**
     * =============================================================================================================
     * =========================================================数据接口====================================================
     * =============================================================================================================
     */
    @Override
    public void onLoadMoreRequested() {
        moreData();
    }

    @Override
    public void onRefresh() {
        totol = 0;
        size = 20;
        refreshData();
    }

    private void getData() {
        loadingDialog.show();
        presenter.pLoadData(id, totol, size);
    }

    private void moreData() {
        presenter.pMoreData(id, totol, size);
    }

    private void refreshData() {
        swipeRefreshLayout.setRefreshing(true);
        presenter.pRefreshData(id, totol, size);
    }


    /**
     * =============================================================================================================
     * ===================================================接口回调==========================================================
     * =============================================================================================================
     */
    @Override
    public void onListSuccess(Object bean) {
        loadingDialog.dismiss();
        commentData = (List<DetailCommentBean>) bean;
        initCommentShow();
    }

    @Override
    public void onListFailed(int code, String msg, Exception e) {
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
    public void onRefreshSuccess(Object bean) {
        //关闭刷新动画
        swipeRefreshLayout.setRefreshing(false);
        commentData = (List<DetailCommentBean>) bean;
        initRefreshShow();
    }

    @Override
    public void onRefreshFailed(int code, String msg, Exception e) {
        //关闭刷新动画
        swipeRefreshLayout.setRefreshing(false);
        if (code == 404) {
            commentData = new ArrayList<>();
            initRefreshShow();
        } else {
            commentData = new ArrayList<>();
            initRefreshShow();
            MLog.e(msg, e.toString());
        }
    }

    @Override
    public void onMoreSuccess(Object bean) {
        commentData = (List<DetailCommentBean>) bean;
        initMoreShow();
    }

    @Override
    public void onMoreFailed(int code, String msg, Exception e) {
        if (code == 404) {
            commentData = new ArrayList<>();
            initMoreShow();
        } else {
            MLog.e(msg, e.toString());
        }

    }


}
