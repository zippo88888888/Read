package com.official.read.view;

import android.support.v4.app.Fragment;

import com.official.read.base.BaseView;
import com.official.read.ui.HistoryFragment;
import com.official.read.ui.JusticeFragment;
import com.official.read.ui.RecommendFragment;

import java.util.List;

/**
 * com.official.read.view
 * Created by ZP on 2017/7/12.
 * description:
 * version: 1.0
 */

public interface MainView extends BaseView {

    void initVpDataAndListener(List<Fragment> data, RecommendFragment recommendFragment,
                               JusticeFragment justiceFragment, HistoryFragment historyFragment);

    void finishActivity();

    void openEgg();

}
