package com.official.read.ui;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.ArrayMap;
import android.view.View;

import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.official.read.R;
import com.official.read.adapter.RecommendAdapter;
import com.official.read.adapter.RecommendAdapter.CheckRecommendStateListener;
import com.official.read.base.BaseFragment;
import com.official.read.base.BaseViewHolder;
import com.official.read.content.Content;
import com.official.read.content.ReadException;
import com.official.read.content.bean.DetailBean;
import com.official.read.content.bean.RecommendBean;
import com.official.read.presenter.RecommendPresenterImpl;
import com.official.read.util.RecyclerUtil;
import com.official.read.view.RecommendView;

import java.util.ArrayList;
import java.util.List;

public class RecommendFragment extends BaseFragment<RecommendPresenterImpl,RecommendView> implements
        RecommendView, CheckRecommendStateListener {

    SwipeRefreshLayout swipeRefreshLayout;
    LuRecyclerView recyclerView;
    RecommendAdapter fragmentAdapter;
    LuRecyclerViewAdapter lRecyclerViewAdapter;
    List<RecommendBean> list = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.fragment_item;
    }

    @Override
    protected RecommendPresenterImpl initPresenter() {
        return new RecommendPresenterImpl();
    }

    @Override
    protected void initView() {
        swipeRefreshLayout = $(R.id.swipeRefresh);
        recyclerView = $(R.id.recycler_view);
        fragmentAdapter = new RecommendAdapter(fragmentActivity, list);
        lRecyclerViewAdapter = new LuRecyclerViewAdapter(fragmentAdapter);
        recyclerView.setAdapter(lRecyclerViewAdapter);
        RecyclerUtil.setSwipeLuRecyclerViewProperty(recyclerView, swipeRefreshLayout);
        RecyclerUtil.setLuRecyclerViewDisplay(fragmentActivity, lRecyclerViewAdapter, recyclerView);
        fragmentAdapter.setCheckRecommendStateListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setOnLoadMoreListener(this);
        recyclerView.setLoadMoreEnabled(false);
        lRecyclerViewAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        onRefresh();
        presenter.getBannerData();
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
        presenter.initRecommendData();
    }

    @Override
    public void onItemClick(View view, int position) {
        RecommendBean item = fragmentAdapter.getItem(position);
        presenter.checkSkipAnim(item, view);
    }

    @Override
    public void initListData(List<RecommendBean> list) {
        isFirst = true;
        swipeRefreshLayout.setRefreshing(false);
        recyclerView.refreshComplete(Content.PAGE_COUNT);
        fragmentAdapter.setDataList(list);
    }

    @Override
    public void initBannerData(List<DetailBean.HouseInfoImage> data) {
        // 添加之后太难看了
//        Banner banner = new Banner(fragmentActivity);
//        int h = AndroidUtil.dip2px(fragmentActivity, 180);
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h);
//        banner.setLayoutParams(params);
//        banner.setImageLoader(new GlideImageLoader());
//        banner.setImages(data);
//        banner.isAutoPlay(true);
//        banner.setIndicatorGravity(BannerConfig.CENTER);
//        banner.start();
//        lRecyclerViewAdapter.addHeaderView(banner);
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
    public void error(int code, ReadException e) {
        super.error(code, e);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void check(BaseViewHolder holder, RecommendBean bean, int listSize,int position) {
        presenter.checkRecommendState(holder, bean, listSize, position);
    }
}
