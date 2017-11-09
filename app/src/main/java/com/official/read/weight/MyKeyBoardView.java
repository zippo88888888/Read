package com.official.read.weight;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.inputmethodservice.KeyboardView;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.official.read.R;
import com.official.read.util.AndroidUtil;
import com.official.read.util.Toaster;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义 View  仿支付输入密码框，以及自定义键盘
 */
public class MyKeyBoardView extends View implements KeyBoardUtil.KeyBoardOutPutListener {

    private final static int KEY_BOARD_HIDDEN_ANIM = 0;
    private final static int KEY_BOARD_SHOW_ANIM = 1;

    /** 默认的 距离屏幕左边，右边的间距 */
    private static final int DEFAULT_LEFT_RIGHT_PADDING = AndroidUtil.dip2px(10);
    /** 默认的 距离屏幕顶部，底部的间距 */
    private static final int DEFAULT_TOP_BOTTOM_PADDING = AndroidUtil.dip2px(10);
    /** 默认的 矩形的间距 */
    private static final int DEFAULT_RECT_PADDING = AndroidUtil.dip2px(10);
    /** 默认的 矩形的圆角大小 */
    private static final int DEFAULT_RECT_ROUND = 10;
    /** 默认的 矩形边框 */
    private static final float DEFAULT_RECT_STROKE_WIDTH = 5;
    /** 默认的 文字大小 */
    private static final float DEFAULT_TXT_SIZE = 40;
    /** 默认的 密文大小 */
    private static final float DEFAULT_PWD_SIZE = 20;
    /** 默认的 输入框的长度 */
    private static final int DEFAULT_LENGTH = 6;
    /** 默认的 未输入时矩形的颜色 */
    private static final int DEFAULT_DEFAULT_INPUT_RECT_COLOR = Toaster.getApplicationContext().getResources().getColor(R.color.gray);
    /** 默认的 正在输入或已经输入过的矩形颜色 */
    private static final int DEFAULT_INPUT_RECT_COLOR = Toaster.getApplicationContext().getResources().getColor(R.color.blue);
    /** 默认的 正在输入或已经输入过的文字、密文颜色 */
    private static final int DEFAULT_INPUT_TXT_COLOR = Toaster.getApplicationContext().getResources().getColor(R.color.blue);

    /** 距离屏幕左边，右边的间距 */
    private float leftRightPadding;
    /** 距离屏幕顶部，底部的间距 */
    private float topBottomPadding;
    /** 矩形的间距 */
    private float rectPadding;

    /** 矩形的圆角大小 */
    private int rectRound;
    /** 矩形边框 */
    private float rectStrokeWidth;
    /** 文字大小 */
    private float txtSize;
    /** 密文大小 */
    private float pwdSize;

    /** 密码框的长度 */
    private int length;

    /** 未输入时矩形的颜色——默认颜色 */
    private int defaultInputRectColor;
    /** 正在输入或已经输入过的矩形颜色 */
    private int inputRectColor;
    /** 正在输入或已经输入过的文字、密文颜色 */
    private int inputTxtColor;

    /** 是否显示密文 默认显示（ */
    private boolean isShowPassWord;

    // 矩形Paint
    Paint rectPaint;
    // 文字Paint
    Paint txtPaint;
    // 密文Paint
    Paint pwdPaint;

    // 矩形的宽高 , rectWidth == rectHeight
    private float rectWidth, rectHeight;
    // 屏幕的宽，高
    private int width, height;

    // 当前输入的下标
    private int index = 0;

    KeyBoardUtil keyBoardUtil;
    KeyboardView keyBoardView;

    List<String> texts;

    // 键盘状态
    boolean isShowBoard = false;
    // 当前输入的状态
    boolean isInput = false;
    // 是否查看密文
    boolean isOutChange = false;

    boolean isOpenLock = false;

    private InputTxtListener inputTxtListener;
    public void setInputTxtListener(InputTxtListener inputTxtListener) {
        this.inputTxtListener = inputTxtListener;
    }

    // 是否需要先显示文本，然后再绘制密文
    private boolean isPWD = false;

    public MyKeyBoardView(Context context) {
        this(context, null);
    }

    public MyKeyBoardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyKeyBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyKeyBoardView, defStyleAttr, 0);
        leftRightPadding = array.getDimension(R.styleable.MyKeyBoardView_leftRightPadding, DEFAULT_LEFT_RIGHT_PADDING);
        topBottomPadding = array.getDimension(R.styleable.MyKeyBoardView_topBottomPadding, DEFAULT_TOP_BOTTOM_PADDING);
        rectPadding = array.getDimension(R.styleable.MyKeyBoardView_rectPadding, DEFAULT_RECT_PADDING);
        rectRound = array.getInteger(R.styleable.MyKeyBoardView_rectRound, DEFAULT_RECT_ROUND);
        rectStrokeWidth = array.getFloat(R.styleable.MyKeyBoardView_rectStrokeWidth, DEFAULT_RECT_STROKE_WIDTH);
        txtSize = array.getFloat(R.styleable.MyKeyBoardView_txtSize, DEFAULT_TXT_SIZE);
        pwdSize = array.getFloat(R.styleable.MyKeyBoardView_pwdSize, DEFAULT_PWD_SIZE);
        length = array.getInteger(R.styleable.MyKeyBoardView_length, DEFAULT_LENGTH);
        defaultInputRectColor = array.getColor(R.styleable.MyKeyBoardView_defaultInputRectColor, DEFAULT_DEFAULT_INPUT_RECT_COLOR);
        inputRectColor = array.getColor(R.styleable.MyKeyBoardView_inputRectColor, DEFAULT_INPUT_RECT_COLOR);
        inputTxtColor = array.getColor(R.styleable.MyKeyBoardView_inputTxtColor, DEFAULT_INPUT_TXT_COLOR);
        isShowPassWord = array.getBoolean(R.styleable.MyKeyBoardView_isShowPassWord, false);
        array.recycle();
        init();
    }

    private void init() {
        rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setColor(defaultInputRectColor);
        rectPaint.setStrokeWidth(rectStrokeWidth);

        txtPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        txtPaint.setColor(inputTxtColor);
        txtPaint.setTextSize(txtSize);

        pwdPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pwdPaint.setColor(inputTxtColor);
        pwdPaint.setStyle(Paint.Style.FILL);

        keyBoardUtil = new KeyBoardUtil(getContext());
        keyBoardUtil.setKeyBoardOutPutListener(this);

        texts = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpec = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightSpec = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpec == MeasureSpec.EXACTLY) {
            // 如果match_parent或者具体的值，直接赋值
            width = widthSize;
        } else {
            // 如果是wrap_content 这里直接变成match_parent
            width = AndroidUtil.getDisplay(getContext())[0];
        }

        leftRightPadding = (float) (AndroidUtil.getDisplay(getContext())[0] * 0.2);

        // 确定好控件的宽度后，再确定每一个矩形的宽度
        float hasWidth = width - rectPadding * (length - 1) - leftRightPadding * 2;
        rectWidth = hasWidth / length;
        rectHeight = rectWidth;

        if (heightSpec == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = (int) (rectHeight + topBottomPadding * 2);
        }
        //保存测量宽度和测量高度
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 详见@drawable/diy_pwd.png
        drawRect(canvas);

        if (isInput) {
            drawRectAndTxt(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                showKeyBoard(true);
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 绘制默认的矩形
     */
    private void drawRect(Canvas canvas) {
        rectPaint.setColor(defaultInputRectColor);
        for (int i = 0; i < length; i++) {
            RectF rect = new RectF();
            rect.left = leftRightPadding + i * rectWidth + i * rectPadding;
            rect.top = topBottomPadding;
            rect.right = leftRightPadding + (i + 1) * rectWidth + i * rectPadding;
            rect.bottom = topBottomPadding + rectHeight;
            canvas.drawRoundRect(rect, rectRound, rectRound, rectPaint);
        }
    }

    /**
     * 根据输入的文本 绘制矩形、输入的文本
     */
    private void drawRectAndTxt(Canvas canvas) {
        for (int i = 0; i < index; i++) {
            // 绘制正在输入或已经输入的矩形
            RectF rect = new RectF();
            rect.left = leftRightPadding + i * rectWidth + i * rectPadding;
            rect.top = topBottomPadding;
            rect.right = leftRightPadding + (i + 1) * rectWidth + i * rectPadding;
            rect.bottom = topBottomPadding + rectHeight;

            rectPaint.setColor(inputRectColor);
            canvas.drawRoundRect(rect, rectRound, rectRound, rectPaint);

            // 判断是否显示密文
            if (isShowPassWord) {
                drawPwdTxt(canvas, rect, i);
            } else {
                drawTxt(canvas, rect, i);
            }
        }

        // 是否查看密文
        if (isOutChange) {
            isOutChange = false;
            if (index == length) {
                return;
            }
            checkIsOk();
            return;
        }

        checkIsOk();
    }

    // 绘制密文
    private void drawPwdTxt(Canvas canvas, RectF rect, int i){
        float txtX = rect.left + rectWidth / 2;
        float txtY = rect.top + rectHeight / 2;
        canvas.drawCircle(txtX, txtY, pwdSize, pwdPaint);
    }

    // 绘制纯文本
    private void drawTxt(Canvas canvas, RectF rect, int i) {
        Rect txtRect = new Rect();
        // 获取单个text的宽，高
        txtPaint.getTextBounds(texts.get(i), 0, texts.get(i).length(), txtRect);
        float txtX = rect.left + rectWidth / 2 - txtRect.width() / 2;
        float txtY = rect.top + rectHeight / 2 + txtRect.height() / 2;
        canvas.drawText(texts.get(i), txtX, txtY, txtPaint);
    }

    // 检测是否已经输入完成
    private void checkIsOk() {
        if (index == length) {
            if (inputTxtListener != null) {
                inputTxtListener.end(getText());
//                hiddenKeyBoard(true);
            }
        }
    }

    @Override
    public void outPut(String value) {
        if (KeyBoardUtil.KEY_BOARD_VAL_OK.equals(value)) {
            if (getText().length() <= 0) {
                return;
            }
            // 确定
//            hiddenKeyBoard(true);
            if (inputTxtListener != null) {
                inputTxtListener.end(getText());
            }
        } else if (KeyBoardUtil.KEY_BOARD_VAL_DEL.equals(value)) {
            // 判断下标
            if (index - 1 >= 0) {
                index--;
                // 从数组中删除
                texts.remove(index);
            } else {
                index = 0;
            }
            isInput = true;
            invalidate();
        } else {
            if (getText().length() == length) {
                return;
            }
            // 判断下标
            if (index + 1 > length) {
                index = length;
            } else {
                index ++;
                // 添加到数组
                texts.add(index - 1, value);
            }
            if (inputTxtListener != null) {
                inputTxtListener.input(value);
            }
            isInput = true;
            invalidate();
        }
    }

    public void isOpenLock(boolean isOpenLock) {
        this.isOpenLock = isOpenLock;
    }

    /**
     * 设置输入状态的边框，文字颜色
     */
    public void setInputColor(int color) {
        this.inputRectColor = color;
        this.inputTxtColor = color;
        pwdPaint.setColor(color);
        invalidate();
    }

    /**
     * 关联自定义键盘
     * @param keyBoardView 自定义键盘类
     */
    public void setKeyBoardView(KeyboardView keyBoardView) {
        this.keyBoardView = keyBoardView;
        keyBoardUtil.showKeyBoard(keyBoardView);
    }

    /**
     * 得到输入的文本
     * @return String
     */
    public String getText() {
        StringBuilder sb = new StringBuilder();
        for (String str : texts) {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * 设置是否显示密文
     */
    public void setShowPassWord(boolean showPassWord) {
        this.isShowPassWord = showPassWord;
        if (getText().length() <= 0) {
            return;
        }
        isOutChange = true;
        isInput = true;
        invalidate();
    }

    /**
     * 显示输入法
     * @param isShowAnim 是否需要开启动画
     */
    public void showKeyBoard(boolean isShowAnim) {
        if (!isShowBoard) {
            if (isShowAnim) {
                showAnim(KEY_BOARD_SHOW_ANIM);
            }
            keyBoardView.setVisibility(View.VISIBLE);
            isShowBoard = true;
        }
    }

    /**
     * 隐藏输入法
     */
    public void hiddenKeyBoard(boolean isShowAnim) {
        if (isShowAnim) {
            showAnim(KEY_BOARD_HIDDEN_ANIM);
        }
        isShowBoard = false;
        isInput = false;
    }

    /**
     * 得到当前输入法的状态
     */
    public boolean getKeyBoardState() {
        return isShowBoard;
    }

    public int getLength() {
        return length;
    }


    /**
     * 设置 KeyboardView 弹出的动画
     * ToolBar，状态栏高度，所以得减去这些
     */
    private void showAnim(final int type) {
        // 得到输入法一行的高度
        int boardHeight = (int) getContext().getResources().getDimension(R.dimen.keyBoardHeight);
        int height = AndroidUtil.getDisplay(getContext())[1];
        int needHeight;
        if (isOpenLock) {
            needHeight = height - boardHeight * 5 - AndroidUtil.getStatusBarHeight(getContext());
        } else {
            needHeight = height - boardHeight * 5 - AndroidUtil.getStatusBarHeight(getContext())
                    - AndroidUtil.getToolBarHeight(getContext());
        }
        ObjectAnimator animator;
        if (type == KEY_BOARD_SHOW_ANIM) {
            // 显示
            animator = ObjectAnimator.ofFloat(keyBoardView, "height", height, needHeight);
        } else if (type == KEY_BOARD_HIDDEN_ANIM) {
            // 隐藏
            animator = ObjectAnimator.ofFloat(keyBoardView, "height", needHeight, height);
        } else {
            throw new RuntimeException("type error");
        }
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                keyBoardView.setY(value);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (type != 1) {
                    keyBoardView.setVisibility(View.GONE);
                }
            }
        });
    }

    public interface InputTxtListener {

        /**
         * 正在输入
         * @param str 当前已输入的文本
         */
        void input(String str);

        /**
         * 输入完成
         * @param str 当前已输入的文本
         */
        void end(String str);
    }
}
