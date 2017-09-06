package com.official.read.weight.attr;

import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;

import com.official.themelibrary.attr.SkinAttr;
import com.official.themelibrary.utils.SkinResourcesUtils;

/**
 * com.official.read.weight
 * Created by ZP on 2017/8/15.
 * description:
 * version: 1.0
 */

public class CollapsingToolbarLayoutAttr extends SkinAttr {

    @Override
    protected void applySkin(View view) {
        if (view instanceof CollapsingToolbarLayout) {
            CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) view;
            if (isColor()) {
                int color = SkinResourcesUtils.getColor(attrValueRefId);
//                Context context = view.getContext();
//                TypedValue typedValue = new TypedValue();
//                context.getTheme().resolveAttribute(color, typedValue, true);
//                int[] attribute = new int[]{color};
//                TypedArray typedArray = context.getTheme().obtainStyledAttributes(typedValue.resourceId, attribute);
//                toolbarLayout.setContentScrim(typedArray.getDrawable(0));
                toolbarLayout.setContentScrimColor(color);
            }
        }
    }
}
