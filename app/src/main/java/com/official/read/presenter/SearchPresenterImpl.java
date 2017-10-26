package com.official.read.presenter;

import android.os.Build;

import com.official.read.base.BasePresenterImpl;
import com.official.read.content.Content;
import com.official.read.content.bean.SearchHistoryBean;
import com.official.read.model.SearchModel;
import com.official.read.model.SearchModelImpl;
import com.official.read.model.SystemModel;
import com.official.read.model.SystemModelImpl;
import com.official.read.util.L;
import com.official.read.util.Toaster;
import com.official.read.view.SearchView;

import org.litepal.crud.DataSupport;

import java.util.Date;
import java.util.List;

/**
 * com.official.read.presenter
 * Created by ZP on 2017/8/3.
 * description:
 * version: 1.0
 */

public class SearchPresenterImpl extends BasePresenterImpl<SearchView> implements SearchPresenter {

    private SearchModel model;
    private SystemModel themeModel;

    public SearchPresenterImpl() {
        this.model = new SearchModelImpl();
        this.themeModel = new SystemModelImpl();
    }

    @Override
    public void getSearchHistory() {
        List<SearchHistoryBean> list = model.getSearchHistory();
        if (list != null && list.size() > 0) {
            getMvpView().initSearchHistoryData(list);
        } else {
            getMvpView().notSearchHistoryData();
        }
    }

    @Override
    public void submitSearch(String history, boolean isAdd) {
        if (history == null || history.length() <= 0 || "".equals(history)) {
            Toaster.makeText("搜索内容不能为空");
        } else {
            if (isAdd) {
                SearchHistoryBean bean = new SearchHistoryBean();
                bean.name = history;
                bean.time = new Date(System.currentTimeMillis());
                if (bean.save()) {
                    getSearchHistory();
                    L.e("添加数据库成功");
                }
            }
            getMvpView().skipActivity(history);
        }
    }

    @Override
    public void clearHistory() {
        int i = DataSupport.deleteAll(SearchHistoryBean.class);
        if (i > 0) {
            getSearchHistory();
        }
    }

    @Override
    public String getTheme() {
        return themeModel.getTheme();
    }

    @Override
    public void setStatusBarState(int state) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        if (state == Content.STATUE_NORMAL) {
            getMvpView().setStatusBarNormal();
        } else if (state == Content.STATUE_TRANSPARENT) {
            getMvpView().setStatusBarTransparent();
        } else {
            L.e("没有这个选项");
        }
    }
}
