package com.official.read.weight.lu_recycler_view.holder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.official.read.R;
import com.official.read.weight.lu_recycler_view.Visitable;
import com.official.read.weight.lu_recycler_view.adapter.HotsAdapter;
import com.official.read.weight.lu_recycler_view.entity.HotList;
import com.official.read.weight.lu_recycler_view.entity.Product;

import java.util.List;

/**
 * com.official.read.weight.lu_recycler_view.holder
 * Created by ZP on 2017/8/8.
 * description:
 * version: 1.0
 */

public class HotListViewHolder extends BetterViewHolder {

    private RecyclerView recyclerView;
    private HotsAdapter adapter;

    public HotListViewHolder(View itemView) {
        super(itemView);
        recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HotsAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void bindItem(Visitable visitable) {
        HotList hotList = (HotList) visitable;
        List<Product> products = hotList.products;
        adapter.setData(products);
        adapter.notifyDataSetChanged();
    }
}
