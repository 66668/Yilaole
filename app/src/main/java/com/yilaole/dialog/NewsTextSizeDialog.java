package com.yilaole.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.inter_face.idialog.INewsCommentDialogCallback;
import com.yilaole.inter_face.idialog.INewsTextSizeDialogCallback;
import com.yilaole.save.SPUtil;


/**
 * 咨询详情 字体设置弹窗fragment
 */

public class NewsTextSizeDialog extends DialogFragment implements View.OnClickListener {

    private Dialog mDialog;

    private TextView tv_small;
    private TextView tv_middle;
    private TextView tv_big;
    private TextView tv_superBig;

    private INewsTextSizeDialogCallback dataCallback;

    @Override
    public void onAttach(Context context) {
        if (!(getActivity() instanceof INewsCommentDialogCallback)) {
            throw new IllegalStateException("DialogFragment 所在的 activity 必须实现 INewsTextSizeDialogCallback 接口");
        }
        super.onAttach(context);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mDialog = new Dialog(getActivity(), R.style.BottomDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //布局
        mDialog.setContentView(R.layout.dialog_textsize_news);
        mDialog.setCanceledOnTouchOutside(true);

        Window window = mDialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);

        //初始化控件
        tv_small = mDialog.findViewById(R.id.tv_small);
        tv_middle = mDialog.findViewById(R.id.tv_middle);
        tv_big = mDialog.findViewById(R.id.tv_big);
        tv_superBig = mDialog.findViewById(R.id.tv_superBig);

        // 初始化选中字体样式
        int size = SPUtil.getNewsSize();
        if (size == 16) {
            tv_small.setTextColor(ContextCompat.getColor(getActivity(), R.color.institute_tv1));
            tv_middle.setTextColor(ContextCompat.getColor(getActivity(), R.color.filter_reset_tvcl));
            tv_big.setTextColor(ContextCompat.getColor(getActivity(), R.color.filter_reset_tvcl));
            tv_superBig.setTextColor(ContextCompat.getColor(getActivity(), R.color.filter_reset_tvcl));
        } else if (size == 18) {
            tv_small.setTextColor(ContextCompat.getColor(getActivity(), R.color.filter_reset_tvcl));
            tv_middle.setTextColor(ContextCompat.getColor(getActivity(), R.color.institute_tv1));
            tv_big.setTextColor(ContextCompat.getColor(getActivity(), R.color.filter_reset_tvcl));
            tv_superBig.setTextColor(ContextCompat.getColor(getActivity(), R.color.filter_reset_tvcl));
        } else if (size == 20) {
            tv_small.setTextColor(ContextCompat.getColor(getActivity(), R.color.filter_reset_tvcl));
            tv_middle.setTextColor(ContextCompat.getColor(getActivity(), R.color.filter_reset_tvcl));
            tv_big.setTextColor(ContextCompat.getColor(getActivity(), R.color.institute_tv1));
            tv_superBig.setTextColor(ContextCompat.getColor(getActivity(), R.color.filter_reset_tvcl));
        } else {//22
            tv_small.setTextColor(ContextCompat.getColor(getActivity(), R.color.filter_reset_tvcl));
            tv_middle.setTextColor(ContextCompat.getColor(getActivity(), R.color.filter_reset_tvcl));
            tv_big.setTextColor(ContextCompat.getColor(getActivity(), R.color.filter_reset_tvcl));
            tv_superBig.setTextColor(ContextCompat.getColor(getActivity(), R.color.institute_tv1));
        }

        tv_small.setOnClickListener(this);
        tv_middle.setOnClickListener(this);
        tv_big.setOnClickListener(this);
        tv_superBig.setOnClickListener(this);

        dataCallback = (INewsTextSizeDialogCallback) getActivity();
        return mDialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_small:
                tv_small.setTextColor(ContextCompat.getColor(getActivity(), R.color.institute_tv1));
                tv_middle.setTextColor(ContextCompat.getColor(getActivity(), R.color.filter_reset_tvcl));
                tv_big.setTextColor(ContextCompat.getColor(getActivity(), R.color.filter_reset_tvcl));
                tv_superBig.setTextColor(ContextCompat.getColor(getActivity(), R.color.filter_reset_tvcl));

                dataCallback.smallTextCallback();
                dismiss();
                break;
            case R.id.tv_middle:
                tv_small.setTextColor(ContextCompat.getColor(getActivity(), R.color.filter_reset_tvcl));
                tv_middle.setTextColor(ContextCompat.getColor(getActivity(), R.color.institute_tv1));
                tv_big.setTextColor(ContextCompat.getColor(getActivity(), R.color.filter_reset_tvcl));
                tv_superBig.setTextColor(ContextCompat.getColor(getActivity(), R.color.filter_reset_tvcl));

                dataCallback.middleTextCallback();
                dismiss();
                break;
            case R.id.tv_big:
                tv_small.setTextColor(ContextCompat.getColor(getActivity(), R.color.filter_reset_tvcl));
                tv_middle.setTextColor(ContextCompat.getColor(getActivity(), R.color.filter_reset_tvcl));
                tv_big.setTextColor(ContextCompat.getColor(getActivity(), R.color.institute_tv1));
                tv_superBig.setTextColor(ContextCompat.getColor(getActivity(), R.color.filter_reset_tvcl));

                dataCallback.bigTextCallback();
                dismiss();
                break;
            case R.id.tv_superBig:
                tv_small.setTextColor(ContextCompat.getColor(getActivity(), R.color.filter_reset_tvcl));
                tv_middle.setTextColor(ContextCompat.getColor(getActivity(), R.color.filter_reset_tvcl));
                tv_big.setTextColor(ContextCompat.getColor(getActivity(), R.color.filter_reset_tvcl));
                tv_superBig.setTextColor(ContextCompat.getColor(getActivity(), R.color.institute_tv1));
                
                dataCallback.superBigTextCallback();
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
