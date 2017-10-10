package com.official.read.weight.my_diy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.official.read.R;
import com.official.read.content.Content;
import com.official.read.util.L;
import com.official.read.util.Toaster;

/**
 * com.official.read.weight
 * Created by ZP on 2017/8/31.
 * description
 * version: 1.0
 */

public class OneView extends View {

    public OneView(Context context) {
        super(context);
    }

    public OneView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public OneView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        L.e("width--->>>" + width);

        try {
            Paint paint = new Paint();
            // 颜色
            paint.setColor(getResources().getColor(R.color.blue));
            //去锯齿
            paint.setAntiAlias(true);
            // 填充内部
            paint.setStyle(Paint.Style.FILL);
//          paint.setStrokeWidth(10f);
            //
            canvas.drawCircle(120, 120, 100, paint);

            canvas.drawRect(20, 240, 800, 400, paint);

            canvas.drawRoundRect(20, 440, 1000, 600, 170, 170, paint);

            canvas.drawLine(20, 620, 1000, 820, paint);

            Path path = new Path();
            paint.setColor(getResources().getColor(R.color.red));
            path.arcTo(200, 200, 400, 400, -225, 225, true);
            path.arcTo(400, 200, 600, 400, -180, 225, false);
            path.lineTo(400, 542);
            canvas.drawPath(path, paint);

            paint.setColor(getResources().getColor(R.color.pink));
            canvas.drawArc(400, 200, 800, 500, -10, 100, true, paint);

            paint.setTextSize(39f);
            canvas.drawText("哈哈哈", 20, 60, paint);

        } catch (NoSuchMethodError e) {
            if (!Content.IS_OFFICIAL) {
                e.printStackTrace();
                Toaster.makeText("Android版本过低或因部分厂商定制ROM，导致部分功能将无法正常使用");
            }
        }
    }
}
