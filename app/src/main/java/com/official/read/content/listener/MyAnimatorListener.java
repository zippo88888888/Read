package com.official.read.content.listener;

import io.codetail.animation.SupportAnimator;

public abstract class MyAnimatorListener implements SupportAnimator.AnimatorListener {

    @Override
    public void onAnimationStart() {
        start();
    }

    @Override
    public void onAnimationEnd() {
        end();
    }

    @Override
    public void onAnimationCancel() {
        cancel();
    }

    @Override
    public void onAnimationRepeat() {
        repeat();
    }

    public abstract void start();
    public abstract void end();

    public void cancel(){}
    public void repeat(){}
}
