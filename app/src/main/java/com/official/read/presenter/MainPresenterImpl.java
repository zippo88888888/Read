package com.official.read.presenter;

import android.support.v4.app.Fragment;

import com.official.read.base.BasePresenterImpl;
import com.official.read.model.MainModel;
import com.official.read.model.MainModelImpl;
import com.official.read.ui.SearchFragment;
import com.official.read.util.AndroidUtil;
import com.official.read.util.SPUtil;
import com.official.read.util.Toaster;
import com.official.read.view.MainView;

import java.util.List;

/**
 * com.official.read.presenter
 * Created by ZP on 2017/7/12.
 * description:
 * version: 1.0
 */

public class MainPresenterImpl extends BasePresenterImpl<MainView> implements MainPresenter {

    private MainModel mainModel;

    public MainPresenterImpl() {
        mainModel = new MainModelImpl();
    }

    @Override
    public void initVPData() {
        List<Fragment> data = mainModel.initViewPagerData();
        getMvpView().initMainVPData(data);
    }

    @Override
    public void checkFragment(Fragment fragment) {
        if (fragment != null) {
            SearchFragment searchFragment = (SearchFragment) fragment;
            searchFragment.onBackPressed();
        } else {
            getMvpView().finishActivity();
        }
    }

    @Override
    public void checkEgg() {
        int count = mainModel.getEggData();
        if (count == -1) {
            SPUtil.put(SPUtil.EGG, 10);
        } else {
            if (count == 0) {
                getMvpView().openEgg();
                return;
            }
            count = count - 1;
            if (count == 0) {
                SPUtil.put(SPUtil.EGG, 0);
                getMvpView().openEgg();
            } else {
                SPUtil.put(SPUtil.EGG, count);
                if (count <= 7) {
                    Toaster.makeText("Just need " + count + " times You can open the EASTER-EGG");
                }
            }
        }
    }
}
