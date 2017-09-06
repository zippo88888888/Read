package com.official.read.view;

import android.view.View;

import com.official.read.base.BaseView;
import com.official.read.content.bean.RecommendBean;

import java.util.List;

/**
 * com.official.read.view
 * Created by ZP on 2017/8/8.
 * description:
 * version: 1.0
 */

public interface HistoryView extends BaseView {

    void initHistoryData(List<RecommendBean> list);

    void loadMoreData(List<RecommendBean> list);

    void setDistrict(int district, String name);

    void setType(int type, String name);

    void setPrice(int price, String name);

    void setArea(int area, String name);

    void skipUseAnim(RecommendBean bean, View view);

    void skipNotUseAnim(RecommendBean bean);
}
