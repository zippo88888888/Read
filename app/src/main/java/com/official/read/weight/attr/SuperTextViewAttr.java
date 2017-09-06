package com.official.read.weight.attr;

import android.view.View;

import com.coorchice.library.SuperTextView;
import com.official.themelibrary.attr.SkinAttr;
import com.official.themelibrary.utils.SkinResourcesUtils;


/**
 * com.official.read.weight
 * Created by ZP on 2017/8/14.
 * description:
 * version: 1.0
 */
public class SuperTextViewAttr extends SkinAttr {

    @Override
    protected void applySkin(View view) {
        if (view instanceof SuperTextView) {
            SuperTextView textView = (SuperTextView) view;
            if (isColor()) {
                int color = SkinResourcesUtils.getColor(attrValueRefId);
                textView.setStrokeColor(color);
            }
        }
    }
}
