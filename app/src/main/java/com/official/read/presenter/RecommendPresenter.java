package com.official.read.presenter;

import android.view.View;

import com.official.read.base.BaseViewHolder;
import com.official.read.content.bean.RecommendBean;

/**
 * com.official.read.presenter
 * Created by ZP on 2017/7/13.
 * description:
 * version: 1.0
 */

public interface RecommendPresenter {

    void initRecommendData();

    void getBannerData();

    void checkRecommendState(BaseViewHolder holder, RecommendBean bean, int listSize, int position);

    void checkSkipAnim(RecommendBean bean, View view);

}
