package com.official.read.weight.lu_recycler_view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.official.read.weight.lu_recycler_view.Visitable;

/**
 * com.official.read.weight.lu_recycler_view
 * Created by ZP on 2017/8/8.
 * description:
 * version: 1.0
 */

public abstract class BetterViewHolder<T extends Visitable> extends RecyclerView.ViewHolder {

    public BetterViewHolder(View itemView) {
        super(itemView);
    }

    /**
     * 绑定数据
     */
    public abstract void bindItem(T t);
}
