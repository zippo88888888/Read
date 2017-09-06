package com.official.read.model;

import com.official.read.content.bean.JusticeBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * com.official.read.model
 * Created by ZP on 2017/8/2.
 * description:
 * version: 1.0
 */

public interface JusticeModel {

    List<JusticeBean> getJusticeDistrictData();

    List<JusticeBean> getJusticeTypeData();

    List<JusticeBean> getJusticePriceData();

    List<JusticeBean> getJusticeAreaData();

    List<JusticeBean> getJusticeStateData();

}
