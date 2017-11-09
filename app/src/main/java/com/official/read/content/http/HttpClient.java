package com.official.read.content.http;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.official.read.content.ReadException;
import com.official.read.content.URL;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * com.official.read.content
 * Created by ZP on 2017/7/12.
 * description:
 * version: 1.0
 */

public class HttpClient {

    /**
     * 默认的解析
     */
    public static final int DEFAULT_FACTORY = 0;

    /**
     * 拦截解析
     */
    @Deprecated
    public static final int INTERCEPT_FACTORY = 1;

    private static HttpClient client;
    private Retrofit retrofit;

    private HttpClient(Integer factoryType) {
        retrofit = createRetrofit(factoryType);
    }

    /**
     * 初始化HttpClient
     * @param factoryType  Factory类型 only {{@link #DEFAULT_FACTORY}，{@link #INTERCEPT_FACTORY}}
     * @return  HttpClient
     */
    public static HttpClient getInstance(Integer factoryType) {
        if (client == null) {
            synchronized (HttpClient.class){
                if (client == null){
                    client = new HttpClient(factoryType);
                }

            }
        }
        return client;
    }

    public <T> T create(Class<T> clazz) {
        checkNotNull(clazz);
        return retrofit.create(clazz);
    }

    private  <T> T checkNotNull(T object) {
        if (object == null) {
            throw new NullPointerException("Service is not null");
        }
        return object;
    }

    private Retrofit createRetrofit(Integer factoryType) {
        //初始化OkHttp
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(9, TimeUnit.SECONDS)    //设置连接超时 9s
                .readTimeout(10, TimeUnit.SECONDS)      //设置读取超时 10s
                .addInterceptor(new HttpInterceptor()); //设置请求拦截器

        Retrofit.Builder rb = new Retrofit.Builder();
        rb.baseUrl(URL.PATH_T);
        rb.client(builder.build());

        if (factoryType == null || factoryType == DEFAULT_FACTORY) {
            rb.addConverterFactory(GsonConverterFactory.create());
        } else if (factoryType == INTERCEPT_FACTORY) {
            rb.addConverterFactory(new MyConverterFactory());
        } else {
            throw new ReadException("ConverterFactory type ERROR");
        }

        rb.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        // 返回 Retrofit 对象
        return rb.build();
    }

}
