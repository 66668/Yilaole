package com.yilaole.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.base.BaseActivity;
import com.yilaole.base.app.Constants;
import com.yilaole.http.cache.ACache;
import com.yilaole.inter_face.ilistener.OnDetailAppointVisitListener;
import com.yilaole.presenter.DetailAppointViistPresenterImpl;
import com.yilaole.utils.MLog;
import com.yilaole.utils.MobileUtils;
import com.yilaole.utils.ToastUtil;
import com.yilaole.widget.CountDownButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 预约参观
 */

public class DetailAppointVisitCreatActivity extends BaseActivity implements OnDetailAppointVisitListener, CountDownButton.CountdownListener {

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


    //人数
    @BindView(R.id.et_number)
    EditText et_number;
    @BindView(R.id.img_number)
    ImageView img_number;

    //长者姓名
    @BindView(R.id.et_older_name)
    EditText et_older_name;
    @BindView(R.id.img_older_name)
    ImageView img_older_name;


    //长者年龄
    @BindView(R.id.et_old)
    EditText et_old;
    @BindView(R.id.img_yearold)
    ImageView img_yearold;


    //手机号
    @BindView(R.id.et_contact)
    EditText et_contact;
    @BindView(R.id.img_contact)
    ImageView img_contact;


    //验证码
    @BindView(R.id.et_inputCode)
    EditText et_inputCode;

    @BindView(R.id.img_code)
    ImageView img_code;

    @BindView(com.yilaole.R.id.tv_sendCode)
    CountDownButton button;

    //提交
    @BindView(R.id.appoint_sure)
    Button appoint_sure;

    //变量
    private String instituteName;
    private int instituteId;
    private String name;
    private int number;
    private String olderName;
    private String olderAge;
    private String contact;
    private String code;
    private String time;
    private String token;

    private DetailAppointViistPresenterImpl presenter;
    private ACache aCache;

    //是否为空的标记
    private boolean isNameNull;
    private boolean isNumberNull;
    private boolean isOldNameNull;
    private boolean isOldYearOldNull;
    private boolean isContactNull;
    private boolean isCodeNull;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_institution_appointvisit_creat);
        ButterKnife.bind(this);

        initMyView();

        initListener();
    }


    private void initMyView() {
        //开启按钮监听
        button.setOnCountDownListener(this);

        getIntentData();
        aCache = ACache.get(this);
        presenter = new DetailAppointViistPresenterImpl(this, this);
        tv_institute_name.setText(instituteName);
        tv_title.setText(getResources().getString(R.string.mine_appointVisit));
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        instituteName = bundle.getString("name");
        instituteId = bundle.getInt("id");
        MLog.d("预约参观-跳转值", instituteName, instituteId);
    }

    //获取数据
    private void getData() {

        name = et_name.getText().toString().trim();
        //人数
        String numberStr = et_number.getText().toString().trim();
        if (numberStr.isEmpty()) {
            number = -1;
        } else {
            number = Integer.parseInt(et_number.getText().toString().trim());
        }
        //
        olderName = et_older_name.getText().toString().trim();
        olderAge = et_old.getText().toString().trim();
        contact = et_contact.getText().toString().trim();
        code = et_inputCode.getText().toString().trim();
        time = tv_appointTime.getText().toString();

    }

    private void getContact() {
        contact = et_contact.getText().toString().trim();
    }

    /**
     * 输入文本监听
     */
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
         * 人数
         */
        et_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                number = Integer.parseInt(editable.toString().trim());
                if (editable.toString().trim().isEmpty()) {
                    img_number.setVisibility(View.VISIBLE);
                } else {
                    img_number.setVisibility(View.INVISIBLE);
                }

            }
        });

        /**
         * 长者姓名
         */
        et_older_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                olderName = editable.toString().trim();
                if (olderName.isEmpty()) {
                    img_older_name.setVisibility(View.VISIBLE);
                } else {
                    img_older_name.setVisibility(View.INVISIBLE);
                }

            }
        });

        /**
         * 长者年龄
         */
        et_old.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                olderAge = editable.toString().trim();
                if (olderAge.isEmpty()) {
                    img_yearold.setVisibility(View.VISIBLE);
                } else {
                    img_yearold.setVisibility(View.INVISIBLE);
                }

            }
        });

        /**
         * 手机号
         */
        et_contact.addTextChangedListener(new TextWatcher() {
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
         * 验证码
         */
        et_inputCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                code = editable.toString().trim();
                if (code.isEmpty()) {
                    img_code.setVisibility(View.VISIBLE);
                } else {
                    img_code.setVisibility(View.INVISIBLE);
                }

            }
        });


    }

    /**
     * 所有空信息显示 标记
     */
    private void getNull() {
        //
        if (name.isEmpty()) {
            img_name.setVisibility(View.VISIBLE);
            isNameNull = true;
        } else {
            img_name.setVisibility(View.INVISIBLE);
            isNameNull = false;
        }

        //getData对number为空时赋值-1
        if (number < 0) {
            isNumberNull = true;
            img_number.setVisibility(View.VISIBLE);
        } else {
            isNumberNull = false;
            img_number.setVisibility(View.INVISIBLE);
        }

        //
        if (olderName.isEmpty()) {
            isOldNameNull = true;
            img_older_name.setVisibility(View.VISIBLE);
        } else {
            isOldNameNull = false;
            img_older_name.setVisibility(View.INVISIBLE);
        }

        //
        if (olderAge.isEmpty()) {
            isOldYearOldNull = true;
            img_yearold.setVisibility(View.VISIBLE);
        } else {
            isOldYearOldNull = false;
            img_yearold.setVisibility(View.INVISIBLE);
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
        if (code.isEmpty()) {
            isCodeNull = true;
            img_code.setVisibility(View.VISIBLE);
        } else {
            isCodeNull = false;
            img_code.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * 非空验证
     *
     * @return
     */
    private boolean isEtNull() {
        if (isNameNull || isNumberNull || isOldNameNull || isOldYearOldNull || isCodeNull || isContactNull) {
            ToastUtil.ToastShort(DetailAppointVisitCreatActivity.this, "信息不能为空！");
            return true;
        }
        return false;
    }

    private boolean isTvNull() {
        if (time.isEmpty()) {
            ToastUtil.ToastShort(DetailAppointVisitCreatActivity.this, "预约时间不能为空！");
            return true;
        }
        return false;

    }


    @OnClick({R.id.layout_back, R.id.appoint_sure, R.id.tv_sendCode})
    public void onClickListenter(View view) {
        switch (view.getId()) {
            case R.id.layout_back://后退
                this.finish();
                break;
            case R.id.tv_sendCode://
                getContact();
                if (contact.isEmpty()) {
                    ToastUtil.ToastShort(DetailAppointVisitCreatActivity.this, "手机号不能为空！");
                    return;
                }
                if (!MobileUtils.isMobile(contact)) {
                    ToastUtil.ToastShort(DetailAppointVisitCreatActivity.this, "手机号不可用！");
                    return;
                }
                getCode();
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

    /**
     * ====================================================================================
     * ========================================数据接口============================================
     * ====================================================================================
     */

    //发送
    private void postData() {

        //// TODO: 2017/10/23 token 必须有
        token = aCache.getAsString(Constants.TOKEN);

        if (token.isEmpty()) {
            ToastUtil.toastInMiddle(DetailAppointVisitCreatActivity.this, "token失效，请登录");
            return;
        }

        loadingDialog.show();
        presenter.pLoadData(
                instituteId
                , instituteName
                , token
                , name
                , number
                , olderName
                , olderAge
                , contact
                , time
                , code);
    }

    //验证码
    private void getCode() {
        loadingDialog.show();
        presenter.getCode(contact);

    }


    /**
     * ====================================================================================
     * ========================================接口回调============================================
     * ====================================================================================
     */

    @Override
    public void onAppointVisitPostSuccess(Object obj) {
        loadingDialog.dismiss();
        ToastUtil.ToastShort(DetailAppointVisitCreatActivity.this, "预约申请成功！");
        this.finish();
    }

    @Override
    public void onAppointVisitPostFailed(int code, String msg, Exception e) {
        loadingDialog.dismiss();

        if (code == -1) {
            ToastUtil.ToastShort(this, msg);
        } else if (code == 401) {
            ToastUtil.ToastShort(this, msg);
            MLog.e(msg, e.toString());
        } else {
            ToastUtil.ToastShort(this, msg);
            MLog.e(msg, e.toString());
        }
    }

    @Override
    public void onAppointVisitCodeSuccess(Object obj) {
        loadingDialog.dismiss();
        ToastUtil.ToastShort(DetailAppointVisitCreatActivity.this, "验证码已发送，请稍后！");
        button.start(this, 45);
    }

    @Override
    public void onAppointVisitCodeFailed(int code, String msg, Exception e) {
        loadingDialog.dismiss();

        if (code == -1) {
            ToastUtil.ToastShort(this, msg);
        } else if (code == 404) {
            ToastUtil.ToastShort(this, " 发送失败，请致电该机构！");
            MLog.e(msg, e.toString());
        } else if (code == 401) {
            ToastUtil.ToastShort(this, " 发送失败，请致电该机构！");
            MLog.e(msg, e.toString());
        } else {
            ToastUtil.ToastShort(this, msg);
            MLog.e(msg, e.toString());
        }
    }

    /**
     * 倒计时
     *
     * @param time
     */
    @Override
    public void timeCountdown(int time) {
        button.setText("倒计时：" + time);

    }

    @Override
    public void stop() {
        button.setText(getResources().getString(R.string.get_identy));
    }
}