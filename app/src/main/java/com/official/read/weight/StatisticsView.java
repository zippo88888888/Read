package com.official.read.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
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

    // 文字距离X轴的距离
    private final static int TEXT_PADDING_X = 30;

    // 矩形的宽度
    private final static int RECTANGLE_WIDTH = 40;
    // 矩形之间的间隔
    private final static int RECTANGLE_PADDING = 45;

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

    private StatisticsClickListener statisticsClickListener;
    public void setStatisticsClickListener(StatisticsClickListener statisticsClickListener) {
        this.statisticsClickListener = statisticsClickListener;
    }

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
        rectanglePaint.setAntiAlias(true);

        discountPaint = new Paint();
        discountPaint.setColor(discountColor);
        discountPaint.setStyle(Paint.Style.STROKE);
        discountPaint.setStrokeWidth(5f);
        discountPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setStrokeWidth(5f);
        textPaint.setTextSize(25f);
        textPaint.setAntiAlias(true);
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
                Toaster.makeText("因部分厂商定制ROM，导致部分功能将无法正常使用");
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isShowRectangle) {
                checkTouch(event);
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 判断点击的是什么东西（矩形图出来了，矩形图范围也就简单了）
     */
    private void checkTouch(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        int j = 0;
        for (int i = 0; i < data.size(); i++) {
            Statistics item = data.get(i);
            j = j + 1;
            int minX = DEFAULT_DP + RECTANGLE_PADDING * j + RECTANGLE_WIDTH * i;
            int maxX = DEFAULT_DP + RECTANGLE_PADDING * j + RECTANGLE_WIDTH * j;
            int minY = maxStatistics + DEFAULT_DP - item.data;
            int maxY = maxStatistics + DEFAULT_DP;
            boolean isX = x >= minX && x <= maxX;
            boolean isY = y >= minY && y <= maxY;
            if (isX && isY) {
                if (statisticsClickListener != null) {
                    statisticsClickListener.click(i, item);
                }
                break;
            }
        }

    }

    /**
     * 绘制坐标轴
     */
    private void drawO(Canvas canvas) {
        canvas.drawLine(DEFAULT_DP, DEFAULT_DP / 2, DEFAULT_DP, maxStatistics + DEFAULT_DP, textPaint);
        int count = data.size();
        canvas.drawLine(DEFAULT_DP, maxStatistics + DEFAULT_DP,
                DEFAULT_DP + RECTANGLE_PADDING * count + RECTANGLE_WIDTH * count + DEFAULT_DP,
                maxStatistics + DEFAULT_DP, textPaint);

        // 绘制坐标文字 10,20,30 坐标微调
        canvas.drawText("份数", DEFAULT_DP / 2 - 10, DEFAULT_DP / 2 + 20, textPaint);
        canvas.drawText("名称", DEFAULT_DP + RECTANGLE_PADDING * count + RECTANGLE_WIDTH * count + DEFAULT_DP / 2,
                maxStatistics + DEFAULT_DP + 30, textPaint);

    }

    /**
     *
     * 绘制矩形图
     *
     * 计算公式：
     * 详见 R.drawable.diy_statisticcs ,图片里的START_X,START_Y，100就是DEFAULT_DP
     *
     *  left: 距离屏幕X的距离(DEFAULT_DP) + 间隔(RECTANGLE_PADDING) * 当前是第几个矩形图 +
     *      宽度(RECTANGLE_WIDTH) * (当前是第几个矩形图 - 1)
     *
     *  top: 矩形图最大高度(maxStatistics) + 距离屏幕Y的距离(DEFAULT_DP) - 当前矩形图的高度
     *
     *  right：距离屏幕X的距离(DEFAULT_DP) + 间隔(RECTANGLE_PADDING) * 当前是第几个矩形图 +
     *         宽度(RECTANGLE_WIDTH) * 当前是第几个矩形图
     *
     *  bottom：矩形图最大高度(maxStatistics) + 距离屏幕Y的距离(DEFAULT_DP)
     *
     *  api可参考 http://blog.csdn.net/u013064109/article/details/77623615
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
                    DEFAULT_DP + RECTANGLE_PADDING * j + RECTANGLE_WIDTH * i,
                    top,
                    DEFAULT_DP + RECTANGLE_PADDING * j + RECTANGLE_WIDTH * j,
                    maxStatistics + DEFAULT_DP, rectanglePaint);

            // 绘制顶部文字
            canvas.drawText(item.data + "",
                    DEFAULT_DP + RECTANGLE_PADDING * j + RECTANGLE_WIDTH * i,
                    maxStatistics + DEFAULT_DP - item.data, textPaint);

            // 绘制底部文字
            canvas.drawText(item.name,
                    DEFAULT_DP + RECTANGLE_PADDING * j + RECTANGLE_WIDTH * i,
                    maxStatistics + DEFAULT_DP + TEXT_PADDING_X, textPaint);

        }
    }

    /**
     * 绘制折线图 （矩形图出来了，折线图就简单了）
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
                    DEFAULT_DP + RECTANGLE_PADDING * j + RECTANGLE_WIDTH * i,
                    maxStatistics + DEFAULT_DP - item.data, textPaint);

            // 绘制底部文字
            canvas.drawText(item.name,
                    DEFAULT_DP + RECTANGLE_PADDING * j + RECTANGLE_WIDTH * i,
                    maxStatistics + DEFAULT_DP + TEXT_PADDING_X, textPaint);

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

    public static class Statistics {
        public String name; // 名称
        public int data; // 具体数据
        public int percentage; // 百分比

        @Override
        public String toString() {
            return "Statistics{" +
                    "name='" + name + '\'' +
                    ", data=" + data +
                    '}';
        }
    }

    public interface StatisticsClickListener {
        void click(int position, Statistics statistics);
    }


}
