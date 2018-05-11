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
import android.widget.Button;
import android.widget.TextView;

import com.yilaole.R;
import com.yilaole.inter_face.idialog.IInstituteContactDialogCallback;


/**
 * 机构详情 联系机构
 */

public class InstituteContactDialog extends DialogFragment implements View.OnClickListener {

    private Dialog mDialog;
    private TextView tv_phoneNumber;
    private Button btn_call;
    private IInstituteContactDialogCallback dataCallback;

    @Override
    public void onAttach(Context context) {
        if (!(getActivity() instanceof IInstituteContactDialogCallback)) {
            throw new IllegalStateException("DialogFragment 所在的 activity 必须实现 IInstituteContactDialogCallback 接口");
        }
        super.onAttach(context);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mDialog = new Dialog(getActivity(), R.style.BottomDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //布局
        mDialog.setContentView(R.layout.dialog_institute_contact);
        mDialog.setCanceledOnTouchOutside(true);

        //代码控制布局显示
        Window window = mDialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.CENTER;//居中
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;

        window.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.dialog_contact_style));//?
        window.setAttributes(layoutParams);


        //初始化控件
        tv_phoneNumber = mDialog.findViewById(R.id.tv_phoneNumber);
        btn_call = mDialog.findViewById(R.id.btn_call);
        fillNumber();
        btn_call.setOnClickListener(this);

        return mDialog;
    }

    //通过回调 填充数据
    private void fillNumber() {
        dataCallback = (IInstituteContactDialogCallback) getActivity();
        tv_phoneNumber.setText(dataCallback.getContactNumber());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_call://打电话
                String contact = tv_phoneNumber.getText().toString();
                dataCallback.postNumber(contact);
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
