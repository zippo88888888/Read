package com.official.read.weight.dialog;

import android.content.Context;
import android.view.View;


public class SpotsAnimView extends View {

    private int target;

    public SpotsAnimView(Context context) {
        super(context);
    }

    public float getXFactor() {
        return getX() / target;
    }

    public void setXFactor(float xFactor) {
        setX(target * xFactor);
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getTarget() {
        return target;
    }
}
