package com.official.read.presenter;

import android.view.View;

import com.official.read.base.BaseViewHolder;
import com.official.read.content.bean.JusticeBean;
import com.official.read.content.bean.RecommendBean;

import java.util.List;

/**
 * com.official.read.presenter
 * Created by ZP on 2017/8/2.
 * description:
 * version: 1.0
 */

public interface JusticePresenter {

    /**
     * 根据GroupButton ID加载数据
     */
    List<JusticeBean> getJusticeDataByID(int groupButtonID);

    /**
     * 得到数据
     * @param city  城市ID
     * @param building_type 类型
     * @param down_payment  价格
     * @param area  面积
     * @param status 状态
     * @param page  页数
     */
    void initJusticeData(int city, int building_type, int down_payment, int area, int status, int page);

    void checkJusticeBean(JusticeBean bean);

    void checkRecommendState(BaseViewHolder holder, RecommendBean item, int listSize, int position);

    void checkSkipAnim(RecommendBean bean, View view);
}
