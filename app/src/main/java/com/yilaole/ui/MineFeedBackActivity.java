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
 * 个人 关于我们-意见反馈
 */

public class MineFeedBackActivity extends BaseActivity {

    //
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.layout_back)
    RelativeLayout layout_back;


    private ACache aCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mine_feedback);
        ButterKnife.bind(this);
        initMyView();
    }


    private void initShow() {


    }

    private void initMyView() {
        tv_title.setText(getResources().getString(R.string.about_feedback));
        aCache = ACache.get(this);
    }

    /**
     * 监听
     *
     * @param view
     */
    @OnClick({R.id.layout_back, R.id.btn_post})
    public void onItemClick(View view) {
        switch (view.getId()) {
            case R.id.layout_back:
                this.finish();
                break;
            case R.id.btn_post:
                ToastUtil.ToastShort(this, "提交失败");
                break;

        }

    }

}
