package com.yilaole.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.adapter.institution.DropMenuAdapter;
import com.yilaole.adapter.institution.InstitutionFilterRecylerAdapter;
import com.yilaole.base.BaseActivity;
import com.yilaole.base.adapterbase.BaseQuickAdapter;
import com.yilaole.base.adapterbase.loadmore.SimpleLoadMoreView;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.institution.filter.FilterBean;
import com.yilaole.bean.institution.filter.InstituteItemBean;
import com.yilaole.bean.institution.filter.InstitutionPriceBean;
import com.yilaole.filter.DropDownMenu;
import com.yilaole.filter.interfaces.OnFilterDoneListener;
import com.yilaole.http.cache.ACache;
import com.yilaole.inter_face.ipresenter.IInstitutionFilterPresenter;
import com.yilaole.inter_face.iview.IInstitutionView;
import com.yilaole.presenter.InstitutionFilterPresenterImpl;
import com.yilaole.save.SPUtil;
import com.yilaole.utils.MLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yilaole.R.id.tv_search;

/**
 * 机构列表筛选 上拉下拉刷新数据list
 * <p>
 * 注：数据列表的获取必须在筛选器构构建完成后才可以调用
 * (1)上拉下拉数据加载
 * 初始数据20个，每次加载10条，刷新重置20条
 * (2)关于定位：
 * 当首页定位到具体城市后，进入该页面（首次或者任意时候），首先加载的是定位城市的数据，当触发筛选条件的时候，定位城市的参数就不可用了，设置成""
 */

public class InstitutionActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, IInstitutionView,
        OnFilterDoneListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    //搜索按钮
    @BindView(tv_search)
    EditText et_search;

    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;


    @BindView(R.id.layout_loaddata)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.mFilterRecyclerView)
    RecyclerView recyclerView;

    //
    private IInstitutionFilterPresenter presenter;//p层
    private FilterBean filterBean;
    private ACache acache;
    private InstitutionFilterRecylerAdapter recylerAdapter;//数据列表适配
    private DropMenuAdapter dropMenuAdapter;
    private String[] titleList;
    private List<String> sortListData;
    private List<InstituteItemBean> listData;//列表数据片段（从网络获取的数据）
    private Map<String, Object> map = new HashMap();//保存上传参数集合

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
    private String locationCity;
    //数据的参数
    private int totle = 0;
    private int size = 20;//初始加载+刷新都是20条数据，加载更多 每次10条数据


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_institution_filter);
        ButterKnife.bind(this);
        initMyView();
        getFilterCacheData();
        initListener();

    }
    /**
     * ============================================================================================================
     * ====================================================初始化及监听========================================================
     * ============================================================================================================
     */
    /**
     * 初始化数据，包括缓存中获取
     */
    private void initMyView() {
        getSearchIntentData();
        initRecycler();
        acache = ACache.get(this);
        presenter = new InstitutionFilterPresenterImpl(this, this);
        //筛选标题数据（固定数据）
        titleList = new String[]{"区域", "筛选", "价格", "排序"};
        //筛选-排序数据（固定数据）
        sortListData = new ArrayList<>();
        sortListData.add("综合排序");
        sortListData.add("价格 从高到底");
        sortListData.add("价格 从低到高");

        filterBean = (FilterBean) acache.getAsObject(Constants.FILTER_ALL);
        token = acache.getAsString(Constants.TOKEN);
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }


    /**
     * 处理从搜索界面跳转过来的数据
     * SearchHistoryActivity
     */
    private void getSearchIntentData() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            searchKeys = bundle.getString("searchKey", "");
            MLog.d("搜索跳转 参数", searchKeys);
            //让搜索内容显示出来
            et_search.setText(searchKeys);
        }

    }

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
                    InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    manager.hideSoftInputFromWindow(textView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //隐藏光标
                    et_search.setCursorVisible(false);
                    initSearchKeys();

                    //首页定位的城市，需要删除
                    map.remove(Constants.FILTER_LOCATION_CITY);
                    map.put(Constants.FILTER_LOCATION_CITY, "");

                    //获取接口
                    loadListData();
                } else {
                    initSearchKeys();

                    //首页定位的城市，需要删除
                    map.remove(Constants.FILTER_LOCATION_CITY);
                    map.put(Constants.FILTER_LOCATION_CITY, "");

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
     * 第一次获取，且token="",获取到数据
     * 当登陆后，token!=空，需要再获取新数据
     */

    private void getFilterCacheData() {

        if (filterBean != null && titleList != null && sortListData != null) {
            initFilterDropDownView();
        } else {
            loadFilterData();
        }
    }

    /**
     * 筛选框 初始化+获取列表数据+筛选条件监听
     */
    private void initFilterDropDownView() {
        //保存跳转参数
        initID();
        //绑定数据源
        dropMenuAdapter = new DropMenuAdapter(this, titleList, this);
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

                MLog.e(InstitutionActivity.this, "MultiGridView回调给==DropMenuAdapter的结果=="
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
        loadListData();
    }

    /**
     * 上传参数封装到map中
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
        locationCity = SPUtil.getLocation();

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
        map.put(Constants.FILTER_LOCATION_CITY, locationCity);//定位城市，默认为""
    }

    private void initSearchKeys() {
        map.put(Constants.SEARCH_KEYS, searchKeys);
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
                intent.setClass(this, InstitutionDetailActivity.class);
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


    /**
     * 初始显示 初始化adapter
     */
    private void initRecyclerView() {

        recylerAdapter = new InstitutionFilterRecylerAdapter(this, listData);
        recylerAdapter.setOnLoadMoreListener(this, recyclerView);
        //        recylerAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);//动画效果
        recylerAdapter.setPreLoadNumber(1);//倒数第一个时，触发加载
        recylerAdapter.setLoadMoreView(new SimpleLoadMoreView());//设置自定义 加载更多 样式（包括正在加载，加载完成，加载失败的提示）
        recyclerView.setAdapter(recylerAdapter);
        recylerAdapter.setOnItemChildClickListener(this);

        //
        totle = recylerAdapter.getData().size();//totol计数
        size = 10;//之后加载每次10条

        if (listData.size() <= 0) {
            //初始化就没有数据，可以设置空白背景
            //            recylerAdapter.setEmptyView();
            recylerAdapter.setEnableLoadMore(false);//不允许加载(没有footer提示)
        } else if (listData.size() < 20 && listData.size() > 0) {
            //            recylerAdapter.setEnableLoadMore(false);//不允许加载(没有footer提示)

            //数据全部加载完毕，显示 没有更多数据
            recylerAdapter.loadMoreEnd();
        } else {
            recylerAdapter.setEnableLoadMore(true);//允许加载
        }
    }

    /**
     * 刷新显示
     */
    private void initRefreshRecyclerView() {

        //更新数据
        recylerAdapter.setNewData(listData);

        //
        totle = recylerAdapter.getData().size();
        size = 10;//之后加载每次10条
        if (listData.size() <= 0) {
            //初始化就没有数据，可以设置空白背景
            //            recylerAdapter.setEmptyView();
            recylerAdapter.setEnableLoadMore(false);//不允许加载(没有footer提示)
        } else if (listData.size() < 20) {
            //            recylerAdapter.setEnableLoadMore(false);//不允许加载(没有footer提示)
            //数据全部加载完毕，显示 没有更多数据
            recylerAdapter.loadMoreEnd();
        } else {
            recylerAdapter.setEnableLoadMore(true);//允许加载
        }
    }

    /**
     * 加载 显示
     */
    private void initMoreRecyclerView() {

        //拼接数据
        recylerAdapter.addData(listData);
        totle = recylerAdapter.getData().size();

        if (listData.size() <= 0) {
            //数据全部加载完毕，再次加载无加载footer
            recylerAdapter.loadMoreEnd();
        } else if (listData.size() < 10 && listData.size() > 0) {
            //数据全部加载完毕，显示 没有更多数据
            recylerAdapter.loadMoreEnd();
        } else {
            //本次加载结束，再次加载仍可用
            recylerAdapter.loadMoreComplete();
        }
    }

    /**
     * 下拉更新数据
     * 需要重置参数，adapter重新赋值
     */
    @Override
    public void onRefresh() {
        MLog.d("刷新");
        //重置参数
        totle = 0;
        size = 20;
        map.remove(Constants.PAGETOTLE);
        map.remove(Constants.PAGESIZE);
        map.put(Constants.PAGETOTLE, totle);
        map.put(Constants.PAGESIZE, size);

        //清空总数据
        listData.clear();
        //刷新
        refreshListData();
    }

    /**
     * 上拉 加载数据
     */
    @Override
    public void onLoadMoreRequested() {
        //上传必要参数
        map.remove(Constants.PAGETOTLE);
        map.remove(Constants.PAGESIZE);
        map.put(Constants.PAGETOTLE, totle);
        map.put(Constants.PAGESIZE, size);

        //加载
        moreListData();

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
        loadingDialog.show();
        presenter.pLoadListData(map);
    }

    /**
     * 刷新
     */
    private void refreshListData() {
        swipeRefreshLayout.setRefreshing(true);
        presenter.prefreshListData(map);
    }

    /**
     * 加载更多
     * <size的关闭加载情况
     */
    private void moreListData() {
        presenter.pMoreListData(map);
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

    }

    @Override
    public void onFilterConditionFailed(int code, String msg, Exception e) {
        loadingDialog.dismiss();
    }

    //初始化list显示
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

    //刷新接口回调
    @Override
    public void onRefreshSuccess(Object bean) {
        swipeRefreshLayout.setRefreshing(false);
        listData = (List<InstituteItemBean>) bean;
        initRefreshRecyclerView();
    }

    @Override
    public void onRefreshFailed(int code, String msg, Exception e) {
        swipeRefreshLayout.setRefreshing(false);
        if (code == 404) {
            listData = new ArrayList<>();//数据清空
            initRefreshRecyclerView();
        }
    }

    //加载更多接口回调
    @Override
    public void onMoreSuccess(Object obj) {
        listData = (List<InstituteItemBean>) obj;
        initMoreRecyclerView();
    }

    @Override
    public void onMoreFailed(int code, String msg, Exception e) {

        if (code == 404) {
            listData = new ArrayList<>();//数据清空
            initRefreshRecyclerView();
        }
    }

    /**
     *
     */

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (listData != null) {
            listData.clear();
        }
    }


}
