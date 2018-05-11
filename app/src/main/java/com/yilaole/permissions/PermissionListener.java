package com.yilaole.permissions;

import android.support.annotation.NonNull;

/**
 *
 */

public interface PermissionListener {
    /**
     * 权限通过
     */
    void permissionGranted(@NonNull String[] permissions);

    /**
     * 权限拒绝
     */

    void permissionDenied(@NonNull String[] permissions);
}
