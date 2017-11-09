package com.official.read.weight.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

import com.official.read.R;
import com.official.read.content.Content;
import com.official.read.util.AndroidUtil;
import com.official.read.util.SPUtil;
import com.official.read.util.Toaster;


public class LoadingView extends View {

    /** 默认小球的半径 */
    private static final float DEFAULT_LOAD_R = 13F;
    /** 默认小球的padding */
    private static final float DEFAULT_LOAD_PADDING = 20F;
    /** 默认小球的个数 */
    private static final int DEFAULT_LOAD_COUNT = 6;
    /** 默认小球的摆动距离 */
    private static final float DEFAULT_LOAD_SWING = 100F;
    /** 动画持续时间 */
    private static final int DEFAULT_LOAD_ANIM = 1000;
    /** 默认中间小球颜色 */
    private static final int DEFAULT_LOAD_COLOR = getThemeColor();
    /** 默认外层小球颜色 */
    private static final int DEFAULT_LOAD_2_COLOR = DEFAULT_LOAD_COLOR;

    // 小球半径
    private float loadR;
    // 小球个数（最小为3）
    private int loadCount;
    // 小球距顶部，底部padding
    private float padding;

    // 中间小球颜色
    private int loadColor;
    // 外层小球颜色
    private int loadColor2;
    // 外层小球左右摇摆的距离
    private float loadSwingL;
    // 动画时间
    private int animTime;

    int width, height;

    // 开始绘制X，Y
    int startW, startH;

    // 中间小球Paint
    Paint paint;
    // 外层小球Paint
    Paint paint2;

    /** 左右摇摆变化的值 */
    private float leftSwing, rightSwing = 0;
    /** 动画状态 */
    private boolean isLeftAnim, isRightAnim = false;
    /** 动画 */
    private ObjectAnimator leftAnim, rightAnim;

    private boolean isChange = false;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoadingView, defStyleAttr, 0);
        loadR = array.getDimension(R.styleable.LoadingView_loadR, DEFAULT_LOAD_R);
        loadCount = array.getInteger(R.styleable.LoadingView_loadCount, DEFAULT_LOAD_COUNT);
        padding = array.getDimension(R.styleable.LoadingView_loadPadding, DEFAULT_LOAD_PADDING);
        loadColor = array.getColor(R.styleable.LoadingView_loadColor, DEFAULT_LOAD_COLOR);
        loadColor2 = array.getColor(R.styleable.LoadingView_load2Color, DEFAULT_LOAD_2_COLOR);
        loadSwingL = array.getDimension(R.styleable.LoadingView_loadSwingL, DEFAULT_LOAD_SWING);
        animTime = array.getInteger(R.styleable.LoadingView_loadAnimTime, DEFAULT_LOAD_ANIM);
        array.recycle();
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(loadColor);

        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setColor(loadColor2);

        if (loadCount < 3) {
            loadCount = DEFAULT_LOAD_COUNT;
        }

        if (animTime < 100) {
            animTime = DEFAULT_LOAD_ANIM;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpec = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        if (widthSpec == MeasureSpec.EXACTLY) {
            // 如果match_parent或者具体的值，直接赋值
            width = widthSize;
        } else {
            // 如果是wrap_content
            width = AndroidUtil.getDisplay(getContext())[0];
        }

        // 计算出小球所需的宽度
        float l = loadR * 2 * loadCount;
        // 小球开始绘制的X
        startW = (int) ((width - l) / 2);

        if (loadSwingL >= startW) {
            // 摆动距离超过左右两边的距离
            loadSwingL = startW - 20;
        }

        height = (int) (loadR * 2 + padding * 2);
        startH = height;

        //保存测量宽度和测量高度
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制外部圆
        drawOut(canvas);
        // 绘制中间圆
        drawOval(canvas);
    }

    private void drawOval(Canvas canvas) {
        // 把外部圆剥离出去绘制
        for (int i = 1; i < loadCount - 1; i++) {
            float left = startW + i * 2 * loadR;
            float top = padding;
            float right = startW + (i + 1) * loadR * 2;
            float bottom = padding + loadR * 2;
            RectF rectF = new RectF(left, top, right, bottom);
            canvas.drawOval(rectF, paint);
        }
    }

    private void drawOut(Canvas canvas) {
        if (isLeftAnim) {
            // 左边摆动状态
            RectF rectF = new RectF(
                    leftSwing,
                    padding,
                    leftSwing + loadR * 2,
                    padding + loadR * 2);
            canvas.drawOval(rectF, paint2);
        } else {
            // 静止状态
            RectF rectF = new RectF(
                    startW,
                    padding,
                    startW + loadR * 2,
                    padding + loadR * 2);
            canvas.drawOval(rectF, paint2);
        }

        if (isRightAnim) {
            // 右边摆动状态
            RectF rectF = new RectF(
                    rightSwing,
                    padding,
                    loadR * 2 + rightSwing,
                    padding + loadR * 2);
            canvas.drawOval(rectF, paint2);
        } else {
            // 禁止状态
            RectF rectF = new RectF(
                    startW + (loadCount - 1) * loadR * 2,
                    padding,
                    startW + loadCount * loadR * 2,
                    padding + loadR * 2);
            canvas.drawOval(rectF, paint2);
        }
    }

    public void startAnim() {

        if (leftAnim == null) {
            Keyframe keyframe1 = Keyframe.ofFloat(0f, startW);
            Keyframe keyframe4 = Keyframe.ofFloat(0.5f, startW - loadSwingL);
            Keyframe keyframe7 = Keyframe.ofFloat(1f, startW);

            PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("leftSwing",
                    keyframe1, keyframe4, keyframe7);

            leftAnim = ObjectAnimator.ofPropertyValuesHolder(this, holder);
            leftAnim.setDuration(animTime);
            leftAnim.setRepeatCount(0);
            leftAnim.setInterpolator(new LoadInterpolator());
            leftAnim.start();
            isLeftAnim = true;
            isChange = false;
            leftAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    isLeftAnim = false;
                    leftAnim.cancel();
                    leftAnim = null;
                    if (!isChange) {
                        startRightAnim();
                    }
                }
            });
        }
    }

    public void startRightAnim() {
        if (rightAnim == null) {
            float start = startW + (loadCount - 1) * loadR * 2;

            Keyframe keyframe1 = Keyframe.ofFloat(0f, start);
            Keyframe keyframe4 = Keyframe.ofFloat(0.5f, start + loadSwingL);
            Keyframe keyframe7 = Keyframe.ofFloat(1f, start);

            PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("rightSwing",
                    keyframe1, keyframe4, keyframe7);

            rightAnim = ObjectAnimator.ofPropertyValuesHolder(this, holder);
            rightAnim.setDuration(animTime);
            rightAnim.setRepeatCount(0);
            rightAnim.setInterpolator(new LoadInterpolator());
            rightAnim.start();
            isRightAnim = true;
            isChange = false;
            rightAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    isRightAnim = false;
                    rightAnim.cancel();
                    rightAnim = null;
                    if (!isChange) {
                        startAnim();
                    }
                }
            });
        }
    }

    public void endAnim() {
        isChange = true;
        if (leftAnim != null) {
            leftAnim.end();
            leftAnim = null;
        }
        if (rightAnim != null) {
            rightAnim.end();
            rightAnim = null;
        }
    }

    private void setLeftSwing(float w) {
        this.leftSwing = w;
        invalidate();
    }

    private void setRightSwing(float w) {
        this.rightSwing = w;
        invalidate();
    }

    public LoadingView setLoadR(float loadR) {
        this.loadR = loadR;
        return this;
    }

    public LoadingView setLoadCount(int loadCount) {
        this.loadCount = loadCount;
        return this;
    }

    public LoadingView setPadding(float padding) {
        this.padding = padding;
        return this;
    }

    public LoadingView setLoadSwingL(float loadSwingL) {
        this.loadSwingL = loadSwingL;
        return this;
    }

    public LoadingView setAnimTime(int animTime) {
        this.animTime = animTime;
        return this;
    }

    public LoadingView setColorByTheme() {
        this.paint.setColor(getThemeColor());
        this.paint2.setColor(getThemeColor());
        return this;
    }

    /**
     * 属性设置后调用
     */
    public void complete() {
        invalidate();
    }

    /**
     * 自动根据当前主题获取颜色
     */
    private static int getThemeColor() {
        String theme = (String) SPUtil.get(SPUtil.BASE_COLOR, Content.THEME_RED);
        int colorByTheme = Content.getBaseColorByTheme(theme);
        return Toaster.getApplicationContext().getResources().getColor(colorByTheme);
    }

    private static class LoadInterpolator implements Interpolator {

        private static double POW = 1.0 / 2.0;

        @Override
        public float getInterpolation(float input) {
            return input < 0.5
                    ? (float) Math.pow(input * 2, POW) * 0.5f
                    : (float) Math.pow((1 - input) * 2, POW) * - 0.5f + 1;
        }
    }

}
