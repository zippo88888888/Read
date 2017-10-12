package com.official.read.presenter;

import android.content.Intent;

import com.official.read.base.BasePresenterImpl;
import com.official.read.content.Content;
import com.official.read.dialog.CommonDialog;
import com.official.read.model.SystemModel;
import com.official.read.model.SystemModelImpl;
import com.official.read.util.L;
import com.official.read.util.SPUtil;
import com.official.read.view.SetView;

/**
 * com.official.read.presenter
 * Created by ZP on 2017/8/18.
 * description:
 * version: 1.0
 */

public class SetPresenterImpl extends BasePresenterImpl<SetView> implements SetPresenter {

    private SystemModel systemModel;

    public SetPresenterImpl() {
        systemModel = new SystemModelImpl();
    }

    @Override
    public void getAnimState() {
        boolean animSet = systemModel.getAnimSet();
        getMvpView().setAnimState(animSet);
    }

    @Override
    public void setAnimState(boolean isOpen) {
        SPUtil.put(SPUtil.OPEN_ANIM, isOpen);
    }

    @Override
    public void getLockState() {
        boolean faceState = systemModel.getLockSet();
        getMvpView().setLockState(faceState);
    }

    @Override
    public void setLockState(boolean isFace) {
        String state = systemModel.getLockState();
        L.e(state);
        if (isFace) { // 设置打开，跳转密码锁页面设置
            if (Content.LUCK_DEFAULT_PWD.equals(state)) { // 没有设置密码锁
                CommonDialog dialog = new CommonDialog(CommonDialog.DIALOG_BUTTON_ONE, getMvpView().getBaseViewContext());
                dialog.showDialog1(new CommonDialog.DialogClickListener(){
                    @Override
                    public void click1() {
                        getMvpView().jumpToSetLockActivity();
                    }
                }, "您还未创建密码！", "马上去创建");
            }
        } else { // 设置关闭，跳转密码锁，确认后关闭
            if (!Content.LUCK_DEFAULT_PWD.equals(state)) { // 已经设置密码锁
                getMvpView().jumpToClearLockActivity();
            }
        }

    }

    @Override
    public void checkErrorState(boolean state) {
        if (state) {
            getMvpView().showErrorLayout();
        }
    }

    @Override
    public void getErrorState() {
        boolean errorSet = systemModel.getErrorSet();
        getMvpView().setErrorState(errorSet);
    }

    @Override
    public void setErrorState(boolean isError) {
        SPUtil.put(SPUtil.ERROR_STATE, isError);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Content.SET_LOCK_PWD_REQUEST_CODE && resultCode == Content.SET_LOCK_PWD_RESULT_CODE) {
            SPUtil.put(SPUtil.OPEN_LOCK, true);
            getMvpView().setLockState(true);
        } else if (requestCode == Content.CLEAR_LOCK_PWD_REQUEST_CODE && resultCode == Content.CLEAR_LOCK_PWD_RESULT_CODE) {
            SPUtil.put(SPUtil.OPEN_LOCK, false);
            getMvpView().setLockState(false);
        }
    }

    @Override
    public void onResume() {
        getLockState();
    }
}
