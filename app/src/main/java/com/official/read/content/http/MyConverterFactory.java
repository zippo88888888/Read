package com.official.read.content.http;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * com.official.read.content
 * Created by ZP on 2017/7/31.
 * description:
 * version: 1.0
 */
@Deprecated
public class MyConverterFactory extends Converter.Factory {

    /**
     * 拦截请求结果，并以String类型的json格式返回
     */
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (String.class.equals(type)) {
            return new Converter<ResponseBody, String>() {
                @Override
                public String convert(ResponseBody value) throws IOException {
                    String msg = value.string();
                    return msg;
                }
            };
        }
        return super.responseBodyConverter(type, annotations, retrofit);
    }

    /**@Override public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations,
    Annotation[] methodAnnotations, Retrofit retrofit) {

    if (String.class.equals(type)) {
    return new Converter<String, RequestBody>() {
    @Override public RequestBody convert(String value) throws IOException {

    L.e("value--->>>" + value);

    return RequestBody.create(MediaType.parse(MEDIA_TYPE), value);
    }
    };
    }
    return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }*/

}
