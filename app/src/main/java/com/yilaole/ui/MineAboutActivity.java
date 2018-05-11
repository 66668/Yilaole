package com.yilaole.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.base.BaseActivity;
import com.yilaole.http.cache.ACache;
import com.yilaole.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人 关于我们
 */

public class MineAboutActivity extends BaseActivity {

    //
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.layout_back)
    RelativeLayout layout_back;


    private ACache aCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mine_aboutus);
        ButterKnife.bind(this);
        initMyView();
    }


    private void initShow() {


    }

    private void initMyView() {
        tv_title.setText(getResources().getString(R.string.mine_about));
        aCache = ACache.get(this);
    }

    /**
     * 监听
     *
     * @param view
     */
    @OnClick({R.id.layout_back, R.id.feedback, R.id.policy, R.id.update})
    public void onItemClick(View view) {
        switch (view.getId()) {
            case R.id.layout_back:
                this.finish();
                break;
            case R.id.feedback://
                startActivity(MineFeedBackActivity.class);
                break;
            case R.id.policy:
                startActivity(MinePolicyActivity.class);
                break;
            case R.id.update:
                ToastUtil.ToastShort(this, "暂无更新");
                break;

        }

    }

}
