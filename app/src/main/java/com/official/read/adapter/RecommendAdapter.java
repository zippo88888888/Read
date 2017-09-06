package com.official.read.adapter;

import android.content.Context;

import com.official.read.R;
import com.official.read.base.BaseAdapter;
import com.official.read.base.BaseViewHolder;
import com.official.read.content.bean.RecommendBean;

import java.util.List;

/**
 * com.official.read.adapter
 * Created by ZP on 2017/7/13.
 * description:
 * version: 1.0
 */

public class RecommendAdapter extends BaseAdapter<RecommendBean> {

    private CheckRecommendStateListener checkRecommendStateListener;

    public void setCheckRecommendStateListener(CheckRecommendStateListener checkRecommendStateListener) {
        this.checkRecommendStateListener = checkRecommendStateListener;
    }

    public RecommendAdapter(Context context, List<RecommendBean> datas) {
        super(context, datas);
    }

    @Override
    public int getLayoutID() {
        return R.layout.new_home_item_layout;
    }

    @Override
    public void onBinderItemHolder(BaseViewHolder holder, int position) {
        RecommendBean item = getItem(position);
        int listSize = getItemCount();
        if (checkRecommendStateListener != null) {
            checkRecommendStateListener.check(holder, item, listSize, position);
        }
    }

    public interface CheckRecommendStateListener {
        void check(BaseViewHolder holder, RecommendBean bean, int listSize, int position);
    }

}
