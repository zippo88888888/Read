package com.official.read.weight.attr;

import android.view.View;

import com.official.read.weight.MyKeyBoardView;
import com.official.themelibrary.attr.SkinAttr;
import com.official.themelibrary.utils.SkinResourcesUtils;

/**
 * com.official.read.weight.attr
 * Created by ZP on 2017/10/11.
 * description:
 * version: 1.0
 */

public class MyKeyBoardViewAttr extends SkinAttr {

    @Override
    protected void applySkin(View view) {
        if (view instanceof MyKeyBoardView) {
            MyKeyBoardView boardView = (MyKeyBoardView) view;
            if (isColor()) {
                int color = SkinResourcesUtils.getColor(attrValueRefId);
                boardView.setInputColor(color);
            }
        }
    }
}
