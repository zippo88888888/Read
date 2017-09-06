package com.official.read.content.listener;

import com.official.read.content.Content;
import com.official.read.content.DisposeManager;
import com.official.read.util.L;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * com.official.read.content
 * Created by ZP on 2017/8/11.
 * description:
 * version: 1.0
 */

public abstract class MyObserver<T> implements Observer<T> {

    String dis;

    protected MyObserver(String dis) {
        this.dis = dis;
    }

    @Override
    public final void onSubscribe(Disposable d) {
        subscribe(d);
    }

    @Override
    public final void onNext(T value) {
        next(value);
    }

    @Override
    public final void onError(Throwable e) {
        error(e);
    }

    @Override
    public final void onComplete() {
        complete();
    }

    protected void subscribe(Disposable d) {
        DisposeManager.getInstance().add(dis, d);
    }

    protected abstract void next(T value);

    protected void error(Throwable e) {
        if (!Content.IS_OFFICIAL) {
            e.printStackTrace();
        }
    }

    protected void complete() {
        if (dis != null) {
            dis = null;
        }
    }
}
