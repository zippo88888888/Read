package com.official.read.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.official.read.util.L;
import com.official.read.util.Toaster;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * com.official.read.base
 * Created by ZP on 2017/8/23.
 * description:
 * version: 1.0
 */

public final class CommonDialog {

    private AlertDialog.Builder builder;

    /**
     * 一个Dialog按钮
     */
    public static final int DIALOG_BUTTON_ONE = 1;

    /**
     * 两个Dialog按钮
     */
    public static final int DIALOG_BUTTON_TWO = 2;

    /**
     * 三个Dialog按钮
     */
    public static final int DIALOG_BUTTON_THREE = 3;

    private Map<String, SoftReference<Context>> mapContext = new HashMap<>();

    /**
     * 按钮的数量
     */
    private int buttonCount;

    /**
     * 开始构造dialog
     * @param buttonCount button count; see {@link #DIALOG_BUTTON_ONE},{@link #DIALOG_BUTTON_TWO},
     *                    {@link #DIALOG_BUTTON_THREE}
     * @param context   Context; see {@link #mapContext}
     */
    public CommonDialog(int buttonCount, Context context) {
        this.buttonCount = buttonCount;
        SoftReference<Context> softReference = new SoftReference<>(context);
        mapContext.put("context", softReference);

    }

    /**
     * 显示Dialog
     * @param dialogClickListener   Dialog按钮监听
     * @param str   [0]--Dialog Message，[其他]--Dialog按钮文字说明（最多{@link #buttonCount}个）
     */
    public void showDialog1(final DialogClickListener dialogClickListener, String... str) {
        checkContext();
        if (buttonCount == DIALOG_BUTTON_ONE) {
            createDialog(dialogClickListener, str);
            builder.show();
        } else {
            throw new IllegalArgumentException("You only use \"createDialog1\"");
        }
    }

    /**
     * 显示Dialog
     * @param dialogClickListener   Dialog按钮监听
     * @param str   [0]--Dialog Message，[其他]--Dialog按钮文字说明（最多{@link #buttonCount}个）
     */
    public void showDialog2(final DialogClickListener dialogClickListener, String... str) {
        checkContext();
        if (buttonCount == DIALOG_BUTTON_TWO) {
            createDialog(dialogClickListener, str);
            builder.setNegativeButton(str[2], new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    dialogClickListener.click2();
                }
            }).show();
        } else {
            throw new IllegalArgumentException("You only use \"createDialog2\"");
        }
    }

    /**
     * 显示Dialog
     * @param dialogClickListener   Dialog按钮监听
     * @param str   [0]--Dialog Message，[其他]--Dialog按钮文字说明（最多{@link #buttonCount}个）
     */
    public void showDialog3(final DialogClickListener dialogClickListener, String... str) {
        checkContext();
        if (buttonCount == DIALOG_BUTTON_THREE) {
            createDialog(dialogClickListener, str);
            builder.setNegativeButton(str[2], new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    dialogClickListener.click2();
                }
            });
            builder.setNeutralButton(str[3], new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    dialogClickListener.click3();
                }
            }).show();
        } else {
            throw new IllegalArgumentException("You only use \"createDialog3\"");
        }
    }

    private void createDialog(final DialogClickListener dialogClickListener, String... str) {
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("温馨提示");
        builder.setMessage(str[0]);
        builder.setCancelable(false);
        builder.setPositiveButton(str[1], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                dialogClickListener.click1();
            }
        });
    }

    private void checkContext() {
        if (getContext() == null) {
            L.e("CommonDialog Context is null");
            Toaster.makeText("系统分配给App的内存不足，可能是个漏洞，请先更新Android系统");
            return;
        }
    }

    private Context getContext() {
        SoftReference<Context> softReference = mapContext.get("context");
        if (softReference != null) {
            Context context = softReference.get();
            if (context != null) {
                return context;
            }
        }
        return null;
    }

    public static class DialogClickListener {

        /**
         * 第一个按钮监听 PositiveButton
         */
        public void click1(){}

        /**
         * 第二个按钮监听 NegativeButton
         */
        public void click2(){}

        /**
         * 第三个按钮监听 NeutralButton
         */
        public void click3(){}
    }

}
