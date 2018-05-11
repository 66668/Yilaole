package com.yilaole.base.app;

/**
 * 全局常量管理
 */

public class Constants {

    public static final String APP_NAME = "efulai";
    public static final String ABSULTEPATH = "/efulai/userphoto";
    public static final String NEW_HTTP = "https://user.efulai.cn/";//app中有些图片没有根路径
    public static final String DETAIL_HTTP = "https://image.efulai.cn/";//app中有些图片没有根路径
    public static final String KEY_SEARCH_KEY = "search_key";//搜索关键词key
    public static final String TOKEN = "token";
    public static final String WEIXIN_ID = "wxb6af9c7c10b7bbf5";//微信 app_id
    public static final String WEIXIN_SECRET = "ea9325b9c0eac1bd92bd6bbb1d11bb38";//微信 APP_SECRET
    public static final String GRANT_TYPE = "authorization_code";//微信 GRANT_TYPE
    public static final int ANDROID = 2;//设备id,android=2,IOS=3

    /**
     * ==============================================================================
     * ======================================缓存文件管理========================================
     * ==============================================================================
     */
    public static final String APP_HTTP_ACACHE_FILE = "efulai_acache_https";//网络路径缓存

    /**
     * ==============================================================================
     * ======================================缓存时间统一管理========================================
     * ==============================================================================
     */
    public static final int TIME_ONE_HOUER = 3600;
    public static final int TIME_TWO_HOUER = 7200;
    public static final int TIME_THREE_HOUER = 10800;
    public static final int TIME_SIX_HOUER = 21600;
    public static final int TIME_TWELVE_HOUER = 43200;
    public static final int TIME_ONE_DAY = 86400;
    public static final int TIME_TWO_DAY = 172800;
    public static final int TIME_THREE_DAY = 259200;


    /**
     * ==============================================================================
     * ======================================首页相关========================================
     * ==============================================================================
     */

    //搜索轮播
    public static final String BANNER_SEARCH = "banner_search";

    //轮播图
    public static final String BANNER_PIC = "banner_pic";

    //文字轮播
    public static final String BANNER_TEXT = "banner_text";

    //首页 资讯
    public static final String HOME_NEWS = "home_news";

    //首页 tab
    public static final String HOME_TABS = "home_tab";

    //首页 tab对应的数据
    public static final String HOME_TABS_DATA = "home_tab_data";


    //首页 当前时间
    public static final String HOME_CURRENT_TIME = "everyday_data";

    //首页 当前时间_嵌套fragment时间
    public static final String CURRENT_TIME_INNERFRG = "everyday_data_innerfrag";

    //首页 机构布局样式的key
    public static class MultiInstituteType {
        //首页 当前时间
        public static final int TYPE1 = 1;//布局样式1
        public static final int TYPE2 = 2;//布局样式1
        public static final int TYPE3 = 3;//布局样式1

    }


    /**
     * ==============================================================================
     * ======================================筛选页相关========================================
     * ==============================================================================
     */

    //首页 当前时间
    public static final String FILTER_CURRENT_TIME = "filterfragment_data";

    /**
     * ==============================================================================
     * ======================================资讯页相关========================================
     * ==============================================================================
     */

    //资讯页 多布局标记 值由后台规定
    public static final int ITEM_NEWS_TYPE1 = 1;
    public static final int ITEM_NEWS_TYPE2 = 2;
    public static final int ITEM_NEWS_TYPE3 = 3;

    //资讯页 当前时间
    public static final String NEWS_CURRENT_TIME = "news_everyday_data";

    //资讯页-fragment 当前时间
    public static final String NEWS_CURRENT_TIME_FRAGMENT = "news_everyday_data_fragment";

    //资讯页 轮播图
    public static final String NEWS_BANNER_PIC = "news_bannerpic";

    //资讯页tab获取
    public static final String NEWS_TAB = "news_tab";

    //资讯页list获取
    public static final String NEWS_LIST = "news_tab_list";

    //资讯 热门文章 list
    public static final String NEWS_HOT_LIST = "news_hot_list";

    //资讯跳转标识
    public static final String NEWS_ID = "NewsID";
    public static final String NEWS_ISHOT = "isHot";

    /**
     * ==============================================================================
     * ======================================资讯页相关========================================
     * ==============================================================================
     */

    //评论弹窗标识
    public static final String NEWS_DETAIL_COMMENT_TAG = "commentDialogFragment";


    //分享弹窗标识
    public static final String NEWS_DETAIL_SHARE_TAG = "shareDialogFragment";

    //字体弹窗标识
    public static final String NEWS_DETAIL_TEXTSIZE_TAG = "textSizeDialogFragment";

    //打电话弹窗标识
    public static final String INSTITUTE_DETAIL_CONTACT = "instituteContactDialogFragment";

    //字体设置标记 小中大 超大
    public static final int SIZE_16 = 16;
    public static final int SIZE_18 = 18;
    public static final int SIZE_20 = 20;
    public static final int SIZE_22 = 22;


    /**
     * ==============================================================================
     * ======================================机构相关========================================
     * ==============================================================================
     */

    //机构筛选页
    public static final String FILTER_ALL = "filter_all";//筛选条件

    //筛选上传参数保
    public static final String FILTER_PROVINCE = "province";//
    public static final String FILTER_CITY = "city";//
    public static final String FILTER_TOWN = "town";//
    public static final String FILTER_TYPE = "type";//机构类型
    public static final String FILTER_PROPERTY = "property";
    public static final String FILTER_BED = "bed";
    public static final String FILTER_PIRCE = "price";
    public static final String FILTER_OBJECT = "object";
    public static final String FILTER_FEATURE = "feature";
    public static final String FILTER_ORDER = "order";
    public static final String FILTER_LOCATION_CITY = "city_location";

    public static final String PAGETOTLE = "pagetotal";
    public static final String PAGESIZE = "pagesize";
    public static final String SEARCH_KEYS = "institute_filter_search";


    /**
     * ==============================================================================
     * ======================================个人相关========================================
     * ==============================================================================
     */
    public static final String USER_NAME = "userName";//保存用户名
    public static final String LOGIN_NAME = "login_name";
    public static final String LOGIN_PS = "login_ps";
    public static final String USER_CODE = "user_code";//验证码 key


    //打电话弹窗标识
    public static final String MINE_COLLECT_DIALOG_TAG = "MineCollectDialog";
}
