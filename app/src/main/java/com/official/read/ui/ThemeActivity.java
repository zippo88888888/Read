package com.official.read.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.official.read.R;
import com.official.read.base.BaseActivity;
import com.official.read.content.Content;
import com.official.read.presenter.ThemePresenterImpl;
import com.official.read.view.ThemeView;


public class ThemeActivity extends BaseActivity<ThemePresenterImpl,ThemeView> implements ThemeView {

    @Override
    protected int getContentView() {
        return R.layout.activity_theme;
    }

    @Override
    protected ThemePresenterImpl initPresenter() {
        return new ThemePresenterImpl();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("主题");
        $(R.id.theme_red).setOnClickListener(this);
        $(R.id.theme_pink).setOnClickListener(this);
        $(R.id.theme_violet).setOnClickListener(this);
        $(R.id.theme_blue).setOnClickListener(this);
        $(R.id.theme_green).setOnClickListener(this);
        $(R.id.theme_black).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.theme_red:
                presenter.changeTheme(Content.THEME_RED);
                break;
            case R.id.theme_pink:
                presenter.changeTheme(Content.THEME_PINK);
                break;
            case R.id.theme_violet:
                presenter.changeTheme(Content.THEME_VIOLET);
                break;
            case R.id.theme_blue:
                presenter.changeTheme(Content.THEME_BLUE);
                break;
            case R.id.theme_green:
                presenter.changeTheme(Content.THEME_GREEN);
                break;
            case R.id.theme_black:
                presenter.changeTheme(Content.THEME_BLACK);
                break;
        }
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected SwipeRefreshLayout getSwipeRefreshLayout() {
        return null;
    }

    @Override
    protected LuRecyclerView getLuRecyclerView() {
        return null;
    }
}
