package com.yilaole.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.base.BaseActivity;
import com.yilaole.base.app.Constants;
import com.yilaole.http.cache.ACache;
import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.presenter.DetailDoorAssessPresenterImpl;
import com.yilaole.utils.MLog;
import com.yilaole.utils.MobileUtils;
import com.yilaole.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 上门评估
 */

public class DetailDoorToAssessCreatActivity extends BaseActivity implements OnCommonListener {
    //
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.layout_back)
    RelativeLayout layout_back;


    //机构名称
    @BindView(R.id.tv_institute_name)
    TextView tv_institute_name;

    //预约时间
    @BindView(R.id.tv_appointTime)
    TextView tv_appointTime;


    //联系人
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.img_name)
    ImageView img_name;


    //手机号
    @BindView(R.id.et_contact)
    EditText et_contact;
    @BindView(R.id.img_contact)
    ImageView img_contact;

    //地址
    @BindView(R.id.et_place)
    EditText et_place;
    @BindView(R.id.img_place)
    ImageView img_place;

    //选择长者
    @BindView(R.id.tv_chooseOlder)
    TextView tv_chooseOlder;


    //创建长者档案
    @BindView(R.id.tv_createRecord)
    TextView tv_createRecordf;


    //备注
    @BindView(R.id.remark_)
    EditText et_Remark;


    private DetailDoorAssessPresenterImpl presenter;
    private ACache aCache;
    //变量
    private String token;
    private String instituteName;
    private int instituteId;

    private String name;
    private String contact;
    private String place;
    private String strRemark;
    private String OlderName;
    private String time;
    private int elder_id; //长者档案

    //标记null
    private boolean isNameNull;
    private boolean isContactNull;
    private boolean isPlaceNull;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_institution_assess_creat);
        ButterKnife.bind(this);
        initMyView();
        initListener();
    }

    private void initMyView() {
        getIntentData();
        aCache = ACache.get(this);
        presenter = new DetailDoorAssessPresenterImpl(this, this);
        tv_title.setText(getResources().getString(R.string.mine_doorAssess));
        tv_institute_name.setText(instituteName);

    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        instituteName = bundle.getString("name");
        instituteId = bundle.getInt("id");
        MLog.d("上门评估-跳转值", instituteName, instituteId);
    }

    private void initListener() {
        /**
         * 姓名
         */
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                name = editable.toString().trim();
                if (name.isEmpty()) {
                    img_name.setVisibility(View.VISIBLE);
                } else {
                    img_name.setVisibility(View.INVISIBLE);
                }

            }
        });


        /**
         * 手机号
         */
        et_place.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                place = editable.toString().trim();
                if (place.isEmpty()) {
                    img_place.setVisibility(View.VISIBLE);
                } else {
                    img_place.setVisibility(View.INVISIBLE);
                }

            }
        });
        /**
         * 地址
         */
        et_place.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                contact = editable.toString().trim();
                if (contact.isEmpty()) {
                    img_contact.setVisibility(View.VISIBLE);
                } else {
                    img_contact.setVisibility(View.INVISIBLE);
                }

            }
        });

        /**
         * 备注
         */
        et_Remark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                strRemark = editable.toString().trim();
            }
        });


    }

    //获取热门机构信息
    private void getData() {
        name = et_name.getText().toString().trim();
        contact = et_contact.getText().toString().trim();
        place = et_place.getText().toString().trim();
        strRemark = et_Remark.getText().toString().trim();
        OlderName = tv_chooseOlder.getText().toString().trim();
        time = tv_appointTime.getText().toString();
    }

    private void getNull() {

        //
        if (name.isEmpty()) {
            img_name.setVisibility(View.VISIBLE);
            isNameNull = true;
        } else {
            img_name.setVisibility(View.INVISIBLE);
            isNameNull = false;
        }
        //
        if (contact.isEmpty()) {
            isContactNull = true;
            img_contact.setVisibility(View.VISIBLE);
        } else {
            isContactNull = false;
            img_contact.setVisibility(View.INVISIBLE);
        }

        //
        if (place.isEmpty()) {
            isPlaceNull = true;
            img_place.setVisibility(View.VISIBLE);
        } else {
            isPlaceNull = false;
            img_place.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * 非空验证
     *
     * @return
     */
    private boolean isEtNull() {
        if (isNameNull || isContactNull || isPlaceNull) {
            ToastUtil.ToastShort(DetailDoorToAssessCreatActivity.this, "信息不能为空！");
            return true;
        }

        return false;
    }

    private boolean isTvNull() {
        if (OlderName.isEmpty()) {
            ToastUtil.ToastShort(DetailDoorToAssessCreatActivity.this, "长者信息不能为空！");
            return true;
        }
        if (time.isEmpty()) {
            ToastUtil.ToastShort(DetailDoorToAssessCreatActivity.this, "预约时间不能为空！");
            return true;
        }
        if (!MobileUtils.isMobile(contact)) {
            ToastUtil.ToastShort(DetailDoorToAssessCreatActivity.this, "手机号不可用！");
            return true;
        }
        return false;

    }

    @OnClick({R.id.layout_back, R.id.appoint_sure})
    public void onClickListenter(View view) {
        switch (view.getId()) {
            case R.id.layout_back://后退
                this.finish();
                break;
            case R.id.appoint_sure://提交
                getData();
                getNull();
                if (isEtNull()) {
                    return;
                }
                if (isTvNull()) {
                    return;
                }
                postData();
                break;
        }
    }

    private void postData() {
        token = aCache.getAsString(Constants.TOKEN);
        if (token.isEmpty()) {
            ToastUtil.toastInMiddle(DetailDoorToAssessCreatActivity.this, "请登录");
            return;
        }
        loadingDialog.show();
        presenter.pLoadData(instituteId
                , instituteName
                , token
                , name
                , contact
                , place
                , elder_id
                , time
                , strRemark);
    }

    @Override
    public void onDataSuccess(Object obj) {
        loadingDialog.dismiss();
        ToastUtil.ToastShort(DetailDoorToAssessCreatActivity.this, "申请成功！");
        this.finish();
    }

    @Override
    public void onDataFailed(int code, String msg, Exception e) {
        loadingDialog.dismiss();

        if (code == -1) {
            ToastUtil.ToastShort(this, msg);
        } else if (code == 401) {
            ToastUtil.ToastShort(this, "申请失败！");
            MLog.e(msg, e.toString());
        } else {
            ToastUtil.ToastShort(this, msg);
            MLog.e(msg, e.toString());
        }
    }
}