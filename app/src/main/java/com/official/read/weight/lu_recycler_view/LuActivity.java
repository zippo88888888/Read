package com.official.read.weight.lu_recycler_view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.official.read.R;
import com.official.read.base.BaseActivity;
import com.official.read.base.BasePresenter;
import com.official.read.content.Content;
import com.official.read.weight.lu_recycler_view.adapter.LuAdapter;
import com.official.read.weight.lu_recycler_view.entity.Category;
import com.official.read.weight.lu_recycler_view.entity.HotList;
import com.official.read.weight.lu_recycler_view.entity.Product;
import com.official.read.weight.lu_recycler_view.entity.ProductList;
import com.official.read.weight.lu_recycler_view.entity.Si;
import com.official.read.weight.lu_recycler_view.type.ListTypeFactory;
import com.official.read.weight.HeaderView;

import java.util.ArrayList;
import java.util.List;

public class LuActivity extends BaseActivity {

    LuRecyclerView luRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    LuAdapter luAdapter;
    List<Visitable> visitableList;
    LuRecyclerViewAdapter luRecyclerViewAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_lu;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("TEST-MAIN-PAGE");
        luRecyclerView = (LuRecyclerView) $(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) $(R.id.swipeRefresh);
//        luRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        luRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        visitableList = getData();
        luAdapter = new LuAdapter(new ListTypeFactory(), visitableList);
        luRecyclerViewAdapter = new LuRecyclerViewAdapter(luAdapter);
        luRecyclerView.setAdapter(luRecyclerViewAdapter);


        swipeRefreshLayout.setColorSchemeResources(R.color.baseColor);
        luRecyclerView.setFooterViewColor(R.color.baseColor, R.color.baseColor, R.color.white);
        luRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallBeat);

        //设置底部加载文字提示
        luRecyclerView.setFooterViewHint("拼命加载中","已经全部为你呈现了","网络不给力啊，点击再试一次吧");
        swipeRefreshLayout.setOnRefreshListener(this);
        luRecyclerViewAdapter.setSpanSizeLookup(new LuRecyclerViewAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                Object item = visitableList.get(position + 1);
                return (item instanceof HotList || item instanceof ProductList || item instanceof Category) ? gridLayoutManager.getSpanCount() : 1;
            }
        });

        luRecyclerViewAdapter.addHeaderView(new HeaderView(this));
        luRecyclerView.setOnLoadMoreListener(this);
        luRecyclerView.setLoadMoreEnabled(true);
        //是否允许嵌套滑动
        luRecyclerView.setNestedScrollingEnabled(true);
        luRecyclerView.scrollToPosition(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        luRecyclerView.scrollToPosition(0);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                luRecyclerView.refreshComplete(Content.PAGE_COUNT);
            }
        }, 1500);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                luRecyclerView.refreshComplete(Content.PAGE_COUNT);
                luRecyclerView.setNoMore(true);
            }
        }, 1500);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    private List<Visitable> getData() {
        List<Visitable> list = new ArrayList<>();
        list.add(new Category("分组一"));
        list.add(new ProductList(getProducts()));
        list.add(new HotList(getProducts()));

        list.add(new Category("分组二"));
        list.add(new ProductList(getProducts()));
        list.add(new HotList(getProducts()));

        list.add(new Category("分组三"));
        list.add(new ProductList(getProducts()));
        list.add(new HotList(getProducts()));

        list.add(new Category("分组四"));
        list.add(new Si("hhhhhhhhhhhhh"));
        return list;
    }

    private List<Product> getProducts(){
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            products.add(new Product("item" + i, R.mipmap.logo));
        }
        return products;
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
        return luRecyclerView;
    }
}
