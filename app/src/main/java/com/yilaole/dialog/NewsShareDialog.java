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
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.inter_face.idialog.INewsCommentDialogCallback;
import com.yilaole.inter_face.idialog.INewsShareDialogCallback;


/**
 * 咨询详情 分享弹窗fragment
 */

public class NewsShareDialog extends DialogFragment implements View.OnClickListener {

    private Dialog mDialog;
    private TextView tv_cancel;
    private TextView tv_weichat;
    private TextView tv_friends;
    private InputMethodManager inputMethodManager;
    private INewsShareDialogCallback dataCallback;

    @Override
    public void onAttach(Context context) {
        if (!(getActivity() instanceof INewsCommentDialogCallback)) {
            throw new IllegalStateException("DialogFragment 所在的 activity 必须实现 INewsShareDialogCallback 接口");
        }
        super.onAttach(context);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mDialog = new Dialog(getActivity(), R.style.BottomDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //布局
        mDialog.setContentView(R.layout.dialog_share_news);
        mDialog.setCanceledOnTouchOutside(true);

        Window window = mDialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);

        //初始化控件
        tv_cancel = mDialog.findViewById(R.id.tv_share_cancel);
        tv_weichat = mDialog.findViewById(R.id.tv_weichat);
        tv_friends = mDialog.findViewById(R.id.tv_friends);

        tv_cancel.setOnClickListener(this);
        tv_weichat.setOnClickListener(this);
        tv_friends.setOnClickListener(this);

        dataCallback = (INewsShareDialogCallback) getActivity();
        return mDialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_share_cancel://取消
                dismiss();
                break;
            case R.id.tv_weichat://微信

                dataCallback.weichatShare();
                dismiss();
                break;

            case R.id.tv_friends://朋友圈

                dataCallback.friendsShare();
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
