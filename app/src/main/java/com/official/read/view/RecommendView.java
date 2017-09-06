package com.official.read.view;

import android.view.View;

import com.official.read.base.BaseView;
import com.official.read.content.bean.DetailBean;
import com.official.read.content.bean.RecommendBean;

import java.util.List;

/**
 * com.official.read.view
 * Created by ZP on 2017/7/13.
 * description:
 * version: 1.0
 */

public interface RecommendView extends BaseView {

    void initListData(List<RecommendBean> list);

    void initBannerData(List<DetailBean.HouseInfoImage> data);

    void skipUseAnim(RecommendBean bean, View view);

    void skipNotUseAnim(RecommendBean bean);
}
