package com.yilaole.inter_face.ipresenter;

/**
 * 资讯嵌套fragment的p层
 */

public interface INewsPresenter {

    void pLoadListData(int totol, int size, int type);

    void pMoreListData(int totol, int size, int type);

    void pRefreshListData(int totol, int size, int type);


}
