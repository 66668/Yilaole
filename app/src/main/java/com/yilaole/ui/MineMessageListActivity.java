package com.yilaole.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.adapter.mine.MineMessageRecyclerAdapter;
import com.yilaole.base.BaseActivity;
import com.yilaole.bean.mine.MessageItemBean;
import com.yilaole.http.cache.ACache;
import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.presenter.MineMessagePresenterImpl;
import com.yilaole.utils.MLog;
import com.yilaole.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人 我的消息
 */

public class MineMessageListActivity extends BaseActivity implements OnCommonListener {

    //
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.layout_back)
    RelativeLayout layout_back;

    @BindView(R.id.message_recyclerView)
    RecyclerView message_recyclerView;


    private ACache aCache;
    private MineMessagePresenterImpl presenter;
    private List<MessageItemBean> listData;
    private MineMessageRecyclerAdapter adapter;
    private LinearLayoutManager manager;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mine_message);
        ButterKnife.bind(this);
        initMyView();
        getData();
        initShow();
        //        loadData();
    }


    private void initMyView() {
        tv_title.setText(getResources().getString(R.string.mine_message));
        aCache = ACache.get(this);
        presenter = new MineMessagePresenterImpl(this, this);
        adapter = new MineMessageRecyclerAdapter(this);
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

    private void getData() {

        listData = new ArrayList<>();
//        listData.add(new MessageItemBean());
//        listData.add(new MessageItemBean());
//        listData.add(new MessageItemBean());
//        listData.add(new MessageItemBean());
//        listData.add(new MessageItemBean());
//        listData.add(new MessageItemBean());
//        listData.add(new MessageItemBean());
//        listData.add(new MessageItemBean());
//        listData.add(new MessageItemBean());
//        listData.add(new MessageItemBean());
    }


    private void initShow() {
        adapter.setList(listData);
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        message_recyclerView.setLayoutManager(manager);
        message_recyclerView.setAdapter(adapter);

        //详情跳转
        adapter.setOnItemClickListener(new MineMessageRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                startActivity(MineMessageDetailActivity.class);
            }
        });

    }


    private void loadData() {
        loadingDialog.show();

    }

    @Override
    public void onDataSuccess(Object obj) {
        loadingDialog.dismiss();
    }

    @Override
    public void onDataFailed(int code,String msg, Exception e) {
        loadingDialog.dismiss();

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
