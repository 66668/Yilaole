package com.yilaole.modle.institute;

import com.yilaole.base.app.Constants;
import com.yilaole.bean.CommonListBean;
import com.yilaole.bean.institution.filter.InstituteItemBean;
import com.yilaole.bean.institution.filter.InstitutionFilterBaseBean;
import com.yilaole.bean.institution.filter.InstitutionListAreaBean;
import com.yilaole.http.MyHttpService;
import com.yilaole.inter_face.ilistener.OnFilterConditionListener;
import com.yilaole.inter_face.ilistener.OnMoreAndRefreshListener;
import com.yilaole.inter_face.ilistener.OnInstitutionAreaListener;
import com.yilaole.inter_face.imodle.IInstitutionModle;
import com.yilaole.utils.DebugResultUtil;
import com.yilaole.utils.MLog;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * 获取机构筛选 m层实现
 */

public class InstitutionModleImpl implements IInstitutionModle {

    /**
     * 省市区
     *
     * @param listener
     */
    @Override
    public void mLoadAreaData(final OnInstitutionAreaListener listener) {

        MyHttpService.Builder.getHttpServer().getInstitutionAreaData()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InstitutionListAreaBean>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "省市区--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "省市区--onError:" + e.toString());
                        listener.onFilterAreraFailed(-1, "获取失败异常", (Exception) e);
                    }

                    @Override
                    public void onNext(InstitutionListAreaBean bean) {
                        MLog.d(TAG, "省市区-onNext");
                        MLog.d(TAG, bean.toString());

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            MLog.d("省市区：" + bean.getResult().size());
                            listener.onFilterAreraSuccess(bean.getResult());
                        } else if (bean.getCode() == 500) {
                            listener.onFilterAreraFailed(bean.getCode(), bean.getMessage(), new Exception("获取省市区失败！"));
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onFilterAreraFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    /**
     * 筛选条件
     *
     * @param listener
     */
    @Override
    public void mLoadFilterData(final OnFilterConditionListener listener) {

        MyHttpService.Builder.getHttpServer().getInstitutionFilterData()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InstitutionFilterBaseBean>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "筛选条件--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "筛选条件--onError:" + e.toString());
                        listener.onFilterConditionFailed(-1, "获取异常", (Exception) e);
                    }

                    @Override
                    public void onNext(InstitutionFilterBaseBean bean) {
                        MLog.d(TAG, "筛选条件-onNext");

                        MLog.d("筛选条件：" + bean.getResult().getType().get(0).getType());

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            listener.onFilterConditionSuccess(bean.getResult());
                            MLog.d("筛选条件：" + bean.getResult().getType().get(0).getType());
                        } else if (bean.getCode() == 500) {
                            listener.onFilterConditionFailed(bean.getCode(), bean.getMessage(), new Exception("获取失败！"));
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onFilterConditionFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }
    /**
     * ===============================================================================================================
     * ======================================================= 如下接口都一样，但listener返回处理不一样 上拉下拉处理========================================================
     * ===============================================================================================================
     */

    /**
     * 列表数据
     *
     * @param map
     * @param listener
     */
    @Override
    public void mLoadListData(Map<String, Object> map, final OnMoreAndRefreshListener listener) {

        MLog.d("传递的参数：", "\nFILTER_PROVINCE=" + map.get(Constants.FILTER_PROVINCE)
                + "\nFILTER_CITY=" + map.get(Constants.FILTER_CITY)
                + "\nFILTER_TOWN=" + map.get(Constants.FILTER_TOWN)
                + "\nFILTER_TYPE=" + map.get(Constants.FILTER_TYPE)
                + "\nFILTER_PROPERTY=" + map.get(Constants.FILTER_PROPERTY)
                + "\nFILTER_BED=" + map.get(Constants.FILTER_BED)
                + "\nFILTER_PIRCE=" + map.get(Constants.FILTER_PIRCE)
                + "\nFILTER_OBJECT=" + map.get(Constants.FILTER_OBJECT)
                + "\nFILTER_FEATURE=" + map.get(Constants.FILTER_FEATURE)
                + "\nFILTER_ORDER=" + map.get(Constants.FILTER_ORDER)
                + "\nTOKEN=" + map.get(Constants.TOKEN)
                + "\nPAGETOTLE=" + map.get(Constants.PAGETOTLE)
                + "\nPAGESIZE=" + map.get(Constants.PAGESIZE)
                + "\nSERCHKEYS=" + map.get(Constants.SEARCH_KEYS)
                + "\ncity=" + map.get(Constants.FILTER_LOCATION_CITY)
        );


        MyHttpService.Builder.getHttpServer().getFilterListData(//11个参数
                (int) (map.get(Constants.FILTER_PROVINCE))
                , (int) (map.get(Constants.FILTER_CITY))
                , (int) (map.get(Constants.FILTER_TOWN))

                , (int) (map.get(Constants.FILTER_TYPE))
                , (int) (map.get(Constants.FILTER_PROPERTY))
                , (int) (map.get(Constants.FILTER_BED))

                , (int) (map.get(Constants.FILTER_PIRCE))
                , (int) (map.get(Constants.FILTER_OBJECT))
                , (String) (map.get(Constants.FILTER_FEATURE))

                , (int) (map.get(Constants.FILTER_ORDER))
                , (String) (map.get(Constants.TOKEN))
                , (int) (map.get(Constants.PAGETOTLE))

                , (int) (map.get(Constants.PAGESIZE))
                , (String) (map.get(Constants.SEARCH_KEYS))
                , (String) (map.get(Constants.FILTER_LOCATION_CITY))
        )
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<InstituteItemBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "筛选列表--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "筛选列表--onError:" + e.toString());
                        listener.onListFailed(-1, "筛选列表异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<InstituteItemBean> bean) {
                        MLog.d(TAG, "筛选列表-onNext");
                        MLog.d(TAG, bean.toString());

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onListSuccess(bean.getResult());
                            MLog.d("筛选列表数据：" + bean.getResult().toString());
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onListFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }


    /**
     * 刷新接口
     *
     * @param map
     * @param listener
     */

    @Override
    public void mRefreshListData(Map<String, Object> map, final OnMoreAndRefreshListener listener) {
        MLog.d("传递的参数：", "\nFILTER_PROVINCE=" + map.get(Constants.FILTER_PROVINCE)
                + "\nFILTER_CITY=" + map.get(Constants.FILTER_CITY)
                + "\nFILTER_TOWN=" + map.get(Constants.FILTER_TOWN)
                + "\nFILTER_TYPE=" + map.get(Constants.FILTER_TYPE)
                + "\nFILTER_PROPERTY=" + map.get(Constants.FILTER_PROPERTY)
                + "\nFILTER_BED=" + map.get(Constants.FILTER_BED)
                + "\nFILTER_PIRCE=" + map.get(Constants.FILTER_PIRCE)
                + "\nFILTER_OBJECT=" + map.get(Constants.FILTER_OBJECT)
                + "\nFILTER_FEATURE=" + map.get(Constants.FILTER_FEATURE)
                + "\nFILTER_ORDER=" + map.get(Constants.FILTER_ORDER)
                + "\nTOKEN=" + map.get(Constants.TOKEN)
                + "\nPAGETOTLE=" + map.get(Constants.PAGETOTLE)
                + "\nPAGESIZE=" + map.get(Constants.PAGESIZE)
                + "\nSERCHKEYS=" + map.get(Constants.SEARCH_KEYS)
                + "\ncity=" + map.get(Constants.FILTER_LOCATION_CITY)
        );


        MyHttpService.Builder.getHttpServer().getFilterListData(//11个参数
                (int) (map.get(Constants.FILTER_PROVINCE))
                , (int) (map.get(Constants.FILTER_CITY))
                , (int) (map.get(Constants.FILTER_TOWN))

                , (int) (map.get(Constants.FILTER_TYPE))
                , (int) (map.get(Constants.FILTER_PROPERTY))
                , (int) (map.get(Constants.FILTER_BED))

                , (int) (map.get(Constants.FILTER_PIRCE))
                , (int) (map.get(Constants.FILTER_OBJECT))
                , (String) (map.get(Constants.FILTER_FEATURE))

                , (int) (map.get(Constants.FILTER_ORDER))
                , (String) (map.get(Constants.TOKEN))
                , (int) (map.get(Constants.PAGETOTLE))

                , (int) (map.get(Constants.PAGESIZE))
                , (String) (map.get(Constants.SEARCH_KEYS))
                , (String) (map.get(Constants.FILTER_LOCATION_CITY))
        )
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<InstituteItemBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "筛选列表--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "筛选列表--onError:" + e.toString());
                        listener.onRefreshFailed(-1, "筛选列表异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<InstituteItemBean> bean) {
                        MLog.d(TAG, "筛选列表-onNext");
                        MLog.d(TAG, bean.toString());

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onRefreshSuccess(bean.getResult());
                            MLog.d("筛选列表数据：" + bean.getResult().toString());
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onRefreshFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });
    }

    /**
     * 加载接口
     *
     * @param map
     * @param listener
     */
    @Override
    public void mMoreListData(Map<String, Object> map, final OnMoreAndRefreshListener listener) {
        MLog.d("传递的参数：", "\nFILTER_PROVINCE=" + map.get(Constants.FILTER_PROVINCE)
                + "\nFILTER_CITY=" + map.get(Constants.FILTER_CITY)
                + "\nFILTER_TOWN=" + map.get(Constants.FILTER_TOWN)
                + "\nFILTER_TYPE=" + map.get(Constants.FILTER_TYPE)
                + "\nFILTER_PROPERTY=" + map.get(Constants.FILTER_PROPERTY)
                + "\nFILTER_BED=" + map.get(Constants.FILTER_BED)
                + "\nFILTER_PIRCE=" + map.get(Constants.FILTER_PIRCE)
                + "\nFILTER_OBJECT=" + map.get(Constants.FILTER_OBJECT)
                + "\nFILTER_FEATURE=" + map.get(Constants.FILTER_FEATURE)
                + "\nFILTER_ORDER=" + map.get(Constants.FILTER_ORDER)
                + "\nTOKEN=" + map.get(Constants.TOKEN)
                + "\nPAGETOTLE=" + map.get(Constants.PAGETOTLE)
                + "\nPAGESIZE=" + map.get(Constants.PAGESIZE)
                + "\nSERCHKEYS=" + map.get(Constants.SEARCH_KEYS)
                + "\ncity=" + map.get(Constants.FILTER_LOCATION_CITY)
        );


        MyHttpService.Builder.getHttpServer().getFilterListData(//11个参数
                (int) (map.get(Constants.FILTER_PROVINCE))
                , (int) (map.get(Constants.FILTER_CITY))
                , (int) (map.get(Constants.FILTER_TOWN))

                , (int) (map.get(Constants.FILTER_TYPE))
                , (int) (map.get(Constants.FILTER_PROPERTY))
                , (int) (map.get(Constants.FILTER_BED))

                , (int) (map.get(Constants.FILTER_PIRCE))
                , (int) (map.get(Constants.FILTER_OBJECT))
                , (String) (map.get(Constants.FILTER_FEATURE))

                , (int) (map.get(Constants.FILTER_ORDER))
                , (String) (map.get(Constants.TOKEN))
                , (int) (map.get(Constants.PAGETOTLE))

                , (int) (map.get(Constants.PAGESIZE))
                , (String) (map.get(Constants.SEARCH_KEYS))
                , (String) (map.get(Constants.FILTER_LOCATION_CITY))
        )
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListBean<InstituteItemBean>>() {
                    @Override
                    public void onCompleted() {
                        MLog.d(TAG, "筛选列表--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLog.e(TAG, "筛选列表--onError:" + e.toString());
                        listener.onMoreFailed(-1, "筛选列表异常", (Exception) e);
                    }

                    @Override
                    public void onNext(CommonListBean<InstituteItemBean> bean) {
                        MLog.d(TAG, "筛选列表-onNext");
                        MLog.d(TAG, bean.toString());

                        //处理返回结果
                        if (bean.getCode() == 200) {
                            //code = 1
                            listener.onMoreSuccess(bean.getResult());
                            MLog.d("筛选列表数据：" + bean.getResult().toString());
                        } else {
                            MLog.e("返回数据错误：", DebugResultUtil.GetReturnException(bean.getCode()));
                            listener.onMoreFailed(bean.getCode(), bean.getMessage(), new Exception(DebugResultUtil.GetReturnException(bean.getCode())));
                        }
                    }

                });

    }
}
