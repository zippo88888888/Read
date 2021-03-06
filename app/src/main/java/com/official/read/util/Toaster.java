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

    private static Context applicationContext;
    private static int color = 0;
    private static Toast toast;

    private Toaster(Context context) {
        super(context);
    }

    public static void init(Context context) {
        Toaster.applicationContext = context;
    }

    public static void setColor(int color) {
        Toaster.color = applicationContext.getResources().getColor(color);
    }

    public static void makeTextS(int textID) {
        String s = applicationContext.getResources().getString(textID);
        makeTextS(s);
    }

    /**
     *  使用系统的Toast
     */
    public static void makeTextS(String str) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(applicationContext.getApplicationContext(), str, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void makeText(String str) {
        makeText(TOP, str);
    }

    public static void makeText(int textID) {
        String s = applicationContext.getResources().getString(textID);
        makeText(s);
    }

    public static void makeText(int type, int textID) {
        String s = applicationContext.getResources().getString(textID);
        makeText(type, s);
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
        int[] display = AndroidUtil.getDisplay(applicationContext);
        View v = LayoutInflater.from(applicationContext).inflate(R.layout.layout_toast, null);
        v.setAlpha(0.9f);
        v.setTranslationY(-300);
        v.animate().setDuration(300).translationY(0);
        TextView msg = (TextView) v.findViewById(R.id.toast_msg);
        msg.setText(text);
        if (color == 0) {
            int color = Content.getBaseColorByTheme((String) SPUtil.get(SPUtil.BASE_COLOR, Content.THEME_RED));
            if (color == R.color.red) {
                msg.setBackgroundColor(applicationContext.getResources().getColor(R.color.green));
            } else {
                msg.setBackgroundColor(applicationContext.getResources().getColor(R.color.red));
            }
        } else {
            msg.setBackgroundColor(color);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(display[0],
                ViewGroup.LayoutParams.WRAP_CONTENT);
        msg.setLayoutParams(params);
        toast = new Toast(applicationContext);
        toast.setDuration(Toast.LENGTH_SHORT);
        if (type != TOP && type != BOTTOM) {
            throw new RuntimeException("type only is TOP or BOTTOM");
        }
        if (type == TOP) {
            toast.setGravity(type, 0, AndroidUtil.getToolBarHeight(applicationContext));
        } else {
            toast.setGravity(type, 0, 0);
        }
        toast.setView(v);
        toast.show();
    }

    public static Context getApplicationContext() {
        if (applicationContext != null) {
            return applicationContext;
        } else {
            throw new NullPointerException("Please first Application invokes the \"init()\" method");
        }
    }
}
