package com.yilaole.ui;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yilaole.R;
import com.yilaole.base.BaseActivity;
import com.yilaole.base.app.Constants;
import com.yilaole.base.app.MyApplication;
import com.yilaole.bean.mine.PhotoBean;
import com.yilaole.dialog.ChoosePhotoPicDialog;
import com.yilaole.http.cache.ACache;
import com.yilaole.http.rx.RxBus;
import com.yilaole.http.rx.RxBusBaseMessage;
import com.yilaole.http.rx.RxCodeConstants;
import com.yilaole.inter_face.ilistener.OnChangePhotoListener;
import com.yilaole.permissions.PermissionListener;
import com.yilaole.permissions.PermissionsUtil;
import com.yilaole.presenter.MineNamePhotoPresenterImpl;
import com.yilaole.save.SPUtil;
import com.yilaole.utils.ToastUtil;
import com.yilaole.widget.CircleImageView;

import java.io.File;
import java.util.ArrayList;

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
 * 个人中心--
 * <p>
 * 头像 添加/修改 权限设置
 * 修改昵称
 * 修改密码
 * 退出登陆
 * 退出程序
 */
public class MineCenterActivity extends BaseActivity implements OnChangePhotoListener {

    //
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.layout_back)
    RelativeLayout layout_back;

    @BindView(R.id.btn_quit)
    Button btn_quit;

    @BindView(R.id.img_userPhoto)
    CircleImageView img_userPhoto;
    private static final int GALLERY = 2;
    private static final int CAMERA = 1;
    private ACache aCache;
    private String token;
    private MineNamePhotoPresenterImpl presenter;
    private Uri uri;

    //图片相关
    private Uri imageUri;
    private File imgfile;
    private CompressConfig config; //图片压缩的操作
    private TakePhotoOptions.Builder optionsBuilder; //图片角度，相册样式选择的操作
    private TakePhoto takePhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mine_center);
        ButterKnife.bind(this);
        initMyView();
    }

    /**
     * ======================================================================================================================
     * ============================================================初始化==========================================================
     * ======================================================================================================================
     */
    private void initMyView() {
        tv_title.setText(getResources().getString(R.string.mine_center));
        aCache = ACache.get(this);
        presenter = new MineNamePhotoPresenterImpl(this, this);
        if (SPUtil.isLogin()) {
            Glide.with(this)
                    .load(SPUtil.getUserImage())
                    .error(ContextCompat.getDrawable(this, R.mipmap.photo_default))
                    .into(img_userPhoto);
        } else {
            Glide.with(this)
                    .load(R.mipmap.photo_default)
                    .error(ContextCompat.getDrawable(this, R.mipmap.photo_default))
                    .into(img_userPhoto);
        }


    }

    /**
     * 监听
     *
     * @param view
     */
    @OnClick({R.id.layout_back, R.id.btn_quit, R.id.layout_name, R.id.layout_ps, R.id.btn_unlog, R.id.img_userPhoto, R.id.layout_logout})
    public void onItemClick(View view) {
        switch (view.getId()) {
            case R.id.layout_back:
                this.finish();
                break;
            case R.id.btn_quit://退出程序

                //消费Rxbus需要释放（必不可少）
                RxBus.getDefault().release();

                //推送 关闭

                //遍历关闭act界面
                MyApplication.getInstance().exit();
                break;
            case R.id.btn_unlog://退出登录
                if (!SPUtil.isLogin()) {
                    ToastUtil.ToastShort(this, "已经是退出状态！");
                } else {
                    SPUtil.setIsLogin(false);
                    MyApplication.getInstance().setLogin(false);
                    SPUtil.setToken("");
                    //清除token+其他信息
                    aCache.put(Constants.TOKEN, "");

                    RxBus.getDefault().post(RxCodeConstants.UNLOG2LOG, new RxBusBaseMessage());
                    this.finish();
                }

                break;

            case R.id.layout_name://修改昵称
                if (!SPUtil.isLogin()) {
                    ToastUtil.ToastShort(this, "请先登录！");
                    return;
                }
                startActivity(MineChangeNameActivity.class);
                break;
            case R.id.layout_logout://注销
                if (!SPUtil.isLogin()) {
                    ToastUtil.ToastShort(this, "已经是退出状态！");
                } else {
                    SPUtil.setIsLogin(false);
                    MyApplication.getInstance().setLogin(false);
                    SPUtil.setToken("");
                    //清除token+其他信息
                    aCache.put(Constants.TOKEN, "");

                    RxBus.getDefault().post(RxCodeConstants.UNLOG2LOG, new RxBusBaseMessage());
                    this.finish();
                }

                break;

            case R.id.img_userPhoto://相机弹窗 权限

                if (!SPUtil.isLogin()) {
                    ToastUtil.ToastShort(this, "请先登录！");
                    return;
                }

                //相机调用相关 需要权限
                if (PermissionsUtil.hasPermission(this, Manifest.permission.CAMERA)) {//ACCESS_FINE_LOCATION ACCESS_COARSE_LOCATION这两个是一组，用一个判断就够了
                    //授权通过
                    initCamera(true);
                    startCameraShow();
                } else {
                    //第一次使用该权限调用
                    PermissionsUtil.requestPermission(this
                            , new PermissionListener() {
                                //授权通过
                                @Override
                                public void permissionGranted(@NonNull String[] permissions) {
                                    initCamera(true);
                                    startCameraShow();
                                }

                                @Override
                                public void permissionDenied(@NonNull String[] permissions) {
                                    ToastUtil.toastLong(MineCenterActivity.this, "该功能不可用");
                                }
                            }
                            , Manifest.permission.CAMERA);
                }


                break;
            case R.id.layout_ps://修改密码
                startActivity(MineChangePsActivity.class);
                break;
        }

    }



    /**
     * ======================================================================================================================
     * ============================================================数据接口==========================================================
     * ======================================================================================================================
     */

    private void postImg() {
        // TODO: 2017/11/10 token
        token = aCache.getAsString(Constants.TOKEN);
        if (token.isEmpty()) {
            ToastUtil.ToastShort(this, "请重新登陆！");
            return;
        }
        loadingDialog.show();
        presenter.pChangePhotoPost(token, imgfile);
    }


    /**
     * ======================================================================================================================
     * ============================================================接口回调==========================================================
     * ======================================================================================================================
     */
    @Override
    public void onChangePhoteSuccess(Object obj) {
        loadingDialog.dismiss();

        //上传成功就显示图片
        Glide.with(this)
                .load(imgfile)
                .into(img_userPhoto);
        //保存
        PhotoBean bean = (PhotoBean) obj;
        SPUtil.setUserImage(bean.getImage());

        //Rxbus通知更改显示
        RxBus.getDefault().post(RxCodeConstants.PHOTO2MINE, new RxBusBaseMessage());
    }

    @Override
    public void onChangePhoteFailed(int code, String msg, Exception e) {
        loadingDialog.dismiss();
        ToastUtil.ToastShort(this, msg);

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
        takePhoto.onPickFromCaptureWithCrop(imageUri, getInitCropOptions());

        //正常相机

        //         takePhoto.onPickFromCapture(imageUri);

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

    @Override
    public void takeCancel() {
        super.takeCancel();
    }


    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        showImg(result.getImages());
    }

    private void showImg(ArrayList<TImage> images) {
        imgfile = new File(images.get(images.size() - 1).getCompressPath());
        postImg();

    }
}
