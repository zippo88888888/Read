package com.official.read.weight.my_diy;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.official.read.R;
import com.official.read.util.Toaster;

import java.util.List;

/**
 * com.official.read.weight
 * Created by ZP on 2017/9/12.
 * description:
 * version: 1.0
 */

public class SuperStatisticsView extends View {

    private final static int NO_VALUE = -1;
    private int x_padding;
    private int y_padding;

    /** 默认矩形图表最大值的颜色 */
    private final static int DEFAULT_MAX_RECTANGLE_COLOR = Toaster.getContext().getResources().getColor(R.color.red);

    /** 默认矩形图表常规颜色 */
    private final static int DEFAULT_RECTANGLE_COLOR = Toaster.getContext().getResources().getColor(R.color.blue);

    /** 默认字颜色 */
    private final static int DEFAULT_TEXT_COLOR = Toaster.getContext().getResources().getColor(R.color.black);

    /** 默认折线颜色 */
    private final static int DEFAULT_DISCOUNT_COLOR = Toaster.getContext().getResources().getColor(R.color.gray);

    /** 默认X、Y轴的颜色 */
    private final static int DEFAULT_XY_COLOR = Toaster.getContext().getResources().getColor(R.color.gray);

    /** 默认的文字大小 */
    private final static float DEFAULT_TEXT_SIZE = 25f;

    /** 默认的stroke_width大小 */
    private final static float DEFAULT_STROKE_WIDTH = 5f;

    /** 矩形图表最大值的颜色 */
    private int maxRectangleColor;

    /** 矩形图表常规颜色 */
    private int rectangleColor;

    /** 所有的文字说明颜色（偷下懒） */
    private int textColor;

    /** 折线表的折线颜色 */
    private int discountColor;

    /** X,Y轴颜色 */
    private int x_yColor;

    /** 显示矩形图 */
    private boolean showRectangle = true;

    /** 显示折线图 */
    private boolean showDiscount = true;

    /** X轴名称 */
    private String x_name;

    /** Y轴名称 */
    private String y_name;

    /** X Y轴文字大小 */
    private float x_y_textSize;

    /** 文字大小 */
    private float textSize;

    /** 矩形图的宽度（默认根据屏幕大小而定）百分比 */
    private int rectangleWidth;

    /** 矩形图的间距（默认根据屏幕大小而定） */
    private int rectanglePadding;

    /** 最大值 */
    private double maxValue;

    private int width;
    private int height;

    // Y轴的总长
    private int y_width;
    // X轴的总长
    private int x_width;
    // double 类型的 X轴的总长
    private double x_width_d;

    // 文字Paint
    private Paint textPaint;
    // 矩形Paint
    private Paint rectanglePaint;
    // 折线Paint
    private Paint discountPaint;
    // X、Y轴Paint
    private Paint xyPaint;

    private List<StatisticsView.Statistics> data;

    public SuperStatisticsView(Context context) {
        this(context, null);
    }

    public SuperStatisticsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperStatisticsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SuperStatisticsView, defStyleAttr, 0);
        maxRectangleColor = array.getColor(R.styleable.SuperStatisticsView_maxRectangleColor, DEFAULT_MAX_RECTANGLE_COLOR);
        rectangleColor = array.getColor(R.styleable.SuperStatisticsView_rectangleColor, DEFAULT_RECTANGLE_COLOR);
        textColor = array.getColor(R.styleable.SuperStatisticsView_allTextColor, DEFAULT_TEXT_COLOR);
        discountColor = array.getColor(R.styleable.SuperStatisticsView_discountColor, DEFAULT_DISCOUNT_COLOR);
        x_yColor = array.getColor(R.styleable.SuperStatisticsView_x_yColor, DEFAULT_XY_COLOR);
        showRectangle = array.getBoolean(R.styleable.SuperStatisticsView_showRectangle, true);
        showDiscount = array.getBoolean(R.styleable.SuperStatisticsView_showDiscount, false);
        x_name = array.getString(R.styleable.SuperStatisticsView_x_name);
        y_name = array.getString(R.styleable.SuperStatisticsView_y_name);
        x_y_textSize = array.getFloat(R.styleable.SuperStatisticsView_x_y_textSize, DEFAULT_TEXT_SIZE);
        textSize = array.getFloat(R.styleable.SuperStatisticsView_allTextSize, DEFAULT_TEXT_SIZE);
        rectangleWidth = array.getInteger(R.styleable.SuperStatisticsView_rectangleWidth, NO_VALUE);
        rectanglePadding = array.getInteger(R.styleable.SuperStatisticsView_rectanglePadding, NO_VALUE);
        array.recycle();
        initPaint();
    }

    private void initPaint() {
        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
        textPaint.setStrokeWidth(DEFAULT_STROKE_WIDTH);
        textPaint.setTextSize(textSize);

        rectanglePaint = new Paint();
//        rectanglePaint.setColor(rectangleColor);
        rectanglePaint.setStyle(Paint.Style.FILL);
        rectanglePaint.setAntiAlias(true);

        discountPaint = new Paint();
        discountPaint.setColor(discountColor);
        discountPaint.setStyle(Paint.Style.STROKE);
        discountPaint.setStrokeWidth(DEFAULT_STROKE_WIDTH);
        discountPaint.setAntiAlias(true);

        xyPaint = new Paint();
        xyPaint.setColor(x_yColor);
        xyPaint.setAntiAlias(true);
        xyPaint.setStrokeWidth(DEFAULT_STROKE_WIDTH);
        xyPaint.setTextSize(x_y_textSize);
    }

    public void setData(List<StatisticsView.Statistics> data) {
        if (data == null || data.size() <= 0) {
            throw new NullPointerException("data is not null");
        }
        this.data = data;
        invalidate();
    }

    public void setShowRectangle(boolean showRectangle) {
        this.showRectangle = showRectangle;
        invalidate();
    }

    public void setShowDiscount(boolean showDiscount) {
        this.showDiscount = showDiscount;
        invalidate();
    }

    // 检查值
    private void checkValue() {
        if (width <= 0) {
            return;
        }

        if (x_name == null || x_name.length() <= 0 || "".equals(x_name)) {
            x_name = "X";
        }

        if (y_name == null || y_name.length() <= 0 || "".equals(y_name)) {
            y_name = "Y";
        }

        int size = data.size();

        x_padding = width / 10;
        y_padding = height / 10;

        x_width = height - y_padding * 2;
        x_width_d = x_width;
        y_width = width - x_padding * 2;

        if (rectangleWidth == NO_VALUE) {
            rectangleWidth = (y_width - x_padding / 2) / size / 2;
        }
        if (rectanglePadding == NO_VALUE) {
            rectanglePadding = rectangleWidth;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);   //获取宽的模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec); //获取高的模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);   //获取宽的尺寸
        int heightSize = MeasureSpec.getSize(heightMeasureSpec); //获取高的尺寸

        if (widthMode == MeasureSpec.EXACTLY) {
            //如果match_parent或者具体的值，直接赋值
            width = widthSize;
        }
       /** else {
            如果是wrap_content，需要得到控件需要多大的尺寸
            控件的宽度就是文本的宽度加上两边的内边距。内边距就是padding值，在构造方法执行完就被赋值

        }*/
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        }
        //保存测量宽度和测量高度
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (data == null || data.size() <= 0) {
            return;
        }

        checkValue();
        getMaxDataForList(data);
        drawXY(canvas);

        if (showRectangle) {
//            drawRectangle(canvas);
        }
        if (showDiscount) {
            drawDiscount(canvas);
        }
    }

    private void drawXY(Canvas canvas) {
        canvas.drawLine(x_padding, y_padding, x_padding, height - y_padding * 2, xyPaint);

        canvas.drawLine(x_padding, height - y_padding * 2,
                width - x_padding, height - y_padding * 2, xyPaint);

        // 绘制坐标文字
        canvas.drawText(x_name, x_padding, y_padding , textPaint);
        canvas.drawText(y_name, width - x_padding, height - y_padding * 2, textPaint);
    }

    private void drawRectangle(Canvas canvas) {
        int j = 0;
        for (int i = 0; i < data.size(); i++) {
            StatisticsView.Statistics bean = data.get(i);
            j = j + 1;
            if (bean.percentage == maxValue) {
                rectanglePaint.setColor(maxRectangleColor);
            } else {
                rectanglePaint.setColor(rectangleColor);
            }

            int left = x_padding + rectanglePadding * j + rectangleWidth * i;

            int top = (int) (x_width_d - x_width_d * bean.percentage);
            int right = x_padding + rectanglePadding * j + rectangleWidth * j;
            int bottom = height - y_padding * 2;
            canvas.drawRect(left, top, right, bottom, rectanglePaint);
            canvas.drawText(bean.name, left, bottom + textSize, textPaint);
        }
    }

    private void drawDiscount(Canvas canvas) {
        int j = 0;
        for (int i = 0; i < data.size(); i++) {
            StatisticsView.Statistics bean = data.get(i);
            j = j + 1;
            int left = x_padding + rectanglePadding * j + rectangleWidth * i;
            int top = (int) (x_width_d - x_width_d * bean.percentage);
            int right = x_padding + rectanglePadding * j + rectangleWidth * j;
            int bottom = height - y_padding * 2;

            // 绘制点
            canvas.drawPoint(right, top, discountPaint);
        }
    }

    /**
     * 根据List<Statistics> 集合计算出最大数据
     */
    private void getMaxDataForList(List<StatisticsView.Statistics> data) {
        for (int i = 0; i < data.size(); i++) {
            maxValue = data.get(i).percentage;
            for (int j = 0; j < data.size(); j++) {
                double realData = data.get(j).percentage;
                if (realData > maxValue) {
                    maxValue = realData;
                }
            }
        }
    }
}
