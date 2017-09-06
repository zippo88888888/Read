package com.official.read.weight.lu_recycler_view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.official.read.weight.lu_recycler_view.Visitable;
import com.official.read.weight.lu_recycler_view.holder.BetterViewHolder;
import com.official.read.weight.lu_recycler_view.type.TypeFactory;

import java.util.List;

/**
 * com.official.read.weight.lu_recycler_view
 * Created by ZP on 2017/8/8.
 * description:
 * version: 1.0
 */

public class LuAdapter extends RecyclerView.Adapter<BetterViewHolder> {

    private TypeFactory mTypeFactory;
    private List<Visitable> mVisitables;

    public LuAdapter(TypeFactory typeFactory, List<Visitable> visitables) {
        mTypeFactory = typeFactory;
        mVisitables = visitables;
    }

    @Override
    public int getItemViewType(int position) {
        return mVisitables.get(position).type(mTypeFactory);
    }

    @Override
    public BetterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mTypeFactory.onCreateViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false), viewType);
    }

    @Override
    public void onBindViewHolder(BetterViewHolder holder, int position) {
        holder.bindItem(mVisitables.get(position));
    }

    @Override
    public int getItemCount() {
        return mVisitables.size();
    }
}
