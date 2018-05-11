package com.yilaole.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.adapter.SearchHistoryRecyclerAdapter;
import com.yilaole.adapter.SearchHotRecyclerAdapter;
import com.yilaole.base.BaseActivity;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.TextBean;
import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.presenter.SearchHistoryActivityPresenterImpl;
import com.yilaole.save.SPUtil;
import com.yilaole.utils.MLog;
import com.yilaole.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 搜索历史
 */

public class SearchHistoryActivity extends BaseActivity implements OnCommonListener {
    //后退
    @BindView(R.id.layout_back)
    RelativeLayout layout_back;

    //热门机构
    @BindView(R.id.list_hot)
    RecyclerView list_hot;

    //删除所有
    @BindView(R.id.img_deleteAll)
    ImageView img_deleteAll;

    //搜索历史
    @BindView(R.id.listView)
    RecyclerView listView;

    //搜索内容
    @BindView(R.id.et_search)
    EditText et_search;

    //搜索按钮
    @BindView(R.id.btn_search)
    TextView btn_search;
    List<TextBean> hotList;
    List<String> historyList;

    SearchHistoryRecyclerAdapter historyAdapter;
    SearchHotRecyclerAdapter hotAdapter;
    LinearLayoutManager hotLayoutManager;
    LinearLayoutManager historyLayoutManager;
    String searchCotent;
    SearchHistoryActivityPresenterImpl presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search_history);
        ButterKnife.bind(this);
        getHistoryData();
        initMyView();
        initListener();
    }

    /**
     * ========================================================================================================
     * ====================================================初始化====================================================
     * ========================================================================================================
     */
    private void initMyView() {
        hotList = new ArrayList<>();
        presenter = new SearchHistoryActivityPresenterImpl(this);
        initHotShow();
        initSearchHistoryShow();

    }


    /**
     * 监听搜索
     */
    private void initListener() {
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchCotent = editable.toString().trim();
            }
        });

    }

    @OnClick({R.id.btn_search, R.id.layout_back, R.id.img_deleteAll})
    public void onClickListenter(View view) {
        switch (view.getId()) {
            case R.id.btn_search://搜索按钮
                startSearch();
                break;
            case R.id.img_deleteAll://删除
                deleteAllHistory();
                break;
            case R.id.layout_back://后退
                this.finish();
                break;
        }
    }

    /**
     * 进入界面加载历史数据
     */
    private void getHistoryData() {

        historyList = new ArrayList<>();

        String searchKey = SPUtil.getString(Constants.KEY_SEARCH_KEY, "");
        if (!TextUtils.isEmpty(searchKey)) {
            String[] keys = searchKey.split(",");
            if (keys != null)
                for (int i = 0; i < keys.length; i++) {
                    if (!TextUtils.isEmpty(keys[i])) {
                        historyList.add(keys[i]);
                    }
                }
        }
    }

    /**
     * 删除所有记录
     */
    private void deleteAllHistory() {
        //清空搜索历史
        SPUtil.putString(Constants.KEY_SEARCH_KEY, "");
        getHistoryData();
        historyAdapter.setList(historyList);
    }

    /**
     * sp保存
     *
     * @param key
     */

    private void saveSearchContent(String key) {
        String searchKey = SPUtil.getString(Constants.KEY_SEARCH_KEY, "");
        if (!TextUtils.isEmpty(searchKey) && !searchKey.contains(key)) {
            searchKey += "," + key;
        } else {
            searchKey = key;
        }
        SPUtil.putString(Constants.KEY_SEARCH_KEY, searchKey);
    }
    /**
     * ========================================================================================================
     * ====================================================显示====================================================
     * ========================================================================================================
     */

    /**
     * 显示热门
     */
    private void initHotShow() {
        hotLayoutManager = new LinearLayoutManager(this);
        hotLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);//竖屏展示
        list_hot.setLayoutManager(hotLayoutManager);
        //获取到数据后的操作
        hotAdapter = new SearchHotRecyclerAdapter(this);
        hotAdapter.setList(hotList);
        list_hot.setAdapter(hotAdapter);
        //热门机构的跳转
        hotAdapter.setOnItemClickListener(new SearchHotRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                MLog.ToastShort(SearchHistoryActivity.this, "点击:" + position);
            }
        });
    }

    /**
     * 显示搜索历史
     */
    private void initSearchHistoryShow() {
        //
        historyLayoutManager = new LinearLayoutManager(this);
        historyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);//竖屏展示
        listView.setLayoutManager(historyLayoutManager);

        historyAdapter = new SearchHistoryRecyclerAdapter(this);
        historyAdapter.setList(historyList);
        listView.setAdapter(historyAdapter);
        historyAdapter.setOnItemClickListener(new SearchHistoryRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                et_search.setText(historyList.get(position));
            }
        });
    }

    /**
     * ========================================================================================================
     * ====================================================接口数据====================================================
     * ========================================================================================================
     */
    private void startSearch() {
        searchCotent = et_search.getText().toString();

        if (TextUtils.isEmpty(searchCotent)) {
            MLog.ToastShort(SearchHistoryActivity.this, "搜索内容不能为空");
            return;
        }

        //保存搜索内容 搜索历史使用
        saveSearchContent(searchCotent);

        //
        Bundle bundle = new Bundle();
        bundle.putString("searchKey", searchCotent);
        startActivity(InstitutionActivity.class, bundle);
        this.finish();
    }

    /**
     * ========================================================================================================
     * ====================================================接口回调====================================================
     * ========================================================================================================
     */

    @Override
    public void onDataSuccess(Object obj) {

    }

    @Override
    public void onDataFailed(int code,String msg, Exception e) {

        if (code == -1) {
            ToastUtil.ToastShort(this, msg);
        } else if (code == 404) {
            ToastUtil.ToastShort(this, msg);
            MLog.e(msg, e.toString());
        } else {
            ToastUtil.ToastShort(this, msg);
            MLog.e(msg, e.toString());
        }
    }
}