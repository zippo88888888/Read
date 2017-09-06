package com.official.read.presenter;

import android.view.View;

import com.official.read.base.BaseViewHolder;
import com.official.read.content.bean.RecommendBean;

/**
 * com.official.read.presenter
 * Created by ZP on 2017/8/3.
 * description:
 * version: 1.0
 */

public interface ListPresenter {

    void getListData(int page, String msg);

    void checkRecommendState(BaseViewHolder holder, RecommendBean item, int listSize, int position);

    void checkSkipAnim(RecommendBean bean, View view);
}
