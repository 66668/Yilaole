package com.yilaole.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.base.BaseActivity;
import com.yilaole.http.cache.ACache;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人 关于我们-政策
 */

public class MinePolicyActivity extends BaseActivity {

    //
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.layout_back)
    RelativeLayout layout_back;

    @BindView(R.id.webview)
    WebView webview;

    private ACache aCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mine_policy);
        ButterKnife.bind(this);
        initMyView();
    }

    private void initMyView() {
        tv_title.setText(getResources().getString(R.string.about_policy));
        aCache = ACache.get(this);
        webview.setWebViewClient(new WebViewClient());

        webview.loadUrl("file:///android_asset/policy.html");//路径显示
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webview != null) {
            webview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webview.clearHistory();

            webview.removeView(webview);
            webview.destroy();
            webview = null;
        }
    }
}
