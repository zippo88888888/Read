package com.official.read.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.official.read.R;
import com.official.read.content.Content;

/**
 * com.test.zzs.text.util
 * Created by ZP on 2017/3/31.
 * description:
 * version: 1.0
 */

public class Toaster extends Toast {

    public final static int TOP = Gravity.TOP;
    public final static int BOTTOM = Gravity.BOTTOM;

    private static Context context;
    private static int color = 0;
    private static Toast toast;

    private Toaster(Context context) {
        super(context);
    }

    public static void init(Context context) {
        Toaster.context = context;
    }

    public static void setColor(int color) {
        Toaster.color = context.getResources().getColor(color);
    }

    public static void makeText(String str) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context.getApplicationContext(), str, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void makeText(int str) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context.getApplicationContext(), str, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 使用自定义布局 Toast
     * @param type 弹窗位置；see {@link #BOTTOM}，{@link #TOP}
     * @param text 文字说明
     */
    public static void makeText(int type, String text) {
        if (toast != null) {
            toast.cancel();
        }
        int[] display = AndroidUtil.getDisplay(context);
        View v = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
        v.setAlpha(0.9f);
        v.setTranslationY(-300);
        v.animate()
                .setDuration(300)
                .translationY(0);
        TextView msg = (TextView) v.findViewById(R.id.toast_msg);
        msg.setText(text);
        if (color == 0) {
            int color = Content.getBaseColorByTheme((String) SPUtil.get(SPUtil.BASE_COLOR, Content.THEME_RED));
            if (color == R.color.red) {
                msg.setBackgroundColor(context.getResources().getColor(R.color.green));
            } else {
                msg.setBackgroundColor(context.getResources().getColor(R.color.red));
            }
        } else {
            msg.setBackgroundColor(color);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(display[0],
                ViewGroup.LayoutParams.WRAP_CONTENT);
        msg.setLayoutParams(params);
        toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        if (type != TOP && type != BOTTOM) {
            throw new RuntimeException("type only is TOP or BOTTOM");
        }
        if (type == TOP) {
            toast.setGravity(type, 0, AndroidUtil.getToolBarHeight(context));
        } else {
            toast.setGravity(type, 0, 0);
        }
        toast.setView(v);
        toast.show();
    }

    public static void makeText(int type, int textID) {
        String s = context.getResources().getString(textID);
        makeText(type, s);
    }

    public static Context getContext() {
        return context;
    }
}
