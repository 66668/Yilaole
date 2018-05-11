package com.yilaole.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.base.BaseActivity;
import com.yilaole.http.cache.ACache;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人 设置
 */

public class MineSettingActivity extends BaseActivity {

    //
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.layout_back)
    RelativeLayout layout_back;

    @BindView(R.id.switch_message)
    Switch switch_message;

    @BindView(R.id.switch_wifi)
    Switch switch_wifi;


    private ACache aCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mine_setting);
        ButterKnife.bind(this);
        initMyView();
        initListener();
    }


    private void initListener() {
        switch_wifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
//                    MLog.ToastShort(MineSettingActivity.this, "打开！");
                } else {
//                    MLog.ToastShort(MineSettingActivity.this, "关闭！");
                }
            }
        });

        switch_message.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
//                    MLog.ToastShort(MineSettingActivity.this, "打开！");
                } else {
//                    MLog.ToastShort(MineSettingActivity.this, "关闭！");
                }
            }
        });

    }

    private void initMyView() {
        tv_title.setText(getResources().getString(R.string.setting_name));
        aCache = ACache.get(this);
    }

    /**
     * 监听
     *
     * @param view
     */
    @OnClick({R.id.layout_back, R.id.layout_clear})
    public void onItemClick(View view) {
        switch (view.getId()) {
            case R.id.layout_back:
                this.finish();
                break;
            case R.id.layout_clear:
//                MLog.ToastShort(this, "清除缓存操作！");
                break;
        }

    }

}
