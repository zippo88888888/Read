package com.official.read.util.anim;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;

import com.official.read.R;
import com.official.read.util.AndroidUtil;

/**
 * com.official.read.AnimUtil
 * Created by ZP on 2017/8/16.
 * description:
 * version: 1.0
 */

public final class AnimUtil {

    private final static int ANIM_TIME = 500;
    private final static int ALL_ANIM_TIME = 300;

    /**
     * 为 DetailActivity 设置底部隐藏动画
     */
    public static void setToBottomHiddenForDetail(Context context, final View view) {
        int[] display = AndroidUtil.getDisplay(context);
        // 屏幕的高度  控件的结束位置
        int height = display[1];
        // 控件的高度
        int h = (int) context.getResources().getDimension(R.dimen.detail_bottom_height);
        // 控件的起始位置
        int startY = height - h;
        int statusHeight = AndroidUtil.getStatusBarHeight(context);
        ValueAnimator animator = ValueAnimator.ofInt(startY - statusHeight, height);
        animator.setTarget(view);
        animator.setDuration(ANIM_TIME).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int scroll = (int) animation.getAnimatedValue();
                view.setY(scroll);
            }
        });
    }

    /**
     * 为 DetailActivity 设置底部显示动画
     */
    public static void setToUpShowForDetail(Context context, final View view, boolean isFirst) {
        int[] display = AndroidUtil.getDisplay(context);
        // 屏幕的高度 控件的开始位置
        int height = display[1];
        // 控件的高度
        int h = (int) context.getResources().getDimension(R.dimen.detail_bottom_height);
        // 控件的结束位置
        int endY = height - h;
        int statusHeight = AndroidUtil.getStatusBarHeight(context);
        ValueAnimator animator = ValueAnimator.ofInt(height, endY - statusHeight);
        animator.setTarget(view);
        if (isFirst) {
            animator.setDuration(ALL_ANIM_TIME);
        } else {
            animator.setDuration(ANIM_TIME);
        }
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int scroll = (int) animation.getAnimatedValue();
                view.setY(scroll);
            }
        });
    }

    /**
     * 为 DetailActivity 设置进入动画
     * @param view  FloatingActionButton
     */
    public static void setStartActionButtonForDetail(View view) {
        view.setVisibility(View.VISIBLE);
        view.setScaleX(0);
        view.setScaleY(0);
        view.animate()
                .setDuration(ALL_ANIM_TIME)
                .scaleX(1)
                .scaleY(1);
    }

    /**
     * 为 DetailActivity 设置进入动画
     * @param view  layout
     */
    public static void setStartRootViewForDetail(View view) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0);
        view.setTranslationY(-100);
        view.animate()
                .setDuration(ALL_ANIM_TIME)
                .alpha(1)
                .translationY(0);
    }

    /**
     * 设置 往左移动还是往右移动动画
     * @param context   Context
     * @param view      View
     * @param type      0--left；1--right
     */
    public static void setToLeftURight(Context context, final View view, final int type) {
        int[] display = AndroidUtil.getDisplay(context);
        int width = display[0];
        ValueAnimator animator;
        if (type == 0) {
            animator = ValueAnimator.ofInt(0, width);
        } else {
            animator = ValueAnimator.ofInt(width, 0);
        }
        animator.setTarget(view);
        animator.setDuration(ALL_ANIM_TIME).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int scroll = (int) animation.getAnimatedValue();
                view.setX(scroll);
            }
        });
    }

    /**
     * 为 SearchFragment 设置进入动画
     * @param view  layout
     */
    public static void setStartAnimForSearch(View view) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0);
        view.setTranslationY(-100);
        view.animate()
                .setDuration(100)
                .alpha(1)
                .translationY(0);
    }
}
