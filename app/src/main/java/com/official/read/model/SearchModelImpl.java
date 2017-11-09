package com.official.read.model;

import com.official.read.content.bean.SearchHistoryBean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * com.official.read.model
 * Created by ZP on 2017/8/3.
 * description:
 * version: 1.0
 */

public class SearchModelImpl implements SearchModel {

    @Override
    public List<SearchHistoryBean> getSearchHistory() {
        return DataSupport.findAll(SearchHistoryBean.class);
    }


}
