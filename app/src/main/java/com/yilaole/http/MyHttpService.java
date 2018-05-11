package com.yilaole.http;


import com.yilaole.bean.CommonBean;
import com.yilaole.bean.CommonListBean;
import com.yilaole.bean.home.BannerBean;
import com.yilaole.bean.home.HomeInstituteItemBean;
import com.yilaole.bean.home.HomeNewsBean;
import com.yilaole.bean.home.HomeTabBean;
import com.yilaole.bean.home.SearchBean;
import com.yilaole.bean.home.TextLooperBean;
import com.yilaole.bean.institution.detail.CharacteristicBean;
import com.yilaole.bean.institution.detail.DetailBannerBean;
import com.yilaole.bean.institution.detail.DetailCommentBean;
import com.yilaole.bean.institution.detail.FeesBean;
import com.yilaole.bean.institution.detail.InstituteDetailBean;
import com.yilaole.bean.institution.detail.QualificationBean;
import com.yilaole.bean.institution.detail.ServiceNotesBean;
import com.yilaole.bean.institution.detail.TravalLineBean;
import com.yilaole.bean.institution.filter.InstituteItemBean;
import com.yilaole.bean.institution.filter.InstitutionFilterBaseBean;
import com.yilaole.bean.institution.filter.InstitutionListAreaBean;
import com.yilaole.bean.mine.AppointVisitDetailBean;
import com.yilaole.bean.mine.AppointVisitItemBean;
import com.yilaole.bean.mine.DoorAssessDetailBean;
import com.yilaole.bean.mine.DoorAssessItemBean;
import com.yilaole.bean.mine.LoginBean;
import com.yilaole.bean.mine.MessageItemBean;
import com.yilaole.bean.mine.MineCollectInstituteBean;
import com.yilaole.bean.mine.MineCommentItemBean;
import com.yilaole.bean.mine.PhotoBean;
import com.yilaole.bean.mine.RegCodeBean;
import com.yilaole.bean.mine.WeichatBindResultBean;
import com.yilaole.bean.news.HotNewsBean;
import com.yilaole.bean.news.NewsBannerBean;
import com.yilaole.bean.news.NewsBean;
import com.yilaole.bean.news.NewsCommentBean;
import com.yilaole.bean.news.NewsDetailBean;
import com.yilaole.bean.news.NewsTabBean;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by jingbin on 16/11/21.
 * 网络请求类（一个接口一个方法）
 * get 无参 和有参样式
 * post 纯文本样式（表单），文本+文件 文本+多文件
 * <p>
 * <p>
 * <p>
 * 注:使用Observable<BaseBean<AddnewReturnBean>> AddnewEmployee(@Field("Obj") String bean);形式时出现gson解析bug,没发用泛型，后续解决
 */

public interface MyHttpService {
    class Builder {

        /**
         * @return
         */
        public static MyHttpService getHttpServer() {
            return HttpUtils.getInstance().getServer(MyHttpService.class);
        }
    }


    /**
     ********************************************--首页相关--********************************************************
     */


    /**
     * 轮播图
     *
     * @param city_name 默认全国
     * @return 200 404
     */

    @FormUrlEncoded
    @POST(URLUtils.Home.BANNER)
    Observable<CommonListBean<BannerBean>> getBanner(
            @Field("city_name") String city_name
            , @Field("device") int device);

    /**
     * 搜索轮播
     *
     * @return 200 404
     */
    @FormUrlEncoded
    @POST(URLUtils.Home.SEARCH_BANNER)
    Observable<CommonListBean<SearchBean>> getSearchData(@Field("city_name") String city_name);

    /**
     * 文字轮播
     *
     * @return 200 404
     */
    @FormUrlEncoded
    @POST(URLUtils.Home.TEXT_BANNER)
    Observable<CommonListBean<TextLooperBean>> getTextData(@Field("city_name") String city_name);

    /**
     * 首页资讯
     *
     * @return 200 404
     */
    @FormUrlEncoded
    @POST(URLUtils.Home.HOME_NEWS)
    Observable<CommonListBean<HomeNewsBean>> getHomeNewsData(@Field("city_name") String type);

    /**
     * 首页tab
     *
     * @return 200 404
     */
    @POST(URLUtils.Home.HOME_TAB)
    Observable<CommonListBean<HomeTabBean>> getHomeTabData();


    /**
     * 首页tab 对应的数据
     */
    @FormUrlEncoded
    @POST(URLUtils.Home.HOME_TAB_DATA)
    Observable<CommonListBean<HomeInstituteItemBean>> postTabData(
            @Field("type") String type
            , @Field("city_name") String city_name
            , @Field("token") String token
            , @Field("device") int device);


    /**
     ********************************************--资讯相关--********************************************************
     */
    /**
     * 轮播图
     *
     * @param city_name 默认全国
     * @return 200 404
     */
    @FormUrlEncoded
    @POST(URLUtils.News.NEWS_BANNER)
    Observable<CommonListBean<NewsBannerBean>> getNewsBanner(@Field("city_name") String city_name, @Field("device") int device);

    /**
     * 获取资讯页 tab数据
     * <p>
     * post无参数
     *
     * @return
     */
    @POST(URLUtils.News.NEWS_TAB)
    Observable<CommonListBean<NewsTabBean>> getNewsTabData();

    /**
     * 获取资讯页 list数据
     * <p>
     * post无参数
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.News.NEWS_LIST)
    Observable<CommonListBean<NewsBean>> postNewsListData(
            @Field("pagetotal") int pagetotal
            , @Field("pagesize") int pagesize
            , @Field("type") int type);

    /**
     * 资讯详情
     */
    @FormUrlEncoded
    @POST(URLUtils.News.NEWS_DETAIL)
    Observable<CommonBean<NewsDetailBean>> getNewsDetailData(
            @Field("id") int id
            , @Field("token") String token);

    /**
     * 热门文章
     * 传空值
     */
    @POST(URLUtils.News.NEWS_HOTNEWS)
    Observable<CommonListBean<HotNewsBean>> getHotNewsData();

    /**
     * 评论
     */
    @FormUrlEncoded
    @POST(URLUtils.News.NEWS_COMMENTLIST)
    Observable<CommonListBean<NewsCommentBean>> getCommentListData(@Field("id") int id);

    /**
     * 发布评论
     */
    @FormUrlEncoded
    @POST(URLUtils.News.NEWS_COMMENT_SEND)
    Observable<CommonBean> postNewsComment(@Field("id") int id
            , @Field("token") String token
            , @Field("content") String content
            , @Field("cid") int cid);

    /**
     * 文章点赞
     */
    @FormUrlEncoded
    @POST(URLUtils.News.NEWS_SUPPPORT)
    Observable<CommonBean> postNewsSupport(@Field("id") int id
            , @Field("token") String token);


    /**
     * *******************************************--我的-登录相关--********************************************************
     */

    /**
     * 登录
     *
     * @param phone
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.Mine.LOGIN)
    Observable<CommonBean<LoginBean>> postLogin(
            @Field("phone") String phone
            , @Field("password") String password);

    /**
     * 注册
     *
     * @param phone
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.Mine.REGIST)
    Observable<CommonBean> postRegist(
            @Field("phone") String phone
            , @Field("password") String password
            , @Field("code") String code);

    /**
     * 忘记密码
     *
     * @param phone
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.Mine.FORGET_CHANGE_PS)
    Observable<CommonBean> changeAndForgetPs(
            @Field("phone") String phone
            , @Field("password") String password
            , @Field("code") String code);


    /**
     * 验证码（注册）
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.Mine.REG_CODE)
    Observable<RegCodeBean> getRegCode(
            @Field("phone") String phone);

    /**
     * 验证码（忘记密码的修改）
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.Mine.FORGET_CODE)
    Observable<RegCodeBean> getChangeAndForgetCode(
            @Field("phone") String phone);

    /**
     * *******************************************--我的 个人中心--********************************************************
     *
     */

    /**
     * 修改昵称）
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.Mine.CHANGENAME_PHOTO)
    Observable<CommonBean> changeNamePost(
            @Field("token") String token,
            @Field("name") String name);

    /**
     * 头像上传/修改头像
     *
     * @return
     */
    @Multipart
    @POST(URLUtils.Mine.CHANGENAME_PHOTO)
    Observable<CommonBean<PhotoBean>> changePhotoPost(
            @Part List<MultipartBody.Part> list);

    /**
     * 修改密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.Mine.CHANGEPS)
    Observable<CommonBean> changePsPost(
            @Field("token") String phone
            , @Field("old_password") String old_password
            , @Field("password") String password);

    /**
     * 微信授权
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.Mine.WEICHAT_LOG)
    Observable<CommonBean<WeichatBindResultBean>> postWeichatMessage(
            @Field("nickname") String nickname
            , @Field("openid") String openid
            , @Field("headimgurl") String headimgurl
            , @Field("unionid") String unionid
            , @Field("device") int device);


    /**
     * *******************************************--我的 界面--********************************************************
     *
     */


    /**
     * 获取 我的评论list
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.Mine.COMMENT_LIST)
    Observable<CommonListBean<MineCommentItemBean>> getMineCommentListData(
            @Field("token") String token
            , @Field("pagetotal") int pagetotal
            , @Field("pagesize") int pagesize
    );

    /**
     * 获取 我的收藏list
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.Mine.MINE_COLLECT_LIST)
    Observable<CommonListBean<MineCollectInstituteBean>> getMineCollectData(
            @Field("token") String token
            , @Field("pagetotal") int pagetotal
            , @Field("pagesize") int pagesize
    );

    /**
     * 获取 我的消息list
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.Mine.MINE_COLLECT_LIST)
    Observable<CommonListBean<MessageItemBean>> getMineMessageData(
            @Field("token") String token
            , @Field("pagetotal") int pagetotal
            , @Field("pagesize") int pagesize
    );

    /**
     * 获取 预约参观list
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.Mine.APPOINTVIST_LIST)
    Observable<CommonListBean<AppointVisitItemBean>> getAppointListData(
            @Field("token") String token
            , @Field("pagetotal") int pagetotal
            , @Field("pagesize") int pagesize
    );

    /**
     * 获取 预约参观Detail
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.Mine.APPOINTVIST_DETAIL)
    Observable<CommonBean<AppointVisitDetailBean>> getAppoinDetailData(
            @Field("token") String token
            , @Field("id") int id);

    /**
     * 获取 上门评估 list
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.Mine.DOORASSESS_LIST)
    Observable<CommonListBean<DoorAssessItemBean>> getDoorAssessListData(
            @Field("token") String token
            , @Field("pagetotal") int pagetotal
            , @Field("pagesize") int pagesize
    );

    /**
     * 获取 上门评估 detail
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.Mine.DOORASSESS_DETIAL)
    Observable<CommonBean<DoorAssessDetailBean>> getDoorAssessDetailData(
            @Field("token") String token
            , @Field("id") int id
    );


    /**
     ********************************************--机构相关/详情 筛选 相关--********************************************************
     */

    /**
     * 搜索 热门机构接口
     */
    @GET(URLUtils.InstitutionOrFilter.AREA)
    Observable<InstitutionListAreaBean> getHotData();

    /**
     * 获取省市区数据
     */
    @GET(URLUtils.InstitutionOrFilter.AREA)
    Observable<InstitutionListAreaBean> getInstitutionAreaData();


    /**
     * 获取所有筛选数据接口
     */
    @GET(URLUtils.InstitutionOrFilter.FILTER)
    Observable<InstitutionFilterBaseBean> getInstitutionFilterData();

    /**
     * 获取筛选列表数据
     */
    @FormUrlEncoded
    @POST(URLUtils.InstitutionOrFilter.FILTER_LISTDATA)
    Observable<CommonListBean<InstituteItemBean>> getFilterListData(
            @Field("province") int provinceId
            , @Field("city") int cityId
            , @Field("town") int areaId

            , @Field("type") int typeId
            , @Field("property") int propertyId
            , @Field("bed") int bedId

            , @Field("price") int priceId
            , @Field("object") int objectId
            , @Field("feature") String featureIds

            , @Field("order") int order
            , @Field("token") String token
            , @Field("pagetotal") int pagetotal
            , @Field("pagesize") int pagesize
            , @Field("keywords") String keywords
            , @Field("city_name") String city_name
    );

    /**
     * 详情 banner
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.InstitutionDetail.DETAILBANNER)
    Observable<CommonListBean<DetailBannerBean>> getDetailBanner(
            @Field("id") int id,
            @Field("device") int device
    );


    /**
     * 机构详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.InstitutionDetail.DETAIL)
    Observable<CommonBean<InstituteDetailBean>> getDetailData(
            @Field("id") int id,
            @Field("uniqueid") String macId,
            @Field("device") int device
    );

    /**
     * 机构评论 列表获取
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.InstitutionDetail.COMMENT_INSTITUTE_LIST)
    Observable<CommonListBean<DetailCommentBean>> getCommentData(
            @Field("id") int id,
            @Field("pagetotal") int all,
            @Field("pagesize") int size,
            @Field("uniqueid") String macId,
            @Field("device") int device
    );

    /**
     * 机构评论 发表评论 (无图上传)
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.InstitutionDetail.COMMENT_INSTITUTE_CREATE)
    Observable<CommonListBean<DetailCommentBean>> postCommentWithoutPic(
            @Field("id") int id,
            @Field("order_id") int order_id,
            @Field("token") String token,
            @Field("agency_score") int agency_score,
            @Field("service_score") int servoce_score,
            @Field("content") String content,
            @Field("type") int type);

    /**
     * 机构评论 发表评论 (有图上传)
     *
     * @return
     */
    @Multipart
    @POST(URLUtils.InstitutionDetail.COMMENT_INSTITUTE_CREATE)
    Observable<CommonListBean<DetailCommentBean>> postCommentWithPic(
            @Part("id") int id,
            @Part("order_id") int order_id,
            @Part("token") String token,
            @Part("agency_score") int agency_score,
            @Part("service_score") int servoce_score,
            @Part("content") String content,
            @Part("type") int type,
            @Part MultipartBody.Part file
    );


    /**
     * 服务须知
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.InstitutionDetail.SERVICENOTES)
    Observable<CommonBean<ServiceNotesBean>> getServiceNotesData(
            @Field("id") int id,
            @Field("uniqueid") String macid,
            @Field("device") int device);

    /**
     * 资质机构
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.InstitutionDetail.QUALIFICATION)
    Observable<CommonBean<QualificationBean>> getQualificaionData(
            @Field("id") int id,
            @Field("uniqueid") String macid,
            @Field("device") int device);

    /**
     * 收费标准
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.InstitutionDetail.DETAIL_FEES)
    Observable<CommonBean<FeesBean>> getFeesData(
            @Field("id") int id,
            @Field("uniqueid") String macid,
            @Field("device") int device);

    /**
     * 医养特色
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.InstitutionDetail.DETAIL_CHARACTERISTIC)
    Observable<CommonBean<CharacteristicBean>> getCharacteristicData(
            @Field("id") int id,
            @Field("uniqueid") String macid,
            @Field("device") int device);

    /**
     * 乘车线路
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.InstitutionDetail.DETAIL_TRAVALLINE)
    Observable<CommonListBean<TravalLineBean>> getTravalLineData(
            @Field("id") int id,
            @Field("uniqueid") String macid,
            @Field("device") int device);


    /**
     * 预约参观 提交
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.InstitutionDetail.DETAIL_APPOINTVISIT)
    Observable<CommonListBean<String>> postAppointVisitData(
            @Field("id") int id
            , @Field("name") String name
            , @Field("token") String token
            , @Field("contact_name") String contact_name
            , @Field("number") int number

            , @Field("elder_name") String elder_name
            , @Field("elder_age") String elder_age
            , @Field("contact_way") String contact_way
            , @Field("visit_time") String visit_time
            , @Field("code") String code
    );


    /**
     * 上门评估 提交
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.InstitutionDetail.DETAIL_DOORASSESS)
    Observable<CommonListBean<String>> postDoorAssessData(
            @Field("id") int id
            , @Field("name") String name
            , @Field("token") String token
            , @Field("contact_name") String contact_name
            , @Field("contact_way") String contact_way

            , @Field("address") String address
            , @Field("elder_id") int elder_id
            , @Field("visit_time") String visit_time
            , @Field("remark") String remark
    );

    /**
     * 收藏
     *
     * @return 200 404
     */
    @FormUrlEncoded
    @POST(URLUtils.InstitutionDetail.COLLECTION)
    Observable<CommonListBean<String>> collectPost(@Field("id") int id
            , @Field("token") String token);


    /**
     * 取消收藏
     *
     * @return 200 404
     */
    @FormUrlEncoded
    @POST(URLUtils.InstitutionDetail.UNCOLLECTION)
    Observable<CommonBean<String>> unCollectPost(@Field("id") int id
            , @Field("token") String token);


    //    /**
    //     * 添加访客
    //     * <p>
    //     * 文本和图片上传
    //     * post
    //     *
    //     * @return
    //     */
    //    @Multipart
    //    @POST(URLUtils.ADD_VISITOR)
    //    Observable<BaseBean> addVisitor(
    //            @Part("obj") String obj
    //            , @Part MultipartBody.Part file);
}