package com.yilaole.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.inter_face.idialog.INewsCommentDialogCallback;
import com.yilaole.utils.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 咨询详情 评论fragment
 */

public class NewsCommentDialog extends DialogFragment implements View.OnClickListener {

    private Dialog mDialog;
    private TextView tv_cancel;
    private TextView tv_send;
    private EditText et_comment_content;
    private InputMethodManager inputMethodManager;
    private INewsCommentDialogCallback dataCallback;

    @Override
    public void onAttach(Context context) {
        if (!(getActivity() instanceof INewsCommentDialogCallback)) {
            throw new IllegalStateException("DialogFragment 所在的 activity 必须实现 INewsCommentDialogCallback 接口");
        }
        super.onAttach(context);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mDialog = new Dialog(getActivity(), R.style.BottomDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //布局
        mDialog.setContentView(R.layout.dialog_comment_news);
        mDialog.setCanceledOnTouchOutside(true);

        Window window = mDialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);

        //初始化控件
        tv_cancel = mDialog.findViewById(R.id.tv_cancel);
        tv_send = mDialog.findViewById(R.id.btn_send);
        et_comment_content = mDialog.findViewById(R.id.et_comment_content);

        fillEditText();
        setSoftKeyboard();

        et_comment_content.addTextChangedListener(mTextWatcher);
        tv_cancel.setOnClickListener(this);
        tv_send.setOnClickListener(this);

        return mDialog;
    }

    private void fillEditText() {
        dataCallback = (INewsCommentDialogCallback) getActivity();
        et_comment_content.setText(dataCallback.getCommentText());
        et_comment_content.setSelection(dataCallback.getCommentText().length());

        if (dataCallback.getCommentText().length() == 0) {
            tv_send.setEnabled(false);
            tv_send.setTextColor(ContextCompat.getColor(getActivity(), R.color.home_text2));
        }
    }

    private void setSoftKeyboard() {
        et_comment_content.setFocusable(true);
        et_comment_content.setFocusableInTouchMode(true);
        et_comment_content.requestFocus();

        // TODO: 17-8-11 为何这里要延时才能弹出软键盘, 延时时长又如何判断？ 目前是手动调试
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
        }, 110);
    }

    private TextWatcher mTextWatcher = new TextWatcher() {

        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (temp.length() > 0) {
                tv_send.setEnabled(true);
                tv_send.setClickable(true);
                tv_send.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrice));
            } else {
                tv_send.setEnabled(false);
                tv_send.setTextColor(ContextCompat.getColor(getActivity(), R.color.home_text2));
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel://取消
                et_comment_content.setText("");
                dismiss();
                break;
            case R.id.btn_send://发送
                String comment = et_comment_content.getText().toString();
                if (comment.isEmpty() || comment == null) {
                    ToastUtil.ToastShort(getActivity(), "评论不能为空！");
                } else {
                    dataCallback.postComment(comment);
                    et_comment_content.setText("");
                    dismiss();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        dataCallback.setCommentText(et_comment_content.getText().toString());
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        dataCallback.setCommentText(et_comment_content.getText().toString());
        super.onCancel(dialog);
    }
}
