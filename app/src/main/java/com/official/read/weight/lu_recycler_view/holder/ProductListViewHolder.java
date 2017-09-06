package com.official.read.weight.lu_recycler_view.holder;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.official.read.R;
import com.official.read.weight.lu_recycler_view.Visitable;
import com.official.read.weight.lu_recycler_view.adapter.ProductsAdapter;
import com.official.read.weight.lu_recycler_view.entity.Product;
import com.official.read.weight.lu_recycler_view.entity.ProductList;

import java.util.List;

/**
 * com.official.read.weight.lu_recycler_view.holder
 * Created by ZP on 2017/8/8.
 * description:
 * version: 1.0
 */

public class ProductListViewHolder extends BetterViewHolder {

    private RecyclerView recyclerView;
    private ProductsAdapter adapter;

    public ProductListViewHolder(View itemView) {
        super(itemView);
        recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(itemView.getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new ProductsAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void bindItem(Visitable visitable) {
        ProductList productList = (ProductList) visitable;
        List<Product> products = productList.products;
        adapter.setData(products);
        adapter.notifyDataSetChanged();
    }
}
