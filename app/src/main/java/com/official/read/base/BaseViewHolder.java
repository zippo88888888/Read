package com.official.read.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * com.official.read.adapter
 * Created by ZP on 2017/7/12.
 * description:
 * version: 1.0
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {

    SparseArray<View> array;

    public BaseViewHolder(View itemView) {
        super(itemView);
        array = new SparseArray<>();
    }

    public <V extends View> V getView(int id) {
        View view = array.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            array.put(id, view);
        }
        return (V) view;
    }
}
