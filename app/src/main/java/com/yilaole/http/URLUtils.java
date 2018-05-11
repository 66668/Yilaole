package com.yilaole.http;

/**
 * URL类
 */

public class URLUtils {
    //URL根目录
    public final static String API_BASE_URL = "https://app.efulai.cn";//阿里云正式
    public final static String WEICHAT_BASE_URL = "https://api.weixin.qq.com";//微信登陆BaseURL

    /**
     * =============================================================================================
     * ============================================通用=================================================
     * =============================================================================================
     *
     */

    /**
     * 微信 获取accessToken
     */
    public static final String ACCESS_TOKEN = "/sns/oauth2/access_token";
    /**
     * 微信 获取个人信息
     */
    public static final String WEICHAT_MESSAGE = "/sns/userinfo";

    /**
     * =============================================================================================
     * ============================================首页相关=================================================
     * =============================================================================================
     */
    public static class Home {


        /**
         * banner
         */
        public static final String BANNER = "/api/v1/banner";

        /**
         * 搜索轮播
         */
        public static final String SEARCH_BANNER = "/api/v1/keywords";

        /**
         * 文字轮播
         */
        public static final String TEXT_BANNER = "/api/v1/agency/enter";
        /**
         * 首页资讯
         */
        public static final String HOME_NEWS = "/api/v1/article/newest";

        /**
         * 首页tab
         */
        public static final String HOME_TAB = "/api/v1/nav";

        /**
         * 首页tab对应的数据
         */
        public static final String HOME_TAB_DATA = "/api/v1/agency";

    }


    /**
     * ==========================================================================================
     * ==========================================养老机构 筛选相关================================================
     * ==========================================================================================
     */
    public static class InstitutionOrFilter {
        /**
         * 省市区单独接口
         */
        public static final String AREA = "/api/v1/get/area";

        /**
         * 机构筛选
         */
        public static final String FILTER = "/api/v1/screen/condition";
        /**
         * 机构列表
         */
        public static final String FILTER_LISTDATA = "/api/v1/agency/data";
    }


    /**
     * ==========================================================================================
     * ==========================================养老机构详情相关================================================
     * ==========================================================================================
     */
    public static class InstitutionDetail {

        /**
         * banner图
         */
        public static final String DETAILBANNER = "/api/v1/agency/banner";

        /**
         * 机构详情
         */
        public static final String DETAIL = "/api/v1/agency/detail";

        /**
         * 评论列表
         */
        public static final String COMMENT_INSTITUTE_LIST = "/api/v1/agency/comment";

        /**
         * 发表评论
         */
        public static final String COMMENT_INSTITUTE_CREATE = "/api/v1/comment/agency";

        /**
         * 服务须知
         */
        public static final String SERVICENOTES = "/api/v1/service/instructions";

        /**
         * 资质机构
         */
        public static final String QUALIFICATION = "/api/v1/agency/qualify";
        /**
         * 收费标准
         */
        public static final String DETAIL_FEES = "/api/v1/price/detail";

        /**
         * 医养特色
         */
        public static final String DETAIL_CHARACTERISTIC = "/api/v1/agency/medical/";

        /**
         * 线路
         */
        public static final String DETAIL_TRAVALLINE = "/api/v1/agency/travel";

        /**
         * 预约参观 提交
         */
        public static final String DETAIL_APPOINTVISIT = "/api/v1/subscribe/visit";

        /**
         * 上门评估 提交
         */
        public static final String DETAIL_DOORASSESS = "/api/v1/subscribe/eval";
        /**
         * 收藏
         */
        public static final String COLLECTION = "/api/v1/collect/agency";
        /**
         * 取消收藏
         */
        public static final String UNCOLLECTION = "/api/v1/cancel/agency";

    }


    /**
     * ==========================================================================================
     * ==========================================资讯相关接口================================================
     * ==========================================================================================
     */
    public static class News {

        /**
         * 资讯Banner
         */
        public static final String NEWS_BANNER = "/api/v1/article/banner";

        /**
         * 资讯tab数据
         */
        public static final String NEWS_TAB = "/api/v1/article/type";

        /**
         * 资讯list数据
         */
        public static final String NEWS_LIST = "/api/v1/articles";

        /**
         * 资讯详情
         */
        public static final String NEWS_DETAIL = "/api/v1/article/detail";

        /**
         * 资讯详情--热门评论
         */
        public static final String NEWS_HOTNEWS = "/api/v1/article/hot";

        /**
         * 资讯详情--评论列表
         */
        public static final String NEWS_COMMENTLIST = "/api/v1/article/comment/list";

        /**
         * 资讯详情--发布评论
         */
        public static final String NEWS_COMMENT_SEND = "/api/v1/comment/article";

        /**
         * 资讯详情--点赞
         */
        public static final String NEWS_SUPPPORT = "/api/v1/article/support";


    }


    /**
     * ==========================================================================================
     * ==========================================个人中心相关================================================
     * ==========================================================================================
     */
    public static class Mine {
        /**
         * 登录
         */
        public static final String LOGIN = "/api/v1/login";

        /**
         * 微信授权
         */
        public static final String WEICHAT_LOG = "/api/v1/wechat/authorization";
        /**
         * 验证码（注册）
         */
        public static final String REG_CODE = "/api/v1/send/sms";
        /**
         * 验证码（忘记密码的修改）
         */
        public static final String FORGET_CODE = "/api/v1/send/forget/sms";

        /**
         * 注册
         */
        public static final String REGIST = "/api/v1/user/register";
        /**
         * =忘记密码
         */
        public static final String FORGET_CHANGE_PS = "/api/v1/save/password";
        /**
         * 修改密码=
         */
        public static final String CHANGEPS = "/api/v1/reset/password";

        /**
         * 修改昵称+修改头像
         */
        public static final String CHANGENAME_PHOTO = "/api/v1/edit/user";

        /**
         * 预约参观 列表
         */
        public static final String APPOINTVIST_LIST = "/api/v1/user/visit";

        /**
         * 预约参观 detail
         */
        public static final String APPOINTVIST_DETAIL = "/api/v1/visit/detail";
        /**
         * 上门评估 列表
         */
        public static final String DOORASSESS_LIST = "/api/v1/user/evaluate";
        /**
         * 上门评估 详情
         */
        public static final String DOORASSESS_DETIAL = "/api/v1/evaluate/detail";
        /**
         * 我的评论 列表
         */
        public static final String COMMENT_LIST = "/api/v1/user/comment";
        /**
         * 我的收藏 列表
         */
        public static final String MINE_COLLECT_LIST = "/api/v1/user/collected/agency";
    }


}
