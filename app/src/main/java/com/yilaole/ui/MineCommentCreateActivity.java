package com.yilaole.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.base.BaseActivity;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.institution.detail.PostCommentBean;
import com.yilaole.http.cache.ACache;
import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.presenter.DetailCommentCreatePresenterImpl;
import com.yilaole.save.SPUtil;
import com.yilaole.utils.MLog;
import com.yilaole.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的-预约参观+上门评估的 机构点评 创建
 * 有订单id
 */

public class MineCommentCreateActivity extends BaseActivity implements OnCommonListener {

    //
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.layout_back)
    RelativeLayout layout_back;

    //机构评分
    @BindView(R.id.institute_ratingBar)
    RatingBar institute_ratingBar;

    //服务评分
    @BindView(R.id.servuce_ratingBar)
    RatingBar servuce_ratingBar;

    //内容
    @BindView(R.id.et_content)
    EditText et_content;

    //内容计数
    @BindView(R.id.tv_count)
    TextView tv_count;

    //图片
    @BindView(R.id.photo_recyclerView)
    RecyclerView photo_recyclerView;

    //提交
    @BindView(R.id.btn_sure)
    Button btn_sure;

    private String content;
    private String token;
    private int instituteId;
    private int bookId;//订单id
    private int minLenght = 10;
    private int agency_score = -1;
    private int servoce_score = -1;
    private DetailCommentCreatePresenterImpl presenter;
    private PostCommentBean bean;//上传的参数封装bean
    private ACache aCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_detail_comment_create);
        ButterKnife.bind(this);
        initMyView();
        initListener();
    }

    private void initMyView() {
        getIntentData();
        tv_title.setText(getResources().getString(R.string.instite_detail_title));
        presenter = new DetailCommentCreatePresenterImpl(this, this);
        aCache = ACache.get(this);
        bean = new PostCommentBean();
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        instituteId = bundle.getInt("id");
        bookId = bundle.getInt("bookId");
        MLog.d("机构点评", instituteId);
    }

    //
    private void initListener() {
        institute_ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingBar.setRating(v);
                agency_score = (int) v;
            }
        });

        servuce_ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingBar.setRating(v);
                servoce_score = (int) v;
            }
        });

        //评论内容监听
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if (length <= 0) {
                    tv_count.setText("至少" + minLenght + "字评价");
                } else if (length < minLenght && length > 0) {
                    tv_count.setText("加油，还差" + (minLenght - length) + "个字！");
                } else {
                    tv_count.setText("");
                }

            }
        });
    }

    private void getData() {
        //token
        token = aCache.getAsString(Constants.TOKEN);

        //评分
        agency_score = (int) institute_ratingBar.getRating();
        servoce_score = (int) servuce_ratingBar.getRating();

        //内容
        content = et_content.getText().toString();

        //图片


    }

    private boolean isNull() {
        if (agency_score == -1 || servoce_score == -1) {
            ToastUtil.ToastShort(MineCommentCreateActivity.this, "评分不能为空！");
            return true;
        }
        if (content.isEmpty()) {
            ToastUtil.ToastShort(MineCommentCreateActivity.this, "提交评论不能为空");
            return true;
        } else if (content.length() < minLenght) {
            ToastUtil.ToastShort(MineCommentCreateActivity.this, "评论字数不够！");
            return true;
        }
        // TODO: 2017/10/24 token统一处理问题
        if (!SPUtil.isLogin()) {
            ToastUtil.ToastShort(MineCommentCreateActivity.this, "请登录！");
            return true;

        }
        if (token.isEmpty()) {
            ToastUtil.toastInMiddle(MineCommentCreateActivity.this, "token失效，请重新登录！");
            return true;
        }
        return false;

    }

    @OnClick({R.id.layout_back, R.id.btn_sure})
    public void onItemClick(View view) {
        switch (view.getId()) {
            case R.id.layout_back:
                this.finish();
                break;
            case R.id.btn_sure:
                getData();
                if (isNull()) {
                    return;
                }
                setData();
                postData();
                break;
            default:
                break;
        }

    }

    /**
     * 封装数据
     */
    private void setData() {
        bean.setId(instituteId);
        bean.setToken(token);
        bean.setAgency_score(agency_score);
        bean.setServoce_score(servoce_score);
        bean.setContent(content);
        bean.setType(2);
        bean.setFile0(null);
        bean.setFile1(null);
        bean.setFile2(null);

    }

    /**
     * ================================================================================================
     * ==============================================接口==================================================
     * ================================================================================================
     */
    private void postData() {
        loadingDialog.show();
        presenter.pPostData(bean);

    }

    /**
     * ================================================================================================
     * ==============================================接口回调==================================================
     * ================================================================================================
     */
    @Override
    public void onDataSuccess(Object obj) {
        loadingDialog.dismiss();
    }

    @Override
    public void onDataFailed(int code,String msg, Exception e) {
        loadingDialog.dismiss();

        if (code == -1) {
            ToastUtil.ToastShort(this, msg);
        } else if (code == 400) {
            ToastUtil.ToastShort(this, "发表评论失败！");
            MLog.e(msg, e.toString());
        } else {
            ToastUtil.ToastShort(this, msg);
            MLog.e(msg, e.toString());
        }
    }
}
