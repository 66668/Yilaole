package com.yilaole.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.adapter.institution.DropMenuAdapter;
import com.yilaole.adapter.institution.InstitutionFilterRecylerAdapter;
import com.yilaole.base.BaseFragment;
import com.yilaole.base.adapterbase.BaseQuickAdapter;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.institution.filter.FilterBean;
import com.yilaole.bean.institution.filter.InstituteItemBean;
import com.yilaole.bean.institution.filter.InstitutionPriceBean;
import com.yilaole.filter.DropDownMenuFragment;
import com.yilaole.filter.interfaces.OnFilterDoneListener;
import com.yilaole.http.cache.ACache;
import com.yilaole.inter_face.ipresenter.IInstitutionFilterPresenter;
import com.yilaole.inter_face.iview.IInstitutionView;
import com.yilaole.presenter.InstitutionFilterPresenterImpl;
import com.yilaole.save.SPUtil;
import com.yilaole.ui.InstitutionDetailActivity;
import com.yilaole.utils.MLog;
import com.yilaole.utils.TimeUtil;
import com.yilaole.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.yilaole.R.id.tv_search;

/**
 * 机构列表筛选
 * <p>
 * 注：数据列表的获取必须在筛选器构构建完成后才可以调用
 */

public class NavFilterFragment extends BaseFragment implements IInstitutionView, OnFilterDoneListener, BaseQuickAdapter.OnItemChildClickListener {

    //搜索按钮
    @BindView(tv_search)
    EditText et_search;

    @BindView(R.id.dropDownMenu)
    DropDownMenuFragment dropDownMenu;

    //    @BindView(R.id.layout_loaddata)
    //    SwipeRefreshLayout layout_load;

    @BindView(R.id.mFilterRecyclerView)
    RecyclerView mFilterRecyclerView;

    //
    private IInstitutionFilterPresenter presenter;//p层
    private FilterBean filterBean;
    private ACache acache;
    private InstitutionFilterRecylerAdapter filterRecylerAdapter;//数据列表适配
    private DropMenuAdapter dropMenuAdapter;
    private String[] titleList;
    private List<String> sortListData;
    private List<InstituteItemBean> listData;//列表数据
    private Map<String, Object> map = new HashMap();//保存上传参数
    //上传参数
    private int provinceId;
    private int cityId;
    private int areaId;
    private int typeId;
    private int propertyId;
    private int bedId;
    private int priceId;
    private int objectId;
    private String featureId;
    private String token;
    private String searchKeys;//搜索内容,也是搜索界面剂跳转过来的搜索内容

    private int orderId = 0;//默认为第一个

    private int totle = 0;
    private int size = 50;
    private Unbinder unbinder;
    private boolean isPrepared = false;//结合BaseFragment的懒加载使用，标记
    private boolean isOldDayRequest;//是否是上一天


    /**
     * 单例
     *
     * @return
     */
    public static NavFilterFragment newInstance() {
        NavFilterFragment fragment = new NavFilterFragment();
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
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MLog.d("onActivityCreated");
        initMyView();
        initListener();
        /**
         * 因为启动时先走lazyLoad()再走onActivityCreated，
         * 所以此处要额外调用lazyLoad(),不然最初不会加载内容
         */
        lazyLoad();
    }

    /**
     * 初始化数据，包括缓存中获取
     */
    private void initMyView() {
        isPrepared = true;
        //        layout_load.setRefreshing(false);
        presenter = new InstitutionFilterPresenterImpl(getActivity(), this);
        acache = ACache.get(getActivity());

        //筛选标题数据（固定数据）
        titleList = new String[]{"区域", "筛选", "价格", "排序"};
        //筛选-排序数据（固定数据）
        sortListData = new ArrayList<>();
        sortListData.add("综合排序");
        sortListData.add("价格 从高到底");
        sortListData.add("价格 从低到高");
        filterBean = (FilterBean) acache.getAsObject(Constants.FILTER_ALL);
        token = acache.getAsString(Constants.TOKEN);
        //        getSearchIntentData();
    }

    /**
     * 当fragment可见时，自动加载
     */
    @Override
    protected void lazyLoad() {
        initRxBus();
        if (!isVisible || !isPrepared) {
            return;
        }
        //获取存储的时间
        String spTime = SPUtil.getString(Constants.FILTER_CURRENT_TIME, "2017-09-01");
        if (!spTime.equals(TimeUtil.getData())) {//非当天时间，获取数据

            if (TimeUtil.isRightTime()) {//大于12：30, 网络加载
                isOldDayRequest = false;// 是昨天
                loadFilterData();
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
     * 读取缓存数据
     */
    private void getACacheData() {
        //是否需要 isFirst标记
        //
        if (filterBean != null && titleList != null && sortListData != null) {
            initFilterDropDownView();
        } else {
            loadFilterData();
        }

    }


    /**
     * 处理从搜索界面跳转过来的数据
     * SearchHistoryActivity
     */
    //    private void getSearchIntentData() {
    //        Bundle bundle = getActivity().getIntent().getExtras();
    //
    //        if (bundle != null) {
    //            searchKeys = bundle.getString("searchKey", "");
    //            MLog.d("搜索跳转 参数", searchKeys);
    //            //让搜索内容显示出来
    //            et_search.setText(searchKeys);
    //        }
    //
    //    }
    private void initListener() {

        /**
         * 搜索 养老机构
         */
        et_search.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                searchKeys = textView.getText().toString();
                if (!TextUtils.isEmpty(searchKeys)) {
                    //隐藏软键盘
                    InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    manager.hideSoftInputFromWindow(textView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //隐藏光标
                    et_search.setCursorVisible(false);
                    initSearchKeys();
                    //获取接口
                    loadListData();
                } else {
                    initSearchKeys();
                    //获取接口
                    loadListData();
                }

                return i == EditorInfo.IME_ACTION_SEARCH;
            }
        });

        et_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //显示光标
                et_search.setCursorVisible(true);
            }
        });
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchKeys = editable.toString().trim();
            }
        });
    }


    /**
     * 筛选框 初始化+获取列表数据+筛选条件监听
     */
    private void initFilterDropDownView() {
        //保存上传参数
        initID();
        //绑定数据源
        dropMenuAdapter = new DropMenuAdapter(getActivity(), titleList, this);
        dropMenuAdapter.setSortListData(sortListData);
        dropMenuAdapter.setFilterBean(filterBean);
        dropDownMenu.setMenuAdapter(dropMenuAdapter);

        //01 省市区 回调参数
        dropMenuAdapter.setOnPlaceCallbackListener(new DropMenuAdapter.OnPlaceCallbackListener() {
            @Override
            public void onPlaceCallbackListener(int provinceId, int cityId, int areaId) {

                map.remove(Constants.FILTER_PROVINCE);
                map.remove(Constants.FILTER_CITY);
                map.remove(Constants.FILTER_TOWN);

                map.put(Constants.FILTER_PROVINCE, provinceId);
                map.put(Constants.FILTER_CITY, cityId);
                map.put(Constants.FILTER_TOWN, areaId);

                //首页定位的城市，需要删除
                map.remove(Constants.FILTER_LOCATION_CITY);
                map.put(Constants.FILTER_LOCATION_CITY, "");
                //异步数据
                loadListData();
            }
        });
        //02 多条件 回调参数
        dropMenuAdapter.setOnMultiFilterCallbackListener(new DropMenuAdapter.OnMultiFilterCallbackListener() {
            @Override
            public void onMultiFilterCallbackListener(int objId, int propertyId, int bedId, int typeId, String serviceId) {

                MLog.e(NavFilterFragment.this, "MultiGridView回调给==DropMenuAdapter的结果=="
                        + objId + "--" + propertyId + "--" + bedId + "--" + typeId + "--" + serviceId);

                map.remove(Constants.FILTER_OBJECT);
                map.remove(Constants.FILTER_PROPERTY);
                map.remove(Constants.FILTER_BED);
                map.remove(Constants.FILTER_TYPE);
                map.remove(Constants.FILTER_FEATURE);

                map.put(Constants.FILTER_OBJECT, objId);
                map.put(Constants.FILTER_PROPERTY, propertyId);
                map.put(Constants.FILTER_BED, bedId);
                map.put(Constants.FILTER_TYPE, typeId);
                map.put(Constants.FILTER_FEATURE, serviceId);

                //首页定位的城市，需要删除
                map.remove(Constants.FILTER_LOCATION_CITY);
                map.put(Constants.FILTER_LOCATION_CITY, "");
                //异步数据
                loadListData();
            }
        });

        //03 dropMenuAdapter 价格范围回调
        dropMenuAdapter.setOnPriceCallbackListener(new DropMenuAdapter.OnPriceCallbackListener() {
            @Override
            public void onPriceCallbackListener(InstitutionPriceBean item) {

                map.remove(Constants.FILTER_PIRCE);
                map.put(Constants.FILTER_PIRCE, item.getId());

                //首页定位的城市，需要删除
                map.remove(Constants.FILTER_LOCATION_CITY);
                map.put(Constants.FILTER_LOCATION_CITY, "");

                //异步数据
                loadListData();
            }
        });

        //04 dropMenuAdapter 排序回调 0 1 2
        dropMenuAdapter.setOnSortCallbackListener(new DropMenuAdapter.OnSortCallbackListener() {
            @Override
            public void onSortCallbackListener(int item) {

                map.remove(Constants.FILTER_ORDER);
                map.put(Constants.FILTER_ORDER, item);

                //首页定位的城市，需要删除
                map.remove(Constants.FILTER_LOCATION_CITY);
                map.put(Constants.FILTER_LOCATION_CITY, "");

                //异步数据
                loadListData();
            }
        });

        //异步加载列表数据
        if (listData != null && listData.size() > 0) {
            MLog.d("数据list显示");
            initRecyclerView();
        } else {
            MLog.d("数据list加载");
            loadListData();
        }
    }

    /**
     * 参数封装到map中
     */
    private void initID() {
        provinceId = filterBean.getProvince().get(0).getId();
        cityId = filterBean.getProvince().get(0).getChild().get(0).getId();
        areaId = filterBean.getProvince().get(0).getChild().get(0).getChild().get(0).getId();
        typeId = filterBean.getType().get(0).getId();
        propertyId = filterBean.getProperty().get(0).getId();
        priceId = filterBean.getPrice().get(0).getId();
        bedId = filterBean.getBed().get(0).getId();
        objectId = filterBean.getPrice().get(0).getId();
        featureId = filterBean.getFeature().get(0).getId() + "";

        //
        //        PostFilterBean bean = new PostFilterBean();

        map.put(Constants.FILTER_PROVINCE, provinceId);
        map.put(Constants.FILTER_CITY, cityId);
        map.put(Constants.FILTER_TOWN, areaId);

        map.put(Constants.FILTER_TYPE, typeId);
        map.put(Constants.FILTER_PROPERTY, propertyId);
        map.put(Constants.FILTER_BED, bedId);

        map.put(Constants.FILTER_PIRCE, priceId);
        map.put(Constants.FILTER_OBJECT, objectId);
        map.put(Constants.FILTER_FEATURE, featureId);

        map.put(Constants.FILTER_ORDER, orderId);
        map.put(Constants.PAGETOTLE, totle);
        map.put(Constants.PAGESIZE, size);

        map.put(Constants.TOKEN, token);
        map.put(Constants.SEARCH_KEYS, searchKeys);
    }

    private void initSearchKeys() {
        map.put(Constants.SEARCH_KEYS, searchKeys);
    }


    /**
     * 监听
     *
     * @param view
     */
    @OnClick({R.id.img_map})
    public void onItemClick(View view) {
        switch (view.getId()) {

            case R.id.img_map:
                ToastUtil.ToastShort(getActivity(), getResources().getString(R.string.aaa));
                break;
        }
    }

    /**
     * BaseQuickAdapter监听
     *
     * @param adapter
     * @param view     The view whihin the ItemView that was clicked
     * @param position The position of the view int the adapter
     */
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.layout_home_institution:
                InstituteItemBean bean = listData.get(position);

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("id", bean.getId());
                intent.putExtras(bundle);
                intent.setClass(getActivity(), InstitutionDetailActivity.class);
                startActivity(intent);
                break;

        }
    }

    /**
     * 筛选器title的变化
     * <p>
     * 点击到选中的item，自动收回
     *
     * @param position
     * @param positionTitle
     * @param urlValue
     */
    @Override
    public void onFilterDone(int position, String positionTitle, String urlValue) {
        //数据显示到筛选标题中
        dropDownMenu.setPositionIndicatorText(position, positionTitle);
        dropDownMenu.close();
    }

    /**
     * ===========================================================================================
     * ============================================显示列表===============================================
     * ===========================================================================================
     */
    private void initRecyclerView() {
        filterRecylerAdapter = new InstitutionFilterRecylerAdapter(getActivity(), listData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFilterRecyclerView.setLayoutManager(layoutManager);

        mFilterRecyclerView.setAdapter(filterRecylerAdapter);
        filterRecylerAdapter.setOnItemChildClickListener(this);
    }

    /**
     * ===========================================================================================
     * ============================================获取数据===============================================
     * ===========================================================================================
     *
     */

    /**
     * 获取筛选数据
     */
    private void loadFilterData() {
        loadingDialog.show();
        presenter.pLoadFilterData();
    }

    /**
     * 获取数据列表
     * 参数sp保存
     */
    private void loadListData() {
        MLog.d("加载筛选list");
        loadingDialog.show();
        presenter.pLoadListData(map);
    }

    /**
     * ================================================================================================
     * ==============================================接口回调==================================================
     * ================================================================================================
     */
    @Override
    public void onFilterConditionSuccess(Object object) {
        loadingDialog.dismiss();
        this.filterBean = (FilterBean) object;

        initFilterDropDownView();

        //设置缓存数据
        acache.remove(Constants.FILTER_ALL);
        //缓存三天
        acache.put(Constants.FILTER_ALL, this.filterBean, Constants.TIME_THREE_DAY);

        //缓存当前时间
        SPUtil.putString(Constants.FILTER_CURRENT_TIME, TimeUtil.getData());
    }

    @Override
    public void onFilterConditionFailed(int code, String msg, Exception e) {
        loadingDialog.dismiss();
    }

    @Override
    public void onListSuccess(Object obj) {
        loadingDialog.dismiss();
        MLog.d("获取数据成功+" + filterBean.toString());
        listData = (List<InstituteItemBean>) obj;
        initRecyclerView();
    }

    /**
     * 处理数据返回
     *
     * @param msg
     * @param e
     */
    @Override
    public void onListFailed(int code, String msg, Exception e) {
        loadingDialog.dismiss();
        /**
         * 当404时，返回数据为空list
         */
        if (code == 404) {
            listData = new ArrayList<>();//数据清空
            initRecyclerView();
        }
    }

    //
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

    /**
     * 使用RxBus 实现定位城市筛选
     */
    private void initRxBus() {
//        RxBus.getDefault().toObservable(RxCodeConstants.LOCATION_FILTER, RxBusBaseMessage.class)
//                .subscribe(new Action1<RxBusBaseMessage>() {
//                    @Override
//                    public void call(RxBusBaseMessage rxBusBaseMessage) {
//                        MLog.d("我的-RxBus消费-重新定位1",SPUtil.getLocation());
//                        if (listData != null && filterRecylerAdapter != null && map != null) {
//                            MLog.e("我的-RxBus消费-重新定位2",SPUtil.getLocation());
//                            map.put(Constants.FILTER_LOCATION_CITY, SPUtil.getLocation());
//                            loadListData();
//                        }
//                    }
//                });
    }
}
