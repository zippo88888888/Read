package com.official.read.weight.lu_recycler_view.type;

import android.view.View;

import com.official.read.R;
import com.official.read.weight.lu_recycler_view.entity.Si;
import com.official.read.weight.lu_recycler_view.holder.BetterViewHolder;
import com.official.read.weight.lu_recycler_view.entity.Category;
import com.official.read.weight.lu_recycler_view.entity.HotList;
import com.official.read.weight.lu_recycler_view.entity.ProductList;
import com.official.read.weight.lu_recycler_view.holder.CategoryViewHolder;
import com.official.read.weight.lu_recycler_view.holder.HotListViewHolder;
import com.official.read.weight.lu_recycler_view.holder.ProductListViewHolder;
import com.official.read.weight.lu_recycler_view.holder.SiViewHolder;

/**
 * com.official.read.weight.lu_recycler_view
 * Created by ZP on 2017/8/8.
 * description:
 * version: 1.0
 */

public class ListTypeFactory implements TypeFactory {

    @Override
    public int type(Category title) {
        return R.layout.layout_list_item_category;
    }

    @Override
    public int type(ProductList productList) {
        return R.layout.layout_item_list;
    }

    @Override
    public int type(HotList hotList) {
        return R.layout.layout_item_horizontal_list;
    }

    @Override
    public int type(Si si) {
        return R.layout.layout_list_item_si;
    }

    @Override
    public BetterViewHolder onCreateViewHolder(View itemView, int viewType) {
        BetterViewHolder viewHolder = null;
        switch (viewType) {
            case R.layout.layout_list_item_category:
                viewHolder = new CategoryViewHolder(itemView);
                break;
            case R.layout.layout_item_list:
                viewHolder = new ProductListViewHolder(itemView);
                break;
            case R.layout.layout_item_horizontal_list:
                viewHolder = new HotListViewHolder(itemView);
                break;
            case R.layout.layout_list_item_si:
                viewHolder = new SiViewHolder(itemView);
                break;
        }
        return viewHolder;
    }
}
