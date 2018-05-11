package com.yilaole.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.mapapi.search.district.DistrictSearch;
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.yilaole.R;
import com.yilaole.base.BaseActivity;
import com.yilaole.filter.DropDownMenuMap;
import com.yilaole.map.lbs_map.PoiOverlay;
import com.yilaole.map.slidingup.SlidingUpPanelLayout;
import com.yilaole.save.SPUtil;
import com.yilaole.utils.MLog;
import com.yilaole.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 养老地图
 * Created by sjy on 2017/11/7.
 */

public class MapOfPensionActivity extends BaseActivity implements OnGetPoiSearchResultListener, OnGetDistricSearchResultListener {

    //区名
    @BindView(R.id.distract)
    TextView distract;

    //自定义总布局
    @BindView(R.id.layout_main)
    SlidingUpPanelLayout layout_main;

    //地图
    @BindView(R.id.map)
    MapView mapView;

    //updown指示图
    @BindView(R.id.img_upDown)
    ImageView img_upDown;

    //滑动布局
    @BindView(R.id.layout_slidingup)
    LinearLayout layout_slidingup;

    //筛选器
    @BindView(R.id.dropDownMenuMap)
    DropDownMenuMap dropDownMenuMap;

    //
    @BindView(R.id.mFilterRecyclerView)
    RecyclerView mFilterRecyclerView;


    private BaiduMap mBaiduMap;
    private String city;//需要定位的城市
    private final int color = 0xAA00FF00;//边界颜色
    private DistrictSearch mDistrictSearch;//区域检索类
    private PoiSearch mPoiSearch = null;//POI检索

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_map_pension);
        ButterKnife.bind(this);

        initMyView();
        initSearch();
        initSliding();
        initListener();
    }

    private void initMyView() {
        mBaiduMap = mapView.getMap();

        //获取定位城市，本app默认初始化是全国，若是全国且没有定位，默认改为北京
        city = SPUtil.getLocation();
        if (city.contains("全国")) {
            city = "北京";
        }
        // 初始化区域搜索模块，注册搜索事件监听
        mDistrictSearch = DistrictSearch.newInstance();
        mDistrictSearch.setOnDistrictSearchListener(this);

        // 初始化POI搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);



    }

    //默认搜索方法
    private void initSearch() {
        //行政区域检索
        //        mDistrictSearch.searchDistrict(new DistrictSearchOption().cityName("北京").districtName("昌平区"));

        //POI检索
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city("北京")
                .keyword("养老院")
                .pageNum(1));//若是多种搜索，设置搜索标记 区分
    }

    /**
     * ==============================================================监听=============================================================
     */

    private void initSliding() {
        //设置默认弹出高度为0.85占屏比
        layout_main.setAnchorPoint(0.85f);
        layout_main.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    private void initListener() {
        //滑动监听
        layout_main.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            //滑动的距离
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                //                MLog.d("PanelSlideListener-onPanelSlide-slideOffset=" + slideOffset);
            }

            //变化前后的状态
            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                MLog.d("PanelSlideListener-onPanelSlide-previousState=" + previousState + "--newState=" + newState);

                if (SlidingUpPanelLayout.PanelState.COLLAPSED == newState) {//底部固定状态
                    img_upDown.setBackground(ContextCompat.getDrawable(MapOfPensionActivity.this, R.mipmap.map_up));
                } else if (SlidingUpPanelLayout.PanelState.ANCHORED == newState || SlidingUpPanelLayout.PanelState.DRAGGING == newState) {
                    img_upDown.setBackground(ContextCompat.getDrawable(MapOfPensionActivity.this, R.mipmap.map_down));
                }
            }
        });

        layout_main.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_main.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                ToastUtil.ToastShort(MapOfPensionActivity.this, "COLLAPSED");
            }
        });
    }


    /**
     * ==============================================================POI检索=============================================================
     * POI检索
     * OnGetPoiSearchResultListener重写返回方法
     *
     * @param poiResult
     */
    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            ToastUtil.ToastShort(MapOfPensionActivity.this, "未找到结果");
            return;
        }
        if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
            mBaiduMap.clear();
            PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(poiResult);
            overlay.addToMap();
            overlay.zoomToSpan();
            return;
        }
        if (poiResult.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";
            for (CityInfo cityInfo : poiResult.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "找到结果";
            ToastUtil.ToastShort(MapOfPensionActivity.this, strInfo);
        }
    }

    /**
     * 获取POI详情搜索结果，得到searchPoiDetail返回的搜索结果
     *
     * @param poiDetailResult
     */
    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
            ToastUtil.ToastShort(MapOfPensionActivity.this, "抱歉，未找到结果");
        } else {
            ToastUtil.ToastShort(MapOfPensionActivity.this, poiDetailResult.getName() + ": " + poiDetailResult.getAddress());
        }
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    /**
     * 自定义内部类
     */
    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            // if (poi.hasCaterDetails) {
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));
            // }
            return true;
        }
    }

    /**
     * ==============================================================行政区域检索=============================================================
     * <p>
     * OnGetDistricSearchResultListener重写返回方法
     *
     * @param districtResult
     */
    @Override
    public void onGetDistrictResult(DistrictResult districtResult) {
        mBaiduMap.clear();
        if (districtResult == null) {
            return;
        }
        if (districtResult.error == SearchResult.ERRORNO.NO_ERROR) {
            List<List<LatLng>> polyLines = districtResult.getPolylines();
            if (polyLines == null) {
                return;
            }
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (List<LatLng> polyline : polyLines) {

                //行政区边界颜色
                OverlayOptions ooPolyline11 = new PolylineOptions()
                        .width(10)
                        .points(polyline)
                        .dottedLine(true)
                        .color(color);
                mBaiduMap.addOverlay(ooPolyline11);

                //行政区填充颜色
                OverlayOptions ooPolygon = new PolygonOptions()
                        .points(polyline)
                        .stroke(new Stroke(5, 0xAA00FF88))
                        .fillColor(0xAAFFFFFF);
                mBaiduMap.addOverlay(ooPolygon);

                for (LatLng latLng : polyline) {
                    builder.include(latLng);
                }
            }
            mBaiduMap.setMapStatus(MapStatusUpdateFactory
                    .newLatLngBounds(builder.build()));

        }
    }

    @Override
    protected void onDestroy() {
        mDistrictSearch.destroy();
        mPoiSearch.destroy();
        super.onDestroy();
    }

    //监听 slidingUp状态
    @Override
    public void onBackPressed() {
        if (layout_main != null &&
                (layout_main.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || layout_main.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            layout_main.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

}
