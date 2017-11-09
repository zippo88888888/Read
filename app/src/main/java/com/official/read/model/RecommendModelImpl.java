package com.official.read.model;

import com.official.read.content.Content;
import com.official.read.content.bean.BaseBean;
import com.official.read.content.bean.DetailBean;
import com.official.read.content.bean.RecommendBean;
import com.official.read.content.http.HttpApi;
import com.official.read.content.http.HttpClient;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * com.official.read.model
 * Created by ZP on 2017/7/13.
 * description:
 * version: 1.0
 */

public class RecommendModelImpl /*extends BaseModel<BaseBean<RecommendBean>, DetailBean.HouseInfoImage>*/ implements RecommendModel {

    /*@Override
    public Observable<BaseBean<RecommendBean>> execute(LinkedHashMap<String, String> map) {
        return HttpClient
                .getInstance(HttpClient.DEFAULT_FACTORY)
                .create(HttpApi.class)
                .getListDataForAll(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public List<DetailBean.HouseInfoImage> get(String... str) {
        List<DetailBean.HouseInfoImage> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            DetailBean.HouseInfoImage bean = new DetailBean.HouseInfoImage();
            if (i == 0) {
                bean.imgs = Content.URL1;
            } else {
                bean.imgs = Content.URL2;
            }
            list.add(bean);
        }
        return list;
    }*/

    @Override
    public Observable<BaseBean<RecommendBean>> initRecommendData(LinkedHashMap<String, String> map) {
        return HttpClient
                .getInstance(HttpClient.DEFAULT_FACTORY)
                .create(HttpApi.class)
                .getListDataForAll(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public List<DetailBean.HouseInfoImage> getBannerData() {
        List<DetailBean.HouseInfoImage> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            DetailBean.HouseInfoImage bean = new DetailBean.HouseInfoImage();
            if (i == 0) {
                bean.imgs = Content.URL1;
            } else {
                bean.imgs = Content.URL2;
            }
            list.add(bean);
        }
        return list;
    }

}
