package com.yilaole.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 倒计时
 */

public class CountDownButton extends android.support.v7.widget.AppCompatButton {


    /**
     * 倒计时默认时间  60s
     */
    private int mCountdowntime = 60;

    /**
     * 动态记录倒计时时间
     */
    private int mNowtime = 0;

    //设置timertask
    TimerTask validateTask = null;
    Timer validateTimer = new Timer();
    private Activity mActivity;

    /**
     * 是否开始
     */
    private boolean isstart;

    public CountdownListener getOnCountDownListener() {
        return onCountDownListener;
    }

    public void setOnCountDownListener(CountdownListener onCountDownListener) {
        this.onCountDownListener = onCountDownListener;
    }

    private CountdownListener onCountDownListener;


    public CountDownButton(Context context) {
        super(context);

    }

    public CountDownButton(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public CountDownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    /**
     * 验证码倒计时开始 m默认30s
     *
     * @param activity 当前activity
     */
    public void start(final Activity activity) {
        mCountdowntime = 30;
        mActivity = activity;
        setValidateCodeTime();
    }

    /**
     * 验证码倒计时开始,设置倒计时时间
     *
     * @param activity 当前activity
     * @param time     单位 s
     */
    public void start(final Activity activity, final int time) {
        //获取按钮点击监听
        mCountdowntime = time;
        mActivity = activity;
        setValidateCodeTime();
    }

    /**
     * 倒计时停止
     */
    public void stopCountDown() {
        if (validateTask == null) {
            return;
        }

        validateTask.cancel();
        setEnabled(true);
        mNowtime = mCountdowntime;
        if (onCountDownListener != null) {
            onCountDownListener.stop();
        }
    }

    public interface CountdownListener {


        /**
         * 倒计时剩余时间
         *
         * @param time
         */
        void timeCountdown(int time);

        /**
         * 倒计时停止
         */
        void stop();
    }


    /**
     * 设置获取验证码间隔时间
     */
    private void setValidateCodeTime() {
        mNowtime = mCountdowntime;
        validateTask = new TimerTask() {
            @Override
            public void run() {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mNowtime <= 0) {
                            setEnabled(true);
                            if (onCountDownListener != null) {
                                onCountDownListener.stop();
                            }
                            validateTask.cancel();
                        } else {
                            setEnabled(false);
                            if (onCountDownListener != null) {
                                onCountDownListener.timeCountdown(mNowtime);
                            }
                        }
                        mNowtime--;
                    }
                });
            }
        };
        mNowtime = mNowtime - 1;
        validateTimer.schedule(validateTask, 0, 1000);
    }
}
