package com.official.read.base;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * com.official.read.adapter
 * Created by ZP on 2017/7/12.
 * description:
 * version: 1.0
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected Context context;
    private List<T> datas = new ArrayList<>();

    public BaseAdapter(Context context, List<T> datas) {
        this.context = context;
        this.datas = datas;
    }

    public List<T> getDatas() {
        return datas;
    }

    /**
     * 设置值
     */
    public void setDataList(Collection<T> list) {
        datas.clear();
        datas.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 添加所有
     */
    public void addAll(Collection<T> list) {
        int startSize = datas.size();
        int endSize = list.size();
        boolean b = datas.addAll(list);
        if (b) {
            notifyItemRangeChanged(startSize, endSize);
        }
    }

    /**
     * 添加单个
     */
    public void addItem(T t) {
        datas.add(t);
        notifyItemRangeChanged(datas.size(), 1);
    }

    /**
     * 清除所有
     */
    public void clear() {
        datas.clear();
        notifyDataSetChanged();
    }

    /**
     * 清除所有，但是不会刷新界面
     */
    public void clearButNoDataSetChanged() {
        datas.clear();
    }

    /**
     * 清除单个
     */
    public void remove(int position) {
        datas.remove(position);
        if (position - 1 >= datas.size()) {
            position = position - 2;
        } else {
            position = position - 1;
        }
        notifyItemRemoved(position);
        // 如果移除的是最后一个，忽略
        if (position != getDatas().size()) {
            notifyItemRangeChanged(position, datas.size() - position);
        }
    }

    public void startActivity(Intent intent) {
        if (intent != null) {
            context.startActivity(intent);
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutID(), parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        onBinderItemHolder(holder, position);
    }

    // 局部刷新
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBinderItemHolder(holder, position);
        } else {
            onBinderItemHolder(holder, position, payloads);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public T getItem(int position) {
        return datas.get(position);
    }

    public abstract int getLayoutID();
    public abstract void onBinderItemHolder(BaseViewHolder holder, int position);
    /** 局部刷新实现，必须重新该方法 */
    public void onBinderItemHolder(BaseViewHolder holder, int position, List<Object> payloads) {

    }
}
