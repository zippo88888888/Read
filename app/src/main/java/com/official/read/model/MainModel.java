package com.official.read.model;

import android.support.v4.app.Fragment;

import java.util.List;

/**
 * com.official.read.model
 * Created by ZP on 2017/7/12.
 * description:
 * version: 1.0
 */

public interface MainModel {

    List<Fragment> initViewPagerData();

    int getEggData();

}
