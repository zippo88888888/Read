package com.official.read.weight.attr;

import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.official.read.R;
import com.official.themelibrary.attr.SkinAttr;
import com.official.themelibrary.utils.SkinResourcesUtils;

/**
 * com.official.read.weight.attr
 * Created by ZP on 2017/8/15.
 * description:
 * version: 1.0
 */

public class FloatingActionButtonAttr extends SkinAttr {
    @Override
    protected void applySkin(View view) {
        if (view instanceof FloatingActionButton) {
            FloatingActionButton actionButton = (FloatingActionButton) view;
            if (isColor()) {
                int color = SkinResourcesUtils.getColor(attrValueRefId);

                int statePressed = android.R.attr.state_pressed;
                int stateFocesed = android.R.attr.state_focused;
//                int[][] state = {{statePressed},{-statePressed},{stateFocesed},{-stateFocesed}};
                int[][] state = {{-statePressed},{-stateFocesed}/*,{statePressed},{stateFocesed}*/};
                int color2 = R.color.gray;
//                int color3 = color;
//                int color4 = color;
                int[] colors = {color, color/*, color, color*/};

                ColorStateList colorStateList = new ColorStateList(state, colors);
                actionButton.setBackgroundTintList(colorStateList);
            }
        }
    }
}
