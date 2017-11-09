package com.official.read.presenter;

import com.official.read.R;
import com.official.read.base.BasePresenterImpl;
import com.official.read.content.Content;
import com.official.read.model.SystemModel;
import com.official.read.model.SystemModelImpl;
import com.official.read.util.L;
import com.official.read.util.SPUtil;
import com.official.read.util.Toaster;
import com.official.read.view.LockView;

/**
 * com.official.read.presenter
 * Created by ZP on 2017/10/11.
 * description:
 * version: 1.0
 */

public class LockPresenterImpl extends BasePresenterImpl<LockView> implements LockPresenter {

    private SystemModel model;

    public LockPresenterImpl() {
        model = new SystemModelImpl();
    }

    @Override
    public void checkLockTxt(String txt, int type) {
        int color = Content.getBaseColorByTheme(model.getTheme());
        if (txt.length() < getMvpView().getLockLength()) {
            if (getMvpView().isOpenLock()) {
                color = R.color.white;
            }
            if (isAttachView()) getMvpView().showMsg("密码输入有误", Toaster.getApplicationContext().getResources().getColor(color));
            return;
        }
        switch (type) {
            case Content.LOCK_STATE_FIRST:
                SPUtil.put(SPUtil.LOCK_STATE, txt);
                if (isAttachView()) getMvpView().setLockSuccess();
                break;
            case Content.LOCK_STATE_CLEAR: // 清除密码
                String lockState = model.getLockState();
                if (lockState.equals(txt)) {
                    if (isAttachView()) getMvpView().clearLockSuccess();
                    SPUtil.put(SPUtil.LOCK_STATE, Content.LUCK_DEFAULT_PWD);
                } else {
                    if (isAttachView()) getMvpView().showMsg("密码输入有误", Toaster.getApplicationContext().getResources().getColor(color));
                }
                break;
            case Content.LOCK_STATE_CHECK:
                String state = model.getLockState();
                L.e(state);
                if (state.equals(txt)) {
                    if (isAttachView()) getMvpView().checkSuccess();
                } else {
                    if (getMvpView().isOpenLock()) {
                        color = R.color.white;
                    }
                    if (isAttachView()) getMvpView().showMsg("密码输入有误", Toaster.getApplicationContext().getResources().getColor(color));
                }
                break;
        }
    }

}
