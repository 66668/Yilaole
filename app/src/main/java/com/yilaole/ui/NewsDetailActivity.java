package com.yilaole.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yilaole.R;
import com.yilaole.adapter.news.NewsDetailCommentRecylerAdapter;
import com.yilaole.adapter.news.NewsDetailLabelRecylerAdapter;
import com.yilaole.adapter.news.NewsHotRecylerAdapter;
import com.yilaole.base.BaseActivity;
import com.yilaole.base.adapterbase.BaseQuickAdapter;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.news.HotNewsBean;
import com.yilaole.bean.news.NewsCommentBean;
import com.yilaole.bean.news.NewsDetailBean;
import com.yilaole.dialog.NewsCommentDialog;
import com.yilaole.dialog.NewsTextSizeDialog;
import com.yilaole.http.cache.ACache;
import com.yilaole.inter_face.idialog.INewsCommentDialogCallback;
import com.yilaole.inter_face.idialog.INewsShareDialogCallback;
import com.yilaole.inter_face.idialog.INewsTextSizeDialogCallback;
import com.yilaole.inter_face.ilistener.OnNewsDetailListener;
import com.yilaole.presenter.NewsDetailPresenterImpl;
import com.yilaole.save.SPUtil;
import com.yilaole.utils.GlideCircleTransform;
import com.yilaole.utils.MLog;
import com.yilaole.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 资讯详情
 */

public class NewsDetailActivity extends BaseActivity implements OnNewsDetailListener, INewsCommentDialogCallback, INewsShareDialogCallback, INewsTextSizeDialogCallback {

    //
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    //资讯标题
    @BindView(R.id.tv_newTitle)
    TextView tv_newTitle;

    //作者照片
    @BindView(R.id.img_author)
    ImageView img_author;
    //作者名
    @BindView(R.id.tv_author)
    TextView tv_author;
    //时间
    @BindView(R.id.tv_author_time)
    TextView tv_author_time;

    //    //内容
    //    @BindView(R.id.tv_news_content)
    //    TextView tv_news_content;
    @BindView(R.id.content_webview)
    WebView content_webview;
    //标签
    @BindView(R.id.label_recylcerView)
    RecyclerView label_recylcerView;
    //评论
    @BindView(R.id.comment_recyclerView)
    RecyclerView comment_recyclerView;

    //热门
    @BindView(R.id.news_hot_recyclerView)
    RecyclerView news_hot_recyclerView;
    //赞图
    @BindView(R.id.img_support)
    ImageView img_support;

    //赞
    @BindView(R.id.tv_SupportNum)
    TextView tv_SupportNum;

    //底部评论
    @BindView(R.id.tv_writeComment)
    TextView tv_writeComment;

    //变量
    private ACache aCache;
    private NewsDetailPresenterImpl presenter;
    private int newsID;//资讯id，有跳转获取
    private boolean isHot = false;//判断改文章是否是热门，有跳转获取（若是热门之间的跳转，前一个热门页面消失）
    private String token = "";
    private String commentContent = "";//评论内容
    private NewsDetailBean detailBean;
    private ArrayList<HotNewsBean> hotList;//热门数据
    private ArrayList<NewsCommentBean> commentList;//评论数据
    private NewsDetailCommentRecylerAdapter commentAdapter;//评论适配
    private NewsDetailLabelRecylerAdapter labelRecylerAdapter;//标签适配
    private NewsHotRecylerAdapter hotAdapter;//热门文章适配
    private ACache maCache;//缓存类
    private WebSettings webSettings;//webView子类
    //判断加载动画 完成标记

    private boolean isDetialFinish = false;//
    private boolean isHotFinish = false;//
    private boolean isCommentFinish = false;//

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_news_detail);
        ButterKnife.bind(this);
        initMyView();
        loadData();
    }

    /**
     * 初始化
     */

    private void initMyView() {
        initToolBar();
        presenter = new NewsDetailPresenterImpl(this);
        labelRecylerAdapter = new NewsDetailLabelRecylerAdapter(this);
        //先获取缓存
        maCache = ACache.get(this);
        hotList = (ArrayList<HotNewsBean>) maCache.getAsObject(Constants.NEWS_HOT_LIST);
        if (!isHot) {
            getIntentData();
        }
        aCache = ACache.get(this);
        token = getCurrentToken();
        //
        webSettings = content_webview.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持js
    }

    private String getCurrentToken() {
        return aCache.getAsString(Constants.TOKEN);
    }

    /**
     * 获取跳转传值
     */
    private void getIntentData() {
        isHot = getIntent().getBooleanExtra(Constants.NEWS_ISHOT, false);
        newsID = getIntent().getIntExtra(Constants.NEWS_ID, -1);
        MLog.d("获取跳转--", "id=" + newsID, "ishot=" + isHot);
    }

    private void loadData() {
        getHotCache();//获取热门文章缓存
        loadDetailData();
        loadNewsCommentData();
    }

    private void getHotCache() {
        //01 搜索轮播
        if (hotList != null && hotList.size() > 0) {
            isHotFinish = true;
            initHotNews();
        } else {
            loadHotData();
        }
    }

    @OnClick({R.id.tv_writeComment, R.id.img_write, R.id.img_send, R.id.img_support, R.id.tv_share_weichat, R.id.tv_share_friends})
    public void onClickHandler(View view) {
        switch (view.getId()) {
            case R.id.img_write:
            case R.id.tv_writeComment://打开评论

                token = getCurrentToken();

                MLog.d("打开评论token", token);
                if (token.isEmpty()) {
                    // TODO: 2017/10/18 该处逻辑需要修改，在该处自动登录
                    //该处
                    ToastUtil.toastInMiddle(this, "请先登录！");
                    return;
                }

                NewsCommentDialog commentDialogFragment = new NewsCommentDialog();
                commentDialogFragment.show(getFragmentManager(), Constants.NEWS_DETAIL_COMMENT_TAG);
                break;
            case R.id.img_send:
                MLog.d("发布评论token", token);
                if (token.isEmpty()) {
                    // TODO: 2017/10/18 该处逻辑需要修改，在该处自动登录
                    //该处
                    ToastUtil.toastInMiddle(this, "请先登录！");
                    return;
                }
                sendNewsComment();
                break;
            case R.id.img_support://点赞
                if (detailBean.getIs_support() == 1) {
                    ToastUtil.ToastShort(this, "已点赞！");
                    return;
                }
                token = getCurrentToken();
                MLog.d("点赞token", token);
                if (token.isEmpty()) {
                    // TODO: 2017/10/18 该处逻辑需要修改，在该处自动登录
                    //该处
                    ToastUtil.toastInMiddle(this, "请先登录！");
                    return;
                }
                //点赞接口
                postSupport();
                break;
            case R.id.tv_share_weichat://微信分享
                token = getCurrentToken();
                MLog.d("微信分享", token);
                if (token.isEmpty()) {
                    // TODO: 2017/10/18 该处逻辑需要修改，在该处自动登录
                    //该处
                    MLog.toastInMiddle(NewsDetailActivity.this, "请先登录！");
                } else {
                    getWeichat();
                }
                break;
            case R.id.tv_share_friends://朋友圈分享

                token = getCurrentToken();
                MLog.d("朋友圈分享", token);
                if (token.isEmpty()) {
                    // TODO: 2017/10/18 该处逻辑需要修改，在该处自动登录
                    //该处
                    MLog.toastInMiddle(NewsDetailActivity.this, "请先登录！");
                } else {
                    getFriends();

                }
                break;
            default:
                break;
        }

    }

    /**
     * =============================================================================
     * ==========================================显示===================================
     * =============================================================================
     */
    /**
     * 详情显示
     */
    private void initShowDetail() {

        //toolbar
        toolbar.setTitle(detailBean.getTitle());

        //图片
        String path = "";
        if (!detailBean.getImage().contains("http")) {
            path = Constants.NEW_HTTP + detailBean.getImage();
        } else {
            path = detailBean.getImage();
        }
        Glide.with(this).load(path)
                .error(ContextCompat.getDrawable(this, R.mipmap.item_bg_default1))
                .placeholder(ContextCompat.getDrawable(this, R.mipmap.item_bg_default1))
                .crossFade()//动画效果显示
                .transform(new GlideCircleTransform(this))//自定义圆形图片
                .into(img_author);

        //标题
        tv_newTitle.setText(detailBean.getTitle());
        //作者名
        tv_author.setText(detailBean.getAuthor());
        //时间
        tv_author_time.setText(detailBean.getTime());

        //内容 咨询详情html代码只是静态显示，不涉及js交互，比较简单
        content_webview.setWebViewClient(new MyWebViewClient());
        content_webview.loadDataWithBaseURL(null, detailBean.getContent(), "text/html", "utf-8", null);

        //赞
        tv_SupportNum.setText(detailBean.getSupport_number() + "");
        if (detailBean.getIs_support() == 1) {
            tv_SupportNum.setTextColor(ContextCompat.getColor(this, R.color.colorPrice));
            img_support.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.news_good1));
        } else {//0或空
            tv_SupportNum.setTextColor(ContextCompat.getColor(this, R.color.institute_tv1));
            img_support.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.news_good0));
        }

        //标签
        MLog.d("标签", detailBean.getLabel().size());
        labelRecylerAdapter.setDataList(detailBean.getLabel());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        label_recylcerView.setLayoutManager(manager);
        label_recylcerView.setAdapter(labelRecylerAdapter);

    }


    /**
     * 评论列表显示
     */
    private void initCommentShow() {
        commentAdapter = new NewsDetailCommentRecylerAdapter(this, commentList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        comment_recyclerView.setLayoutManager(manager);
        comment_recyclerView.setAdapter(commentAdapter);
        commentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.layout_comment:
                        MLog.ToastShort(NewsDetailActivity.this, "回复该评论！并弹窗");
                        break;
                }
            }
        });
    }

    /**
     * 热门显示
     */

    private void initHotNews() {
        hotAdapter = new NewsHotRecylerAdapter(this, hotList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        news_hot_recyclerView.setLayoutManager(manager);
        news_hot_recyclerView.setAdapter(hotAdapter);

        //热门监听
        hotAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.layout_news_item:
                        isHot = true;
                        HotNewsBean bean = (HotNewsBean) (adapter.getItem(position));
                        newsID = bean.getId();
                        //调 onResume方法重新加载
                        onResume();
                        break;
                }
            }
        });
    }


    /**
     * =============================================================================
     * ==========================================private 方法===================================
     * =============================================================================
     */

    /**
     * 设置字体大小
     */
    private void changeTextSizeShow() {
        //        tv_news_content.setTextSize(SPUtil.getNewsSize());
    }

    private void getWeichat() {
        //        MLog.ToastShort(this, "分享");
    }

    private void getFriends() {
        //        MLog.ToastShort(this, "分享");
    }

    /**
     * 重写WebViewClient 修改图片，适应手机尺寸
     */
    public class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            imgReset();//重置webview中img标签的图片大小
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        /**
         * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
         **/
        private void imgReset() {
            content_webview.loadUrl("javascript:(function(){" +
                    "var objs = document.getElementsByTagName('img'); " +
                    "for(var i=0;i<objs.length;i++)  " +
                    "{"
                    + "var img = objs[i];   " +
                    "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                    "}" +
                    "})()");
        }

    }

    /**
     * =============================================================================
     * ==========================================调接口===================================
     * =============================================================================
     */


    /**
     * 详情数据
     */
    private void loadDetailData() {
        if (token.isEmpty()) {
            MLog.d("咨询详情", "没有传token");
        }
        isDetialFinish = false;
        loadingDialog.show();
        presenter.pGetDetailData(newsID, token);
    }

    /**
     * 热门文章
     */
    private void loadHotData() {
        isHotFinish = false;
        loadingDialog.show();
        presenter.pGetHotNewsData();

    }

    /**
     * 评论列表
     */
    private void loadNewsCommentData() {
        isCommentFinish = false;
        loadingDialog.show();
        presenter.pGetNewsCommentData(newsID);

    }

    /**
     * 发布评论
     */
    private void sendNewsComment() {
        MLog.d("发布评论commentContent", commentContent);
        if (commentContent.isEmpty()) {
            ToastUtil.ToastShort(this, "评论不能为空！");
            return;
        }
        loadingDialog.show();
        presenter.pSendDetailCommnetData(newsID, token, commentContent, 0);

    }

    /**
     * 点赞
     */
    private void postSupport() {
        presenter.pSupprotNewsData(newsID, token);
    }


    /**
     * =============================================================================
     * ==========================================接口回调===================================
     * =============================================================================
     */

    //
    @Override
    public void onDetailSuccess(Object obj) {
        isDetialFinish = true;
        loadingDialogDismiss();
        detailBean = (NewsDetailBean) obj;

        //界面回到顶端
        scrollView.fullScroll(NestedScrollView.FOCUS_UP);

        initShowDetail();

    }

    @Override
    public void onDetailFailed(int code, String msg, Exception e) {
        isDetialFinish = true;
        loadingDialogDismiss();
    }

    //
    @Override
    public void onHotNewsSuccess(Object obj) {
        isHotFinish = true;
        loadingDialogDismiss();
        hotList = (ArrayList<HotNewsBean>) obj;
        //缓存
        if (hotList != null && hotList.size() > 0) {
            initHotNews();
            //添加缓存
            maCache.remove(Constants.NEWS_HOT_LIST);
            // 缓存30min
            maCache.put(Constants.NEWS_HOT_LIST, hotList, 1800);
        }
    }

    @Override
    public void onHotNewsFailed(int code, String msg, Exception e) {
        isHotFinish = true;
        loadingDialogDismiss();
    }

    //
    @Override
    public void onNewsCommentSuccess(Object obj) {
        isCommentFinish = true;
        loadingDialogDismiss();
        commentList = (ArrayList<NewsCommentBean>) obj;
        initCommentShow();
    }

    @Override
    public void onNewsCommentFailed(int code, String msg, Exception e) {
        isCommentFinish = true;
        loadingDialogDismiss();
    }

    //
    @Override
    public void onSendSuccess(Object obj) {
        loadingDialog.dismiss();
        //清空评论内容
        tv_writeComment.setText("");
        ToastUtil.ToastShort(this, "发布成功！");

        //刷新评论数据
        loadNewsCommentData();
    }

    @Override
    public void onSendFailed(int code, String msg, Exception e) {
        loadingDialog.dismiss();
        if (msg.contains("400")) {
            ToastUtil.ToastShort(this, e.toString());
        } else {
            MLog.d(msg, e.toString());
        }
    }

    //
    @Override
    public void onSupportSuccess(Object obj) {
        loadingDialog.dismiss();
        ToastUtil.ToastShort(this, "点赞成功！");
        loadDetailData();
    }

    @Override
    public void onSupportFailed(int code, String msg, Exception e) {
        loadingDialog.dismiss();
        ToastUtil.ToastShort(this, msg);

    }

    /**
     * 异步加载完 统一结束 弹窗加载
     */
    private void loadingDialogDismiss() {
        if (isCommentFinish && isDetialFinish && isHotFinish) {
            loadingDialog.dismiss();
        }
    }

    /**
     * =============================================================================
     * ==========================================生命周期方法===================================
     * =============================================================================
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (isHot) {
            loadData();
            //界面回到顶端
            scrollView.fullScroll(NestedScrollView.FOCUS_UP);
        }

    }

    /**
     * 在 Activity 销毁（ WebView ）的时候，先让 WebView 加载null内容，然后移除 WebView，再销毁 WebView，最后置空。
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (content_webview != null) {
            content_webview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            content_webview.clearHistory();

            content_webview.removeView(content_webview);
            content_webview.destroy();
            content_webview = null;
        }


    }

    /**
     * ===============================================================================
     * ========================================菜单处理=======================================
     * ===============================================================================
     */
    /**
     *
     */
    private void initToolBar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        toolbar.setNavigationIcon(R.mipmap.newsdetail_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsDetailActivity.this.finish();
            }
        });

    }

    /**
     * 要重写onCreateOptionsMenu()方法，把这个菜单加载进去
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_detail, menu);
        return true;
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_textSize://字体
                    NewsTextSizeDialog textSizeDialogFragment = new NewsTextSizeDialog();
                    textSizeDialogFragment.show(getFragmentManager(), Constants.NEWS_DETAIL_TEXTSIZE_TAG);
                    break;
                //                case R.id.action_share:
                //                    token = getCurrentToken();
                //                    MLog.d("分享token", token);
                //                    if (token.isEmpty()) {
                //                        // TODO: 2017/10/18 该处逻辑需要修改，在该处自动登录
                //                        //该处
                //                        MLog.toastInMiddle(NewsDetailActivity.this, "请先登录！");
                //                    } else {
                //                        NewsShareDialog shareDialogFragment = new NewsShareDialog();
                //                        shareDialogFragment.show(getFragmentManager(), Constants.NEWS_DETAIL_SHARE_TAG);
                //                    }
                //                    break;
            }
            return true;
        }
    };


    /**
     * ===============================================================================
     * ========================================评论，分享，字体设置 弹窗回调=======================================
     * ===============================================================================
     */
    /**
     * 评论弹窗
     *
     * @return
     */
    @Override
    public String getCommentText() {
        return tv_writeComment.getText().toString();
    }

    @Override
    public void setCommentText(String commentTextTemp) {
        commentContent = commentTextTemp;
        this.tv_writeComment.setText(commentTextTemp);
    }

    @Override
    public void postComment(String commentTextTemp) {
        commentContent = commentTextTemp;
        sendNewsComment();
    }


    /**
     * 分享弹窗
     */

    @Override
    public void weichatShare() {
        getWeichat();
    }

    @Override
    public void friendsShare() {
        getFriends();

    }

    /**
     * 字体设置弹窗
     */

    @Override
    public void smallTextCallback() {
        SPUtil.setNewsSize(Constants.SIZE_16);
        changeTextSizeShow();
    }

    @Override
    public void middleTextCallback() {
        SPUtil.setNewsSize(Constants.SIZE_18);
        changeTextSizeShow();
    }

    @Override
    public void bigTextCallback() {
        SPUtil.setNewsSize(Constants.SIZE_20);
        changeTextSizeShow();
    }

    @Override
    public void superBigTextCallback() {
        SPUtil.setNewsSize(Constants.SIZE_22);
        changeTextSizeShow();
    }

}
