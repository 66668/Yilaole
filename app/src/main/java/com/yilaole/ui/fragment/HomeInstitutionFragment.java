package com.yilaole.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yilaole.R;
import com.yilaole.adapter.home.HomeInstitutionRecylerAdapter;
import com.yilaole.base.BaseFragment;
import com.yilaole.base.adapterbase.BaseQuickAdapter;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.home.HomeInstituteItemBean;
import com.yilaole.http.cache.ACache;
import com.yilaole.http.rx.RxBus;
import com.yilaole.http.rx.RxBusBaseMessage;
import com.yilaole.http.rx.RxCodeConstants;
import com.yilaole.inter_face.iview.IHomeInstitutionView;
import com.yilaole.presenter.HomeInstitutionPresenterImpl;
import com.yilaole.save.SPUtil;
import com.yilaole.ui.InstitutionDetailActivity;
import com.yilaole.utils.MLog;
import com.yilaole.utils.TimeUtil;
import com.yilaole.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * 主页fragment中，嵌套 机构fragment
 */

public class HomeInstitutionFragment extends BaseFragment implements IHomeInstitutionView, BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.recycleview)
    RecyclerView recycleview;

    //变量
    private String type;
    private String city;
    private ArrayList<HomeInstituteItemBean> dataList = new ArrayList<>();
    private HomeInstitutionRecylerAdapter adapter;

    private boolean isPrepared = false;//结合BaseFragment的懒加载使用，标记
    private boolean isOldDayRequest;//是否是上一天
    private boolean dataListInitBoolean;//数据是否初始化
    private ACache aCache;//缓存类
    private boolean mIsFirst = true;
    private HomeInstitutionPresenterImpl presenter;

    //常量
    public static HomeInstitutionFragment newInstance(int page, String type, String city) {
        Bundle args = new Bundle();
        args.putString("city", city);
        args.putString("fragment_type", type);
        HomeInstitutionFragment pageFragment = new HomeInstitutionFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取复activity/fragment的传值
        type = getArguments().getString("fragment_type");
        city = getArguments().getString("city");
        MLog.d("嵌套机构参数------------------------------------", "type=" + type + "--city=" + city);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_institution, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initMyView();
        isPrepared = true;
        /**
         * 因为启动时先走lazyLoad()再走onActivityCreated，
         * 所以此处要额外调用lazyLoad(),不然最初不会加载内容
         */
        lazyLoad();

    }

    private void initMyView() {
        isPrepared = true;
        presenter = new HomeInstitutionPresenterImpl(getActivity(), this);
        //先获取缓存
        aCache = ACache.get(getContext());
        //
        dataList = (ArrayList<HomeInstituteItemBean>) aCache.getAsObject(Constants.HOME_TABS_DATA + type);
    }

    @Override
    protected void lazyLoad() {
        if (!isVisible || !isPrepared) {
            return;
        }

        //获取存储的时间
        String spTime = SPUtil.getString(Constants.CURRENT_TIME_INNERFRG, "2017-09-01");
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
     * item及其子控件的点击监听事件
     *
     * @param adapter
     * @param view     The view whihin the ItemView that was clicked
     * @param position The position of the view int the adapter
     */
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        HomeInstituteItemBean bean = dataList.get(position);
        Intent intent = new Intent();
        String token = aCache.getAsString(Constants.TOKEN);
        int id = bean.getAgencyid();
        Bundle bundle = new Bundle();

        boolean isLogin = SPUtil.isLogin();

        switch (view.getId()) {
            case R.id.layout_home_institution0:

                bundle.putInt("id", bean.getAgencyid());
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setClass(getActivity(), InstitutionDetailActivity.class);
                startActivity(intent);

                break;
            //            case R.id.toCollect0://收藏
            //                if (!token.isEmpty() && isLogin) {
            //                    collect(id, token);
            //                } else {
            //                    toLoginDialog();
            //                }
            //                break;

            case R.id.layout_home_institution://type2下的item

                bundle.putInt("id", bean.getAgencyid());
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setClass(getActivity(), InstitutionDetailActivity.class);
                startActivity(intent);

                break;
            //            case R.id.toCollect://收藏
            //
            //                if (!token.isEmpty() && isLogin) {
            //                    collect(id, token);
            //                } else {
            //                    toLoginDialog();
            //                }
            //                break;

            case R.id.layout_child1:
                HomeInstituteItemBean bean1 = dataList.get(position);
                bundle.putInt("id", bean1.getChild().get(0).getAgencyid());
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setClass(getActivity(), InstitutionDetailActivity.class);
                startActivity(intent);
                break;

            case R.id.layout_child2:
                HomeInstituteItemBean bean2 = dataList.get(position);
                bundle.putInt("id", bean2.getChild().get(1).getAgencyid());
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setClass(getActivity(), InstitutionDetailActivity.class);
                startActivity(intent);
                break;
        }

    }

    /**
     * 获取缓存数据
     */
    private void getACacheData() {
        if (!mIsFirst) {
            return;
        }
        //01
        if (dataList != null && dataList.size() > 0) {

            initData();
        } else {
            dataListInitBoolean = false;
            loadData();
        }

    }

    private void initData() {
        if (dataListInitBoolean) {
            return;
        }

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        recycleview.setLayoutManager(layoutManager);

        adapter = new HomeInstitutionRecylerAdapter(getActivity(), dataList);
        recycleview.setAdapter(adapter);
        adapter.setOnItemChildClickListener(this);
        dataListInitBoolean = true;
    }

    /**
     * 修改数据源 后台没给加type判断，移动端自己加判断参数
     */

    private void setDataType() {
        for (HomeInstituteItemBean bean : dataList) {
            if (bean.getChild() == null || bean.getChild().size() <= 0) {
                bean.setItemType(Constants.MultiInstituteType.TYPE1);
            } else {
                bean.setItemType(Constants.MultiInstituteType.TYPE2);
            }
        }
    }

    /*
     * 弹窗提示登录
     */
    private void toLoginDialog() {
        ToastUtil.toastInMiddle(getActivity(), "请先登录！");
    }

    /**
     * 刷新定位
     */
    private void initCityRxBus() {
        MLog.d("寻找RxBus消费事件");
        RxBus.getDefault().toObservable(RxCodeConstants.LOCATION_FRAGMENT, RxBusBaseMessage.class)
                .subscribe(new Action1<RxBusBaseMessage>() {
                    @Override
                    public void call(RxBusBaseMessage rxBusBaseMessage) {
                        MLog.i("我的-RxBus消费事件！定位新地址", type + "--" + city, SPUtil.getLocation());
                        city = SPUtil.getLocation();
                        dataListInitBoolean = false;
                        //重新获取定位的数据
                        loadData();
                    }
                });
    }
    /**
     * ===========================================================================================
     * ============================================获取数据===============================================
     * ===========================================================================================
     *
     */

    /**
     * 加载数据
     */
    private void loadData() {
        loadingDialog.show();
        presenter.pLoadData(city.trim(), type);
    }

    /**
     * 收藏 接口
     *
     * @param id
     * @param token
     */
    private void collect(int id, String token) {
        loadingDialog.show();
        presenter.pCollect(id, token);
    }

    /**
     * ================================================================================================
     * ==============================================接口回调==================================================
     * ================================================================================================
     */

    @Override
    public void onTabDataSuccess(List<HomeInstituteItemBean> list) {
        loadingDialog.dismiss();
        dataList = (ArrayList<HomeInstituteItemBean>) list;
        if (dataList != null && dataList.size() > 0) {
            setDataType();
            initData();
            //缓存list数据 6h
            aCache.remove(Constants.HOME_TABS_DATA + type);
            aCache.put(Constants.HOME_TABS_DATA + type, dataList, 21600);
        }
        //缓存当前时间
        SPUtil.putString(Constants.CURRENT_TIME_INNERFRG, TimeUtil.getData());
        mIsFirst = false;

    }

    @Override
    public void onTabDataFailed(int code, String msg, Exception e) {
        loadingDialog.dismiss();
        if (code == 404) {
            dataList = new ArrayList<>();
            initData();
        }


    }

    @Override
    public void onCollectionSuccess(Object obj) {
        loadingDialog.dismiss();
        ToastUtil.ToastShort(getActivity(), "收藏成功！");
    }

    @Override
    public void onCollectionFailed(int code, String msg, Exception e) {
        loadingDialog.dismiss();
        if (code == 404) {
            ToastUtil.ToastShort(getActivity(), "收藏失败！");
        } else {
            MLog.e(msg, e.toString());
        }
    }

    /**
     * ===================================================================================================
     * ================================================== 生命周期方法=================================================
     * ===================================================================================================
     */
    @Override
    public void onResume() {
        super.onResume();
        initCityRxBus();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
