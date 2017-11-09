package com.official.read.base;

import android.content.Context;

import com.official.read.content.ReadException;

/**
 * com.official.read.base
 * Created by ZP on 2017/7/12.
 * description:
 * version: 1.0
 */

public interface BaseView {

    /**
     * 显示加载对话框
     */
    void showDialog(Boolean cancelable);

    /**
     * 销毁加载对话框
     */
    void dismissDialog();

    /**
     * 获取Context
     */
    Context getBaseViewContext();

    /**
     * 没有更多数据了
     */
    void noLoadMoreData();

    /**
     * 没有任何数据
     */
    void noAnywayData();

    /**
     * 请求数据出错
     */
    void error(int code, ReadException e);

    /**
     * 设置提示信息
     */
    @Deprecated
    void setMessage(String message, ReadException e);

}
