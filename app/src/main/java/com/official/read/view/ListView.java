package com.official.read.view;

import android.view.View;

import com.official.read.base.BaseView;
import com.official.read.content.bean.RecommendBean;

import java.util.List;

/**
 * com.official.read.view
 * Created by ZP on 2017/8/3.
 * description:
 * version: 1.0
 */

public interface ListView extends BaseView {

    void initListData(List<RecommendBean> list);

    void loadMoreData(List<RecommendBean> list);

    void skipUseAnim(RecommendBean bean, View view);

    void skipNotUseAnim(RecommendBean bean);

}
