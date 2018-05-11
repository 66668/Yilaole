package com.yilaole.http;


import retrofit2.Converter;

/**
 * 自定义json解析器
 * 未做
 */

public class JsonResultConvertFactory extends Converter.Factory {
//    private final Gson gson;
//
//    public static JsonResultConvertFactory create() {
//        return create(new Gson());
//    }
//
//    public static JsonResultConvertFactory create(Gson gson) {
//        return new JsonResultConvertFactory(gson);
//    }
//
//    private JsonResultConvertFactory(Gson gson) {
//        if (gson == null)
//            throw new NullPointerException("gson == null");
//        this.gson = gson;
//    }

//    @Override
//    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
//        //返回我们自定义的Gson响应体变换器
//        return new JsonResultConverter<>(gson, type);
//    }
//
//
//    @Override
//    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations
//            , Annotation[] methodAnnotations, Retrofit retrofit) {
//        //返回我们自定义的Gson响应体变换器
//        return new GsonResponseBodyConverter<>(gson, type);
//    }
}
