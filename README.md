颐老乐app开发中难点记录：
====
（说明：该项目本有希望成为全国知名养老平台，但是时势本码农改变不了，开源供大家学习。只开发了一期（2个月），到2017.12后，已停止维护开发，所有代码目前已很规范，如果接口不能使用，请根据gif效果自己研究----2018.05.11 sjy,如有疑问，请联系本人邮箱，有问必答）
请先参考gif图，之后会有个人开发进度

(1)首页 获取地图定位 功能：

产品要求android5.0以上

//====================================================个人进度提示=====================================================

***token失效统一刷新的问题（所有带token的接口都要修改）
* 微信分享 wxapi的界面还没做修改

**.上拉下拉没做(资讯)



2.资讯/机构详情 评论-没写回复界面(多布局样式)

4.机构详情 toolbar的title随滑动而改变的代码实现 （未做，不重要，可以不做）

* 首页 文字轮播不稳定（bug，未测试）

* 机构评价 ratingBar自定义处理没做

* 我的评论使用 用户图片，但是登录返回没有图片信息（暂用默认代替）

* 咨询详情 切换文章 无法返回顶部


1.使用metrial design 风格设计的使用，如首页和资讯页滑动效果。
2.高德地图使用，首页定位，养老地图
3.

#首页设计思路：



##1.布局设计：

 
###1-1.首页滑动xml布局使用：

<CoordinatorLayout
    <AppbarLayout
        <其他布局，并添加app:layout_scrollFlags滑动属性
        <Tablayout/>
    </AppbarLayout>
    <ViewPager/>
<coordinatorLayout>
使AppbarLayout的众多内容可以做到滑动查看，当appbarLayout到底部时，toolbar设置不可滑动，就相当于悬浮的效果。
底部ViewPager设置可以兼容N个fragment设置效果。

###1-2.MainActivity有关透明状态栏和沉浸式状态栏的讨论

####1.2.1沉浸式状态栏：
由于mainActivity中包含三个fragment，设置沉浸式效果会偏离公司产品要求，正确的沉浸式状态栏设置，应该是郭神的这种：http://blog.csdn.net/guolin_blog/article/details/51763825
我实现的代码设置步骤是（测试的时候 只写(4)就能实现）：
(1)res/value/style设置成noActionBar:

     <!-- 沉浸式状态栏设置-->
        <style name="TranslucentAppTheme" parent="Theme.AppCompat.Light.NoActionBar">
            <item name="android:windowTranslucentStatus">true</item>
            <item name="android:windowTranslucentNavigation">true</item>
            <item name="android:statusBarColor">@android:color/transparent</item>
        </style>
(2)manifest中设置style:

        <activity
            android:name=".ui.MainActivity"
            android:label="@string/app_name"
            android:theme="@style/TranslucentAppTheme">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>

                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>
 (3)xml布局中设置：
 一定不要在mainActivity中的根布局中设置  android:fitsSystemWindows="true"（在状态栏中生成一个状态栏高度的布局）
 直接在fragment布局中设置该值。

(4)mainActivity代码设置(将郭神的那段代码添加过来就可以)：
        
         @Override
            public void onWindowFocusChanged(boolean hasFocus) {
                super.onWindowFocusChanged(hasFocus);
                if (hasFocus && Build.VERSION.SDK_INT >= 19) {
                    View decorView = getWindow().getDecorView();
                    decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }
            }

        
        
 常见的flag的解释：
(1) SYSTEM_UI_FLAG_VISIBLE                 状态栏和Activity共存，Activity不全屏显示。也就是应用平常的显示画面
(2) SYSTEM_UI_FLAG_FULLSCREEN              Activity全屏显示，且状态栏被覆盖掉   
(3) SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN       Activity全屏显示，但是状态栏不会被覆盖掉，而是正常显示，只是Activity顶端布局会被覆盖住
                                             让View全屏显示，Layout会被拉伸到StatusBar下面，不包含NavigationBar
(4) SYSTEM_UI_FLAG_HIDE_NAVIGATION         隐藏虚拟按键(手机屏底部有些是虚拟键，不是物理键，用次可以隐藏虚拟键)   
(5) SYSTEM_UI_FLAG_LAYOUT_STABLE           状态栏隐藏时内容布局不会变化   
(6) SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION  效果同(3)SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN   
(7) SYSTEM_UI_FLAG_IMMERSIVE               完全沉浸式必写的一个标签,同时也包括(8)
                                              常用的组合(7)+(8)+(2)+**/ (7)+(8)+(4)+**   
(8) SYSTEM_UI_FLAG_IMMERSIVE_STICKY        状态栏出现一段时间后，自动隐藏
(9) SYSTEM_UI_LAYOUT_FLAGS                 效果同(3)SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

使用场景说明：
<1>.(7) SYSTEM_UI_FLAG_IMMERSIVE + (4)SYSTEM_UI_FLAG_HIDE_NAVIGATION + (7)SYSTEM_UI_FLAG_FULLSCREEN/(8)可以加上
 三个一起使用，可以隐藏状态栏与导航栏，同时让你的app可以捕捉到用户的所有触摸屏事件。
<2>

最后的效果gif：

<img width="320" height=“590” src="https://github.com/66668/Yilaole/tree/master/gif/Immersive_mode.gif"></img>


##2.首页城市定位
 
 定位要求的功能是 进入首页首先定位，并根据定位获取该城市的信息显示到首页对应的布局中。
 点击定位，选择要查看的城市，然后获取选择城市的信息
 这块设计，主要参考： https://github.com/zaaach/CityPicker
 ，源码在com.yilaole.map.location包下。

  效果很好，基本上改改UI，用自己的高德key就可以使用。
  同时还需要修改一个地方：mianActivty的基类需要重写onActivityResult方法，
  并将值传递给fragment（这也就是常见的fragment的onActivityResult 接收不到值的一个难点） 写法见源码
  效果很好，基本上改改UI，用自己的高德key就可以使用,
  同时还需要修改一个地方：mianActivty的基类需要重写onActivityResult方法，并将值传递给fragment（这也就是常见的fragment的onActivityResult
  接收不到值的一个难点）
  写法见定位相关源码
  效果图如下：
  
##3.fragment添加懒加载和缓存，首页fragment的AppbarLayout无法滑动情况解析：
首页布局是：

    <CoordinatorLayout
        <AppbarLayout
            <其他布局，并添加app:layout_scrollFlags滑动属性
            <Tablayout/>
        </AppbarLayout>
        <ViewPager/>
    <coordinatorLayout>

viewpager装的是fragment，且fragment中的布局是recyclerView，
当Fragment使用setUserVisibleHint()方法后，切换tab页，导致首页无法上下滑动：
（1）分析原因：
原因之一其实就是一个touch事件消耗占用的问题，我们让指定的View在错误的时机获取并消耗了本不该由它处理的touch事件。这里所出的问题就是AppBarLayout在未完全折叠和未完全展开的情况下，
上下滑动的touch事件被ViewPager提前消耗了
原因之二：Fragment使用setUserVisibleHint()之后，又重新初始化了界面，导致界面重新布局时appbarLayout下的布局没有初始化出来，就没法滑动，所以修改一下代码，避免每次frag可见就加载数据/初始化界面
（2）解决思路：让当前fragment处于可见时，设置AppbarLayout的touch时间为true即可（AppbarLayout.setEnabled(true)）
    <1>在Fragment中重写setUserVisibleHint（）方法:
    
     @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);
            if (getUserVisibleHint()) {//frag可见
                if (appBarLayout != null) {
                           appBarLayout.addOnOffsetChangedListener(this);//this是appbar的监听
                       }
            } else {//frag不可见
               
            }
    
        }
    
   <2>实现AppBarLayout的监听接口
                
         /**
             * AppBarLayout.OnOffsetChangedListener接口实现
             * <p>
             * 用于解决appbarlayout的滑动卡顿
             *
             * @param appBarLayout
             * @param verticalOffset 当AppBarLayout完全展开时 值是0
             */
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                fragment_viewpager.setEnabled(false);
                appBarLayout.setEnabled(true);
            }
    
        
##4首页 机构多布局 baseRecylcerViewHelper的multiItemApdater的详解
(1)已经解决：详见com.yilaole.adapter.home包下HomeInstitutionRecylerAdapter

生命周期顺序很重要：
getItemViewType->onCreateDefViewHolder->onBindViewHolder）。


参考：http://blog.csdn.net/jiecsdn/article/details/64127318


 #机构筛选

 这一块的筛选，类似猎聘app,多条件筛选，参考了俩款开源:
 https://github.com/baiiu/DropDownMenu
 https://github.com/dongjunkun/DropDownMenu
 ,源码在com.yilaole.filter包下
 本app做了适当修改，解决了里头未考虑的bug和三级省市区筛选View,
 筛选包括：
 （1）省市区三级联动筛选（保留了参考的二级筛选代码，可以替换看一下效果）；
 （2）多条件选择筛选（多gridView筛选，保留了DoubleGridView筛选代码,解决了item复用状态混乱的bug）
 （3）单gridView筛选
 （4）简单的listView的String筛选
 筛选的数据来自后台，不过可以自己修改成本地（看代码很容易修改）
 目前本人以单独摘取出来，请参考：https://github.com/66668/DropDownMenuplus
 gif效果图如下：
 
 其中的难点解析：
 （1）多条件筛选中使用 list<T>.contains(tag)时，出现了问题，当时使用tag为String,且重写T对象的equals和hasCode方法，返回都是false，所以将tag提升成T对象比较，返回正确结果;
 （2）recyclerView复用item导致item状态混乱的问题，比如，选中该item后，划出屏幕，再划回来，item状态没有显示或者别的item显示选中（状态混乱）
 解决思路是用数据源的数据保存item状态：http://blog.csdn.net/fesdgasdgasdg/article/details/52069164
 ，虽然对数据不友好，但是考虑的代码少，简单高效
  问题出处：com.yilaole.filter.typeview包下的MultiGridView+MultiGridAdapter的item适配。
  
#机构详情技术点：

##机构详情页：
###1TextView划中线设置：
代码中设置
tv.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
###2TextView的伸缩设置：
###3.metrial Design风格的细节问题：见 机构详情的注释
http://www.jianshu.com/p/06c0ae8d9a96/

###4.toolbar详解：
####1菜单的效果
步骤：（1）布局 
 
            <android.support.v7.widget.Toolbar
                android:id="@+id/id_toolbar"
                android:layout_width="match_parent"
                android:layout_height="?attr/actionBarSize"
                android:background="@android:color/transparent"
                app:layout_collapseMode="pin"
                app:popupTheme="@style/ThemeOverlay.AppCompat.Dark">

            </android.support.v7.widget.Toolbar>
            
（2）act的onCreate中初始化：
 
    private void initToolBar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        toolbar.setNavigationIcon(R.mipmap.detail_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InstitutionDetailActivity.this.finish();
            }
        });

    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_institute_detail, menu);
        return true;     
    }
    
     private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_collect:
                    ToastUtil.ToastShort(InstitutionDetailActivity.this, "收藏");
                    break;
                case R.id.action_share:
                    ToastUtil.ToastShort(InstitutionDetailActivity.this, "分享");
                    break;
            }
            return true;
        }
    };
（3）menu_institute_detail.xml布局在res/menu下：
    
    <menu xmlns:android="http://schemas.android.com/apk/res/android"
          xmlns:app="http://schemas.android.com/apk/res-auto"
          xmlns:tools="http://schemas.android.com/tools"
          tools:context="com.yilaole.ui.InstitutionDetailActivity">
        <!-- 收藏按钮-->
        <item
            android:id="@+id/action_collect"
            android:icon="@mipmap/detail_collect2"
            android:title="@string/app_name"
            app:showAsAction="ifRoom"/>
        <item
            android:id="@+id/action_share"
            android:icon="@mipmap/detail_share2"
            android:title="@string/app_name"
            app:showAsAction="ifRoom"/>
    </menu>
 效果：
 
![image](https://github.com/66668/Yilaole/tree/master/pic/toolbar_style.png) 

####2.toobar颜色随滑动变化（系统是变成主题颜色），此处做监听处理，变化成各种颜色
http://blog.csdn.net/qq402164452/article/details/53760203

####3.toolbar的title居中问题，以及 跟随滑动 title隐藏于显示
toolbar中设置如下参数，可实现 title居中效果，但是字体不能动态隐藏和显示

            app:contentInsetLeft="0dp"
            app:contentInsetStart="0dp"

解决：监听CollapsingToolbarLayout的滑动，根据滑动状态，动态显示title
 代码见InstitutionDetailActivity
 
####4 toolbar中Menu的问题：如何动态修改MenuItem的图标颜色
使用Toolbar.OnMenuItemClickListener方法，然后item.setIcon修改图标即可
注：onOptionsItemSelected方法无反应！
 
###5.咨询详情评论的弹窗效果：
https://github.com/66668/PureComment

###6.appBarLayout+newsScrollView的边界阴影问题：
appbarLayout的阴影，在xml中添加 app：elevation = "0dp"属性去除

###7:机构详情相册 大图预览，并支持缩放：
使用开源：https://github.com/chrisbanes/PhotoView
具体集成在：project/library/gallery_lib下

#资讯页技术点：
##1.viewPager+fragment+FragmentPagerAdapter,切换fragment如何保存fragemnt的状态，即fragment被切换出去后，可能被销毁，如何再次进入页面有数据：
解决方案：
（1）：当tab页较少时，推荐在父控件（activity/fragment）中使用：viewPager .setOffscreenPageLimit(2);
以上述为例，当前界面为1，limit = 2，表示缓存2、3两个界面。如此便避免了界面3被销毁。
(2):通过打印生命周期了解：fragemnt的销毁创建必走 onCreateView和onDestoryView方法，所以在onCreateView中创建布局的视图，onDestoryView中销毁视图
以资讯页的子fragment为例：重用的布局提升为全局变量：


    View rootView;
    private Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DebugUtil.d("NewsFragment", "onCreateView");

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_page, container, false);
            unbinder = ButterKnife.bind(this, rootView);//使用 getActivty()出错
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DebugUtil.d("NewsFragment", "onDestroyView");

        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        //销毁的fragment的View
        if (rootView != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }

    }
    
##2咨询详情之间如何自己跳转到自己的界面：
资讯详情：目前做法是onResume重新加载新数据更新界面

##3解决recylcerView的阴影 滑块问题
（1）方法：在xml的属性中添加

                   <android.support.v7.widget.RecyclerView
                    android:id="@+id/rv_search_one"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:overScrollMode="never"
                    android:scrollbars="none" />
 （2）若还是有阴影，那就有可能是appbarLayout的阴影，在xml中添加 app：elevation = "0dp"属性去除
                   
    
##在线评估技术要点:

##关于recyclerView的优化框架BaseRecyclerViewAdapterHelper：
使用的是github的 BaseRecyclerViewAdapterHelper，具体源码位置在com.yilaole.base.adapterbase包下。
使用到的功能：
（1）单一布局的显示
（2）多布局样式（首页-精品+优惠（完成）/资讯评价/机构评价/个人-机构收藏/咨询页）
（3）footer+header点击监听(在线评估/机构点评-拍照)
（4）item中子控件的点击监听（个人-收藏信息 预约参观 上门评估）
(5)上拉下拉刷新数据-----机构筛选act+资讯fragment(只有上拉使用base的,下拉使用SwipeFreshLayout):
布局使用：
<SwipeFreshLayout><RecyclerView/></SwipeRefreshLayout>
下拉刷新调用的是SwipeFreshLayout的onRefresh()方法，下拉加载使用的是BaseRecyclerViewAdapterHelper的，recylerAdapter.setOnLoadMoreListener(this, recyclerView);然后调用
onLoadMoreRequested（）方法





#网络异常技术要点：
#retrofit2.adapter.rxjava.HttpException: HTTP 504 Unsatisfiable Request (only-if-cached)异常处理：
             https://www.2cto.com/kf/201607/532175.html
            http://www.jianshu.com/p/b1979c25634f
            
#RxJava+Retrofit实现全局过期token自动刷新 步骤：
1.不适用 token 是放在 http 请求的 header 中的请求，这种情况的需要通过使用 okhttp 的拦截器来实现（http://www.jianshu.com/p/8d1ee61bc2d2）
2.

 
#个人界面 技术总结
##登录 注册中EditText密码可见设置：
监听checkBox的OnCheckedChangeListener
ture:Et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
false:Et.setTransformationMethod(PasswordTransformationMethod.getInstance());

#退出登录设计：

#地图功能中的上拉设计：
使用的框架：https://github.com/umano/AndroidSlidingUpPanel
，已经集成到项目目录下：com.yilaole.map.slidingup。


#WebView的使用：
1.咨询详情/机构详情-服务须知 使用h5字段
2.关于我们-隐私政策 使用html

#handler引起的内存泄露问题
在baseFragment中，处理banner图用到，用弱引用处理

#gradle版本统一处理:这样libs依赖的更新也一起处理，操作简单
步骤：根目录的gradle.properties下设置常数，其他地方直接引用（有的不可用,后期补上）

#app中涉及相机相册的操作，使用开源框架TakePhoto
实现功能：在list中点击进入查看大图的界面，支持大图滑动切换，其他操作等功能
使用到的源码部分：1.HackyViewPager
开源地址：https://github.com/crazycodeboy/TakePhoto


#项目多个libs的统一到一个文件下管理：
http://www.jianshu.com/p/d951ea1b8c91


#开源支持：

##框架支持：
1.BRVAH的RecyclerAdapter框架：https://github.com/CymChad/BaseRecyclerViewAdapterHelper

2.项目主页bottom框架：https://github.com/aurelhubert/ahbottomnavigation

3.城市定位，城市选择框架（已做优化，使用高德地图定位）：https://github.com/zaaach/CityPicker

4.百度统计sdk

5.百度地图

6.地图信息弹起框架：https://github.com/umano/AndroidSlidingUpPanel


7.机构相册预览,支持缩放：https://github.com/chrisbanes/PhotoView

8.相机相册：https://github.com/crazycodeboy/TakePhoto
##技术博客支持：
1.郭神的沉浸状态栏：http://blog.csdn.net/guolin_blog/article/details/51763825
2.

小问题：
1.泛型个数有n个怎么实现
2.Rxbus实用总结
3.BaseRecyclerViewAdapterHelper的使用总结
