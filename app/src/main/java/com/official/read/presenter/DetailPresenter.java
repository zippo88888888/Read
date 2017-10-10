package com.official.read.presenter;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.official.read.content.bean.CollectBean;
import com.official.read.content.bean.DetailBean;

import java.util.List;

/**
 * com.official.read.presenter
 * Created by ZP on 2017/8/3.
 * description:
 * version: 1.0
 */

public interface DetailPresenter {

    void getDetailData(String fID);

    void checkTelPermission();

    void checkLocationPermission();

    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);

    void isShowOrHidden(boolean isShow);

    void setLightGridViewData(String data);

    /**
     * 检查值
     * @param deposit   保证金
     * @param source    房屋类型
     * @param priority  优先购买人
     */
    void checkTop3Value(String deposit, String source, String priority);

    /**
     * 检查DetailBean是否为空
     * @param bean 实体
     * @param state 1--收藏；2--咨询；3--地图
     */
    void checkDetailBean(DetailBean bean, int state);

    /**
     * 检查LatLonPoint 是否为空
     */
    void checkLatLonPoint(DetailBean bean, LatLonPoint point);

    /**
     * 检查状态
     */
    void checkState(DetailBean bean);

    /**
     * 检查当前是否已经收藏过
     */
    void checkConnectionByFID(String FID);

    void connection(CollectBean bean);

    void showBottomLayout(boolean isShow);

    void hiddenBottomLayout(boolean isShow);

    void checkSkipAnim(boolean isFirst);

    /**
     * 地理编码查询回调
     */
    void onGeocodeSearched(GeocodeResult result, int rCode);

}
