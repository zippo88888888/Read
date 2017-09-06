package com.official.read.ui;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.ArrayMap;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.official.read.R;
import com.official.read.adapter.HistoryAdapter;
import com.official.read.adapter.RecommendAdapter;
import com.official.read.base.BaseFragment;
import com.official.read.content.Content;
import com.official.read.content.ReadException;
import com.official.read.content.bean.JusticeBean;
import com.official.read.content.bean.RecommendBean;
import com.official.read.dialog.JusticeDialog;
import com.official.read.presenter.HistoryPresenterImpl;
import com.official.read.util.L;
import com.official.read.util.RecyclerUtil;
import com.official.read.util.Toaster;
import com.official.read.util.anim.EasyTransition;
import com.official.read.util.anim.EasyTransitionOptions;
import com.official.read.view.HistoryView;

import java.util.ArrayList;
import java.util.List;

/**
 * com.official.read.ui
 * Created by ZP on 2017/7/13.
 * description:
 * version: 1.0
 */

public class HistoryFragment extends BaseFragment<HistoryPresenterImpl,HistoryView> implements
        HistoryView, HistoryAdapter.FuckSetColorListener {

    /**
     * 页码，区域，类型，价格，面积
     */
    int page = 1,district = 0,type = 0,price = 0,area = 0;

    SwipeRefreshLayout swipeRefreshLayout;
    LuRecyclerView recyclerView;
    HistoryAdapter historyAdapter;
    List<RecommendBean> list = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.fragment_history;
    }

    @Override
    protected HistoryPresenterImpl initPresenter() {
        return new HistoryPresenterImpl();
    }

    @Override
    protected void initView() {
        $(R.id.history_district).setOnClickListener(radioListener);
        $(R.id.history_type).setOnClickListener(radioListener);
        $(R.id.history_price).setOnClickListener(radioListener);
        $(R.id.history_area).setOnClickListener(radioListener);

        swipeRefreshLayout = $(R.id.swipeRefresh);
        recyclerView = $(R.id.recycler_view);

        historyAdapter = new HistoryAdapter(fragmentActivity, list);
        historyAdapter.setFuckSetColorListener(this);
        LuRecyclerViewAdapter lRecyclerViewAdapter = new LuRecyclerViewAdapter(historyAdapter);
        recyclerView.setAdapter(lRecyclerViewAdapter);

        RecyclerUtil.setSwipeLuRecyclerViewProperty(recyclerView, swipeRefreshLayout);

        RecyclerUtil.setLuRecyclerViewDisplay(fragmentActivity, lRecyclerViewAdapter, recyclerView);

        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setOnLoadMoreListener(this);
        recyclerView.setLoadMoreEnabled(true);
        lRecyclerViewAdapter.setOnItemClickListener(this);
    }

    private void getData() {
        presenter.getHistoryData(district, type, price, area, page);
    }

    @Override
    protected void initData() {
        onRefresh();
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
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        recyclerView.setRefreshing(true);
        page = 1;
        recyclerView.scrollToPosition(0);
        getData();
    }

    @Override
    public void onLoadMore() {
        page ++;
        getData();
    }

    @Override
    public void initHistoryData(List<RecommendBean> list) {
        isFirst = true;
        swipeRefreshLayout.setRefreshing(false);
        recyclerView.refreshComplete(Content.PAGE_COUNT);
        historyAdapter.setDataList(list);
    }

    @Override
    public void loadMoreData(List<RecommendBean> list) {
        historyAdapter.addAll(list);
        recyclerView.refreshComplete(Content.PAGE_COUNT);
    }

    @Override
    public void noLoadMoreData() {
        recyclerView.setNoMore(true);
    }

    @Override
    public void noAnywayData() {
        swipeRefreshLayout.setRefreshing(false);
        historyAdapter.clear();
        Toaster.makeText("没有数据");
    }

    @Override
    public void error(int code, ReadException e) {
        super.error(code, e);
        swipeRefreshLayout.setRefreshing(false);
        recyclerView.setOnNetWorkErrorListener(this);
    }

    @Override
    public void reload() {
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        historyAdapter.notifyDataSetChanged();
    }

    View.OnClickListener radioListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            List<JusticeBean> data = presenter.getJusticeDataByID(id);
            JusticeDialog dialog = JusticeDialog.newInstance(data);
            dialog.setDialogItemClickListener(HistoryFragment.this);
            dialog.show(getChildFragmentManager(), "historyDialog");
        }
    };

    @Override
    public void dialogClick(View view, Object obj) {
        presenter.checkJusticeBean((JusticeBean) obj);
    }

    @Override
    public void onItemClick(View view, int position) {
        RecommendBean item = historyAdapter.getItem(position);
        presenter.checkSkipAnim(item, view);
    }

    @Override
    public void setDistrict(int district, String name) {
        ((RadioButton) $(R.id.history_district)).setText(name);
        this.district = district;
        onRefresh();
    }

    @Override
    public void setType(int type, String name) {
        ((RadioButton) $(R.id.history_type)).setText(name);
        this.type = type;
        onRefresh();
    }

    @Override
    public void setPrice(int price, String name) {
        ((RadioButton) $(R.id.history_price)).setText(name);
        this.price = price;
        onRefresh();
    }

    @Override
    public void setArea(int area, String name) {
        ((RadioButton) $(R.id.history_area)).setText(name);
        this.area = area;
        onRefresh();
    }

    @Override
    public void setColor(TextView cjMoney) {
        presenter.fuckSetColor(cjMoney);
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
}
