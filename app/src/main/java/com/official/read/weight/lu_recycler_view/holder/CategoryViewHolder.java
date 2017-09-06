package com.official.read.weight.lu_recycler_view.holder;

import android.view.View;
import android.widget.TextView;

import com.official.read.R;
import com.official.read.weight.lu_recycler_view.Visitable;
import com.official.read.weight.lu_recycler_view.entity.Category;
import com.official.read.util.Toaster;

/**
 * com.official.read.weight.lu_recycler_view.holder
 * Created by ZP on 2017/8/8.
 * description:
 * version: 1.0
 */

public class CategoryViewHolder extends BetterViewHolder {

    TextView textView;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.title);
    }

    @Override
    public void bindItem(Visitable visitable) {
        final Category category = (Category) visitable;
        textView.setText(category.title);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toaster.makeText(category.title);
            }
        });
    }
}
