package com.yilaole.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yilaole.R;
import com.yilaole.adapter.institution.InstituteDetailBannerAdapter;
import com.yilaole.adapter.institution.InstituteDetailObjectRecylerAdapter;
import com.yilaole.adapter.institution.InstituteDetailPicturesRecylerAdapter;
import com.yilaole.adapter.institution.InstituteDetailServiceRecylerAdapter;
import com.yilaole.adapter.institution.InstitutionDetailCommentRecylerAdapter;
import com.yilaole.base.BaseActivity;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.institution.detail.DetailBannerBean;
import com.yilaole.bean.institution.detail.DetailCommentBean;
import com.yilaole.bean.institution.detail.InstituteDetailBean;
import com.yilaole.dialog.InstituteContactDialog;
import com.yilaole.http.cache.ACache;
import com.yilaole.http.rx.RxBus;
import com.yilaole.http.rx.RxBusBaseMessage;
import com.yilaole.http.rx.RxCodeConstants;
import com.yilaole.inter_face.idialog.IInstituteContactDialogCallback;
import com.yilaole.inter_face.ilistener.OnInstituteDetailListener;
import com.yilaole.inter_face.ipresenter.IInstituteDetailPresenter;
import com.yilaole.map.location.CheckPermissionsListener;
import com.yilaole.presenter.InstituteDetailPresenterImpl;
import com.yilaole.save.SPUtil;
import com.yilaole.utils.DpUtils;
import com.yilaole.utils.MLog;
import com.yilaole.utils.ToastUtil;
import com.yilaole.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

import static android.os.Build.VERSION_CODES.M;

/**
 * 机构详情
 */

public class InstitutionDetailActivity extends BaseActivity implements OnInstituteDetailListener, CheckPermissionsListener, IInstituteContactDialogCallback {
    //toolbar
    @BindView(R.id.id_toolbar)
    Toolbar toolbar;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    //轮播图
    @BindView(R.id.banner_viewpager)
    ViewPager banner_viewpager;

    @BindView(R.id.ll_index_loopImg)
    LinearLayout ll_index_loopImg;

    //机构图
    @BindView(R.id.detail_institute_placeImg)
    ImageView detail_institute_placeImg;

    //公办民办
    @BindView(R.id.tv_civilianRunOrStateRun)
    TextView tv_civilianRunOrStateRun;

    //入驻
    @BindView(R.id.tv_entreing)
    TextView tv_entreing;
    //认领机构/入驻状态
    @BindView(R.id.tv_godo)
    TextView tv_godo;

    @BindView(R.id.tv_done)
    TextView tv_done;

    //    //机构电话
    //    @BindView(R.id.tv_detail_institute_phone)
    //    TextView tv_detail_institute_phone;

    //    // 是否收藏
    //    @BindView(R.id.isCollected)
    //    ImageView isCollected;

    // 收住对象
    @BindView(R.id.service_recyclerView)
    RecyclerView service_recyclerView;

    // 机构特色
    @BindView(R.id.recyclerView_object)
    RecyclerView recyclerView_object;

    // 机构地址
    @BindView(R.id.tv_address)
    TextView tv_address;

    // 机构名称
    @BindView(R.id.tv_detail_institute_name)
    TextView tv_detail_institute_name;

    // 机构床位
    @BindView(R.id.tv_bedCount)
    TextView tv_bedCount;

    // 成立时间
    @BindView(R.id.tv_establishmentTime)
    TextView tv_establishmentTime;

    // 现价
    @BindView(R.id.tv_realPrice)
    TextView tv_realPrice;

    //市场价
    @BindView(R.id.tv_marketPrice)
    TextView tv_marketPrice;

    //简介
    //    @BindView(R.id.tv_instrudution)
    //    TextView tv_instrudution;

    @BindView(R.id.tv_instrudution)
    WebView tv_instrudution;

    //院长图片
    @BindView(R.id.tv_deanPic)
    ImageView tv_deanPic;

    //院长名称
    @BindView(R.id.tv_deanName)
    TextView tv_deanName;

    //院长寄语
    @BindView(R.id.tv_dean_content)
    TextView tv_dean_content;

    //院长寄语收缩伸展按钮
    @BindView(R.id.img_isDeanContentShow)
    ImageView img_isDeanContentShow;

    //机构图片
    @BindView(R.id.institute_imgs_recyclerView)
    RecyclerView institute_imgs_recyclerView;

    //综合分数（底部位置处）
    @BindView(R.id.tv_combinedScore)
    TextView tv_combinedScore;

    //综合评分（顶部位置处）
    @BindView(R.id.tv_score)
    TextView tv_score;

    //seekbar 信息分数
    @BindView(R.id.seekbar_message)
    SeekBar seekbar_message;
    @BindView(R.id.tv_message_score)
    TextView tv_message_score;

    //seekbar 评价分数
    @BindView(R.id.seekbar_estimate)
    SeekBar seekbar_estimate;
    @BindView(R.id.tv_estimate_score)
    TextView tv_estimate_score;

    //评论
    @BindView(R.id.comment_recyclerView)
    RecyclerView comment_recyclerView;


    //常量
    private final int HOME_BANNER_RESULT = 121;
    //变量
    private ACache aCache;
    private String token = "";
    private String macId = "";
    private String callPhone;
    private IInstituteDetailPresenter presenter;
    private List<DetailBannerBean> bannerData;
    private List<String> objectData;//入驻对象
    private List<DetailCommentBean> commentData;
    private InstituteDetailBean detailBean;
    private InstituteDetailServiceRecylerAdapter serviceRecyclerAdpater;//机构特色适配
    private InstituteDetailObjectRecylerAdapter objectRecyclerAdpater;//收住对象适配
    private InstituteDetailPicturesRecylerAdapter picturesRecylerAdapter;//图片适配
    private InstitutionDetailCommentRecylerAdapter commentRecylerAdapter;//评论适配
    private InstituteDetailBannerAdapter adapter;//banner适配
    //
    private int InstituteId;//机构Id

    //判断加载动画 完成标记
    private boolean isBannerFinish = false;//
    private boolean isDetialFinish = false;//
    private boolean isCommentFinish = false;//

    //滑动 高度变化
    private float headerHeight;//顶部高度
    private float minHeaderHeight;//顶部最低高度，即Bar的高度

    /**
     * ===============================================================================
     * ========================================初始化操作=======================================
     * ===============================================================================
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_institution_detail);
        ButterKnife.bind(this);

        initMyView();
        initRxBus();
        initListener();
        getData();
    }

    /**
     *
     */
    private void initMyView() {
        aCache = ACache.get(this);
        token = getToken();
        macId = Utils.getMacId(this);
        presenter = new InstituteDetailPresenterImpl(this, this);
        objectRecyclerAdpater = new InstituteDetailObjectRecylerAdapter(this);
        picturesRecylerAdapter = new InstituteDetailPicturesRecylerAdapter(this);
        serviceRecyclerAdpater = new InstituteDetailServiceRecylerAdapter(this);
        initToolBar();
        getIntentData();
    }

    /**
     *
     */
    private void initToolBar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        toolbar.setNavigationIcon(R.mipmap.detail_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InstitutionDetailActivity.this.finish();
            }
        });
        initHeightMeasure();
    }

    /**
     * 获取初始化的高度
     */
    private void initHeightMeasure() {
        minHeaderHeight = toolbar.getHeight();
        headerHeight = collapsingToolbarLayout.getHeight();
        MLog.e("监听高度：", minHeaderHeight, headerHeight);

    }

    /**
     * 获取跳转值
     */
    private void getIntentData() {

        InstituteId = getIntent().getExtras().getInt("id", 1);//默认1
        MLog.e(" 跳转值id：" + InstituteId);

        if (InstituteId <= 0) {
            ToastUtil.toastLong(this, "参数错误或后台数据错误！");
            this.finish();
        }
    }

    private String getToken() {
        return aCache.getAsString(Constants.TOKEN);
    }

    /**
     * 监听
     */

    @SuppressLint("NewApi")
    private void initListener() {

        /**
         * 信息分数监听滑动，让滑动禁止
         */
        seekbar_message.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        /**
         * 信息分数监听滑动，让滑动禁止
         */
        seekbar_estimate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        //toolbar颜色变化监听  以及Toolbar的title隐藏与显示

        collapsingToolbarLayout.setOnScrollChangeListener(scrollChangeListener);

    }

    View.OnScrollChangeListener scrollChangeListener = new View.OnScrollChangeListener() {
        @Override
        public void onScrollChange(View view, int i, int i1, int i2, int i3) {
            float currentScrollY = collapsingToolbarLayout.getScrollY();
            MLog.e("滑动高度：", currentScrollY);
        }
    };

    /**
     * 监听
     */
    @OnClick({R.id.tv_godo, R.id.tv_yiyangtese, R.id.tv_serviceNotes, R.id.tv_zizhi, R.id.tv_quanjing
            , R.id.tv_video, R.id.tv_travel, R.id.Price_standard, R.id.tv_comment, R.id.tv_institute_moreEstimate
            , R.id.tv_appointVisit_create, R.id.tv_doorToAssess, R.id.tv_assess, R.id.tv_contact, R.id.tv_institute_morePic})
    public void onClickListener(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", InstituteId);
        switch (view.getId()) {
            case R.id.tv_godo://
                ToastUtil.ToastShort(this, getResources().getString(R.string.other));
                break;
            case R.id.tv_institute_morePic://更多图片
                bundle.putSerializable("bean", detailBean);
                startActivity(DetailGalleryActivity.class, bundle);
                break;
            case R.id.tv_serviceNotes://服务须知01
                startActivity(DetailServiceNotesActivity.class, bundle);
                break;

            case R.id.tv_yiyangtese://医养特色02
                startActivity(DetailCharacteristicActivity.class, bundle);
                break;

            case R.id.tv_zizhi://资质03
                startActivity(DetailQualificationActivity.class, bundle);
                break;

            case R.id.tv_quanjing://360全景04
                startActivity(DetailFullViewActivity.class, bundle);
                break;
            case R.id.tv_video://视频介绍05
                startActivity(DetailVideoActivity.class, bundle);
                break;

            case R.id.tv_travel://线路06
                startActivity(DetailTravlLineActivity.class, bundle);
                break;

            case R.id.Price_standard://收费标准07
                startActivity(DetailFeesActivity.class, bundle);
                break;

            case R.id.tv_comment://用户评价08
                startActivity(DetailUserCommentActivity.class, bundle);
                break;

            case R.id.tv_institute_moreEstimate://查看更多评价
                startActivity(DetailUserCommentActivity.class, bundle);
                break;


            case R.id.tv_appointVisit_create://01预约参观
                if (isLogoutOrNull()) {
                    return;
                }
                bundle.putString("name", detailBean.getName());
                startActivity(DetailAppointVisitCreatActivity.class, bundle);
                break;

            case R.id.tv_doorToAssess://02上门评估
                if (isLogoutOrNull()) {
                    return;
                }
                bundle.putString("name", detailBean.getName());
                startActivity(DetailDoorToAssessCreatActivity.class, bundle);
                break;

            case R.id.tv_assess://03创建点评
                if (isLogoutOrNull()) {
                    return;
                }
                startActivity(DetailCommentCreateActivity.class, bundle);
                break;

            case R.id.tv_contact://04联系机构
                if (isLogoutOrNull()) {
                    return;
                }
                InstituteContactDialog dialogFragment = new InstituteContactDialog();
                dialogFragment.show(getFragmentManager(), Constants.INSTITUTE_DETAIL_CONTACT);
                break;
        }

    }

    /**
     * 登录设置
     *
     * @return
     */
    private boolean isLogoutOrNull() {
        //未登录 触发登录
        if (!SPUtil.isLogin()) {
            startActivity(LoginActivity.class);
            return true;
        }
        if (getToken().isEmpty()) {
            ToastUtil.ToastShort(InstitutionDetailActivity.this, InstitutionDetailActivity.this.getResources().getString(R.string.dialog_token));
            return true;
        }
        return false;

    }


    /**
     * 设置透明状态栏
     * //flag的详细用法见Readme讲解
     *
     * @param hasFocus
     */

    //    @Override
    //    public void onWindowFocusChanged(boolean hasFocus) {
    //        super.onWindowFocusChanged(hasFocus);
    //        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
    //            View decorView = getWindow().getDecorView();
    //
    //            //设置自动隐藏+手动显示的透明状态栏
    //            //            decorView.setSystemUiVisibility(
    //            //                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    //            //                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    //            //                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    //            //                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
    //            //                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
    //            //                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
    //
    //
    //            //设置固定 透明状态栏
    //            decorView.setSystemUiVisibility(
    //                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    //                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    //                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    //        }
    //    }

    /**
     * ===============================================================================
     * ========================================数据显示=======================================
     * ===============================================================================
     */

    /**
     * banner显示
     */
    private void initBanner() {
        //viewpager监听
        banner_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                refreshPoint(position % bannerData.size());
                if (handler.hasMessages(HOME_BANNER_RESULT)) {
                    handler.removeMessages(HOME_BANNER_RESULT);
                }
                handler.sendEmptyMessageDelayed(HOME_BANNER_RESULT, 5000);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (ViewPager.SCROLL_STATE_DRAGGING == state
                        && handler.hasMessages(HOME_BANNER_RESULT)) {
                    handler.removeMessages(HOME_BANNER_RESULT);
                }
            }
        });

        adapter = new InstituteDetailBannerAdapter(InstitutionDetailActivity.this, bannerData);
        banner_viewpager.setAdapter(adapter);
        addIndicatorImageViews(bannerData.size());

        banner_viewpager.setCurrentItem(bannerData.size() * 1000, false);
        //自动轮播线程
        handler.sendEmptyMessageDelayed(HOME_BANNER_RESULT, 5000);//3s一轮播
    }

    // 为ViewPager的选中点的颜色设置监听器
    private void refreshPoint(int position) {
        if (ll_index_loopImg != null) {
            for (int i = 0; i < ll_index_loopImg.getChildCount(); i++) {
                ll_index_loopImg.getChildAt(i).setEnabled(false);

                if (i == position) {
                    ll_index_loopImg.getChildAt(i).setEnabled(true);
                }
            }
        }
    }

    // 添加指示图标
    private void addIndicatorImageViews(int size) {

        ll_index_loopImg.removeAllViews();
        for (int i = 0; i < size; i++) {
            ImageView iv = new ImageView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DpUtils.dip2px(this, 5), DpUtils.dip2px(this, 5));
            if (i != 0) {
                lp.leftMargin = DpUtils.dip2px(this, 7);
            }
            iv.setLayoutParams(lp);
            iv.setBackgroundResource(R.drawable.xml_round_orange_grey_sel);
            iv.setEnabled(false);
            if (i == 0) {
                iv.setEnabled(true);
            }
            ll_index_loopImg.addView(iv);
        }
    }

    @Override
    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case HOME_BANNER_RESULT://图片轮播
                banner_viewpager.setCurrentItem(banner_viewpager.getCurrentItem() + 1, true);
                break;
        }
        super.handleMessage(msg);
    }

    /**
     * 由获得的detailBean-详情显示
     * 按界面显示顺序编码
     */
    private void initShowDetail() {
        //设置toolbar标题
        //        toolbar.setTitle(detailBean.getName());

        //现价
        if (detailBean.getPrice() == null || detailBean.getPrice().isEmpty()) {
            tv_realPrice.setText("暂无");
        } else {
            if (detailBean.getPrice().contains("null")) {
                tv_realPrice.setText("暂无");
            } else {
                tv_realPrice.setText("￥" + detailBean.getPrice() + "起");
            }
        }

        //原价
        if (detailBean.getOldprice() == null || detailBean.getOldprice().isEmpty()) {
            tv_marketPrice.setText("暂无");
        } else {
            if (detailBean.getOldprice().contains("null")) {
                tv_marketPrice.setText("暂无");
            } else {
                tv_marketPrice.setText("￥" + detailBean.getOldprice() + "起");
                tv_marketPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
            }
        }


        //机构名称
        tv_detail_institute_name.setText(detailBean.getName());

        //公办 民办
        if (detailBean.getProperty() == 1) {
            tv_civilianRunOrStateRun.setText(getResources().getString(R.string.instite_detail_civilianRun));
        } else {
            tv_civilianRunOrStateRun.setText(getResources().getString(R.string.instite_detail_StateRun));

        }

        //入驻0 1 2
        if (detailBean.getStatus() == 2) {//已入驻
            tv_entreing.setText(getResources().getString(R.string.instite_detail_entered));
            tv_godo.setVisibility(View.GONE);
            tv_done.setVisibility(View.VISIBLE);

        } else if (detailBean.getStatus() == 1) {//已认领
            tv_entreing.setText(getResources().getString(R.string.instite_detail_entering));
            tv_godo.setVisibility(View.GONE);
            tv_done.setVisibility(View.VISIBLE);
        } else {//未入驻
            tv_entreing.setText(getResources().getString(R.string.instite_detail_noEntering));
            tv_godo.setVisibility(View.VISIBLE);
            tv_done.setVisibility(View.GONE);
        }

        //机构电话
        //        if (detailBean.getAgencyphone() == null || TextUtils.isEmpty(detailBean.getAgencyphone())) {
        //            tv_detail_institute_phone.setText("暂未公布");
        //        }
        //        tv_detail_institute_phone.setText(detailBean.getAgencyphone());

        //        //是否收藏
        //        if (detailBean.getIs_collect() == 1) {//收藏
        //            isCollected.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_attention_to_1));
        //        } else {//未收藏
        //            isCollected.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_attention_to));
        //        }

        //收住对象的适配
        objectData = detailBean.getObject();
        removeObjectDataNUll();//清除入驻对象中为空的数据
        objectRecyclerAdpater.setDataList(objectData);
        LinearLayoutManager objectManager = new LinearLayoutManager(this);
        objectManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_object.setLayoutManager(objectManager);
        recyclerView_object.setAdapter(objectRecyclerAdpater);


        //机构床位
        tv_bedCount.setText(detailBean.getBed() + "张");

        //成立时间
        if (detailBean.getOpendate() == null || detailBean.getOpendate().isEmpty()) {
            tv_establishmentTime.setText("暂无");
        } else {
            if (detailBean.getOpendate().contains("null")) {
                tv_establishmentTime.setText("暂无");
            } else {
                tv_establishmentTime.setText(detailBean.getOpendate() + "");
            }
        }

        //机构地址
        tv_address.setText(detailBean.getAddress());

        //机构logo图
        String logoURL = "";
        if (detailBean.getLogo_url() == null || detailBean.getLogo_url().isEmpty()) {
            logoURL = "";
        } else {
            if (!detailBean.getLogo_url().contains("http")) {
                logoURL = Constants.DETAIL_HTTP + detailBean.getLogo_url();
            } else {
                logoURL = detailBean.getLogo_url();
            }
        }

        Glide.with(this).load(logoURL)
                .override(DpUtils.dpToPx(this, 94), DpUtils.dpToPx(this, 94))
                .error(ContextCompat.getDrawable(this, R.mipmap.item_bg_default1))
                .placeholder(ContextCompat.getDrawable(this, R.mipmap.item_bg_default1))
                .centerCrop()
                .into(detail_institute_placeImg);


        //机构图片适配
        picturesRecylerAdapter.setDataList(detailBean.getImage());
        LinearLayoutManager pictureManager = new LinearLayoutManager(this);
        pictureManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        institute_imgs_recyclerView.setLayoutManager(pictureManager);
        institute_imgs_recyclerView.setAdapter(picturesRecylerAdapter);
        picturesRecylerAdapter.setOnItemClick(new InstituteDetailPicturesRecylerAdapter.OnItemClickListner() {
            @Override
            public void onItemClickListener(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("selectInt", position);// 选中的图片position
                bundle.putSerializable("list", (Serializable) detailBean.getImage());
                startActivity(DetailGalleryBigActivity.class, bundle);
            }
        });

        //机构特色
        if (detailBean.getService() == null || detailBean.getService().isEmpty()) {

        } else {
            serviceRecyclerAdpater.setDataList(detailBean.getService());
            GridLayoutManager serviceManager = new GridLayoutManager(this, 3);
            service_recyclerView.setLayoutManager(serviceManager);
            service_recyclerView.setAdapter(serviceRecyclerAdpater);
        }


        //院长图片

        String DeanURL = "";
        if (detailBean.getDeaninfo().getPhoto() == null || detailBean.getDeaninfo().getPhoto().isEmpty()) {
            DeanURL = "";
        } else {
            if (!detailBean.getDeaninfo().getPhoto().contains("http")) {
                DeanURL = Constants.DETAIL_HTTP + detailBean.getDeaninfo().getPhoto();
            } else {
                DeanURL = detailBean.getDeaninfo().getPhoto();
            }
        }
        Glide.with(this).load(DeanURL)
                .override(DpUtils.dpToPx(this, 74), DpUtils.dpToPx(this, 90))
                .error(ContextCompat.getDrawable(this, R.mipmap.item_bg_default1))
                .placeholder(ContextCompat.getDrawable(this, R.mipmap.item_bg_default1))
                .centerCrop()
                .into(tv_deanPic);

        //院长名称
        if (detailBean.getDeaninfo().getDeanname() == null | TextUtils.isEmpty(detailBean.getDeaninfo().getDeanname())) {
            tv_deanName.setText("暂无");
        } else {
            tv_deanName.setText(detailBean.getDeaninfo().getDeanname());
        }


        //院长寄语
        if (detailBean.getDeaninfo().getWishes() == null | TextUtils.isEmpty(detailBean.getDeaninfo().getWishes())) {
            tv_dean_content.setText(getResources().getString(R.string.instite_detail_deany));
            img_isDeanContentShow.setVisibility(View.INVISIBLE);
        } else {
            tv_dean_content.setText(detailBean.getDeaninfo().getWishes());
        }

        //简介
        //        tv_instrudution.setText(detailBean.getAgencydiscribe() + "");
        // webView显示
        tv_instrudution.loadDataWithBaseURL(null, detailBean.getAgencydiscribe(), "text/html", "utf-8", null);

        //机构分数相关 seekbar max=50
        tv_combinedScore.setText(detailBean.getGrade().getAverage_score() + "分");//综合分数
        tv_score.setText(detailBean.getGrade().getAverage_score() + "分");//综合分数

        tv_message_score.setText(detailBean.getGrade().getInfo_score() + "分");//信息分数
        seekbar_message.setProgress((int) (detailBean.getGrade().getInfo_score() * 10));

        tv_estimate_score.setText(detailBean.getGrade().getComment_score() + "分");//评价分数
        seekbar_estimate.setProgress((int) (detailBean.getGrade().getComment_score() * 10));


        //机构特色
    }


    /**
     * 评论
     */
    private void initCommentShow() {
        commentRecylerAdapter = new InstitutionDetailCommentRecylerAdapter(this, commentData);
        LinearLayoutManager commentManager = new LinearLayoutManager(this);
        commentManager.setOrientation(LinearLayoutManager.VERTICAL);
        comment_recyclerView.setLayoutManager(commentManager);
        comment_recyclerView.setAdapter(commentRecylerAdapter);
    }

    /**
     * 排除后台空数据
     */
    private void removeObjectDataNUll() {
        if (objectData == null || objectData.size() <= 0) {
            return;
        }
        for (int i = 0; i < objectData.size(); i++) {
            if (objectData.get(i).isEmpty()) {
                objectData.remove(i);
            }
        }
    }

    /**
     * ===============================================================================
     * ========================================生命周期=======================================
     * ===============================================================================
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * ===============================================================================
     * ========================================获取数据 接口=======================================
     * ===============================================================================
     */
    private void getData() {
        loadBanner();
        loadDetailData();
        loadCommentData();

    }

    /**
     * 获取banner图
     */
    private void loadBanner() {
        loadingDialog.show();
        presenter.pLoadBannergData(InstituteId);
    }

    /**
     * 获取详情数据
     */
    private void loadDetailData() {
        loadingDialog.show();

        presenter.pLoadDetailData(InstituteId, macId);
    }

    /**
     * 获取评论list数据
     */
    private void loadCommentData() {
        loadingDialog.show();
        presenter.pLoadCommentData(InstituteId, 0, 20);
    }

    /**
     * 收藏机构
     */
    private void collecte() {
        token = getToken();
        // TODO: 2017/10/24 tokn问题
        if (token.isEmpty()) {
            MLog.ToastShort(this, "token失效，重新登录");
            return;
        }
        loadingDialog.show();
        MLog.d("收藏");
        presenter.pCollect(InstituteId, token);

    }

    /**
     * 取消收藏机构
     */
    private void unCollect() {
        token = getToken();
        // TODO: 2017/10/24 tokn问题
        if (token.isEmpty()) {
            MLog.ToastShort(this, "token失效，重新登录");
            return;
        }
        loadingDialog.show();
        MLog.d("取消收藏");
        presenter.pUnCollect(InstituteId, token);

    }

    /**
     * ===============================================================================
     * ========================================接口回调=======================================
     * ===============================================================================
     */
    @Override
    public void onBannerSuccess(Object obj) {
        isBannerFinish = true;
        loadingDialogDismiss();
        bannerData = (List<DetailBannerBean>) obj;
        initBanner();
    }

    @Override
    public void onBannerFailed(int code, String msg, Exception e) {
        isBannerFinish = true;
        loadingDialogDismiss();
    }

    @Override
    public void onDetailSuccess(Object object) {
        isDetialFinish = true;
        loadingDialogDismiss();
        detailBean = (InstituteDetailBean) object;
        initShowDetail();
    }

    @Override
    public void onDetailFailed(int code, String msg, Exception e) {
        isDetialFinish = true;

        loadingDialogDismiss();
    }

    @Override
    public void onCommentSuccess(Object obj) {
        isCommentFinish = true;
        loadingDialogDismiss();
        commentData = (List<DetailCommentBean>) obj;
        initCommentShow();
    }

    @Override
    public void onCommentFailed(int code, String msg, Exception e) {
        isCommentFinish = true;
        loadingDialogDismiss();
    }

    @Override
    public void onCollectSuccess(Object object) {
        loadingDialog.dismiss();
        ToastUtil.ToastShort(this, "收藏成功！");
        //调接口更新数据
        loadDetailData();
    }

    @Override
    public void onCollectFailed(int code, String msg, Exception e) {
        loadingDialog.dismiss();
        if (msg.contains("404")) {
            ToastUtil.ToastShort(this, "收藏失败！");
        } else {
            MLog.e(msg, e.toString());
        }
    }

    @Override
    public void onUnCollectSuccess(Object object) {
        loadingDialog.dismiss();
        ToastUtil.ToastShort(this, "取消成功！");
        //调接口更新数据
        loadDetailData();
    }

    @Override
    public void onUnCollectFailed(int code, String msg, Exception e) {
        loadingDialog.dismiss();
        if (code == -1) {
            MLog.e(msg, e.toString());
            ToastUtil.ToastShort(this, msg);
        } else {
            MLog.e(msg, e.toString());
            ToastUtil.ToastShort(this, msg);
        }
    }

    /**
     * 异步加载完 统一结束 弹窗加载
     */
    private void loadingDialogDismiss() {
        if (isBannerFinish && isCommentFinish && isDetialFinish) {
            loadingDialog.dismiss();
        }
    }

    /**
     * ===============================================================================
     * ========================================菜单处理=======================================
     * ===============================================================================
     */
    /**
     * 点击toolbar按钮监听
     */
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_collect:
                    if (detailBean.getIs_collect() == 1) {//1是已经收藏 点击后取消收藏
                        item.setIcon(ContextCompat.getDrawable(InstitutionDetailActivity.this, R.mipmap.detail_collect2));
                        unCollect();
                    } else if (detailBean.getIs_collect() == 0) {//0是未收藏 点击后收藏
                        item.setIcon(ContextCompat.getDrawable(InstitutionDetailActivity.this, R.mipmap.xiangqing_xingxing));
                        collecte();
                    }
                    break;
                case R.id.action_share:
                    ToastUtil.ToastShort(InstitutionDetailActivity.this, "分享");
                    break;
            }
            return true;
        }
    };

    /**
     * 要重写onCreateOptionsMenu()方法，把这个菜单加载进去
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //调用xml样式
        getMenuInflater().inflate(R.menu.menu_institute_detail, menu);
        //代码样式：
        //        menu.add(Menu.NONE, Menu.FIRST + 1, 5, "分享").setIcon(android.R.drawable.ic_menu_edit);
        //
        //        menu.add(Menu.NONE, Menu.FIRST + 2, 2, "保存").setIcon(android.R.drawable.ic_menu_edit);
        //
        //        menu.add(Menu.NONE, Menu.FIRST + 3, 6, "帮助").setIcon(android.R.drawable.ic_menu_help);
        //
        //        menu.add(Menu.NONE, Menu.FIRST + 4, 1, "添加").setIcon(android.R.drawable.ic_menu_add);
        //
        //        menu.add(Menu.NONE, Menu.FIRST + 5, 4, "详细").setIcon(android.R.drawable.ic_menu_info_details);
        //
        //        menu.add(Menu.NONE, Menu.FIRST + 6, 3, "发送").setIcon(android.R.drawable.ic_menu_send);

        return true;
    }

    // TODO: 2017/10/20 假的功能，后期修改

    /**
     * 点击操作
     * <p>
     * 该处无效
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_collect:
                break;
            case R.id.action_share:
                break;
        }

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * 权限修改
     */
    private CheckPermissionsListener mListener;
    private static final int REQUEST_CODE = 2333;

    public void requestPermissions(Activity activity, String[] permissions, CheckPermissionsListener listener) {
        if (activity == null)
            return;
        mListener = listener;
        List<String> deniedPermissions = findDeniedPermissions(activity, permissions);
        if (!deniedPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
        } else {
            //所有权限都已经同意了
            mListener.onGranted();
        }
    }

    private List<String> findDeniedPermissions(Activity activity, String... permissions) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permission);
            }
        }
        return deniedPermissions;
    }

    //权限设置
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                List<String> deniedPermissions = new ArrayList<>();
                int length = grantResults.length;
                for (int i = 0; i < length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        //该权限被拒绝了
                        deniedPermissions.add(permissions[i]);
                    }
                }
                if (deniedPermissions.size() > 0) {
                    mListener.onDenied(deniedPermissions);
                } else {
                    mListener.onGranted();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 打开电话
     */
    private void startCallPhone() {
        //直接拨打
        //        Intent intent = new Intent(); // 意图对象：动作 + 数据
        //        intent.setAction(Intent.ACTION_CALL); // 设置动作
        //        Uri data = Uri.parse("tel:" + callPhone); // 设置数据
        //        intent.setData(data);
        //        startActivity(intent);

        //进入拨打界面
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + callPhone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * 打电话的回调
     *
     * @return
     */

    @Override
    public String getContactNumber() {
        return detailBean.getAgencyphone();
    }

    //电话权限
    protected final String[] neededPermissions = new String[]{
            Manifest.permission.CALL_PHONE
    };

    @Override
    public void postNumber(String commentTextTemp) {
        callPhone = commentTextTemp;
        //请求权限
        if (Build.VERSION.SDK_INT < M) {
            startCallPhone();
        } else {
            requestPermissions(this, neededPermissions, this);
        }
    }

    //权限回调
    @Override
    public void onGranted() {
        startCallPhone();

    }

    @Override
    public void onDenied(List<String> permissions) {

        ToastUtil.toastLong(this, "权限被禁止，请到设置中打开！");
    }

    private void initRxBus() {
        RxBus.getDefault().toObservable(RxCodeConstants.COMMENT2DETAIL, RxBusBaseMessage.class)
                .subscribe(new Action1<RxBusBaseMessage>() {
                    @Override
                    public void call(RxBusBaseMessage rxBusBaseMessage) {
                        //重新调用评论接口
                        loadCommentData();
                    }
                });
    }
}
