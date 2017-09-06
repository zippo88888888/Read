package com.official.read.adapter;

import android.content.Context;
import android.widget.TextView;

import com.official.read.R;
import com.official.read.base.BaseAdapter;
import com.official.read.base.BaseViewHolder;
import com.official.read.content.bean.SearchHistoryBean;

import java.util.List;

/**
 * com.official.read.adapter
 * Created by ZP on 2017/8/3.
 * description:
 * version: 1.0
 */

public class SearchHistoryAdapter extends BaseAdapter<SearchHistoryBean> {

    public SearchHistoryAdapter(Context context, List<SearchHistoryBean> datas) {
        super(context, datas);
    }

    @Override
    public int getLayoutID() {
        return R.layout.search_history_layout;
    }

    @Override
    public void onBinderItemHolder(BaseViewHolder holder, int position) {
        TextView history = holder.getView(R.id.search_history_item);
        history.setText(getItem(position).name);
    }
}
