package com.official.read.presenter;

import android.support.v4.app.Fragment;

import com.official.read.base.BasePresenterImpl;
import com.official.read.content.Content;
import com.official.read.model.MainModel;
import com.official.read.model.MainModelImpl;
import com.official.read.ui.HistoryFragment;
import com.official.read.ui.JusticeFragment;
import com.official.read.ui.RecommendFragment;
import com.official.read.ui.SearchFragment;
import com.official.read.util.AndroidUtil;
import com.official.read.util.L;
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
        RecommendFragment recommendFragment = null;
        JusticeFragment justiceFragment = null;
        HistoryFragment historyFragment = null;
        for (Fragment f : data) {
            if (f instanceof RecommendFragment) {
                recommendFragment  = (RecommendFragment) f;
            } else if (f instanceof JusticeFragment) {
                justiceFragment  = (JusticeFragment) f;
            } else if (f instanceof HistoryFragment) {
                historyFragment  = (HistoryFragment) f;
            }
        }
        if (recommendFragment == null || justiceFragment == null || historyFragment == null) {
            Toaster.makeText("未知错误");
            return;
        }
        if (isAttachView()) getMvpView().initVpDataAndListener(data, recommendFragment, justiceFragment, historyFragment);
    }

    @Override
    public void checkFragment(Fragment fragment) {
        if (fragment != null) {
            SearchFragment searchFragment = (SearchFragment) fragment;
            searchFragment.onBackPressed();
        } else {
            if (isAttachView()) getMvpView().finishActivity();
        }
    }

    @Override
    public void checkEgg() {
        int count = mainModel.getEggData();
        if (count == -1) {
            SPUtil.put(SPUtil.EGG, 10);
        } else {
            if (count == 0) {
                if (isAttachView()) getMvpView().openEgg();
                return;
            }
            count = count - 1;
            if (count == 0) {
                SPUtil.put(SPUtil.EGG, 0);
                if (isAttachView()) getMvpView().openEgg();
            } else {
                SPUtil.put(SPUtil.EGG, count);
                if (count <= 7) {
                    Toaster.makeText("Just need " + count + " times You can open the EASTER-EGG");
                }
            }
        }
    }

    @Override
    public void telActivity(Fragment fragment, Object value) {

    }

}
