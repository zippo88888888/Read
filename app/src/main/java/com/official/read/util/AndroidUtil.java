package com.official.read.util;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Point;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.official.read.R;

/**
 * com.official.casual.util
 * Created by ZP on 2017/6/23.
 * description:
 * version: 1.0
 */
public class AndroidUtil {

    /**
     * 获取状态栏高度
     * @param context context
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 返回ToolBar的高度
     */
    public static int getToolBarHeight(Context context) {
        return dip2px(context, 55f);
    }

    /**
     * 获取屏幕的宽，高
     * @param context   Context
     * @return int[] 0--width;1--height
     */
    public static int[] getDisplay(Context context) {
        int[] display = new int[2];
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        manager.getDefaultDisplay().getSize(point);
        display[0] = point.x;
        display[1] = point.y;
        return display;
    }

    /**
     * 从 dp 转成为 px
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 从 px转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 设置弹窗背景
     */
    public static void setBackgroundAlpha(Context context,float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) context).getWindow().setAttributes(lp);
    }

    /**
     * 关闭软键盘
     */
    public static void closeKeyboard(Context context) {
        InputMethodManager m = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = m.isActive();
        if (isOpen) { // 表示打开
            // 如果打开，则关闭
            m.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken()
                    , InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 去除 NavigationView 的滚动条
     */
    public static void disableNavigationViewScrollbars(NavigationView drawerLayout) {
        NavigationMenuView navigationMenuView = (NavigationMenuView) drawerLayout.getChildAt(0);
        if (navigationMenuView != null) {
            navigationMenuView.setVerticalScrollBarEnabled(false);
        }
    }

    /**
     * 复制到剪贴板管理器
     */
    public static void copy(String content, Context context) {
        ClipboardManager cmb = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

}
