package com.yilaole.http;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;


/**
 * 自定义Gson响应体变换器
 * Created by sjy on 2017/5/16.
 */

public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private static final String TAG = "SJY";
    private final Gson gson;
    private final Type type;

    public GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        Response httpResult = gson.fromJson(response, Response.class);
        Log.d(TAG, "convert: GsonResponseBodyConverter--code=" + httpResult.code() +
                "\n--msg=" + httpResult.message() + "\n--result=" + httpResult.body());
        if (httpResult.code() == 200) {
            //200的时候就直接解析，不可能出现解析异常。因为我们实体基类中传入的泛型，就是数据成功时候的格式
            return gson.fromJson(response, type);
        } else {
            //返回异常
            try {
                throw new MyException(httpResult.code(), httpResult.message());
            } catch (MyException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
