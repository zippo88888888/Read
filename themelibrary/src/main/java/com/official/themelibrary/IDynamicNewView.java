package com.official.themelibrary;

import android.view.View;
import android.widget.TextView;

import com.official.themelibrary.attr.DynamicAttr;

import java.util.List;


/**
 * Created by _SOLID
 * Date:2016/4/14
 * Time:10:26
 */
public interface IDynamicNewView {
    void dynamicAddView(View view, List<DynamicAttr> pDAttrs);

    void dynamicAddView(View view, String attrName, int attrValueResId);

    /**
     * add the textview for font switch
     *
     * @param textView textview
     */
    void dynamicAddFontView(TextView textView);
}
