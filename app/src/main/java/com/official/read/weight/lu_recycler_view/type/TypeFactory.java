package com.official.read.weight.lu_recycler_view.type;

import android.view.View;

import com.official.read.weight.lu_recycler_view.entity.Category;
import com.official.read.weight.lu_recycler_view.entity.HotList;
import com.official.read.weight.lu_recycler_view.entity.ProductList;
import com.official.read.weight.lu_recycler_view.entity.Si;
import com.official.read.weight.lu_recycler_view.holder.BetterViewHolder;

/**
 * com.official.read.weight.lu_recycler_view
 * Created by ZP on 2017/8/8.
 * description:
 * version: 1.0
 */

public interface TypeFactory {

    int type(Category title);
    int type(ProductList productList);
    int type(HotList hotList);
    int type(Si si);

    BetterViewHolder onCreateViewHolder(View itemView, int viewType);
}
