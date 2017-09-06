package com.official.read.view;

import android.view.View;

import com.official.read.base.BaseView;
import com.official.read.content.bean.RecommendBean;

import java.util.List;

/**
 * com.official.read.view
 * Created by ZP on 2017/8/2.
 * description:
 * version: 1.0
 */

public interface JusticeView extends BaseView {

    void initJusticeData(List<RecommendBean> list);

    void loadMore(List<RecommendBean> list);

    void setDistrict(int district, String name);

    void setType(int type, String name);

    void setPrice(int price, String name);

    void setArea(int area, String name);

    void setState(int state, String name);

    void skipUseAnim(RecommendBean bean, View view);

    void skipNotUseAnim(RecommendBean bean);

}
