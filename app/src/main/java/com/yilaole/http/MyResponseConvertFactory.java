package com.yilaole.http;

import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 自定义返回值处理
 * Created by sjy on 2017/5/16.
 */

public class MyResponseConvertFactory extends Converter.Factory {

    public static MyResponseConvertFactory create() {
        return create(new Gson());
    }


    public static MyResponseConvertFactory create(Gson gson) {
        return new MyResponseConvertFactory(gson);
    }

    private final Gson gson;

    private MyResponseConvertFactory(Gson gson) {
        if (gson == null)
            throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        //返回我们自定义的Gson响应体变换器
        return new GsonResponseBodyConverter<>(gson, type);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        //返回我们自定义的Gson响应体变换器
        return new GsonResponseBodyConverter<>(gson, type);
    }
}
