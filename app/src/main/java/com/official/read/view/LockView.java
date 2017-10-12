package com.official.read.view;

import com.official.read.base.BaseView;

/**
 * com.official.read.view
 * Created by ZP on 2017/10/11.
 * description:
 * version: 1.0
 */

public interface LockView extends BaseView {

    int getLockLength();

    void setLockSuccess();

    void clearLockSuccess();

    void showMsg(String msg, int color);

    void checkSuccess();

    boolean isOpenLock();
}
