package com.official.read.model;

import com.official.read.R;
import com.official.read.content.Content;
import com.official.read.util.SPUtil;
import com.official.read.util.Toaster;

/**
 * com.official.read.model
 * Created by ZP on 2017/8/9.
 * description:
 * version: 1.0
 */

public class SystemModelImpl implements SystemModel {

    @Override
    public String getTheme() {
       return (String) SPUtil.get(SPUtil.BASE_COLOR, Content.THEME_RED);
    }

    @Override
    public String getLastTheme() {
        return (String) SPUtil.get(SPUtil.LAST_COLOR, Content.THEME_RED);
    }

    @Override
    public Boolean getThemeThanTheme() {
        return (Boolean) SPUtil.get(SPUtil.THEME_THAN_THEME, false);
    }

    @Override
    public int getThemeRed() {
        return Toaster.getApplicationContext().getResources().getColor(R.color.red);
    }

    @Override
    public int getThemePink() {
        return Toaster.getApplicationContext().getResources().getColor(R.color.pink);
    }

    @Override
    public int getThemeViolet() {
        return Toaster.getApplicationContext().getResources().getColor(R.color.violet);
    }

    @Override
    public int getThemeBlue() {
        return Toaster.getApplicationContext().getResources().getColor(R.color.blue);
    }

    @Override
    public int getThemeGreen() {
        return Toaster.getApplicationContext().getResources().getColor(R.color.green);
    }

    @Override
    public int getThemeBlack() {
        return Toaster.getApplicationContext().getResources().getColor(R.color.black);
    }

    @Override
    public boolean getAnimSet() {
        return (boolean) SPUtil.get(SPUtil.OPEN_ANIM, false);
    }

    @Override
    public boolean getDialogSet() {
        return (boolean) SPUtil.get(SPUtil.DIALOG_STATE, false);
    }

    @Override
    public boolean getLockSet() {
        return (boolean) SPUtil.get(SPUtil.OPEN_LOCK, false);
    }

    @Override
    public String getLockState() {
        return (String) SPUtil.get(SPUtil.LOCK_STATE, Content.LUCK_DEFAULT_PWD);
    }

    @Override
    public boolean getErrorSet() {
        return (boolean) SPUtil.get(SPUtil.ERROR_STATE, false);
    }
}
