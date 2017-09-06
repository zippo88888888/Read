package com.official.read.ui;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.ArrayMap;
import android.view.View;

import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.official.read.R;
import com.official.read.base.BaseViewHolder;
import com.official.read.adapter.RecommendAdapter;
import com.official.read.base.BaseActivity;
import com.official.read.content.Content;
import com.official.read.content.DisposeManager;
import com.official.read.content.ReadException;
import com.official.read.content.bean.RecommendBean;
import com.official.read.presenter.ListPresenterImpl;
import com.official.read.util.RecyclerUtil;
import com.official.read.util.Toaster;
import com.official.read.util.anim.EasyTransition;
import com.official.read.util.anim.EasyTransitionOptions;
import com.official.read.view.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends BaseActivity<ListPresenterImpl,ListView> implements ListView,
        RecommendAdapter.CheckRecommendStateListener {

    SwipeRefreshLayout swipeRefreshLayout;
    LuRecyclerView recyclerView;
    RecommendAdapter fragmentAdapter;
    List<RecommendBean> list = new ArrayList<>();

    int page = 1;
    String msg;

    @Override
    protected int getContentView() {
        return R.layout.activity_list;
    }

    @Override
    protected ListPresenterImpl initPresenter() {
        return new ListPresenterImpl();
    }

    @Override
    protected void initView() {
        setTitle("搜索结果");
        swipeRefreshLayout = $(R.id.list_swipeRefresh);
        recyclerView = $(R.id.list_RecyclerView);
        fragmentAdapter = new RecommendAdapter(this, list);
        LuRecyclerViewAdapter lRecyclerViewAdapter = new LuRecyclerViewAdapter(fragmentAdapter);
        recyclerView.setAdapter(lRecyclerViewAdapter);
        RecyclerUtil.setSwipeLuRecyclerViewProperty(recyclerView, swipeRefreshLayout);
        RecyclerUtil.setLuRecyclerViewDisplay(this, lRecyclerViewAdapter, recyclerView);
        fragmentAdapter.setCheckRecommendStateListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setOnLoadMoreListener(this);
        lRecyclerViewAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        page = 1;
        msg = getIntent().getStringExtra("history");
        onRefresh();
    }

    @Override
    public void onItemClick(View view, int position) {
        RecommendBean item = fragmentAdapter.getItem(position);
        presenter.checkSkipAnim(item, view);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        recyclerView.setRefreshing(true);
        page = 1;
        recyclerView.scrollToPosition(0);

        presenter.getListData(page, msg);
    }

    @Override
    public void onLoadMore() {
        page ++;
        presenter.getListData(page, msg);
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
        return recyclerView;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DisposeManager.getInstance().cancel(Content.DISPOSABLE_LIST_INIT_DATA);
    }

    @Override
    public void check(BaseViewHolder holder, RecommendBean bean, int listSize, int position) {
        presenter.checkRecommendState(holder, bean, listSize, position);
    }

    @Override
    public void initListData(List<RecommendBean> list) {
        swipeRefreshLayout.setRefreshing(false);
        fragmentAdapter.setDataList(list);

        recyclerView.refreshComplete(Content.PAGE_COUNT);

    }

    @Override
    public void loadMoreData(List<RecommendBean> list) {
        recyclerView.refreshComplete(Content.PAGE_COUNT);
        fragmentAdapter.addAll(list);
    }

    @Override
    public void skipUseAnim(RecommendBean bean, View view) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("fID", bean.id + "");
        jumpActivity(map, DetailActivity.class, view.findViewById(R.id.new_home_list_item_pic));
    }

    @Override
    public void skipNotUseAnim(RecommendBean bean) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("fID", bean.id + "");
        jumpActivity(map, DetailActivity.class);
    }

    @Override
    public void noAnywayData() {
        swipeRefreshLayout.setRefreshing(false);
        Toaster.makeText("未搜索到关于\"" + msg + "\"的数据");
    }

    @Override
    public void noLoadMoreData() {
        recyclerView.setNoMore(true);
    }

    @Override
    public void error(int code, ReadException e) {
        super.error(code, e);
        swipeRefreshLayout.setRefreshing(false);
        recyclerView.setOnNetWorkErrorListener(this);
    }

    @Override
    public void reload() {
        presenter.getListData(page, msg);
    }
}
