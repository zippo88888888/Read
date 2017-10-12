package com.official.read.presenter;

/**
 * com.official.read.presenter
 * Created by ZP on 2017/10/11.
 * description:
 * version: 1.0
 */

public interface LockPresenter {

    /**
     * 检查输入的密码
     * @param txt   输入的密码
     * @param type  当前输入的状态
     */
    void checkLockTxt(String txt, int type);

}
