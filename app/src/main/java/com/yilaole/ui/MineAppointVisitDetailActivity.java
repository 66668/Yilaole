package com.yilaole.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.base.BaseActivity;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.mine.AppointVisitDetailBean;
import com.yilaole.http.cache.ACache;
import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.presenter.MineAppointVisitDetailPresenterImpl;
import com.yilaole.utils.MLog;
import com.yilaole.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的-预约参观详情
 */

public class MineAppointVisitDetailActivity extends BaseActivity implements OnCommonListener {
    //
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.layout_back)
    RelativeLayout layout_back;

    //图片状态
    @BindView(R.id.img_status1)
    ImageView img_status1;

    @BindView(R.id.img_status2)
    ImageView img_status2;


    //审批状态
    @BindView(R.id.tv_status1)
    TextView tv_status1;

    @BindView(R.id.tv_status2)
    TextView tv_status2;

    //时间
    @BindView(R.id.time_status1)
    TextView time_status1;

    @BindView(R.id.time_status2)
    TextView time_status2;

    //订单号
    @BindView(R.id.tv_visitId)
    TextView tv_visitId;

    //订单类型
    @BindView(R.id.tv_visitType)
    TextView tv_visitType;

    //订单状态
    @BindView(R.id.tv_visitStatus)
    TextView tv_visitStatus;


    //订单时间
    @BindView(R.id.tv_visitTime)
    TextView tv_visitTime;

    //机构名称
    @BindView(R.id.tv_instituteName)
    TextView tv_instituteName;

    //联系人姓名
    @BindView(R.id.tv_contactName)
    TextView tv_contactName;


    //联系手机
    @BindView(R.id.tv_contactPhone)
    TextView tv_contactPhone;

    //参观人数
    @BindView(R.id.tv_visitNumber)
    TextView tv_visitNumber;


    //长者姓名
    @BindView(R.id.tv_olderName)
    TextView tv_olderName;


    //长者年龄
    @BindView(R.id.tv_olderAge)
    TextView tv_olderAge;


    private MineAppointVisitDetailPresenterImpl presenter;
    private int visitId;
    private String token;
    private ACache aCache;
    private AppointVisitDetailBean bean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mine_appoint_detail);
        ButterKnife.bind(this);
        initMyView();

        loadDetailData();
    }

    private void initMyView() {
        getIntentData();
        tv_title.setText(getResources().getString(R.string.visit_detailTitle));
        presenter = new MineAppointVisitDetailPresenterImpl(this, this);
        aCache = ACache.get(this);
        token = aCache.getAsString(Constants.TOKEN);
    }

    private void getIntentData() {
        visitId = getIntent().getExtras().getInt("id");
    }

    private void initShow() {
        //审批状态
        //1
        img_status1.setBackground(ContextCompat.getDrawable(this, R.mipmap.detail_pass));
        tv_status1.setText(getResources().getString(R.string.mine_detail_pass));
        time_status1.setText(bean.getOrdertime() + "");

        //2
        if (bean.getStatus().contains("待审核")) {
            img_status2.setBackground(ContextCompat.getDrawable(this, R.mipmap.detail_undovisit));
            time_status2.setText("");
        } else if (bean.getStatus().contains("待参观")) {
            img_status2.setBackground(ContextCompat.getDrawable(this, R.mipmap.detail_waitvisit));
            time_status2.setText(bean.getAgreetime() + "");
        } else if (bean.getStatus().contains("已参观")) {
            time_status2.setText(bean.getVisittime() + "");
            img_status2.setBackground(ContextCompat.getDrawable(this, R.mipmap.detail_pass));
        } else if (bean.getStatus().contains("未通过")) {
            time_status2.setText(bean.getRefusetime() + "");
            img_status2.setBackground(ContextCompat.getDrawable(this, R.mipmap.detail_refuse));
        }
        tv_status2.setText(bean.getStatus() + "");


        //订单号
        tv_visitId.setText(bean.getOrderid() + "");
        tv_visitType.setText(getResources().getString(R.string.mine_booktrce));
        tv_visitStatus.setText(bean.getStatus());
        //预约时间
        tv_visitTime.setText(bean.getTime() + "");

        //机构名称
        tv_instituteName.setText(bean.getAgencyname() + "");
        tv_contactName.setText(bean.getUser_name() + "");
        tv_contactPhone.setText(bean.getPhone() + "");
        tv_visitNumber.setText(bean.getNumber() + "");
        //长者
        tv_olderName.setText(bean.getName() + "");
        tv_olderAge.setText(bean.getAge() + "");

    }


    private void loadDetailData() {
        loadingDialog.show();
        presenter.pLoadDetailData(token, visitId);
    }

    @OnClick({R.id.layout_back})
    public void onClickListenter(View view) {
        switch (view.getId()) {
            case R.id.layout_back://后退
                this.finish();
                break;
        }
    }


    @Override
    public void onDataSuccess(Object obj) {
        loadingDialog.dismiss();
        bean = (AppointVisitDetailBean) obj;
        initShow();
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