package com.official.read.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.ArrayMap;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.official.read.R;
import com.official.read.adapter.CollectAdapter;
import com.official.read.base.BaseActivity;
import com.official.read.dialog.CommonDialog;
import com.official.read.content.Content;
import com.official.read.content.bean.CollectBean;
import com.official.read.presenter.CollectPresenterImpl;
import com.official.read.util.RecyclerUtil;
import com.official.read.util.Toaster;
import com.official.read.util.anim.EasyTransition;
import com.official.read.util.anim.EasyTransitionOptions;
import com.official.read.view.CollectView;

import java.util.ArrayList;
import java.util.List;

public class CollectActivity extends BaseActivity<CollectPresenterImpl, CollectView> implements
        CollectView, CollectAdapter.SwipeListener {

    SwipeRefreshLayout swipeRefreshLayout;
    LuRecyclerView recyclerView;
    CollectAdapter fragmentAdapter;
    List<CollectBean> list = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_collect;
    }

    @Override
    protected CollectPresenterImpl initPresenter() {
        return new CollectPresenterImpl();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.collect_menu, menu);
        return true;
    }

    @Override
    protected void initView() {
        setTitle("我的收藏");
        setToolbarInMenu();
        setOnMenuItemClickListener(this);
        setNavigationIconClickListener(this);

        swipeRefreshLayout = $(R.id.swipeRefresh);
        recyclerView = $(R.id.recycler_view);

        fragmentAdapter = new CollectAdapter(this, list);
        LuRecyclerViewAdapter lRecyclerViewAdapter = new LuRecyclerViewAdapter(fragmentAdapter);
        recyclerView.setAdapter(lRecyclerViewAdapter);

        RecyclerUtil.setSwipeLuRecyclerViewProperty(recyclerView, swipeRefreshLayout);
        RecyclerUtil.setLuRecyclerViewDisplay(this, lRecyclerViewAdapter, recyclerView);

        fragmentAdapter.setSwipeListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLoadMoreEnabled(false);
    }

    @Override
    protected void initData() {
        presenter.getCollectData();
    }

    @Override
    public void onRefresh() {
        presenter.getCollectData();
    }

    @Override
    public void noAnywayData() {
        fragmentAdapter.clear();
        swipeRefreshLayout.setRefreshing(false);
        Toaster.makeText("还未收藏数据");
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    @Override
    protected LuRecyclerView getLuRecyclerView() {
        return null;
    }

    @Override
    public void setCollectData(List<CollectBean> list) {
        swipeRefreshLayout.setRefreshing(false);
        recyclerView.refreshComplete(Content.PAGE_COUNT);
        fragmentAdapter.setDataList(list);
    }

    @Override
    public void itemClick(View view, CollectBean bean, int position) {
        presenter.checkSkipAnim(bean, view);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        new CommonDialog(CommonDialog.DIALOG_BUTTON_ONE, this)
                .showDialog1(new CommonDialog.DialogClickListener(), "向左滑动即可取消收藏哦！", "我知道了");
        return true;
    }

    @Override
    public void onClick(View v) {
        back();
    }

    @Override
    public void delete(CollectBean bean, int position) {
        presenter.deleteCollect(bean, position);
    }

    @Override
    public void deleteSuccess(CollectBean bean,int position) {
        fragmentAdapter.remove(position);
    }

    @Override
    public void skipUseAnim(CollectBean bean, View view) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("fID", bean.fID + "");
        jumpActivity(map, DetailActivity.class, view);
    }

    @Override
    public void skipNotUseAnim(CollectBean bean) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("fID", bean.fID + "");
        jumpActivity(map, DetailActivity.class);
    }

    @Override
    public Context getBaseViewContext() {
        return this;
    }
}
