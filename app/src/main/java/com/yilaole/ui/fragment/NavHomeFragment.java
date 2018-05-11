package com.yilaole.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yilaole.R;
import com.yilaole.adapter.home.HomeBannerAdapter;
import com.yilaole.adapter.home.HomeFragmentPagerAdapter;
import com.yilaole.adapter.home.HomeNewsAdapter;
import com.yilaole.base.BaseFragment;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.home.BannerBean;
import com.yilaole.bean.home.HomeNewsBean;
import com.yilaole.bean.home.HomeTabBean;
import com.yilaole.bean.home.SearchBean;
import com.yilaole.bean.home.TextLooperBean;
import com.yilaole.http.cache.ACache;
import com.yilaole.http.rx.RxBus;
import com.yilaole.http.rx.RxBusBaseMessage;
import com.yilaole.http.rx.RxCodeConstants;
import com.yilaole.inter_face.imodle.INavHomeModle;
import com.yilaole.inter_face.ipresenter.INavHomePresenter;
import com.yilaole.inter_face.iview.INavHomeView;
import com.yilaole.modle.home.HomeModleImpl;
import com.yilaole.presenter.HomePresenterImpl;
import com.yilaole.save.SPUtil;
import com.yilaole.ui.CityPickerActivity;
import com.yilaole.ui.InstitutionActivity;
import com.yilaole.ui.InstitutionDetailActivity;
import com.yilaole.ui.NewsDetailActivity;
import com.yilaole.ui.SearchHistoryActivity;
import com.yilaole.utils.DpUtils;
import com.yilaole.utils.MLog;
import com.yilaole.utils.TimeUtil;
import com.yilaole.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import lib.yilaole.textbanner.MarqueeLayout;
import lib.yilaole.textbanner.MarqueeLayoutAdapter;

import static android.app.Activity.RESULT_OK;


/**
 * 首页
 */

public class NavHomeFragment extends BaseFragment implements INavHomeView, AppBarLayout.OnOffsetChangedListener {

    //
    @BindView(R.id.viewpager)
    ViewPager fragment_viewpager;

    //顶部父布局
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    //顶部定位 搜索
    @BindView(R.id.topbar_local_search)
    RelativeLayout topbar_local_search;

    //定位
    @BindView(R.id.tv_location)
    TextView tv_location;

    //嵌套fragment的tab页
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    //搜索
    @BindView(R.id.tv_search)
    TextView tv_search;

    //图片轮播
    @BindView(R.id.loopImg_viewpager)
    ViewPager Banner_viewpager;

    @BindView(R.id.ll_index_loopImg)
    LinearLayout ll_index_loopImg;

    //文字轮播
    @BindView(R.id.marquee_layout)
    MarqueeLayout marquee_layout;

    //横向资讯
    @BindView(R.id.news_recycle)
    RecyclerView news_recycle;


    //变量
    private String locationCity;
    private CountDownTimer timer;//倒计时类，搜索内容轮播使用

    private ArrayList<SearchBean> searchList = new ArrayList<>();//搜索轮播数据
    private ArrayList<BannerBean> ADList;//图片轮播数据
    private ArrayList<TextLooperBean> headLineList;//文字轮播数据
    private ArrayList<HomeNewsBean> newsList;//资讯数据
    private ArrayList<HomeTabBean> tabList;//tab数据

    private boolean searchListInitBoolean = false;//数据是否初始化到界面
    private boolean ADListInitBoolean = false;
    private boolean headLineListInitBoolean = false;
    private boolean newsListInitBoolean = false;
    private boolean tabListInitBoolean = false;

    private ACache maCache;//缓存类
    private boolean mIsFirst = true;
    private boolean isOldDayRequest;//是否是上一天
    private boolean isPrepared = false;//结合BaseFragment的懒加载使用，标记
    private Unbinder unbinder;
    //资讯变量
    LinearLayoutManager newsLayoutManager;
    MarqueeLayoutAdapter adapter;//文字轮播适配
    HomeNewsAdapter newsAdapter;
    HomeFragmentPagerAdapter pagerAdapter;
    INavHomePresenter homePresenter;//
    INavHomeModle modle;

    //加载动画 结束判断
    private boolean isLooperSearchFinish = false;
    private boolean isBannerFinish = false;
    private boolean isTextLooperFinish = false;
    private boolean isNewsFinish = false;
    private boolean isTabFinish = false;
    //常量
    private final int HOME_AD_RESULT = 111;
    private final int REQUEST_HOME_CITY = 112;//城市定位筛选

    public static NavHomeFragment newInstance() {
        NavHomeFragment fragment = new NavHomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home_test, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initMyView();
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
        MLog.d("initMyView()");
        modle = new HomeModleImpl();
        isPrepared = true;
        homePresenter = new HomePresenterImpl(getActivity(), this);
        //先获取缓存
        maCache = ACache.get(getContext());
        //01 搜索轮播
        searchList = (ArrayList<SearchBean>) maCache.getAsObject(Constants.BANNER_SEARCH);

        //02 获取缓存轮播图
        ADList = (ArrayList<BannerBean>) maCache.getAsObject(Constants.BANNER_PIC);

        //03 文字轮播
        headLineList = (ArrayList<TextLooperBean>) maCache.getAsObject(Constants.BANNER_TEXT);

        //首页资讯
        newsList = (ArrayList<HomeNewsBean>) maCache.getAsObject(Constants.HOME_NEWS);

        //首页tab
        tabList = (ArrayList<HomeTabBean>) maCache.getAsObject(Constants.HOME_TABS);

        //定位城市
        locationCity = SPUtil.getLocation();
        tv_location.setText(locationCity);
    }


    /**
     * 当fragment可见时，自动加载
     */
    @Override
    protected void lazyLoad() {
        if (!isVisible || !isPrepared) {
            return;
        }
        //获取存储的时间
        String spTime = SPUtil.getString(Constants.HOME_CURRENT_TIME, "2017-09-01");
        if (!spTime.equals(TimeUtil.getData())) {//非当天时间，获取数据

            if (TimeUtil.isRightTime()) {//大于12：30, 网络加载
                isOldDayRequest = false;
                //01 搜索
                loadSearch();
                //02 轮播图
                loadBannerPicture();
                //03 文字
                loadLooperText();
                //04 横向资讯
                loadHorizotalNewsData();
                //05嵌套fragment
                loadFragmentTabData();
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
        //01 搜索轮播
        if (searchList != null && searchList.size() > 0) {
            isLooperSearchFinish = true;
            initSearch();
        } else {
            searchListInitBoolean = false;
            loadSearch();
        }

        //02 轮播图
        if (ADList != null && ADList.size() > 0) {
            isBannerFinish = true;
            initLoopImg();
        } else {
            ADListInitBoolean = false;
            loadBannerPicture();
        }

        //03 文字轮播
        if (headLineList != null && headLineList.size() > 0) {
            isTextLooperFinish = true;
            initHeadLine();
        } else {
            headLineListInitBoolean = false;

            loadLooperText();
        }

        //04 资讯
        if (newsList != null && newsList.size() > 0) {
            isNewsFinish = true;
            initHorizontalNews();
        } else {
            newsListInitBoolean = false;
            loadHorizotalNewsData();
        }

        //05 tab数据
        if (tabList != null && tabList.size() > 0) {
            isTabFinish = true;
            initFragmentList();
        } else {
            tabListInitBoolean = false;
            loadFragmentTabData();
        }
    }

    /**
     * 跳转 点击 监听
     */
    @OnClick({R.id.tv_location, R.id.tv_search, R.id.layout_institution, R.id.layout_map, R.id.news_more, R.id.layout_assess})
    public void onClickListener(View view) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        switch (view.getId()) {
            case R.id.tv_location://城市定位

                intent.setClass(getActivity(), CityPickerActivity.class);
                getActivity().startActivityForResult(intent, REQUEST_HOME_CITY);
                break;

            case R.id.tv_search://搜索历史
                intent.setClass(getActivity(), SearchHistoryActivity.class);
                getActivity().startActivity(intent);

                break;
            case R.id.layout_institution://养老机构
                intent.setClass(getActivity(), InstitutionActivity.class);
                getActivity().startActivity(intent);
                break;

            case R.id.layout_map://养老地图
                ToastUtil.ToastShort(getActivity(), getResources().getString(R.string.aaa));
                //                intent.setClass(getActivity(), MapOfPensionActivity.class);
                //                getActivity().startActivity(intent);
                break;

            case R.id.layout_assess://在线评估
                ToastUtil.ToastShort(getActivity(), getResources().getString(R.string.aaa));
                //                intent.setClass(getActivity(), AssessMainActivity.class);
                //                getActivity().startActivity(intent);
                break;

            case R.id.news_more://资讯更多
                MLog.d("更多资讯");

                RxBus.getDefault().post(RxCodeConstants.JUMP_NEWS, new RxBusBaseMessage());
                break;
        }

    }

    /**
     * 城市定位
     * <p>
     * 定位新城市的数据，更新接口数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //获取城市定位 更新数据
        if (requestCode == REQUEST_HOME_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                //获取跳转数据
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                tv_location.setText(city);

                //如果重复定位同一个城市，就不刷新数据
                if (locationCity.contains(city)) {
                    return;
                }
                //缓存定位信息,
                SPUtil.setLocation(city);
                locationCity = city;
                loadSearch();
                loadBannerPicture();
                loadLooperText();
                loadHorizotalNewsData();

                //更新嵌套fragemnt-HomeInstitutionFragment数据
                RxBus.getDefault().post(RxCodeConstants.LOCATION_FRAGMENT, new RxBusBaseMessage());
                //
                //                //更新筛选界面的默认数据为当前城市的数据
                //                RxBus.getDefault().post(RxCodeConstants.LOCATION_FILTER, new RxBusBaseMessage());
            }
        }
    }


    /**
     * 01 顶部搜索框内容定时更新，简单的采用倒计时功能
     */
    private void initSearch() {
        if (searchListInitBoolean) {
            return;
        }
        timer = new CountDownTimer(3000000, 5000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (searchList == null || searchList.size() <= 0) {
                    tv_search.setText("");
                } else {
                    tv_search.setText(searchList.get(new Random().nextInt(searchList.size() - 1)).getKeywords());
                }
            }

            @Override
            public void onFinish() {
            }
        };
        timer.start();
        searchListInitBoolean = true;
    }

    /**
     * 02 图片轮播
     */

    private void initLoopImg() {

        if (ADListInitBoolean) {
            return;
        }
        //viewpager监听
        Banner_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                refreshPoint(position % ADList.size());
                if (mHandler.hasMessages(HOME_AD_RESULT)) {
                    mHandler.removeMessages(HOME_AD_RESULT);
                }
                mHandler.sendEmptyMessageDelayed(HOME_AD_RESULT, 5000);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (ViewPager.SCROLL_STATE_DRAGGING == state
                        && mHandler.hasMessages(HOME_AD_RESULT)) {
                    mHandler.removeMessages(HOME_AD_RESULT);
                }
            }
        });

        HomeBannerAdapter adapter = new HomeBannerAdapter(getActivity(), ADList);
        Banner_viewpager.setAdapter(adapter);
        addIndicatorImageViews(ADList.size());

        Banner_viewpager.setCurrentItem(ADList.size() * 1000, false);
        //自动轮播线程
        mHandler.sendEmptyMessageDelayed(HOME_AD_RESULT, 5000);//5s一轮播

        ADListInitBoolean = true;
    }

    // 添加指示图标
    private void addIndicatorImageViews(int size) {

        ll_index_loopImg.removeAllViews();
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
            ll_index_loopImg.addView(iv);
        }
    }

    // 为ViewPager的选中点的颜色设置监听器
    private void refreshPoint(int position) {
        if (ll_index_loopImg != null) {
            for (int i = 0; i < ll_index_loopImg.getChildCount(); i++) {
                ll_index_loopImg.getChildAt(i).setEnabled(false);

                if (i == position) {
                    ll_index_loopImg.getChildAt(i).setEnabled(true);
                }
            }
        }
    }

    /**
     * 03 文字轮播
     */
    private void initHeadLine() {
        if (headLineListInitBoolean) {
            return;
        }
        adapter = new MarqueeLayoutAdapter<TextLooperBean>(getActivity(), headLineList) {
            @Override
            protected int getItemLayoutId() {
                return R.layout.item_marquee;
            }

            @Override
            protected void initView(View view, final int position, TextLooperBean item) {
                ((TextView) view).setText(item.getName());

                //监听 跳转机构详情
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextLooperBean bean = (TextLooperBean) adapter.getItem(position);
                        MLog.d("text轮播：position=" + position + "id=" + bean.getId());
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", bean.getId());
                        intent.putExtras(bundle);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setClass(getActivity(), InstitutionDetailActivity.class);
                        startActivity(intent);
                    }
                });
            }
        };
        marquee_layout.setAdapter(adapter);
        marquee_layout.setSwitchTime(4000);//5s一切换
        marquee_layout.setScrollTime(1500);//1.5s翻滚时间
        marquee_layout.start();
        headLineListInitBoolean = true;
    }

    @Override
    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case HOME_AD_RESULT://图片轮播
                // TODO: 2017/10/24 退出程序 在进入异常  java.lang.NullPointerException: Attempt to invoke virtual method 'int android.support.v4.view.ViewPager.getCurrentItem()' on a null object reference
                Banner_viewpager.setCurrentItem(Banner_viewpager.getCurrentItem() + 1, true);
                break;
        }
    }

    /**
     * 04 最新资讯
     */
    private void initHorizontalNews() {
        if (newsListInitBoolean) {
            return;
        }
        //设置横向
        newsLayoutManager = new LinearLayoutManager(getActivity());
        newsLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        news_recycle.setLayoutManager(newsLayoutManager);
        //关联
        newsAdapter = new HomeNewsAdapter(getActivity());
        newsAdapter.setList(newsList);
        news_recycle.setAdapter(newsAdapter);
        newsAdapter.setOnItemClickListener(new HomeNewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {

                //资讯详情跳转
                HomeNewsBean bean = newsAdapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra(Constants.NEWS_ISHOT, false);
                intent.putExtra(Constants.NEWS_ID, bean.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setClass(getActivity(), NewsDetailActivity.class);
                startActivity(intent);
            }
        });

        newsListInitBoolean = true;
    }


    /**
     * 创建子fragment
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
        pagerAdapter = new HomeFragmentPagerAdapter(getChildFragmentManager(), getActivity());
        pagerAdapter.setTabList(tabList);
        pagerAdapter.setCity(locationCity);
        fragment_viewpager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(fragment_viewpager);
        tabListInitBoolean = true;
    }

    /**
     * 获取当天日期
     */
    private ArrayList<String> getTodayTime() {
        String data = TimeUtil.getData();
        String[] split = data.split("-");
        String year = split[0];
        String month = split[1];
        String day = split[2];
        ArrayList<String> list = new ArrayList<>();
        list.add(year);
        list.add(month);
        list.add(day);
        return list;
    }


    /**
     * =========================================================================
     * ===================================生命周期相关======================================
     * =========================================================================
     */
    @Override
    public void onStart() {
        super.onStart();
        if (timer != null) {
            timer.start();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // 开始图片请求
        Glide.with(getActivity()).resumeRequests();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // 停止全部图片请求 跟随着Activity
        Glide.with(getActivity()).pauseRequests();
    }

    /**
     * baseFragment中重写的方法，用于修改appBarLayout的touch事件，使appbarLayout可以重新获取滑动焦点
     */
    @Override
    protected void setEnabled() {
        super.setEnabled();
        if (appBarLayout != null && fragment_viewpager != null) {
            appBarLayout.addOnOffsetChangedListener(this);
        }

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
     * ===================================访问异步数据======================================
     * =========================================================================
     *
     */

    /**
     * 01 文字轮播
     */
    private void loadSearch() {
        MLog.d("加载-- 搜索轮播开始");
        isLooperSearchFinish = false;
        loadingDialog.show();
        homePresenter.pLoadSearchData(locationCity);
    }

    /**
     * 02 图片轮播
     */
    private void loadBannerPicture() {
        MLog.d("加载---图片轮播开始");
        isBannerFinish = false;
        loadingDialog.show();
        homePresenter.pLoadLooperImgData(locationCity);
    }


    /**
     * 03 加载文字轮播
     */
    private void loadLooperText() {
        MLog.d("加载---文字轮播--开始");
        isTextLooperFinish = false;
        loadingDialog.show();
        homePresenter.pLoadLooperTextData(locationCity);
    }

    /**
     * 04 最新资讯
     */
    private void loadHorizotalNewsData() {
        MLog.d("加载---首页资讯");
        isNewsFinish = false;
        loadingDialog.show();
        homePresenter.pLoadNewsData(locationCity);
    }

    /**
     * 05 嵌套fragment
     */

    private void loadFragmentTabData() {
        MLog.d("加载---首页tab");
        isTabFinish = false;
        loadingDialog.show();
        homePresenter.pLoadTabData(locationCity);
    }

    /**
     * =========================================================================
     * ===================================View接口回调======================================
     * =========================================================================
     */


    //01搜索轮播
    @Override
    public void onLooperSearchSuccess(List<SearchBean> list) {
        isLooperSearchFinish = true;
        loadingDialogDismiss();
        searchList = (ArrayList<SearchBean>) list;
        //缓存
        if (searchList != null && searchList.size() > 0) {
            initSearch();
            //添加缓存
            maCache.remove(Constants.BANNER_SEARCH);
            // 缓存1h
            maCache.put(Constants.BANNER_SEARCH, searchList, Constants.TIME_ONE_HOUER);
        }
    }

    @Override
    public void onLooperSearchFailed(int code, String msg, Exception e) {
        isLooperSearchFinish = true;
        loadingDialogDismiss();
    }

    //02 轮播图
    @Override
    public void onLooperImgSuccess(List<BannerBean> list) {
        isBannerFinish = true;
        loadingDialogDismiss();
        ADList = (ArrayList<BannerBean>) list;
        //缓存
        if (ADList != null && ADList.size() > 0) {
            initLoopImg();
            //添加缓存
            maCache.remove(Constants.BANNER_PIC);
            // 缓存1h
            maCache.put(Constants.BANNER_PIC, ADList, Constants.TIME_ONE_HOUER);
        }

        //缓存当前时间
        SPUtil.putString(Constants.HOME_CURRENT_TIME, TimeUtil.getData());

        mIsFirst = false;
    }

    @Override
    public void onLooperImgFailed(int code, String msg, Exception e) {
        isBannerFinish = true;
        loadingDialogDismiss();
    }

    //03 文字轮播
    @Override
    public void onLooperTextSuccess(List<TextLooperBean> list) {
        isTextLooperFinish = true;
        loadingDialogDismiss();
        headLineList = (ArrayList<TextLooperBean>) list;
        initHeadLine();
        if (headLineList != null && headLineList.size() > 0) {
            initHeadLine();
            //缓存
            maCache.remove(Constants.BANNER_TEXT);
            // 缓存1h
            maCache.put(Constants.BANNER_TEXT, headLineList, Constants.TIME_ONE_HOUER);
        }
    }

    @Override
    public void onLooperTextFailed(int code, String msg, Exception e) {
        isTextLooperFinish = true;
        loadingDialogDismiss();
    }

    //04 首页资讯
    @Override
    public void onNewsSuccess(List<HomeNewsBean> list) {
        isNewsFinish = true;
        loadingDialogDismiss();
        newsList = (ArrayList<HomeNewsBean>) list;
        initHorizontalNews();
        if (newsList != null && newsList.size() > 0) {
            initHorizontalNews();
            //缓存
            maCache.remove(Constants.HOME_NEWS);
            // 缓存1h
            maCache.put(Constants.HOME_NEWS, newsList, Constants.TIME_ONE_HOUER);
        }
    }

    @Override
    public void onNewsFailed(int code, String msg, Exception e) {
        isNewsFinish = true;
        loadingDialogDismiss();
        if (code == 404) {
            MLog.d("首页资讯没有值404", "返回处理");
        }
    }

    //05 首页tab
    @Override
    public void onTabSuccess(List<HomeTabBean> list) {
        isTabFinish = true;
        loadingDialogDismiss();
        tabList = (ArrayList<HomeTabBean>) list;
        if (tabList != null && tabList.size() > 0) {
            initFragmentList();
            //缓存
            maCache.remove(Constants.HOME_TABS);
            // 缓存6h
            maCache.put(Constants.HOME_TABS, tabList, Constants.TIME_SIX_HOUER);
        }

    }

    @Override
    public void onTabFailed(int code, String msg, Exception e) {
        isTabFinish = true;
        loadingDialogDismiss();
    }

    /**
     * AppBarLayout.OnOffsetChangedListener接口实现
     * <p>
     * 用于解决appbarlayout的滑动卡顿问题
     *
     * @param appBarLayout
     * @param verticalOffset 当AppBarLayout完全展开时 值是0
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        fragment_viewpager.setEnabled(false);
        this.appBarLayout.setEnabled(true);
    }

    /**
     * 统一所有异步加载完 结束弹窗加载
     */
    private void loadingDialogDismiss() {
        MLog.d("查看首页 加载--", isLooperSearchFinish, isBannerFinish, isTextLooperFinish, isNewsFinish, isTabFinish);
        if (isLooperSearchFinish && isBannerFinish && isTextLooperFinish && isNewsFinish && isTabFinish) {
            loadingDialog.dismiss();
        }
    }
}