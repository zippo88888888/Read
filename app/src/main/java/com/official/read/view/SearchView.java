package com.official.read.view;

import com.official.read.base.BaseView;
import com.official.read.content.bean.SearchHistoryBean;

import java.util.List;

/**
 * com.official.read.view
 * Created by ZP on 2017/8/3.
 * description:
 * version: 1.0
 */

public interface SearchView extends BaseView {

    void initSearchHistoryData(List<SearchHistoryBean> list);

    void notSearchHistoryData();

    void skipActivity(String history);

    /**
     * 设置状态栏透明
     */
    void setStatusBarTransparent();

    /**
     * 设置状态栏正常状态
     */
    void setStatusBarNormal();
}
