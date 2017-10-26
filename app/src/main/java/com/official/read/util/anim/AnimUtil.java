package com.official.read.util.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.official.read.R;
import com.official.read.util.AndroidUtil;
import com.official.read.util.L;
import com.official.read.util.Toaster;

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

    public static void setLeftRightVibratorAnim(final View view) {
        int w = view.getWidth();
        int width = AndroidUtil.getDisplay(view.getContext())[0];

        int hasW = (width - w) / 2;

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "", hasW - 20, hasW + 20, hasW + 20, hasW - 20, hasW, hasW, hasW);
        animator.setDuration(150);
        animator.setRepeatCount(2);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setX(value);
            }
        });
        animator.start();
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
     * 为 SearchFragment 设置进入动画
     * @param view  layout
     */
    public static void setStartAnimForSearch(View view) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0);
        view.setTranslationY(-100);
        view.animate()
                .setDuration(200)
                .alpha(1)
                .translationY(0);
    }

    /**
     * 地图页面动画实现
     * @param view      View
     * @param isShow    显示or隐藏
     */
    public static void startAnimForMap(final View view, final boolean isShow) {
        int height = view.getHeight();
        ObjectAnimator animator;
        if (isShow) { // 显示
            animator = ObjectAnimator.ofFloat(view, "y", -height, 0);
            animator.setInterpolator(new BounceInterpolator());
        } else { // 隐藏
            animator = ObjectAnimator.ofFloat(view, "y", 0, -height);
            animator.setInterpolator(new LinearInterpolator());
        }
        animator.setDuration(400).start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isShow) {
                    view.setVisibility(View.GONE);
                }
            }
        });
    }

}
