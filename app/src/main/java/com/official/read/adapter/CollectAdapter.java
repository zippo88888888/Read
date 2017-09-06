package com.official.read.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.official.read.R;
import com.official.read.base.BaseAdapter;
import com.official.read.base.BaseViewHolder;
import com.official.read.content.bean.CollectBean;
import com.official.read.util.ImageLoader;
import com.official.read.weight.SwipeMenuView;

import java.util.List;

/**
 * com.official.read.adapter
 * Created by ZP on 2017/8/7.
 * description:
 * version: 1.0
 */

public class CollectAdapter extends BaseAdapter<CollectBean> {

    private SwipeListener swipeListener;
    public void setSwipeListener(SwipeListener swipeListener) {
        this.swipeListener = swipeListener;
    }

    public CollectAdapter(Context context, List<CollectBean> datas) {
        super(context, datas);
    }

    @Override
    public int getLayoutID() {
        return R.layout.collect_item_layout;
    }

    @Override
    public void onBinderItemHolder(final BaseViewHolder holder, final int position) {
        RelativeLayout root = holder.getView(R.id.collect_item_rootView);
        ImageView pic = holder.getView(R.id.new_home_list_item_pic);
        TextView title = holder.getView(R.id.collect_item_title);
        TextView date = holder.getView(R.id.collect_item_date);
        TextView delete = holder.getView(R.id.collect_item_delete);
        final SwipeMenuView swipeMenuView = (SwipeMenuView) holder.itemView;
        swipeMenuView.setIos(true).setLeftSwipe(true);
        final CollectBean item = getItem(position);
        ImageLoader.loadImage(pic, item.imgURL);
        title.setText(item.title);
        date.setText("收藏日期：" + item.getDate());
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeListener.itemClick(holder.getView(R.id.new_home_list_item_pic), item, position);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                swipeMenuView.quickClose();
                swipeListener.delete(item, position);
            }
        });
    }

    public interface SwipeListener {
        void itemClick(View view, CollectBean bean, int position);

        void delete(CollectBean bean, int position);
    }
}
