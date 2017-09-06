package com.official.read.base;

/**
 * com.official.read.base
 * Created by ZP on 2017/7/12.
 * description:
 * version: 1.0
 */

public interface BasePresenter<V extends BaseView> {

    /**
     * presenter和对应的view绑定
     * @param mvpView  目标view
     */
    void attachView(V mvpView);

    /**
     * presenter与view解绑
     */
    void detachView();

}
