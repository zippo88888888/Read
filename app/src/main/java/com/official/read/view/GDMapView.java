package com.official.read.view;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.official.read.base.BaseView;

import java.util.ArrayList;

/**
 * com.official.read.view
 * Created by ZP on 2017/9/28.
 * description:
 * version: 1.0
 */

public interface GDMapView extends BaseView {

    // 自动定位成功
    void change(AMapLocation aMapLocation);

    // 手动定位
    void moveToChange(AMapLocation aMapLocation);

    // 开始导航，默认驾车导航
    void startNavigate(int navigateType);

    void showMarkerByKey(String key);

    /**
     * Poi搜索后，显示Marker
     * @param items     Marker集合
     * @param result    PoiResult，补充
     */
    void showMarker(ArrayList<PoiItem> items, PoiResult result);

    /**
     * 去掉PoiOverlay上所有的Marker。
     */
    void removeFromMap();

    /**
     * 全屏显示
     */
    void fullShow();

    /**
     * 正常显示
     */
    void normalShow();

}
