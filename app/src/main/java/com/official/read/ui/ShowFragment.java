package com.official.read.ui;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.official.read.R;
import com.official.read.base.BaseFragment;
import com.official.read.base.BasePresenter;
import com.official.read.weight.lu_recycler_view.LuActivity;

/**
 * com.official.read.ui
 * Created by ZP on 2017/7/13.
 * description:
 * version: 1.0
 */

public class ShowFragment extends BaseFragment {

    @Override
    protected int getContentView() {
        return R.layout.fragment_show;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        $(R.id.show_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(fragmentActivity, LuActivity.class));
            }
        });
    }

    @Override
    public void onRefresh() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected SwipeRefreshLayout getSwipeRefreshLayout() {
        return null;
    }

    @Override
    protected LuRecyclerView getLuRecyclerView() {
        return null;
    }


}
