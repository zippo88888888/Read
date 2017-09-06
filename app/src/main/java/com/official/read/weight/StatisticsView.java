package com.official.read.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.official.read.R;
import com.official.read.content.Content;
import com.official.read.util.Toaster;

import java.util.List;

/**
 * com.official.read.weight
 * Created by ZP on 2017/9/5.
 * description: 我行你也行
 * version: 1.0
 */

public class StatisticsView extends View {

    // 矩形最高的颜色
    private int rectangleMaxColor = getResources().getColor(R.color.red);
    // 矩形普通颜色
    private int rectangleColor = getResources().getColor(R.color.blue);
    // 折线颜色
    private int discountColor = getResources().getColor(R.color.black);
    // 文字颜色
    private int textColor = getResources().getColor(R.color.green);

    // 最开始的点距离屏幕高度，宽度，以及坐标线段开始，末尾的长度
    private final static int DEFAULT_DP = 100;

    // 文字距离Y轴的距离
    private final static int TEXT_PADDING_Y = 30;

    // 矩形的宽度
    private final static int RECTANGLE_WIDTH = 40;
    // 矩形之间的间隔
    private final static int RECTANGLE_PADDING = 40;

    // 折线统计图 是否显示
    private boolean isShowDiscount = false;
    // 矩形统计图 是否显示
    private boolean isShowRectangle = true;

    // 矩形Paint
    private Paint rectanglePaint;
    // 折线Paint
    private Paint discountPaint;
    // 文字Paint
    private Paint textPaint;

    // 统计数据
    private List<Statistics> data;
    // 最大的数据
    private int maxStatistics = 0;

    // 屏幕的宽，高
    private int width;
    private int height;

    public StatisticsView(Context context) {
        super(context);
        initPaint();
    }

    public StatisticsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public StatisticsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        rectanglePaint = new Paint();
        rectanglePaint.setColor(rectangleColor);
        rectanglePaint.setStyle(Paint.Style.FILL);

        discountPaint = new Paint();
        discountPaint.setColor(discountColor);
        discountPaint.setStyle(Paint.Style.STROKE);
        discountPaint.setStrokeWidth(5f);

        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setStrokeWidth(5f);
        textPaint.setTextSize(25f);
    }

    public void setData(List<Statistics> data) {
        if (data != null && data.size() > 0) {
            this.data = data;
            invalidate();
        } else {
            throw new NullPointerException("data is not null!");
        }
    }

    // 设置显示矩形图
    public void setRectangleShow() {
        this.isShowRectangle = true;
        this.isShowDiscount = false;
        invalidate();
    }

    // 设置显示折线图
    public void setDiscountShow() {
        this.isShowRectangle = false;
        this.isShowDiscount = true;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (data == null || data.size() <= 0) {
            return;
        }
        try {
            getMaxDataForList(data);

            // 绘制坐标
            drawO(canvas);

            if (isShowRectangle) {
                // 绘制矩形
                drawRectangle(canvas);
            }
            if (isShowDiscount) {
                // 绘制折线
                drawDiscount(canvas);
            }
        } catch (NoSuchMethodError e) {
            if (!Content.IS_OFFICIAL) {
                e.printStackTrace();
                Toaster.makeText("Android版本过低或因部分厂商定制ROM，导致部分功能将无法正常使用");
            }
        }
    }

    /**
     * 绘制坐标
     */
    private void drawO(Canvas canvas) {
        canvas.drawLine(DEFAULT_DP, DEFAULT_DP, DEFAULT_DP, maxStatistics + DEFAULT_DP, textPaint);
        int count = data.size();
        canvas.drawLine(DEFAULT_DP, maxStatistics + DEFAULT_DP,
                DEFAULT_DP + RECTANGLE_PADDING * count + RECTANGLE_WIDTH * count + DEFAULT_DP,
                maxStatistics + DEFAULT_DP, textPaint);

    }

    /**
     * 绘制矩形图
     */
    private void drawRectangle(Canvas canvas) {
        int j = 0;
        for (int i = 0; i < data.size(); i++) {
            Statistics item = data.get(i);
            j = j + 1;
            if (item.data == maxStatistics) {
                rectanglePaint.setColor(rectangleMaxColor);
            } else {
                rectanglePaint.setColor(rectangleColor);
            }
            int top = maxStatistics + DEFAULT_DP - item.data;
            // 绘制矩形
            canvas.drawRect(
                    DEFAULT_DP + RECTANGLE_PADDING * j + RECTANGLE_WIDTH * (j - 1),
                    top,
                    DEFAULT_DP + RECTANGLE_PADDING * j + RECTANGLE_WIDTH * j,
                    maxStatistics + DEFAULT_DP, rectanglePaint);

            // 绘制顶部文字
            canvas.drawText(item.data + "",
                    DEFAULT_DP + RECTANGLE_PADDING * j + RECTANGLE_WIDTH * (j - 1),
                    maxStatistics + DEFAULT_DP - item.data, textPaint);

            // 绘制底部文字
            canvas.drawText(item.name,
                    DEFAULT_DP + RECTANGLE_PADDING * j + RECTANGLE_WIDTH * (j - 1),
                    maxStatistics + DEFAULT_DP + TEXT_PADDING_Y, textPaint);

        }
    }

    /**
     * 绘制折线图
     */
    private void drawDiscount(Canvas canvas) {
        float lines[] = new float[data.size() * 2];
        int j = 0;
        int k = 0;
        for (int i = 0; i < data.size(); i++) {
            Statistics item = data.get(i);
            j = j + 1;
            // 绘制点
            canvas.drawPoint(DEFAULT_DP + RECTANGLE_PADDING * j + RECTANGLE_WIDTH * j,
                    maxStatistics + DEFAULT_DP - item.data, textPaint);

            // 绘制顶部文字
            canvas.drawText(item.data + "",
                    DEFAULT_DP + RECTANGLE_PADDING * j + RECTANGLE_WIDTH * (j - 1),
                    maxStatistics + DEFAULT_DP - item.data, textPaint);

            // 绘制底部文字
            canvas.drawText(item.name,
                    DEFAULT_DP + RECTANGLE_PADDING * j + RECTANGLE_WIDTH * (j - 1),
                    maxStatistics + DEFAULT_DP + TEXT_PADDING_Y, textPaint);

            lines[k] = DEFAULT_DP + RECTANGLE_PADDING * j + RECTANGLE_WIDTH * j;
            k = k + 1;
            lines[k] = maxStatistics + DEFAULT_DP - item.data;
            k = k + 1;
        }

        // 绘制线
        int _k = 0;
        do {

            //        canvas.drawLine(lines[0], lines[1], lines[2], lines[3], textPaint);
            //        canvas.drawLine(lines[2], lines[3], lines[4], lines[5], textPaint);
            //        canvas.drawLine(lines[4], lines[5], lines[6], lines[7], textPaint);
            //        canvas.drawLine(lines[6], lines[7], lines[8], lines[9], textPaint);

            canvas.drawLine(lines[_k], lines[_k + 1], lines[_k + 2], lines[_k + 3], discountPaint);
            _k = _k + 2;
            if ((_k + 3) >= lines.length) {
                break;
            }
        } while (_k < lines.length);


    }

    /**
     * 根据List<Statistics> 集合计算出最大数据
     * @param data List<Statistics>
     */
    private void getMaxDataForList(List<Statistics> data) {
        for (int i = 0; i < data.size(); i++) {
            maxStatistics = data.get(i).data;
            for (int j = 0; j < data.size(); j++) {
                int realData = data.get(j).data;
                if (realData > maxStatistics) {
                    maxStatistics = realData;
                }
            }
        }
    }

    // 统计图实体类
    public static class Statistics {
        public String name; // 名称
        public int data; // 具体数据

        @Override
        public String toString() {
            return "Statistics{" +
                    "name='" + name + '\'' +
                    ", data=" + data +
                    '}';
        }
    }
}
