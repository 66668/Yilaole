package com.yilaole.presenter;

import android.content.Context;

import com.yilaole.base.app.Constants;
import com.yilaole.bean.home.HomeInstituteItemBean;
import com.yilaole.http.cache.ACache;
import com.yilaole.inter_face.ilistener.OnCollectionListener;
import com.yilaole.inter_face.ilistener.OnHomeInstitutionAreaListener;
import com.yilaole.inter_face.iview.IHomeInstitutionView;
import com.yilaole.modle.institute.CollectionModleImpl;
import com.yilaole.modle.home.HomeInstitutionModleImpl;

import java.util.List;

/**
 * 首页 嵌套fragment的p层实现
 */

public class HomeInstitutionPresenterImpl implements OnHomeInstitutionAreaListener {
    Context context;
    IHomeInstitutionView view;
    HomeInstitutionModleImpl modle;
    CollectionModleImpl collectionModle;
    ACache aCache;

    public HomeInstitutionPresenterImpl(Context context, IHomeInstitutionView view) {
        this.context = context;
        this.view = view;
        modle = new HomeInstitutionModleImpl();
        collectionModle = new CollectionModleImpl();
        aCache = ACache.get(context);
    }

    public void pLoadData(String city,String type) {

        String token = aCache.getAsString(Constants.TOKEN);
        modle.mLoadData(this, type, city, token);
    }

    /**
     * 收藏
     *
     * @param id
     * @param token
     */
    public void pCollect(int id, String token) {
        collectionModle.mCollectPost(id, token, new OnCollectionListener() {
            @Override
            public void onCollectionSuccess(Object obj) {
                view.onCollectionSuccess(obj);
            }

            @Override
            public void onCollectionFailed(int code,String msg, Exception e) {
                view.onCollectionFailed( code,msg, e);
            }
        });
    }

    @Override
    public void onTabDataSuccess(List<HomeInstituteItemBean> List) {
        view.onTabDataSuccess(List);
    }

    @Override
    public void onTabDataFailed(int code,String msg, Exception e) {
        view.onTabDataFailed( code,msg, e);

    }
}
