package com.official.read.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

/**
 * com.official.read.adapter
 * Created by ZP on 2017/7/13.
 * description:
 * version: 1.0
 */

public class HomeFragmentAdapter extends FragmentPagerAdapter {

    private String[] titles;
    private List<Fragment> fragments;

    public HomeFragmentAdapter(FragmentManager manager, String[] titles, List<Fragment> views) {
        super(manager);
        this.titles = titles;
        this.fragments = views;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
