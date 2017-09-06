package com.official.read.model;

/**
 * com.official.read.model
 * Created by ZP on 2017/8/9.
 * description:
 * version: 1.0
 */

public interface ThemeModel {

    /**
     * 得到当前主题
     */
    String getTheme();

    /**
     * 得到上次主题
     */
    String getLastTheme();

    /**
     * 是否已经切换过主题
     */
    Boolean getThemeThanTheme();

    int getThemeRed();

    int getThemePink();

    int getThemeViolet();

    int getThemeBlue();

    int getThemeGreen();

    int getThemeBlack();

    boolean getAnimSet();

}
