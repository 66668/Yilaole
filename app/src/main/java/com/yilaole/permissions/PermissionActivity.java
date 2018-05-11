package com.yilaole.permissions;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import java.io.Serializable;

/**
 *
 *
 */

public class PermissionActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 64;
    private boolean isRequestChecked;

    private String[] permissions;
    private String key;
    private boolean showTip;
    private TipInfo tipInfo;

    private final String defaultTitle = "帮助";
    private final String defaultContent = "当前应用缺少必要权限,请点击 \"设置\"-\"权限管理\"-修改权限。";
    private final String defaultCancel = "取消";
    private final String defaultSure = "设置";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //如果获取跳转值为空，返回
        if (getIntent() == null || !getIntent().hasExtra("permission")) {
            finish();
            return;
        }
        isRequestChecked = true;
        getIntentValue();//获取跳转值

    }

    //获取跳转值
    private void getIntentValue() {
        permissions = getIntent().getStringArrayExtra("permission");
        key = getIntent().getStringExtra("key");
        showTip = getIntent().getBooleanExtra("showTip", true);
        Serializable serializable = getIntent().getSerializableExtra("tip");
        if (serializable == null) {
            tipInfo = new TipInfo(defaultTitle, defaultContent, defaultCancel, defaultSure);
        } else {
            tipInfo = (TipInfo) serializable;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isRequestChecked) {
            if (PermissionsUtil.hasPermission(this, permissions)) {
                // 全部权限均已获取
                permissionsGranted();
            } else {
                // 请求权限,回调时会触发onResume
                myRequestPermissions(permissions);
                isRequestChecked = false;
            }
        } else {
            isRequestChecked = true;
        }
    }


    // 请求权限兼容低版本
    private void myRequestPermissions(String[] permissions) {
        //调用该方法后，会自动调用onRequestPermissionsResult方法处理权限
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    /**
     * 用户权限处理
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //部分厂商手机系统返回授权成功时，厂商可以拒绝权限，所以要用PermissionChecker二次判断
        if (requestCode == PERMISSION_REQUEST_CODE
                && PermissionsUtil.isGranted(grantResults)
                && PermissionsUtil.hasPermission(this, permissions)) {
            permissionsGranted();

        } else if (showTip) {
            showMissionPermissionDialog();
        } else {
            permissionsDenied();
        }
    }

    // 全部权限均已获取，由接口回调处理
    private void permissionsGranted() {
        PermissionListener listener = PermissionsUtil.removeThisListener(key);
        if (listener != null) {
            listener.permissionGranted(permissions);
        }
        finish();
    }

    // 权限已拒绝，由接口回调处理
    private void permissionsDenied() {
        PermissionListener listener = PermissionsUtil.removeThisListener(key);
        if (listener != null) {
            listener.permissionDenied(permissions);
        }
        finish();
    }

    //显示缺失的权限提示
    private void showMissionPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PermissionActivity.this);
        builder.setTitle(TextUtils.isEmpty(tipInfo.title) ? defaultTitle : tipInfo.title);
        builder.setTitle(TextUtils.isEmpty(tipInfo.content) ? defaultContent : tipInfo.content);
        builder.setNegativeButton(TextUtils.isEmpty(tipInfo.cancel) ? defaultCancel : tipInfo.cancel
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        permissionsDenied();
                    }
                });
        builder.setPositiveButton(TextUtils.isEmpty(tipInfo.ensure) ? defaultSure : tipInfo.ensure
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PermissionsUtil.gotoSetting(PermissionActivity.this);
                    }
                });
        builder.setCancelable(false);
        builder.show();
    }
    protected void onDestroy() {
        PermissionsUtil.removeThisListener(key);
        super.onDestroy();
    }
}
