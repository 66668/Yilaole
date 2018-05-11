package com.yilaole.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.yilaole.R;
import com.yilaole.base.BaseLogActivity;
import com.yilaole.base.app.Constants;
import com.yilaole.base.app.MyApplication;
import com.yilaole.bean.CommonBean;
import com.yilaole.bean.mine.WeichatAccessTokenBean;
import com.yilaole.bean.mine.WeichatBindResultBean;
import com.yilaole.bean.mine.WeichatPersonMessageBean;
import com.yilaole.http.MyHttpService;
import com.yilaole.http.WeichatHttpService;
import com.yilaole.http.cache.ACache;
import com.yilaole.http.rx.RxBus;
import com.yilaole.http.rx.RxBusBaseMessage;
import com.yilaole.http.rx.RxCodeConstants;
import com.yilaole.save.SPUtil;
import com.yilaole.utils.MLog;
import com.yilaole.utils.ToastUtil;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sjy on 2017/11/17.
 * <p>
 * 微信登陆界面设置
 */

public class WXEntryActivity extends BaseLogActivity implements IWXAPIEventHandler {

    public static final String TAG = "wechat";
    public static String code;
    public static BaseResp resp = null;
    private ACache aCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_wxentry);

        aCache = ACache.get(this);

        boolean handleIntent = MyApplication.api.handleIntent(getIntent(), this);
        //下面代码是判断微信分享后返回WXEnteryActivity的，
        // 如果handleIntent==false,说明没有调用IWXAPIEventHandler，
        // 则需要在这里销毁这个透明的Activity;
        if (handleIntent == false) {
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        MyApplication.api.handleIntent(intent, this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq baseReq) {
        MLog.d(TAG, "onReq: ");
        finish();
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp baseResp) {

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                MLog.d(TAG, "onResp: 成功");
                if (baseResp != null) {
                    resp = baseResp;
                    code = ((SendAuth.Resp) baseResp).code; //即为所需的access_token
                    loadingDialog.show();
                    getAccess_token(code);
                }

                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                MLog.d(TAG, "onResp: 用户取消");
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                MLog.d(TAG, "onResp: 发送请求被拒绝");
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        MLog.d(TAG, "onResume: 1");
        super.onResume();
    }

    //根据code获取access_token,这里用第三方volley框架进行post请求
    private void getAccess_token(final String code) {
        String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + Constants.WEIXIN_ID
                + "&secret="
                + Constants.WEIXIN_SECRET
                + "&code="
                + code
                + "&grant_type=authorization_code";

        MLog.d("微信获取access_token路径：" + path);

        /**
         *
         * 根据参数 通过Rxjava+okhttp方式获取：access_token、refresh_token、openid、scope、unionid、expires_in等值
         * 获取成功后再通过access_token 和openid获取用户信息
         *
         * 这是调通接口的成功返回json:
         *  {"access_token":"xxx","expires_in":7200,"refresh_token":"xxx","openid":"xxx","scope":"snsapi_userinfo","unionid":"xxx"}
         *  失败的返回json:
         *  {"code":400,"message":"xxx",result:[]}
         */
        WeichatHttpService.WeiChatBuilder.getWeiChatServer().getAccessToken(Constants.WEIXIN_ID, Constants.WEIXIN_SECRET, code, Constants.GRANT_TYPE)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeichatAccessTokenBean>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "微信获取access_token--onError:--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.dismiss();
                        MLog.e(TAG, "微信获取access_token--onError:" + e.toString());
                        ToastUtil.ToastShort(WXEntryActivity.this, "登陆失败,请用手机号登陆");
                        WXEntryActivity.this.finish();
                    }

                    @Override
                    public void onNext(WeichatAccessTokenBean bean) {
                        //将获取的信息再次获取个人信息
                        getUserInfo(bean.getAccess_token(), bean.getOpenid());
                    }

                });

    }


    /**
     * 获取用户信息
     *
     * @param access_token
     * @param openid
     */
    private void getUserInfo(String access_token, String openid) {
        //路径
        String path = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + access_token
                + "&openid="
                + openid;
        /**
         *
         * 根据参数 通过Rxjava+okhttp方式获取用户个人信息
         *
         * 这是调通接口的成功返回json:
         *  {"openid":"xxx","nickname":"xxx","sex":0,"language":"zh_CN","city":"","province":"","country":"","headimgurl":"xxx","privilege":[],"unionid":"xxx"}
         *  失败的返回json:
         *  {"code":400,"message":"xxx",result:[]}
         */
        WeichatHttpService.WeiChatBuilder.getWeiChatServer().getWeichatMessage(access_token, openid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeichatPersonMessageBean>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "咨询详情--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.dismiss();
                        MLog.e(TAG, "微信获取access_token--onError:" + e.toString());
                        ToastUtil.ToastShort(WXEntryActivity.this, "登陆失败,请用手机号登陆");
                        //                        WXEntryActivity.this.finish();
                    }

                    @Override
                    public void onNext(WeichatPersonMessageBean bean) {

                        //将微信信息绑定到后台
                        postWeichatMessage(bean.getNickname(), bean.getOpenid(), bean.getHeadimgurl(), bean.getUnionid());
                    }

                });
    }

    /**
     * 微信授权
     * <p>
     * 绑定到后台
     *
     * @param nickname
     * @param openid
     * @param headimgurl
     * @param unionid
     */
    private void postWeichatMessage(String nickname, String openid, String headimgurl, String unionid) {

        MyHttpService.Builder.getHttpServer().postWeichatMessage(nickname, openid, headimgurl, unionid, 3)//android 设备统一是3
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonBean<WeichatBindResultBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "咨询详情--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.dismiss();
                        MLog.e(TAG, "微信获取access_token--onError:" + e.toString());
                        ToastUtil.ToastShort(WXEntryActivity.this, "登陆失败,请用手机号登陆");
                        //                        WXEntryActivity.this.finish();
                    }

                    @Override
                    public void onNext(CommonBean<WeichatBindResultBean> bean) {
                        loadingDialog.dismiss();
                        //将微信信息绑定到后台
                        if (bean.getCode() == 200) {
                            //更新登陆信息

                            // 登陆为true
                            SPUtil.setIsLogin(true);
                            SPUtil.setUserName(bean.getResult().getName());
                            SPUtil.setUserImage(bean.getResult().getImage());
                            SPUtil.setToken(bean.getResult().getToken());
                            aCache.put(Constants.TOKEN, bean.getResult().getToken(), bean.getResult().getExpires());

                            //设置跳转
                            //需要回调 修改个人登录状态 RxBus
                            RxBus.getDefault().post(RxCodeConstants.LOG2LOG, new RxBusBaseMessage());

                            MyApplication.getInstance().closeAllActOFSome();

                        } else {
                            ToastUtil.ToastShort(WXEntryActivity.this, "登陆失败,请用手机号登陆");
                            WXEntryActivity.this.finish();
                        }

                    }

                });
    }
}
