package com.official.read.weight.lu_recycler_view.holder;

import android.view.View;
import android.widget.TextView;

import com.official.read.R;
import com.official.read.weight.lu_recycler_view.Visitable;
import com.official.read.weight.lu_recycler_view.entity.Si;

/**
 * com.official.read.weight.lu_recycler_view.holder
 * Created by ZP on 2017/8/8.
 * description:
 * version: 1.0
 */

public class SiViewHolder extends BetterViewHolder {

    private TextView si;

    public SiViewHolder(View itemView) {
        super(itemView);
        si = (TextView) itemView.findViewById(R.id.si_text);
    }

    @Override
    public void bindItem(Visitable visitable) {
        Si i = (Si) visitable;
        si.setText(i.si);
    }
}
