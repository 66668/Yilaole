package com.yilaole.permissions;

import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * 自定义权限提示内容设置.
 */

public class TipInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    String title;
    String content;
    String cancel;//取消按钮文本
    String ensure;//确定按钮文本

    public TipInfo(@Nullable String title, @Nullable String content, @Nullable String cancel, @Nullable String ensure) {
        this.title = title;
        this.content = content;
        this.cancel = cancel;
        this.ensure = ensure;
    }
}
