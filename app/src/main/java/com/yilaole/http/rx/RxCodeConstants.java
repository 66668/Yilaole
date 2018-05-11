package com.yilaole.http.rx;

/**
 * Created by jingbin on 2016/12/2.
 */

public class RxCodeConstants {

    // 跳转首页
    public static final int JUMP_HOME = 0;
    // 跳转资讯页
    public static final int JUMP_NEWS = 1;
    //登陆成功 返回我的
    public static final int LOG2LOG = 2;

    //由 修改密码 返回登录
    public static final int FORGET2LOG = 3;

    //由 注册 返回登录
    public static final int REG2LOG = 4;

    //由 个人中心 退出登录 返回登录界面
    public static final int UNLOG2LOG = 5;

    //由 修改昵称 返回 个人界面
    public static final int NAME2CENTER = 6;

    //首页刷新定位后 更新嵌套fragment的数据
    public static final int LOCATION_FRAGMENT = 7;

    //首页刷新定位后 更新筛的数据
    public static final int LOCATION_FILTER = 8;

    //机构点评返回机构详情页
    public static final int COMMENT2DETAIL = 9;

    //头像修改后 我的界面显示
    public static final int PHOTO2MINE = 10;

}
