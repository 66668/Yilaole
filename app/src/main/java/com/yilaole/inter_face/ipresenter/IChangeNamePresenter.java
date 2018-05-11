package com.yilaole.inter_face.ipresenter;

import java.io.File;

/**
 * 修改密码 p层
 */

public interface IChangeNamePresenter {

    void pChangeNamePost(String token, String name);

    void pChangePhotoPost(String token, File file);


}
