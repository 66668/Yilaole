package com.yilaole.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.inter_face.idialog.IMineCollectDialogCallback;
import com.yilaole.utils.MLog;


/**
 * 我的收藏 弹窗fragment
 */

public class MineCollectDialog extends DialogFragment implements View.OnClickListener {

    private Dialog mDialog;
    private TextView tv_collect_cancel;
    private TextView tv_weichat;
    private TextView tv_goin;
    private TextView tv_uncollect;
    private IMineCollectDialogCallback dataCallback;
    private int intentID;

    public MineCollectDialog() {
        super();
    }


    @Override
    public void onAttach(Context context) {
        if (!(getActivity() instanceof IMineCollectDialogCallback)) {
            throw new IllegalStateException("DialogFragment 所在的 activity 必须实现 IMineCollectDialogCallback 接口");
        }
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intentID = getArguments().getInt("id");
        MLog.d("弹窗获取跳转值", intentID);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mDialog = new Dialog(getActivity(), R.style.BottomDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //布局
        mDialog.setContentView(R.layout.dialog_mine_collect);
        mDialog.setCanceledOnTouchOutside(true);

        Window window = mDialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);

        dataCallback = (IMineCollectDialogCallback) getActivity();

        //初始化控件
        tv_collect_cancel = mDialog.findViewById(R.id.tv_collect_cancel);
        tv_weichat = mDialog.findViewById(R.id.tv_weichat);
        tv_goin = mDialog.findViewById(R.id.tv_goin);
        tv_uncollect = mDialog.findViewById(R.id.tv_uncollect);

        //获取值
        dataCallback = (IMineCollectDialogCallback) getActivity();


        tv_collect_cancel.setOnClickListener(this);
        tv_weichat.setOnClickListener(this);
        tv_uncollect.setOnClickListener(this);
        tv_goin.setOnClickListener(this);


        return mDialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_collect_cancel://取消
                dismiss();
                break;
            case R.id.tv_weichat://微信

                dataCallback.collectWeichat(intentID);
                dismiss();
                break;

            case R.id.tv_uncollect://取消收藏

                dataCallback.unCollect(intentID);
                dismiss();
                break;
            case R.id.tv_goin://进入机构

                dataCallback.collectGoin(intentID);
                dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
