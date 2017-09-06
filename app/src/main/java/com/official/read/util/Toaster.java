package com.official.read.util;

import android.content.Context;
import android.widget.Toast;

/**
 * com.test.zzs.text.util
 * Created by ZP on 2017/3/31.
 * description:
 * version: 1.0
 */

public class Toaster extends Toast {

    static Context mContext;
    static Toast mToast;

    private Toaster(Context context) {
        super(context);
    }

    public static void init(Context context) {
        mContext = context;
    }

    public static void makeText(String str) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(mContext.getApplicationContext(), str, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static void makeText(int str) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(mContext.getApplicationContext(), str, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static Context getContext() {
        return mContext;
    }
}
