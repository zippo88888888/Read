package com.official.read.weight.my_diy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.official.read.R;
import com.official.read.content.Content;
import com.official.read.util.Toaster;

import java.util.Timer;
import java.util.TimerTask;

/**
 * com.official.read.weight
 * Created by ZP on 2017/9/4.
 * description:
 * version: 1.0
 */

public class RadarView extends View {

    private final static String RADAR_START = "GO";
    private final static String RADAR_STOP = "OP";

    // 雷达状态
    int radar_state = RADAR_STATE_STOP;
    private final static int RADAR_STATE_START = 1;
    private final static int RADAR_STATE_STOP = 2;

    // 是否开启雷达扫描
    private boolean isDrawRadar = false;

    // 雷达扫描颜色
    private int color;

    private int color1 = getResources().getColor(R.color.red);
    private int color2 = getResources().getColor(R.color.transparent);

    Timer timer;

    // X,Y
    int allX;
    int allY;

    // 中心圆半径
    int centerCircleR = 50;

    private RadarClickListener radarClickListener;

    public void setRadarClickListener(RadarClickListener radarClickListener) {
        this.radarClickListener = radarClickListener;
    }

    public RadarView(Context context) {
        super(context);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {

            allX = getWidth();
            allY = getHeight();

            Paint paint = new Paint();
            paint.setColor(getResources().getColor(R.color.red));
            //去锯齿
            paint.setAntiAlias(true);
            // 填充内部
            paint.setStyle(Paint.Style.FILL);

            canvas.drawCircle(allX / 2, allY / 2, centerCircleR, paint);

            paint.setTextSize(45f);
            paint.setColor(getResources().getColor(R.color.white));

            if (isDrawRadar) {
                drawRadar(canvas);
                canvas.drawText(RADAR_STOP, allX / 2 - 30, allY / 2 + 15, paint);
            } else {
                canvas.drawText(RADAR_START, allX / 2 - 30, allY / 2 + 15, paint);
            }

        } catch (NoSuchMethodError e) {
            if (!Content.IS_OFFICIAL) {
                e.printStackTrace();
                Toaster.makeText("Android版本过低或因部分厂商定制ROM，导致部分功能将无法正常使用");
            }
        }
    }

    private void drawRadar(Canvas canvas) {
        Paint paint = new Paint();
        //去锯齿
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);

        // 雷达是由一个小环渐变成一个大环
        for (int i = 1; i <= 4; i++) {
            canvas.drawCircle(allX / 2, allY / 2, centerCircleR + i * 80, paint);
        }
    }

    private class MyTask extends TimerTask {
        @Override
        public void run() {
            if (color == color1) {
                color = color2;
            } else {
                color = color1;
            }
            postInvalidate();
        }
    }

    // 清除定时器
    public void clear() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    // 供外部调用
    public void stop() {
        // 停止
        isDrawRadar = false;
        radar_state = RADAR_STATE_STOP;
        clear();
        color = color2;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // 获取点击屏幕时的点的坐标
            float x = event.getX();
            float y = event.getY();

            int minX = allX / 2 - centerCircleR;
            int minY = allY / 2 - centerCircleR;

            int maxX = allX / 2 + centerCircleR;
            int maxY = allY /2 + centerCircleR;

            boolean isX = x >= minX && x <= maxX;
            boolean isY = y >= minY && y <= maxY;

            if (isX & isY) {

                if (radar_state == RADAR_STATE_STOP) {
                    // 开启
                    isDrawRadar = true;
                    radar_state = RADAR_STATE_START;

                    timer = new Timer();
                    timer.schedule(new MyTask(), 0, 1000);

                    if (radarClickListener != null) {
                        radarClickListener.start();
                    }

                } else {
                    // 停止
                    isDrawRadar = false;
                    radar_state = RADAR_STATE_STOP;
                    clear();
                    color = color2;
                    invalidate();

                    if (radarClickListener != null) {
                        radarClickListener.stop();
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public interface RadarClickListener {
        void start();
        void stop();
    }
}
