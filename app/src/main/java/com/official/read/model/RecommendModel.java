package com.official.read.model;

import com.official.read.content.bean.BaseBean;
import com.official.read.content.bean.DetailBean;
import com.official.read.content.bean.RecommendBean;

import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Observable;

/**
 * com.official.read.model
 * Created by ZP on 2017/7/13.
 * description:
 * version: 1.0
 */

public interface RecommendModel {


    Observable<BaseBean<RecommendBean>> initRecommendData(LinkedHashMap<String, String> mMap);

    List<DetailBean.HouseInfoImage> getBannerData();

}
