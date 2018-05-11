package com.yilaole.ui.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.adapter.news.NewsBannerAdapter;
import com.yilaole.adapter.news.NewsFragmentPagerAdapter;
import com.yilaole.base.BaseFragment;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.news.NewsBannerBean;
import com.yilaole.bean.news.NewsTabBean;
import com.yilaole.http.cache.ACache;
import com.yilaole.inter_face.ipresenter.INavNewsPresenter;
import com.yilaole.inter_face.iview.INavNewsView;
import com.yilaole.presenter.NewsPresenterImpl;
import com.yilaole.save.SPUtil;
import com.yilaole.utils.DpUtils;
import com.yilaole.utils.MLog;
import com.yilaole.utils.TimeUtil;
import com.yilaole.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 资讯页根fragment
 */

public class NavNewsFragment extends BaseFragment implements INavNewsView {


    //标题
    @BindView(R.id.tv_news_title)
    TextView tv_news_title;

    //投稿
    @BindView(R.id.tv_news_submit)
    TextView tv_news_submit;

    //轮播图
    @BindView(R.id.news_banner_viewpager)
    ViewPager news_banner_viewpager;
    @BindView(R.id.ll_index_banner)
    LinearLayout ll_index_banner;

    //嵌套fragment的tab页
    @BindView(R.id.news_tabLayout)
    TabLayout news_tabLayout;
    @BindView(R.id.news_viewpager)
    ViewPager news_viewpager;

    //顶部父布局
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    //顶部定位 搜索
    @BindView(R.id.topbar_local_search)
    RelativeLayout topbar_local_search;


    //变量
    private ArrayList<NewsBannerBean> ADList;//图片轮播数据
    private ArrayList<NewsTabBean> tabList;//资讯tab数据
    private NewsFragmentPagerAdapter pagerAdapter;//适配
    private INavNewsPresenter presenter;
    private ACache maCache;//缓存类
    private boolean ADListInitBoolean = false;//数据是否初始化到界面
    private boolean tabListInitBoolean = false;
    private boolean mIsFirst = true;
    private boolean isOldDayRequest;//是否是上一天
    private boolean isPrepared = false;//结合BaseFragment的懒加载使用，标记
    private Unbinder unbinder;

    //加载动画 判断
    private boolean isBannerFinish = false;
    private boolean isTabFinish = false;
    //    常量
    private final int NEWS_AD_RESULT = 222;

    public static NavNewsFragment newInstance() {
        NavNewsFragment fragment = new NavNewsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news2, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //轮播图
        initMyView();
        isPrepared = true;
        /**
         * 因为启动时先走lazyLoad()再走onActivityCreated，
         * 所以此处要额外调用lazyLoad(),不然最初不会加载内容
         */
        lazyLoad();
    }

    /**
     * 初始化数据
     */
    private void initMyView() {
        //先获取缓存
        maCache = ACache.get(getContext());

        // 获取缓存轮播图
        ADList = (ArrayList<NewsBannerBean>) maCache.getAsObject(Constants.NEWS_BANNER_PIC);

        // 数据
        tabList = (ArrayList<NewsTabBean>) maCache.getAsObject(Constants.NEWS_TAB);

        presenter = new NewsPresenterImpl(getActivity(), this);
    }

    @Override
    protected void lazyLoad() {
        if (!isVisible || !isPrepared) {
            return;
        }
        //获取存储的时间
        String spTime = SPUtil.getString(Constants.NEWS_CURRENT_TIME, "2017-09-01");
        if (!spTime.equals(TimeUtil.getData())) {//非当天时间，获取数据

            if (TimeUtil.isRightTime()) {//大于12：30, 网络加载
                isOldDayRequest = false;

                //加载轮播图
                loadBannerPicture();

                //加载tab
                loadFragmentTab();

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

        //轮播图
        if (ADList != null && ADList.size() > 0) {
            isBannerFinish = true;
            initBanner();
        } else {
            ADListInitBoolean = false;
            loadBannerPicture();
        }

        //tab
        if (tabList != null && tabList.size() > 0) {
            isTabFinish = true;
            initFragmentList();
        } else {
            tabListInitBoolean = false;
            loadFragmentTab();
        }
    }

    @OnClick({R.id.tv_news_submit})
    public void onClicksss(View view) {
        switch (view.getId()) {
            case R.id.tv_news_submit:
                ToastUtil.toastLong(getActivity(), getActivity().getResources().getString(R.string.tougao));

                break;
        }

    }

    /**
     * 轮播图
     */
    private void initBanner() {
        if (ADListInitBoolean) {
            return;
        }
        //viewpager监听
        news_banner_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                refreshPoint(position % ADList.size());
                if (mHandler.hasMessages(NEWS_AD_RESULT)) {
                    mHandler.removeMessages(NEWS_AD_RESULT);
                }
                mHandler.sendEmptyMessageDelayed(NEWS_AD_RESULT, 3000);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (ViewPager.SCROLL_STATE_DRAGGING == state
                        && mHandler.hasMessages(NEWS_AD_RESULT)) {
                    mHandler.removeMessages(NEWS_AD_RESULT);
                }
            }
        });

        NewsBannerAdapter adapter = new NewsBannerAdapter(getActivity(), ADList); //同首页轮播适配
        news_banner_viewpager.setAdapter(adapter);
        addIndicatorImageViews(ADList.size());

        news_banner_viewpager.setCurrentItem(ADList.size() * 1000, false);
        //自动轮播线程
        mHandler.sendEmptyMessageDelayed(NEWS_AD_RESULT, 5000);//3s一轮播

        ADListInitBoolean = true;
    }

    // 添加指示图标
    private void addIndicatorImageViews(int size) {
        ll_index_banner.removeAllViews();
        for (int i = 0; i < size; i++) {
            ImageView iv = new ImageView(getActivity());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DpUtils.dip2px(getActivity(), 5), DpUtils.dip2px(getActivity(), 5));
            if (i != 0) {
                lp.leftMargin = DpUtils.dip2px(getActivity(), 7);
            }
            iv.setLayoutParams(lp);
            iv.setBackgroundResource(R.drawable.xml_round_orange_grey_sel);
            iv.setEnabled(false);
            if (i == 0) {
                iv.setEnabled(true);
            }
            ll_index_banner.addView(iv);
        }
    }

    // 为ViewPager设置监听器
    private void refreshPoint(int position) {
        if (ll_index_banner != null) {
            for (int i = 0; i < ll_index_banner.getChildCount(); i++) {
                ll_index_banner.getChildAt(i).setEnabled(false);

                if (i == position) {
                    ll_index_banner.getChildAt(i).setEnabled(true);
                }
            }
        }
    }

    @Override
    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case NEWS_AD_RESULT://图片轮播
                news_banner_viewpager.setCurrentItem(news_banner_viewpager.getCurrentItem() + 1, true);
                break;
        }
        super.handleMessage(msg);

    }


    /**
     * 02嵌套fragment的tab标题显示
     */
    private void initFragmentList() {
        if (tabListInitBoolean) {
            return;
        }
        //方式1嵌套fragment
        //        viewpagerAdapter = new HomeInnerFrgPagerAdapter(getChildFragmentManager());
        //        viewpagerAdapter.addFragment(NavHelpFragment.newInstance(), "热门精品");
        //        viewpagerAdapter.addFragment(NavHelpFragment.newInstance(), "特价优惠");
        //        fragment_viewpager.setAdapter(viewpagerAdapter);
        //
        //        tabLayout.addTab(tabLayout.newTab().setText("热门精品"));
        //        tabLayout.addTab(tabLayout.newTab().setText("特价优惠"));
        //        tabLayout.setupWithViewPager(fragment_viewpager);


        //方式2嵌套fragment
        pagerAdapter = new NewsFragmentPagerAdapter(getChildFragmentManager(), getActivity());
        pagerAdapter.setList(tabList);
        news_viewpager.setAdapter(pagerAdapter);
        news_viewpager.setOffscreenPageLimit(2);//设置切换不销毁重建fragment的个数
        //tab绑定viewPager
        news_tabLayout.setupWithViewPager(news_viewpager);
        tabListInitBoolean = true;
    }

    /**
     * =========================================================================
     * ===================================生命周期相关======================================
     * =========================================================================
     */
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
    /**
     * =========================================================================
     * ===================================接口======================================
     * =========================================================================
     *
     */
    /**
     * 加载tab数据
     */
    private void loadFragmentTab() {
        MLog.e("加载--tab数据");
        isTabFinish = true;
        loadingDialog.show();
        presenter.pLoadTabData();

    }

    /**
     * 加载轮播图数据
     */
    private void loadBannerPicture() {
        MLog.e("加载--轮播数据");
        isBannerFinish = true;
        loadingDialog.show();
        presenter.pLoadLooperImgData();
    }

    /**
     * =========================================================================
     * ===================================View接口回调======================================
     * =========================================================================
     */

    @Override
    public void onTabDataSuccess(List<NewsTabBean> Listb) {
        isTabFinish = true;
        loadingDialogDismiss();
        tabList = (ArrayList<NewsTabBean>) Listb;

        //嵌套fragment
        if (tabList != null && tabList.size() > 0) {
            initFragmentList();
            //缓存
            maCache.remove(Constants.NEWS_TAB);
            // 缓存6h
            maCache.put(Constants.NEWS_TAB, tabList, Constants.TIME_SIX_HOUER);
        }

        //缓存当前时间
        SPUtil.putString(Constants.NEWS_CURRENT_TIME, TimeUtil.getData());
        //
        mIsFirst = false;
    }

    @Override
    public void onTabDataFailed(int code, String msg, Exception e) {
        isTabFinish = true;
        loadingDialogDismiss();
    }

    @Override
    public void onLooperImgSuccess(List<NewsBannerBean> list) {

        isBannerFinish = true;
        loadingDialogDismiss();

        ADList = (ArrayList<NewsBannerBean>) list;
        //缓存
        if (ADList != null && ADList.size() > 0) {
            initBanner();
            //添加缓存
            maCache.remove(Constants.NEWS_BANNER_PIC);
            // 缓存1h
            maCache.put(Constants.NEWS_BANNER_PIC, ADList, Constants.TIME_ONE_HOUER);
        }

        //缓存当前时间
        SPUtil.putString(Constants.NEWS_CURRENT_TIME, TimeUtil.getData());
        //
        mIsFirst = false;

    }

    @Override
    public void onLooperImgFailed(int code, String msg, Exception e) {
        isBannerFinish = true;
        loadingDialogDismiss();
    }

    /**
     * 异步加载完 统一结束 弹窗加载
     */
    private void loadingDialogDismiss() {
        if (isBannerFinish && isTabFinish) {
            loadingDialog.dismiss();
        }
    }
}
