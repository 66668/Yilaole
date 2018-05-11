package com.yilaole.inter_face.ipresenter;

import java.util.Map;

/**
 * Created by sjy on 2017/8/31.
 */

public interface IInstitutionFilterPresenter {

    void pLoadFilterData();

    void pLoadListData(Map<String, Object> map);

    void prefreshListData(Map<String, Object> map);

    void pMoreListData(Map<String, Object> map);

}
