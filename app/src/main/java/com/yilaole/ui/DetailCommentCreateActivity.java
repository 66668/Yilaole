package com.yilaole.ui;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.adapter.institution.InstitutionDetailCommentCreatePicAddRecylerAdapter;
import com.yilaole.base.BaseActivity;
import com.yilaole.base.app.Constants;
import com.yilaole.bean.institution.detail.PostCommentBean;
import com.yilaole.dialog.ChoosePhotoPicDialog;
import com.yilaole.http.cache.ACache;
import com.yilaole.http.rx.RxBus;
import com.yilaole.http.rx.RxBusBaseMessage;
import com.yilaole.http.rx.RxCodeConstants;
import com.yilaole.inter_face.ilistener.OnCommonListener;
import com.yilaole.permissions.PermissionListener;
import com.yilaole.permissions.PermissionsUtil;
import com.yilaole.presenter.DetailCommentCreatePresenterImpl;
import com.yilaole.save.SPUtil;
import com.yilaole.utils.MLog;
import com.yilaole.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.yilaole.takephoto.app.TakePhoto;
import lib.yilaole.takephoto.compress.CompressConfig;
import lib.yilaole.takephoto.model.CropOptions;
import lib.yilaole.takephoto.model.TImage;
import lib.yilaole.takephoto.model.TResult;
import lib.yilaole.takephoto.model.TakePhotoOptions;

/**
 * 机构点评 创建
 * 无订单id
 * 图片最多三张
 */

public class DetailCommentCreateActivity extends BaseActivity implements OnCommonListener {

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
    private int minLenght = 10;
    private int agency_score = -1;
    private int servoce_score = -1;
    private DetailCommentCreatePresenterImpl presenter;
    private PostCommentBean bean;//上传的参数封装bean
    private ACache aCache;
    private InstitutionDetailCommentCreatePicAddRecylerAdapter picAdapter;//拍照图片适配
    private List<File> picLists; //评论的图片集合
    private LinearLayoutManager manager;//横屏现实
    //图片相关
    private Uri imageUri;
    private File imgfile;
    private CompressConfig config; //图片压缩的操作
    private TakePhotoOptions.Builder optionsBuilder; //图片角度，相册样式选择的操作
    private TakePhoto takePhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_detail_comment_create);
        ButterKnife.bind(this);
        initMyView();
        initListener();


        //测试
        initManager();

        initTestAdapter();
    }

    /**
     * =============================================================================
     * 拍照适配 测试
     */
    /**
     * 显示样式 横向
     */
    private void initManager() {
        picLists = new ArrayList<>();

        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        photo_recyclerView.setLayoutManager(manager);
    }

    private void initTestAdapter() {
        picAdapter = new InstitutionDetailCommentCreatePicAddRecylerAdapter(this, picLists);
        //添加footer 并监听
        View footerView = getFooterView(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相机调用相关 需要权限
                if (PermissionsUtil.hasPermission(DetailCommentCreateActivity.this, Manifest.permission.CAMERA)) {//ACCESS_FINE_LOCATION ACCESS_COARSE_LOCATION这两个是一组，用一个判断就够了
                    //授权通过
                    initCamera(true);
                    startCameraShow();
                } else {
                    if (picLists != null && picLists.size() >= 3) {
                        ToastUtil.ToastShort(DetailCommentCreateActivity.this, "最多三张照片");
                        return;
                    }
                    //第一次使用该权限调用
                    PermissionsUtil.requestPermission(DetailCommentCreateActivity.this
                            , new PermissionListener() {
                                //授权通过
                                @Override
                                public void permissionGranted(@NonNull String[] permissions) {
                                    initCamera(true);
                                    startCameraShow();
                                }

                                @Override
                                public void permissionDenied(@NonNull String[] permissions) {
                                    ToastUtil.toastLong(DetailCommentCreateActivity.this, "该功能不可用");
                                }
                            }
                            , Manifest.permission.CAMERA);
                }


                //                MLog.d("pic", picAdapter.getItemCount());
                //                if (picLists != null && picLists.size() < 3) {
                //                    MLog.ToastShort(DetailCommentCreateActivity.this, "添加一张图片");
                //                    MLog.d("pic", picLists.size(),picAdapter.getItemCount());
                //                    picAdapter.addData("数据");
                //
                //                } else {
                //                    MLog.ToastShort(DetailCommentCreateActivity.this, "最多三张");
                //                }
            }
        });
        picAdapter.addFooterView(footerView, 0);

        photo_recyclerView.setAdapter(picAdapter);
    }

    /**
     * 添加footer布局
     *
     * @param type
     * @param listener
     * @return
     */
    private View getFooterView(int type, View.OnClickListener listener) {
        View view = getLayoutInflater().inflate(R.layout.act_detail_comment_footer, (ViewGroup) photo_recyclerView.getParent(), false);
        view.setOnClickListener(listener);
        return view;
    }

    /**
     * =============================================================================
     */

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
        MLog.d("机构点评", instituteId);
    }

    /**
     * 监听
     */
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

    /**
     * 监听
     *
     * @param view
     */
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
     * ======================================================================================
     * ================================================继承自TakePhotoActivity要使用的方法|相机相关操作======================================
     * ======================================================================================
     *
     */
    /**
     * 初始化 相机使用参数
     * <p>
     * 说明：
     * 1.路径：efulai/userphoto/xxx.jpg
     * 2.像素框：800*800
     * 3.允许相机的压缩：800*800/300Kb大小/允许保存原图/压缩允许显示进度条
     * 4.旋转角度纠正true
     * 5.使用系统相机效果
     *
     * @param isCompress 是否压缩处理
     */
    private void initCamera(boolean isCompress) {

        takePhoto = getTakePhoto();

        //设置文件路径：//efulai/userphoto/xxx.jpg
        File file = new File(Environment.getExternalStorageDirectory(), Constants.ABSULTEPATH + "/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        imageUri = Uri.fromFile(file);


        //图片压缩
        if (!isCompress) {
            takePhoto.onEnableCompress(null, false);
            return;
        }
        //方式1：使用libs自带压缩工具
        config = new CompressConfig.Builder()
                .setMaxSize(1024 * 300)//最大不超过300kb
                .setMaxPixel(800)//设置最大尺寸
                .enableReserveRaw(true)//是否保存原图
                .create();
        //方式2：使用luban压缩工具
        //        LubanOptions option = new LubanOptions.Builder()
        //                .setMaxHeight(800)
        //                .setMaxWidth(800)
        //                .setMaxSize(1024 * 300)
        //                .create();
        //        config = CompressConfig.ofLuban(option);
        //        config.enableReserveRaw(true);//是否保存原图

        takePhoto.onEnableCompress(config, true);//是否显示压缩进度

        //
        optionsBuilder = new TakePhotoOptions.Builder()
                .setCorrectImage(true)//纠正旋转角度
                .setWithOwnGallery(false);//是否使用libs的相册效果
        takePhoto.setTakePhotoOptions(optionsBuilder.create());
    }

    private void startCameraShow() {
        //调用相机弹窗
        ChoosePhotoPicDialog dialog = new ChoosePhotoPicDialog(this, new ChoosePhotoPicDialog.ChooseItemDialogCallBack() {
            @Override
            public void galleryCallback() {
                gotoGallery();
            }

            @Override
            public void cameraCallback() {
                gotoCamera();
            }

        });
        dialog.show();
    }

    /**
     * 打开相机
     */

    private void gotoCamera() {
        //剪裁压缩操作
        //        takePhoto.onPickFromCaptureWithCrop(imageUri, getInitCropOptions());

        //正常相机

        takePhoto.onPickFromCapture(imageUri);

    }

    /**
     * 拍照前初始化必要参数（支持剪裁的功能必须为true）
     *
     * @return
     */
    private CropOptions getInitCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setOutputX(800).setOutputY(800);//设置剪裁的像素值
        builder.setWithOwnCrop(true);//true是libs自带剪裁框，false是第三方框
        return builder.create();
    }

    /**
     * 打开相册
     */

    private void gotoGallery() {

        //多张+剪裁压缩操作
        //        takePhoto.onPickMultipleWithCrop(3, getInitCropOptions());

        //剪裁压缩+1张
        //        takePhoto.onPickFromGalleryWithCrop(imageUri, getInitCropOptions());

        //正常相册
        takePhoto.onPickFromGallery();

        //正常+选择多张
        //        takePhoto.onPickMultiple(3);


    }

    /**
     * 拍照 相册取照片后的回调
     *
     * @param result
     */
    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        showImg(result.getImages());
    }

    /**
     * 图片展示
     *
     * @param images
     */
    private void showImg(ArrayList<TImage> images) {
        imgfile = new File(images.get(images.size() - 1).getCompressPath());

        if (picLists != null && picLists.size() < 3) {
            MLog.ToastShort(DetailCommentCreateActivity.this, "添加一张图片");
            MLog.d("pic", picLists.size(), picAdapter.getItemCount());
            picAdapter.addData(imgfile);

        } else {
            MLog.ToastShort(DetailCommentCreateActivity.this, "最多三张");
        }

    }


    /**
     *
     */

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
            ToastUtil.ToastShort(DetailCommentCreateActivity.this, "评分不能为空！");
            return true;
        }
        if (content.isEmpty()) {
            ToastUtil.ToastShort(DetailCommentCreateActivity.this, "提交评论不能为空");
            return true;
        } else if (content.length() < minLenght) {
            ToastUtil.ToastShort(DetailCommentCreateActivity.this, "评论字数不够！");
            return true;
        }
        // TODO: 2017/10/24 token统一处理问题
        if (!SPUtil.isLogin()) {
            ToastUtil.ToastShort(DetailCommentCreateActivity.this, "请登录！");
            return true;

        }
        if (token.isEmpty()) {
            ToastUtil.toastInMiddle(DetailCommentCreateActivity.this, "token失效，请重新登录！");
            return true;
        }
        return false;

    }

    /**
     * 封装数据 无订单id
     */
    private void setData() {
        bean.setId(instituteId);
        bean.setToken(token);
        bean.setType(2);
        bean.setAgency_score(agency_score);
        bean.setServoce_score(servoce_score);
        bean.setContent(content);
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
        ToastUtil.ToastShort(this, "发表评论成功！");
        //修改机构评论
        RxBus.getDefault().post(RxCodeConstants.COMMENT2DETAIL, new RxBusBaseMessage());
        this.finish();
    }

    @Override
    public void onDataFailed(int code, String msg, Exception e) {
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
