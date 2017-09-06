package com.official.read.weight;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

import com.official.read.util.L;

/**
 * com.official.read.weight
 * Created by ZP on 2017/8/16.
 * description:
 * version: 1.0
 */

public class MyNestedScrollView extends NestedScrollView {

    final static int MIN_SCROLL_DP = 10;

    private ScrollChangeListener scrollChangeListener;
    public void setScrollChangeListener(ScrollChangeListener scrollChangeListener) {
        this.scrollChangeListener = scrollChangeListener;
    }

    public MyNestedScrollView(Context context) {
        super(context);
    }

    public MyNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (oldt > t && oldt - t > MIN_SCROLL_DP) {
            if (scrollChangeListener != null) {
                scrollChangeListener.scrollToDown();
            }
        } else if (oldt < t && t - oldt > MIN_SCROLL_DP) {
            if (scrollChangeListener != null) {
                scrollChangeListener.scrollToUp();
            }
        }
    }

    public interface ScrollChangeListener {

        void scrollToUp();

        void scrollToDown();
    }
}
