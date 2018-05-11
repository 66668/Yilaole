package com.yilaole.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yilaole.R;
import com.yilaole.adapter.news.NewsRecylerAdapter;
import com.yilaole.base.BaseFragment;
import com.yilaole.base.adapterbase.BaseQuickAdapter;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.news.NewsBean;
import com.yilaole.bean.news.NewsTabBean;
import com.yilaole.http.cache.ACache;
import com.yilaole.inter_face.ilistener.OnMoreAndRefreshListener;
import com.yilaole.inter_face.ipresenter.INewsPresenter;
import com.yilaole.presenter.NewsPresenterImpl;
import com.yilaole.save.SPUtil;
import com.yilaole.ui.NewsDetailActivity;
import com.yilaole.utils.MLog;
import com.yilaole.utils.TimeUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 咨询页子fragment
 */

public class NewsFragment extends BaseFragment implements OnMoreAndRefreshListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycleview)
    RecyclerView recycleview;

    private int mPage;
    private NewsTabBean bean;
    private int type;
    private int totol = 0;
    private int size = 50;

    private NewsRecylerAdapter adapter;
    private INewsPresenter presenter;
    private Unbinder unbinder;

    private ArrayList<NewsBean> tabDataList = new ArrayList<>();
    private boolean isPrepared = false;//结合BaseFragment的懒加载使用，标记
    private boolean isOldDayRequest;//是否是上一天
    private boolean tabDataListInitBoolean;//数据是否初始化
    private ACache maCache;//缓存类
    private boolean mIsFirst = true;

    //加载动画 结束标记
    private boolean isLoadingFinish = false;

    public static NewsFragment newInstance(int page, NewsTabBean bean) {
        Bundle args = new Bundle();
        args.putInt("ARG_PAGE", page);
        args.putSerializable("BEAN", bean);
        NewsFragment pageFragment = new NewsFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt("ARG_PAGE");
        bean = (NewsTabBean) getArguments().getSerializable("BEAN");
        type = bean.getId();
    }

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MLog.d("NewsFragment", "onCreateView");

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_news_common_rc, container, false);
            unbinder = ButterKnife.bind(this, rootView);//使用 getActivty()出错
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MLog.d("NewsFragment", "onDestroyView");

        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        //销毁的fragment的View
        if (rootView != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MLog.d("NewsFragment", "onActivityCreated");
        initMyView();
        /**
         * 因为启动时先走lazyLoad()再走onActivityCreated，
         * 所以此处要额外调用lazyLoad(),不然最初不会加载内容
         */
        lazyLoad();
    }

    private void initMyView() {
        isPrepared = true;
        presenter = new NewsPresenterImpl(getActivity(), this);
        maCache = ACache.get(getContext());
        //先获取缓存
        maCache = ACache.get(getContext());
        //
        tabDataList = (ArrayList<NewsBean>) maCache.getAsObject(Constants.NEWS_LIST + type);
    }


    @Override
    protected void lazyLoad() {
        if (!isVisible || !isPrepared) {
            return;
        }
        //获取存储的时间
        String spTime = SPUtil.getString(Constants.NEWS_CURRENT_TIME_FRAGMENT, "2017-09-01");
        if (!spTime.equals(TimeUtil.getData())) {//非当天时间，获取数据

            if (TimeUtil.isRightTime()) {//大于12：30, 网络加载
                isOldDayRequest = false;
                //
                loadData();
            } else {// 小于12：30，取缓存 没有请求前一天
                isOldDayRequest = true;// 是昨天
                getACacheData();
            }
        } else {//是当天时间
            isOldDayRequest = false;
            getACacheData();
        }

    }


    /**
     * 取缓存
     */
    private void getACacheData() {
        if (!mIsFirst) {
            return;
        }
        //
        if (tabDataList != null && tabDataList.size() > 0) {
            isLoadingFinish = true;
            initShowData();
        } else {
            tabDataListInitBoolean = false;
            loadData();
        }
    }

    private void loadData() {
        isLoadingFinish = true;
        loadingDialog.show();
        presenter.pLoadListData(totol, size, type);

    }

    //数据显示到recylerView
    private void initShowData() {
        if (tabDataListInitBoolean) {
            return;
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleview.setLayoutManager(layoutManager);
        adapter = new NewsRecylerAdapter(getActivity(), tabDataList);
        recycleview.setAdapter(adapter);

        //跳转
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                NewsBean bean = (NewsBean) adapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra(Constants.NEWS_ISHOT, false);
                intent.putExtra(Constants.NEWS_ID, bean.getId());
                intent.setClass(getActivity(), NewsDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        tabDataListInitBoolean = true;
    }

    @Override
    public void onRefresh() {

    }


    /**
     * =========================================================================
     * ===================================View接口回调======================================
     * =========================================================================
     */


    /**
     * 异步加载完 统一结束 弹窗加载
     */
    private void loadingDialogDismiss() {
        if (isLoadingFinish) {
            loadingDialog.dismiss();
        }
    }


    @Override
    public void onListSuccess(Object obj) {
        isLoadingFinish = true;
        loadingDialogDismiss();
        tabDataList = (ArrayList<NewsBean>) obj;
        //缓存
        if (tabDataList != null && tabDataList.size() > 0) {
            initShowData();
            //添加缓存
            maCache.remove(Constants.NEWS_LIST + type);
            // 缓存1h
            maCache.put(Constants.NEWS_LIST + type, tabDataList, 3600);
        } else {

        }

        //缓存当前时间
        SPUtil.putString(Constants.NEWS_CURRENT_TIME_FRAGMENT, TimeUtil.getData());
        mIsFirst = false;
    }

    @Override
    public void onListFailed(int code, String msg, Exception e) {
        isLoadingFinish = true;
        if (code == -1) {
        } else if (code == 404) {
            MLog.e(msg, e.toString());
        } else {
            MLog.e(msg, e.toString());
        }
        loadingDialogDismiss();
    }

    @Override
    public void onRefreshSuccess(Object bean) {

    }

    @Override
    public void onRefreshFailed(int code, String msg, Exception e) {

    }

    @Override
    public void onMoreSuccess(Object bean) {

    }

    @Override
    public void onMoreFailed(int code, String msg, Exception e) {

    }
}