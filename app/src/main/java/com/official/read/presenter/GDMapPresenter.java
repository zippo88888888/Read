package com.official.read.presenter;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.poisearch.PoiResult;
import com.official.read.util.GDMapUtil;

/**
 * com.official.read.presenter
 * Created by ZP on 2017/9/28.
 * description:
 * version: 1.0
 */

public interface GDMapPresenter {

    String getTheme();

    /**
     * 定位
     * @param aMapLocation  定位Data
     * @param isAutoLocation 是否是自动定位
     */
    void onLocationChanged(AMapLocation aMapLocation, boolean isAutoLocation);

    /**
     * 检查导航数据
     * @param nowPoint  定位位置
     * @param point     目标位置
     */
    void checkNavigateData(LatLonPoint nowPoint, LatLonPoint point);

    void createSelectDialog();

    /**
     * POI检索回调
     */
    void onPoiSearched(PoiResult poiResult, int i);

    /**
     * 检查是否为空
     */
    void checkPoiOverlay(GDMapUtil.MyPoiOverlay poiOverlay);
}
