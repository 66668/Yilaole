package com.yilaole.map.location;

import java.util.List;

/**
 * 权限监听
 */

public interface CheckPermissionsListener {
    void onGranted();

    void onDenied(List<String> permissions);
}
