package com.official.read.presenter;

import com.official.read.base.BasePresenterImpl;
import com.official.read.model.ThemeModel;
import com.official.read.model.ThemeModelImpl;
import com.official.read.util.SPUtil;
import com.official.read.view.SetView;

/**
 * com.official.read.presenter
 * Created by ZP on 2017/8/18.
 * description:
 * version: 1.0
 */

public class SetPresenterImpl extends BasePresenterImpl<SetView> implements SetPresenter {

    ThemeModel themeModel;

    public SetPresenterImpl() {
        themeModel = new ThemeModelImpl();
    }

    @Override
    public void getAnimState() {
        boolean animSet = themeModel.getAnimSet();
        getMvpView().setAnimState(animSet);
    }

    @Override
    public void setAnimState(boolean isOpen) {
        SPUtil.put(SPUtil.OPEN_ANIM, isOpen);
    }
}
