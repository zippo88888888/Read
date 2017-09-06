package com.official.read.adapter;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.official.read.R;
import com.official.read.base.BaseAdapter;
import com.official.read.base.BaseViewHolder;
import com.official.read.content.bean.JusticeBean;

import java.util.List;

/**
 * com.official.read.adapter
 * Created by ZP on 2017/8/7.
 * description:
 * version: 1.0
 */
public class JusticeAdapter extends BaseAdapter<JusticeBean> {

    public JusticeAdapter(Context context, List<JusticeBean> datas) {
        super(context, datas);
    }

    @Override
    public int getLayoutID() {
        return R.layout.justice_item_layout;
    }

    @Override
    public void onBinderItemHolder(BaseViewHolder holder, int position) {
        RelativeLayout root = holder.getView(R.id.justice_item_rootView);
        TextView name = holder.getView(R.id.justice_item_name);

        // selectableItemBackground
//        TypedValue typedValue = new TypedValue();
//        context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
//        int[] attribute = new int[]{android.R.attr.selectableItemBackground};
//        TypedArray typedArray = context.getTheme().obtainStyledAttributes(typedValue.resourceId, attribute);
//        root.setBackground(typedArray.getDrawable(0));

        JusticeBean item = getItem(position);
        name.setTextColor(item.color);
        name.setText(item.name);
    }
}
