package com.official.read.content;

import android.util.ArrayMap;

import java.util.Set;

import io.reactivex.disposables.Disposable;

/**
 * com.official.read.content
 * Created by ZP on 2017/7/12.
 * description:
 * version: 1.0
 */

public class DisposeManager {

    private static DisposeManager manager;

    private ArrayMap<Object, Disposable> map;

    private DisposeManager() {
        map = new ArrayMap<>();
    }

    public static DisposeManager getInstance() {
        if (manager == null) {
            synchronized (DisposeManager.class) {
                if (manager == null) {
                    manager = new DisposeManager();
                }
            }
        }
        return manager;
    }

    public void add(Object key, Disposable disposable) {
        if (map.isEmpty()) {
            return;
        }
        map.put(key, disposable);
    }

    public void clear() {
        if (map.isEmpty()) {
            return;
        }
        map.clear();
    }

    public void cancel(Object key) {
        if (map.isEmpty()) {
            return;
        }
        if (map.get(key) == null) {
            return;
        }
        if (!map.get(key).isDisposed()) {
            map.get(key).dispose();
            map.remove(key);
        }
    }

    public void cancelAll() {
        if (map.isEmpty()) {
            return;
        }
        Set<Object> keys = map.keySet();
        for (Object apiKey : keys) {
            cancel(apiKey);
        }
    }

}
