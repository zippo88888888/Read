package com.official.read.content.http;


import com.official.read.content.Content;
import com.official.read.util.L;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * com.rx_java.demo.util
 * Created by ZP on 2017/7/11.
 * description:
 * version: 1.0
 */

public class HttpInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
//        HttpUrl httpUrl = request.url().newBuilder()
//                .addQueryParameter("os_api", "22")
//                .addQueryParameter("device_type", "MI")
//                .addQueryParameter("device_platform", "android")
//                .addQueryParameter("ssmix", "a")
//                .addQueryParameter("manifest_version_code", "232")
//                .addQueryParameter("dpi", "400")
//                .addQueryParameter("abflag", "0")
//                .addQueryParameter("uuid", "651384659521356")
//                .addQueryParameter("version_code", "232")
//                .addQueryParameter("app_name", "tuchong")
//                .addQueryParameter("version_name", "2.3.2")
//                .addQueryParameter("openudid", "65143269dafd1f3a5")
//                .addQueryParameter("resolution", "1280*1000")
//                .addQueryParameter("os_version", ".8.1")
//                .addQueryParameter("ac", "wifi")
//                .addQueryParameter("aid", "0")
//                .build();
//        Request newRequest = request.newBuilder().url(httpUrl).build();
//        Response proceed = chain.proceed(newRequest);
        if (!Content.IS_OFFICIAL) {
//            Request request1 = proceed.request();
            Headers headers = request.headers();
            L.e("headers--->>>" + headers.toString());
            L.e("url--->>>" + request.url().toString());
            L.e("tag--->>>" + request.tag().toString());
        }
//        return proceed;
        return chain.proceed(request);
    }

}
