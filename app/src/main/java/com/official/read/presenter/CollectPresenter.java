package com.official.read.presenter;

import android.view.View;

import com.official.read.content.bean.CollectBean;

/**
 * com.official.read.presenter
 * Created by ZP on 2017/8/7.
 * description:
 * version: 1.0
 */

public interface CollectPresenter {

    void getCollectData();

    void deleteCollect(CollectBean bean, int position);

    void checkSkipAnim(CollectBean bean, View view);
}
