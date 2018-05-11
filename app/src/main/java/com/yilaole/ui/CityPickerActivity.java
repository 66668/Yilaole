package com.yilaole.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.yilaole.R;
import com.yilaole.map.location.CheckPermissionsActivity;
import com.yilaole.map.location.CheckPermissionsListener;
import com.yilaole.map.location.adapter.CityListAdapter;
import com.yilaole.map.location.adapter.ResultListAdapter;
import com.yilaole.map.location.db.DBManager;
import com.yilaole.map.location.model.City;
import com.yilaole.map.location.model.LocateState;
import com.yilaole.map.location.utils.StringUtils;
import com.yilaole.map.location.widget.SideLetterBar;
import com.yilaole.utils.MLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页定位，选择
 */

public class CityPickerActivity extends CheckPermissionsActivity implements CheckPermissionsListener, View.OnClickListener {
    public static final String KEY_PICKED_CITY = "picked_city";

    @BindView(R.id.listview_all_city)
    ListView mListView;

    @BindView(R.id.tv_letter_overlay)
    TextView overlay;

    @BindView(R.id.side_letter_bar)
    SideLetterBar mLetterBar;

    @BindView(R.id.et_search)
    EditText searchBox;

    @BindView(R.id.listview_search_result)
    ListView mResultListView;

    @BindView(R.id.iv_search_clear)
    ImageView clearBtn;

    @BindView(R.id.layout_back)
    RelativeLayout backBtn;

    @BindView(R.id.empty_view)
    ViewGroup emptyView;

    @BindView(R.id.tv_title)
    TextView tv_title;


    private CityListAdapter mCityAdapter;
    private ResultListAdapter mResultAdapter;
    private List<City> mAllCities;
    private DBManager dbManager;

    private AMapLocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_localcity_list);
        ButterKnife.bind(this);
        tv_title.setText(R.string.cp_select_city);
        initData();
        initView();
        initLocation();
        //请求权限
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mLocationClient.startLocation();
        } else {
            requestPermissions(this, neededPermissions, this);
        }
    }

    private void initLocation() {
        mLocationClient = new AMapLocationClient(this);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        String city = aMapLocation.getCity();
                        String district = aMapLocation.getDistrict();
                        String location = StringUtils.extractLocation(city, district);
                        //定位成功
                        mCityAdapter.updateLocateState(LocateState.SUCCESS, location);
                    } else {
                        //定位失败
                        mCityAdapter.updateLocateState(LocateState.FAILED, null);
                    }
                }
            }
        });
        //        mLocationClient.startLocation();
    }

    private void initData() {
        dbManager = new DBManager(this);
        dbManager.copyDBFile();
        mAllCities = dbManager.getAllCities();
        mCityAdapter = new CityListAdapter(this, mAllCities);

        //点击监听
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {
                MLog.d("城市定位，点击监听",name);
                backWithData(name);
            }

            @Override
            public void onLocateClick() {
                MLog.d("城市定位，点击监听");
                mCityAdapter.updateLocateState(LocateState.LOCATING, null);
                requestPermissions(CityPickerActivity.this, neededPermissions, CityPickerActivity.this);
                //                mLocationClient.startLocation();
            }
        });

        mResultAdapter = new ResultListAdapter(this, null);
    }

    private void initView() {
        mListView.setAdapter(mCityAdapter);

        mLetterBar.setOverlay(overlay);
        mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                mListView.setSelection(position);
            }
        });

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    clearBtn.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    mResultListView.setVisibility(View.GONE);
                } else {
                    clearBtn.setVisibility(View.VISIBLE);
                    mResultListView.setVisibility(View.VISIBLE);
                    List<City> result = dbManager.searchCity(keyword);
                    if (result == null || result.size() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        mResultAdapter.changeData(result);
                    }
                }
            }
        });


        mResultListView.setAdapter(mResultAdapter);
        mResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MLog.d("mResultListView 城市选择监听");
                backWithData(mResultAdapter.getItem(position).getName());
            }
        });

        clearBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }

    /**
     * 点击list
     * @param city
     */
    private void backWithData(String city) {

        Intent data = new Intent();
        data.putExtra(KEY_PICKED_CITY, city);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_search_clear) {
            searchBox.setText("");
            clearBtn.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
            mResultListView.setVisibility(View.GONE);
        } else if (i == R.id.layout_back) {
            finish();

        }
    }

    @Override
    public void onGranted() {
        mLocationClient.startLocation();
    }

    @Override
    public void onDenied(List<String> permissions) {
        Toast.makeText(this, "权限被禁用，请到设置里打开", Toast.LENGTH_SHORT).show();
        mCityAdapter.updateLocateState(LocateState.FAILED, null);
    }
}
