package com.official.read.presenter;

/**
 * com.official.read.presenter
 * Created by ZP on 2017/8/3.
 * description:
 * version: 1.0
 */

public interface SearchPresenter {

    void getSearchHistory();

    /**
     * 添加至本地数据库并查询数据
     * @param history   值
     * @param isAdd     是否需要添加到数据库
     */
    void submitSearch(String history, boolean isAdd);

    void clearHistory();

    String getTheme();

    /**
     * 设置状态栏
     */
    void setStatusBarState(int state);
}
