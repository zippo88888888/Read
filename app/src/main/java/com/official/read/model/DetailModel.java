package com.official.read.model;

import com.official.read.content.bean.CommonBean;
import com.official.read.content.bean.CollectBean;
import com.official.read.content.bean.DetailBean;

import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Observable;

/**
 * com.official.read.model
 * Created by ZP on 2017/8/3.
 * description:
 * version: 1.0
 */

public interface DetailModel {

    Observable<CommonBean<DetailBean>> getDetailData(LinkedHashMap<String, String> mMap);

    List<CollectBean> findConnectionByFID(String fID);

}
