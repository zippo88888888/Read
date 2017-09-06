package com.official.read.model;

import com.official.read.content.bean.CommonBean;
import com.official.read.content.bean.CollectBean;
import com.official.read.content.bean.DetailBean;
import com.official.read.content.http.HttpApi;
import com.official.read.content.http.HttpClient;

import org.litepal.crud.DataSupport;

import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * com.official.read.model
 * Created by ZP on 2017/8/3.
 * description:
 * version: 1.0
 */

public class DetailModelImpl implements DetailModel {
    @Override
    public Observable<CommonBean<DetailBean>> getDetailData(LinkedHashMap<String, String> mMap) {
        return HttpClient
                .getInstance(HttpClient.DEFAULT_FACTORY)
                .create(HttpApi.class)
                .getDetailData(mMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public List<CollectBean> findConnectionByFID(String fID) {
        return DataSupport.where("fID=?", fID).find(CollectBean.class);
    }
}
