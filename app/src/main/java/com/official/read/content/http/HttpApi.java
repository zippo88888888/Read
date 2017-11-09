package com.official.read.content.http;

import com.official.read.content.URL;
import com.official.read.content.bean.BaseBean;
import com.official.read.content.bean.CommonBean;
import com.official.read.content.bean.DetailBean;
import com.official.read.content.bean.RecommendBean;

import java.util.LinkedHashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * com.official.read.content
 * Created by ZP on 2017/7/12.
 * description:
 * version: 1.0
 */
public interface HttpApi {

    @FormUrlEncoded
    @POST(URL.RECOMMEND_HOUSE_URL)
    Observable<BaseBean<RecommendBean>> getListDataForAll(@FieldMap LinkedHashMap<String, String> mMap);

    @FormUrlEncoded
    @POST(URL.DETAIL_URL)
    Observable<CommonBean<DetailBean>> getDetailData(@FieldMap LinkedHashMap<String, String> mMap);
}
