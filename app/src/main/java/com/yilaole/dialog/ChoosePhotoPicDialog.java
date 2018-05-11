package com.yilaole.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yilaole.R;


/**
 * 选择相机 弹窗
 */

public class ChoosePhotoPicDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private ChooseItemDialogCallBack callBack;

    public ChoosePhotoPicDialog(Context context, ChooseItemDialogCallBack callBack) {
        super(context, R.style.LoadingDialog);
        this.context = context;
        this.callBack = callBack;
        init("从相册选择", "拍照");
    }

    public interface ChooseItemDialogCallBack {

        void galleryCallback();

        void cameraCallback();

    }

    private void init(String item1Text, String item2Text) {

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_itemchoose, null);

        TextView tv1 = dialogView.findViewById(R.id.tv_1);
        TextView tv2 = dialogView.findViewById(R.id.tv_2);
        TextView tv_cancel = dialogView.findViewById(R.id.tv_cancel);

        tv1.setText(item1Text);
        tv2.setText(item2Text);


        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);


        setContentView(dialogView);
        setCanceledOnTouchOutside(false);
        getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();

        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        this.getWindow().setAttributes(lp);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dismiss();
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_1:
                callBack.galleryCallback();
                dismiss();
                break;
            case R.id.tv_2:
                callBack.cameraCallback();
                dismiss();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
            default:
                break;
        }
    }

}
