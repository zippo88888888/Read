package com.official.read.view;

import com.official.read.base.BaseView;

/**
 * com.official.read.view
 * Created by ZP on 2017/8/18.
 * description:
 * version: 1.0
 */

public interface SetView extends BaseView {

    void setAnimState(boolean state);

    void setLockState(boolean state);

    void jumpToSetLockActivity();

    void jumpToClearLockActivity();

    void showErrorLayout();

    void setErrorState(boolean state);
}
