package com.official.read.model;

/**
 * com.official.read.model
 * Created by ZP on 2017/8/9.
 * description:App相关的数据
 * version: 1.0
 */

public interface SystemModel {

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

    // 偷懒了

    boolean getAnimSet();

    boolean getDialogSet();

    // 得到密码锁的配置
    boolean getLockSet();

    // 得到设置过密码锁数据
    String getLockState();

    boolean getErrorSet();

}
