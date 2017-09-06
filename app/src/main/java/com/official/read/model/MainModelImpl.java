package com.official.read.model;

import android.support.v4.app.Fragment;

import com.official.read.ui.JusticeFragment;
import com.official.read.ui.HistoryFragment;
import com.official.read.ui.ShowFragment;
import com.official.read.ui.RecommendFragment;
import com.official.read.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * com.official.read.model
 * Created by ZP on 2017/7/12.
 * description:
 * version: 1.0
 */

public class MainModelImpl implements MainModel {

    @Override
    public List<Fragment> initViewPagerData() {
        List<Fragment> list = new ArrayList<>();
        list.add(new RecommendFragment());
        list.add(new JusticeFragment());
        list.add(new HistoryFragment());
        return list;
    }

    @Override
    public int getEggData() {
        return (int) SPUtil.get(SPUtil.EGG, -1);
    }
}
