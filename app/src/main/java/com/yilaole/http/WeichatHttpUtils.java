package com.yilaole.http;

import android.content.Context;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yilaole.base.app.Constants;
import com.yilaole.utils.CheckNetworkUtils;
import com.yilaole.utils.MLog;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求工具类 retrofit+okhttp3
 * <p>
 * 微信请求专用
 * <p>
 */

public class WeichatHttpUtils {
    private static WeichatHttpUtils instance;
    private Gson gson;
    private Context context;
    private Object https;
    Cache cache = null;
    static File httpCacheDirectory;

    private boolean debug;//判断 app版本，由application设置

    private final static String TAG = "HttpUtils";


    public static WeichatHttpUtils getInstance() {
        if (instance == null) {
            synchronized (WeichatHttpUtils.class) {
                if (instance == null) {
                    instance = new WeichatHttpUtils();
                }
            }
        }
        return instance;
    }

    //application中初始化
    public void init(Context context, boolean debug) {
        this.context = context;
        this.debug = debug;
        HttpHead.init(context);

        //设置单独缓存
        httpCacheDirectory = new File(context.getCacheDir(), "efulai_acache_http");
        try {
            cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
        } catch (Exception e) {
            Log.e("OKHttp", "Could not create http cache", e);
        }
    }

    /**
     * 02
     * <p>
     * 微信登陆的baseURL
     *
     * @param clz
     * @param <T>
     * @return
     */

    public <T> T getWeiChatServer(Class<T> clz) {
        if (https == null) {
            synchronized (WeichatHttpUtils.class) {
                https = getRetrofitBuilder(URLUtils.WEICHAT_BASE_URL).build().create(clz);
            }
        }
        if (httpCacheDirectory == null || cache == null) {
            synchronized (WeichatHttpUtils.class) {
                httpCacheDirectory = new File(context.getCacheDir(), Constants.APP_HTTP_ACACHE_FILE);
                try {
                    cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
                } catch (Exception e) {
                    Log.e("OKHttp", "Could not create http cache", e);
                }
            }
        }

        return (T) https;
    }

    /**
     * retrofit配置 方式1
     * 源码方式，gson解析自定义，和该app的解析方式不同，不使用该方法
     *
     * @param apiUrl
     * @return
     */
    private Retrofit.Builder getBuilder(String apiUrl) {

        //retrofit配置 可用链式结构
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(getOkhttp3Client());//设置okhttp（重点），不设置走默认的
        builder.baseUrl(apiUrl);//设置远程地址
        builder.addConverterFactory(new NullOnEmptyConverterFactory()); //添加自定义转换器，处理响应
        builder.addConverterFactory(GsonConverterFactory.create(getGson())); //添加Gson转换器，处理返回
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create()); //Rx
        return builder;
    }

    /**
     * retrofit配置 方式2
     * gson 用第三方处理
     *
     * @param apiUrl
     * @return
     */
    private Retrofit.Builder getRetrofitBuilder(String apiUrl) {

        //retrofit配置 可用链式结构
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(getOkhttp3Client());//设置okhttp3（重点），不设置走默认的
        builder.baseUrl(apiUrl);//设置远程地址

        //官方的json解析，要求格式必须规范才不会异常，但后台的不一定规范，这就要求自定义一个解析器避免这个情况
        builder.addConverterFactory(GsonConverterFactory.create());
        //        builder.addConverterFactory(JsonResultConvertFactory.create());//自定义的json解析器


        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create()); //Rx
        return builder;
    }

    //
    private Gson getGson() {
        Log.d(TAG, "getGson: HttpUtils走gson转换方法");
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.setLenient();
            builder.setFieldNamingStrategy(new AnnotateNaming());
            builder.serializeNulls();
            gson = builder.create();
        }
        return gson;
    }


    private static class AnnotateNaming implements FieldNamingStrategy {
        @Override
        public String translateName(Field field) {
            ParamNames a = field.getAnnotation(ParamNames.class);
            return a != null ? a.value() : FieldNamingPolicy.IDENTITY.translateName(field);
        }
    }

    //okhttp配置
    public OkHttpClient getOkhttp3Client() {
        OkHttpClient client1;
        //        client1 = getUnsafeOkHttpClient();//（https）
        client1 = getUnsafeOkHttpClient2();//（http）
        return client1;
    }

    /**
     * 01
     * <p>
     * okhttp配置
     *
     * @return
     */
    public OkHttpClient getUnsafeOkHttpClient() {
        try {
            //获取目标网站的证书
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            }};

            // Install the all-trusting trust manager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();


            //具体配置，可用链式结构
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.readTimeout(60, TimeUnit.SECONDS);
            okBuilder.connectTimeout(60, TimeUnit.SECONDS);
            okBuilder.writeTimeout(60, TimeUnit.SECONDS);
            okBuilder.addInterceptor(new HttpCacheInterceptor());
            okBuilder.addInterceptor(getInterceptor());//设置拦截器
            okBuilder.sslSocketFactory(sslSocketFactory);
            okBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    //                    Log.d("HttpUtils", "==come");
                    return true;
                }
            });

            return okBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 03
     * okhttp配置 修正（https）
     * 如果服务端没有配合处理cache请求头，会抛出如下504异常：
     * onError:retrofit2.adapter.rxjava.HttpException: HTTP 504 Unsatisfiable Request (only-if-cached)
     * 解决该类异常，可以自定义cache策略，见getUnsafeOkHttpClient2()方法
     * 请见修正后的方法：getUnsafeOkHttpClient2（）
     *
     * @return
     */
    public OkHttpClient getUnsafeOkHttpClient3() {
        try {
            //具体配置，可用链式结构
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.readTimeout(20, TimeUnit.SECONDS);
            okBuilder.connectTimeout(10, TimeUnit.SECONDS);
            okBuilder.writeTimeout(20, TimeUnit.SECONDS);
            okBuilder.addInterceptor(new HttpCacheInterceptor());//公共缓存拦截器
            okBuilder.addInterceptor(getInterceptor());//设置拦截器,打印
            okBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    MLog.d("HttpUtils", "hostname: " + hostname);
                    return true;
                }
            });

            return okBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 02 单独缓存+自定义缓存路径：
     * okBuilder.addInterceptor(new CacheInterceptor());//添加缓存拦截器
     * okBuilder.addNetworkInterceptor(new CacheInterceptor());//添加缓存拦截器
     * <p>
     * okhttp配置 修正（https）
     *
     * @return
     */
    public OkHttpClient getUnsafeOkHttpClient2() {
        try {
            //具体配置，可用链式结构
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            //            okBuilder.cache(cache);//自定义缓存路径
            okBuilder.readTimeout(20, TimeUnit.SECONDS);
            okBuilder.connectTimeout(10, TimeUnit.SECONDS);
            okBuilder.writeTimeout(20, TimeUnit.SECONDS);
            okBuilder.addInterceptor(new CacheInterceptor());//添加缓存拦截器
            okBuilder.addNetworkInterceptor(new CacheInterceptor());//添加缓存拦截器
            okBuilder.addInterceptor(getInterceptor());//设置拦截器,打印
            okBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    MLog.d("HttpUtils", "hostname: " + hostname);
                    return true;
                }
            });

            return okBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 缓存拦截器，需要有缓存文件
     * <p>
     * 离线读取本地缓存，在线获取最新数据
     */

    class HttpCacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            //可添加token验证，oAuth验证，可用链式结构
            Request.Builder builder = request.newBuilder();
            builder.addHeader("Accept", "application/json;versions=1");
            if (CheckNetworkUtils.isNetworkConnected(context)) {//在线
                int maxAge = 60;//缓存时间
                builder.addHeader("Cache-Control", "public, max-age=" + maxAge);//设置请求的缓存时间
            } else {//离线
                int maxStale = 60 * 60 * 24 * 28;// tolerate 4-weeks stale
                builder.addHeader("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
            }
            // 可添加token
            //            if (listener != null) {
            //                builder.addHeader("token", listener.getToken());
            //            }
            // 如有需要，添加请求头
            //            builder.addHeader("a", HttpHead.getHeader(request.method()));
            return chain.proceed(builder.build());
        }
    }

    /**
     * 设置缓存拦截器
     */
    class CacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            if (CheckNetworkUtils.isNetworkConnected(context)) {//在线缓存
                Response response = chain.proceed(request);
                int maxAge = 60; // 在线缓存在6s内可读取
                String cacheControl = request.cacheControl().toString();
                MLog.e("sjy-cache", "在线缓存在1分钟内可读取" + cacheControl);
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {//离线缓存
                MLog.e("sjy-cache", "离线时缓存时间设置");
                request = request.newBuilder()
                        .cacheControl(FORCE_CACHE1)//此处设置了7秒---修改了默认系统方法，不能用默认的CacheControl.FORCE_CACHE--是int型最大值，就相当于断网的情况下，一直不清除缓存
                        .build();

                Response response = chain.proceed(request);
                //下面注释的部分设置也没有效果，因为在上面已经设置了
                return response.newBuilder()
                        //                        .removeHeader("Pragma")
                        //                        .removeHeader("Cache-Control")
                        //                        .header("Cache-Control", "public, only-if-cached, max-stale=50")
                        .build();
            }
        }
    }

    //---修改了系统方法--这是设置在多长时间范围内获取缓存里面
    public static final CacheControl FORCE_CACHE1 = new CacheControl.Builder()
            .onlyIfCached()
            .maxStale(60 * 60 * 24 * 28, TimeUnit.SECONDS)//这里是60 * 60 * 24 * 28s，CacheControl.FORCE_CACHE--是int型最大值
            .build();

    /**
     * 设置log打印拦截器
     */
    private HttpLoggingInterceptor getInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //可以通过 setLevel 改变日志级别,共包含四个级别：NONE、BASIC、HEADER、BODY
        /**
         * NONE 不记录
         * BASIC 请求/响应行
         * HEADERS 请求/响应行 + 头
         * BODY 请求/响应行 + 头 + 体
         */
        if (debug) {
            // 打印okhttp
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // 测试
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE); // 打包
        }
        return interceptor;
    }
}
