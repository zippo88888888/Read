package com.official.read.content.listener;

import android.support.design.widget.AppBarLayout;

/**
 * com.official.read.weight
 * Created by ZP on 2017/8/18.
 * description: 为DetailActivity 设置AppBarLayout滑动监听
 * version: 1.0
 */

public abstract class AppBarChangeListener implements AppBarLayout.OnOffsetChangedListener {


    @Override
    public final void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            unfold();
            changeTitleAlpha(1f);
            changeToolBarTitleAlpha(0f);
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            fold();
            changeTitleAlpha(0f);
            changeToolBarTitleAlpha(1f);
        } else {
            scroll(verticalOffset);
            float max = appBarLayout.getTotalScrollRange();
            float alpha = verticalOffset / max;
            changeToolBarTitleAlpha(Math.abs(alpha));
            changeTitleAlpha(1f - Math.abs(alpha));
        }
    }

    /**
     * 改变ToolBar标题的透明度
     * @param alpha 0--完全透明；1--完全不透明
     */
    public abstract void changeToolBarTitleAlpha(float alpha);

    /**
     * 改变标题的透明度
     * @param alpha 0--完全透明；1--完全不透明
     */
    public abstract void changeTitleAlpha(float alpha);

    /**
     * 展开状态
     */
    public void unfold() {

    }

    /**
     * 折叠状态
     */
    public void fold() {

    }

    public void scroll(int scroll) {

    }
}
