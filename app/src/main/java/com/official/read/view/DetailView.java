package com.official.read.view;

import com.amap.api.services.core.LatLonPoint;
import com.official.read.base.BaseView;
import com.official.read.content.bean.DetailBean;

import java.util.List;

/**
 * com.official.read.view
 * Created by ZP on 2017/8/3.
 * description:
 * version: 1.0
 */

public interface DetailView extends BaseView {

    void initDetailData(DetailBean bean);

    /**
     * 拨打电话咨询
     */
    void tel();

    /**
     * 展开
     */
    void show();

    /**
     * 隐藏
     */
    void hidden();

    /**
     * 设置亮点数据
     */
    void setLightGridViewData(List<String> data);

    /**
     * 隐藏亮点GridView
     */
    void hiddenLightGridView();

    /**
     * 赋值
     * @param deposit   保证金
     * @param source    房屋类型
     * @param priority  优先购买人
     */
    void setTop3Value(String deposit, String source, String priority);

    /**
     * 设置值
     */
    void setValue(String bidPrice, String bidPriceT, String qpPrice, String qpPriceT, String houseTime);

    /**
     * 设置收藏状态
     */
    void setConnectionState(String state);

    /**
     * 显示底部Layout
     */
    void showBottomLayout();

    /**
     * 隐藏底部Layout
     */
    void hiddenBottomLayout();

    /**
     * 开始底部使用动画，隐藏底部（前个页面跳转至本页面）
     */
    void firstUseAnim();

    /**
     * 开始底部Layout不使用动画（前个页面跳转至本页面）
     */
    void firstNotUseAnim();

    /**
     * 跳转使用动画（前个页面跳转至本页面）
     */
    void useAnim();

    /**
     * 跳转不使用动画（前个页面跳转至本页面）
     */
    void notUseAnim();

    /**
     * 跳转至地图
     */
    void skipMapView();

    /**
     * 地理编码查询失败后再次调用
     */
    void getLatLonPoint();

    /**
     * 设置LatLonPoint
     */
    void setLatLonPoint(LatLonPoint point);

}
