package com.official.read.weight.dialog;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.official.read.R;
import com.official.read.util.AndroidUtil;

public class SpotsDialog extends AlertDialog {

    private static final int DELAY = 150;
    private static final int DURATION = 1500;

    private SpotsAnimView[] spots;
    private SpotsAnimator animator;

    private int count;
    private CharSequence message;

    private TextView textView;
    private SpotsLayout progress;

    public SpotsDialog(Context context) {
        super(context);
    }

    /**
     * 带小球数量的Dialog
     * 如果xml中也申明，此时以代码为主
     */
    public SpotsDialog(Context context, int count) {
        this(context);
        if (count >= 5 && count <= 10) {
            this.count = count;
        }
    }

    /**
     * 带标题的Dialog
     */
    public SpotsDialog(Context context, CharSequence message) {
        this(context);
        this.message = message;
    }

    /**
     * 带标题，数量的Dialog
     * 如果xml中也申明，此时以代码为主
     */
    public SpotsDialog(Context context, CharSequence message, int count) {
        this(context);
        this.message = message;
        if (count >= 5 && count <= 10) {
            this.count = count;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
    }

    private View getContentView() {
        LinearLayout layout = new LinearLayout(getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(layoutParams);
        layout.setOrientation(LinearLayout.VERTICAL);

        textView = new TextView(getContext());
        LinearLayout.LayoutParams textLP = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textLP.topMargin = getValue(R.dimen.dialog_title_margin);
        textLP.gravity = Gravity.CENTER;
        textView.setLayoutParams(textLP);
        textView.setTextSize(18f);

        textView.setTextColor(getContext().getResources().getColor(R.color.black));

        int w = (int) (AndroidUtil.getDisplay(getContext())[0] * 0.85);
        progress = new SpotsLayout(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                w, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        int margin = getValue(R.dimen.dialog_progress_margin);
        params.leftMargin = params.rightMargin = params.topMargin = params.bottomMargin = margin;
        progress.setLayoutParams(params);

        layout.addView(textView);
        layout.addView(progress);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Android 5.0以下主题兼容
            layout.setBackgroundResource(R.drawable.dialog_shape);
        }

        initMessage();
        initProgress(params.width);

        return layout;
    }

    private void initView() {
        textView = (TextView) findViewById(R.id.dialog_wait_title);
        progress = (SpotsLayout) findViewById(R.id.dialog_wait_progress);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                getValue(R.dimen.dialog_progress_width), LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.leftMargin = params.rightMargin = params.topMargin = params.bottomMargin = getValue(R.dimen.dialog_progress_margin);
        progress.setLayoutParams(params);

        initMessage();
        initProgress(params.width);
    }

    private void initMessage() {
        if (message != null && message.length() > 0) {
            textView.setText(message);
        } else {
            textView.setText(R.string.loading);
        }
    }

    /**
     * 初始化
     * @param progressWidth  ProgressLayout的宽
     */
    private void initProgress(int progressWidth) {
        if (count <= 0) {
            count = progress.getSpotsCount();
        }
        spots = new SpotsAnimView[count];
        int size = progress.getSpotsSize();
        for (int i = 0; i < spots.length; i++) {
            SpotsAnimView v = new SpotsAnimView(getContext());
            v.setBackground(getDrawable());
            v.setTarget(progressWidth);
            v.setXFactor(-1f);
            progress.addView(v, size, size);
            spots[i] = v;
        }
    }

    @Override
    public void setMessage(CharSequence message) {
        textView.setText(message);
    }

    @Override
    protected void onStart() {
        super.onStart();
        animator = new SpotsAnimator(createAnimations());
        animator.play();
    }

    @Override
    protected void onStop() {
        super.onStop();
        animator.stop();
    }

    private Animator[] createAnimations() {
        Animator[] animators = new Animator[count];
        for (int i = 0; i < spots.length; i++) {
            Animator move = ObjectAnimator.ofFloat(spots[i], "xFactor", 0, 1);
            move.setDuration(DURATION);
            move.setInterpolator(new SpotsAnimator.HesitateInterpolator());
            // 设置延时时间
            move.setStartDelay(DELAY * i);
            animators[i] = move;
        }
        return animators;
    }

    private GradientDrawable getDrawable() {
        int strokeWidth = 0; // 边框宽度
        int roundRadius = progress.getSpotsSize(); // 圆角半径
        int strokeColor = Color.TRANSPARENT; // 边框颜色
        int fillColor = progress.getSpotsColor(); // 内部填充颜色

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        return gd;
    }

    private int getValue(int id) {
        return (int) getContext().getResources().getDimension(id);
    }
}
