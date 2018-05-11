package com.yilaole.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yilaole.R;
import com.yilaole.base.BaseFragment;
import com.yilaole.http.rx.RxBus;
import com.yilaole.http.rx.RxBusBaseMessage;
import com.yilaole.http.rx.RxCodeConstants;
import com.yilaole.save.SPUtil;
import com.yilaole.ui.LoginActivity;
import com.yilaole.ui.MineAboutActivity;
import com.yilaole.ui.MineAppointVisitListActivity;
import com.yilaole.ui.MineCenterActivity;
import com.yilaole.ui.MineCollectListActivity;
import com.yilaole.ui.MineCommentListActivity;
import com.yilaole.ui.MineDoorAssessListActivity;
import com.yilaole.ui.MineMessageListActivity;
import com.yilaole.ui.MineSettingActivity;
import com.yilaole.ui.RegistActivity;
import com.yilaole.utils.GlideCircleTransform;
import com.yilaole.utils.MLog;
import com.yilaole.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;


/**
 * 我的
 */

public class NavMineFragment extends BaseFragment {

    //头像
    @BindView(R.id.img_face)
    ImageView img_face;

    //登录
    @BindView(R.id.layout_login)
    RelativeLayout layout_login;

    //姓名
    @BindView(R.id.layout_name)
    RelativeLayout layout_name;

    @BindView(R.id.tv_user_name)
    TextView tv_user_name;


    private boolean isLogin;

    public static NavMineFragment newInstance() {
        NavMineFragment fragment = new NavMineFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initMyView();
        /**
         * 因为启动时先走lazyLoad()再走onActivityCreated，
         * 所以此处要额外调用lazyLoad(),不然最初不会加载内容
         */
        lazyLoad();
    }

    private void initMyView() {
        initRxBus();
        isLogin = SPUtil.isLogin();//从存储中获取登录状态
        changeLoginState();
        changePhotoState();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * =====================================================================================================
     * ==================================================根据Rxbus值，修改状态===================================================
     * =====================================================================================================
     */

    /**
     * 判断登录状态
     */
    private void changeLoginState() {

        if (isLogin) {//isLogin
            MLog.d(layout_login == null);

            layout_login.setVisibility(View.INVISIBLE);
            layout_name.setVisibility(View.VISIBLE);
            //
            //            //姓名
            tv_user_name.setText(SPUtil.getUserName());

            Glide.with(getActivity())
                    .load(SPUtil.getUserImage() != null ? SPUtil.getUserImage() : "")
                    .error(ContextCompat.getDrawable(getActivity(), R.mipmap.photo_default))
                    .transform(new GlideCircleTransform(getActivity()))
                    .into(img_face);

        } else {
            layout_login.setVisibility(View.VISIBLE);
            layout_name.setVisibility(View.INVISIBLE);

            //未登录不显示头像
            img_face.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.mipmap.photo_default));
        }

    }

    /**
     * 昵称状态
     */
    private void changeNameState() {
        tv_user_name.setText(SPUtil.getUserName());

    }

    /**
     * 头像状态
     */

    private void changePhotoState() {
        if (isLogin) {//isLogin
            Glide.with(getActivity())
                    .load(SPUtil.getUserImage() != null ? SPUtil.getUserImage() : "")
                    .error(ContextCompat.getDrawable(getActivity(), R.mipmap.photo_default))
                    .transform(new GlideCircleTransform(getActivity()))
                    .into(img_face);
        } else {
            //未登录不显示头像
            Glide.with(getActivity())
                    .load(R.mipmap.photo_default)
                    .error(ContextCompat.getDrawable(getActivity(), R.mipmap.photo_default))
                    .transform(new GlideCircleTransform(getActivity()))
                    .into(img_face);
        }

    }

    /**
     * 监听
     *
     * @param view
     */

    @OnClick({R.id.img_setting, R.id.img_face, R.id.tv_login, R.id.tv_reg, R.id.appointment, R.id.assessment, R.id.record
            , R.id.layout_center, R.id.layout_message, R.id.layout_collect, R.id.layout_commentaries
            , R.id.layout_name, R.id.layout_about})
    public void onItemClick(View view) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        switch (view.getId()) {
            case R.id.img_setting://设置
                intent.setClass(getActivity(), MineSettingActivity.class);
                startActivity(intent);
                break;
            case R.id.img_face://图像

                break;
            case R.id.tv_login://登录
                intent.setClass(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_reg://注册
                intent.setClass(getActivity(), RegistActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_name://个人姓名
                intent.setClass(getActivity(), MineCenterActivity.class);
                startActivity(intent);
                break;

            case R.id.appointment://预约参观
                if (!SPUtil.isLogin()) {
                    ToastUtil.ToastShort(getActivity(), getResources().getString(R.string.dialog_login));
                    return;
                }
                intent.setClass(getActivity(), MineAppointVisitListActivity.class);
                startActivity(intent);
                break;

            case R.id.assessment://评估
                if (!SPUtil.isLogin()) {
                    ToastUtil.ToastShort(getActivity(), getResources().getString(R.string.dialog_login));
                    return;
                }
                intent.setClass(getActivity(), MineDoorAssessListActivity.class);
                startActivity(intent);

                break;
            case R.id.record://长者记录 aaa
                ToastUtil.ToastShort(getActivity(), getResources().getString(R.string.aaa));
                break;
            case R.id.layout_center://个人中心
                intent.setClass(getActivity(), MineCenterActivity.class);
                startActivity(intent);
                break;

            case R.id.layout_message://我的消息
                if (!SPUtil.isLogin()) {
                    ToastUtil.ToastShort(getActivity(), getResources().getString(R.string.dialog_login));
                    return;
                }
                intent.setClass(getActivity(), MineMessageListActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_collect://我的收藏
                if (!SPUtil.isLogin()) {
                    ToastUtil.ToastShort(getActivity(), getResources().getString(R.string.dialog_login));
                    return;
                }
                intent.setClass(getActivity(), MineCollectListActivity.class);
                startActivity(intent);

                break;
            case R.id.layout_commentaries://我的评价
                if (!SPUtil.isLogin()) {
                    ToastUtil.ToastShort(getActivity(), getResources().getString(R.string.dialog_login));
                    return;
                }
                intent.setClass(getActivity(), MineCommentListActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_about://关于我们 MineAboutActivity
                intent.setClass(getActivity(), MineAboutActivity.class);
                startActivity(intent);
                break;
            //            case R.id.layout_wallet://我的钱包
            //
            //                break;
            default:
                break;
        }
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 使用RxBus 实现登录状态修改
     */
    private void initRxBus() {

        //微信登陆 后台登陆
        RxBus.getDefault().toObservable(RxCodeConstants.LOG2LOG, RxBusBaseMessage.class)
                .subscribe(new Action1<RxBusBaseMessage>() {
                    @Override
                    public void call(RxBusBaseMessage rxBusBaseMessage) {
                        MLog.i("我的-RxBus消费事件！登录");
                        // 修改状态
                        //isLogin = MyApplication.getInstance().isLogin(getActivity());
                        isLogin = SPUtil.isLogin();
                        changeLoginState();
                    }
                });

        RxBus.getDefault().toObservable(RxCodeConstants.UNLOG2LOG, RxBusBaseMessage.class)
                .subscribe(new Action1<RxBusBaseMessage>() {
                    @Override
                    public void call(RxBusBaseMessage rxBusBaseMessage) {
                        MLog.i("我的-RxBus消费事件！取消登录");
                        // 修改状态
                        //isLogin = MyApplication.getInstance().isLogin(getActivity());
                        isLogin = SPUtil.isLogin();
                        MLog.d("RxBus-取消登录", isLogin);
                        changeLoginState();
                    }
                });
        RxBus.getDefault().toObservable(RxCodeConstants.NAME2CENTER, RxBusBaseMessage.class)
                .subscribe(new Action1<RxBusBaseMessage>() {
                    @Override
                    public void call(RxBusBaseMessage rxBusBaseMessage) {
                        MLog.i("我的-RxBus消费事件！修改昵称");
                        // 修改状态
                        changeNameState();
                    }
                });

        RxBus.getDefault().toObservable(RxCodeConstants.PHOTO2MINE, RxBusBaseMessage.class)
                .subscribe(new Action1<RxBusBaseMessage>() {
                    @Override
                    public void call(RxBusBaseMessage rxBusBaseMessage) {
                        MLog.i("我的-RxBus消费事件！修改头像");
                        // 修改状态
                        changePhotoState();
                    }
                })

        ;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
