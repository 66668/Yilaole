package com.yilaole.http;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 要自定义Converter
 */

public class NullOnEmptyConverterFactory extends Converter.Factory {

    //主要用于响应体的处理，Factory中默认实现为返回null，表示不处理
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type,
                                                            Annotation[] annotations, Retrofit retrofit) {
        final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);

        return new Converter<ResponseBody, Object>() {

            @Override
            public Object convert(ResponseBody value) throws IOException {
                if (value.contentLength() == 0)
                    return null;
                return delegate.convert(value);
            }
        };
    }

    /**
     *主要用于请求体的处理，Factory中默认实现为返回null，不能处理返回null
     *作用对象Part、PartMap、Body
     */
//    @Override
//    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
//        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
//        return new GsonRequestBodyConverter<>(gson, adapter);
//    }

}