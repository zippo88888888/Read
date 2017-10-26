package com.official.read.weight.dialog;

import android.animation.*;
import android.view.animation.Interpolator;

public class DialogAnimator extends AnimatorListenerAdapter {

    private boolean interrupted = false;
    private Animator[] animators;

    public DialogAnimator(Animator[] animators) {
        this.animators = animators;
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        if (!interrupted) animate();
    }

    public void play() {
        animate();
    }

    public void stop() {
        interrupted = true;
    }

    private void animate() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animators);
        set.addListener(this);
        set.start();
    }

    public static class HesitateInterpolator implements Interpolator {

        private static double POW = 1.0/2.0;

        @Override
        public float getInterpolation(float input) {
            return input < 0.5
                    ? (float) Math.pow(input * 2, POW) * 0.5f
                    : (float) Math.pow((1 - input) * 2, POW) * -0.5f + 1;
        }
    }
}
