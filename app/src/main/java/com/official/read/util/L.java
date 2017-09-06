package com.official.read.util;

import android.util.Log;

import com.official.read.content.Content;


/**
 * com.test.zzs.text.util
 * Created by ZP on 2017/3/31.
 * description:
 * version: 1.0
 */

public class L {

    private static final String TAG = "APP_LOG";

    private L() {
        throw new UnsupportedOperationException("\"L\" class cannot be instantiated");
    }

    public static void d(String TAG, String message) {
        if (!Content.IS_OFFICIAL) {
            Log.d(TAG, message);
        }
    }

    public static void e(String TAG, String message) {
        if (!Content.IS_OFFICIAL) {
            Log.e(TAG, message);
        }
    }

    public static void i(String TAG, String message) {
        if (!Content.IS_OFFICIAL)
            Log.i(TAG, message);
    }


    public static void v(String TAG, String message) {
        if (!Content.IS_OFFICIAL)
            Log.i(TAG, message);
    }

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (!Content.IS_OFFICIAL)
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (!Content.IS_OFFICIAL)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (!Content.IS_OFFICIAL)
            Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (!Content.IS_OFFICIAL)
            Log.v(TAG, msg);
    }

}
