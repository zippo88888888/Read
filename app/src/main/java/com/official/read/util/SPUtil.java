package com.official.read.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * com.official.casual.util
 * Created by ZP on 2017/6/22.
 * description:
 * version: 1.0
 */

public class SPUtil {

    /**
     * 当前主题色
     */
    public static final String BASE_COLOR = "baseColor";

    /**
     * 上一次的主题色
     */
    public static final String LAST_COLOR = "lastColor";

    /**
     * 是否已经切换过主题
     */
    public static final String THEME_THAN_THEME = "changeTheme";

    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "read_data";

    /**
     * 是否开启动画
     */
    public static final String OPEN_ANIM = "anim_open";

    /**
     * loading state
     */
    public static final String DIALOG_STATE = "dialog_state";

    /**
     * 是否启动密码锁
     */
    public static final String OPEN_LOCK = "lock_open";

    /**
     * 密码锁数据
     */
    public static final String LOCK_STATE = "lock_state";

    /**
     * 兼容性错误
     */
    public static final String ERROR_STATE = "error_state";

    public static final String EGG = "egg";

    public static final String CANCEL_COLLECT = "cancel_collect";

    /**
     * 保存数据的方法，先拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */
    public static void put(String key, Object object) {

        SharedPreferences sp = Toaster.getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key           键
     * @param defaultObject 默认类型，默认值
     * @return
     */
    public static Object get(String key, Object defaultObject) {
        SharedPreferences sp = Toaster.getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 移除某个key值已经对应的值
     */
    public static void remove(String key) {
        SharedPreferences sp = Toaster.getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public static void clear() {
        SharedPreferences sp = Toaster.getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     */
    public static boolean contains(String key) {
        SharedPreferences sp = Toaster.getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public static Map<String, ?> getAll() {
        SharedPreferences sp = Toaster.getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         */
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (Exception e) {
            }
            editor.commit();
        }
    }

}
