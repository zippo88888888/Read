package com.official.read.weight.dialog;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.official.read.R;
import com.official.read.content.Content;
import com.official.read.util.SPUtil;
import com.official.read.util.Toaster;


public class SpotsLayout extends FrameLayout {

    private static final int DEFAULT_COUNT = 6;
    private static final int DEFAULT_SIZE = Toaster.getContext().getResources().getDimensionPixelSize(R.dimen.dialog_spot_size);
    private int spotsCount;
    private int spotsColor;
    private int spotsSize;

    public SpotsLayout(Context context) {
        this(context, null);
    }

    public SpotsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpotsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SpotsLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.SpotsDialog, defStyleAttr, defStyleRes);
        spotsCount = a.getInt(R.styleable.SpotsDialog_DialogSpotCount, DEFAULT_COUNT);
        spotsColor = a.getColor(R.styleable.SpotsDialog_DialogSpotColor, getThemeColor());
        spotsSize = a.getInt(R.styleable.SpotsDialog_DialogSpotSize, DEFAULT_SIZE);
        a.recycle();
    }

    public int getSpotsCount() {
        if (spotsCount >= 5 && spotsCount <= 10) {
            return spotsCount;
        } else {
            return DEFAULT_COUNT;
        }
    }

    public int getSpotsColor() {
        return spotsColor;
    }

    public int getSpotsSize() {
        return spotsSize;
    }

    public void setSpotsCount(int spotsCount) {
        this.spotsCount = spotsCount;
    }

    public void setSpotsColor(int spotsColor) {
        this.spotsColor = spotsColor;
    }

    public void setSpotsSize(int spotsSize) {
        this.spotsSize = spotsSize;
    }

    /**
     * 自动根据当前主题获取颜色
     */
    private int getThemeColor() {
        String theme = (String) SPUtil.get(SPUtil.BASE_COLOR, Content.THEME_RED);
        int colorByTheme = Content.getBaseColorByTheme(theme);
        return getContext().getResources().getColor(colorByTheme);
    }

}
