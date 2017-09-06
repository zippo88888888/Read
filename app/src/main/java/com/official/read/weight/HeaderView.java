package com.official.read.weight;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.official.read.R;
import com.official.read.content.bean.DetailBean;
import com.official.read.ui.WebViewActivity;
import com.official.read.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * com.official.read.view
 * Created by ZP on 2017/8/8.
 * description:
 * version: 1.0
 */

public class HeaderView extends LinearLayout {

    public HeaderView(Context context) {
        super(context);
        init(context);
    }

    public HeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {
        View view = inflate(context, R.layout.foot_layout, this);
        Banner banner = (Banner) view.findViewById(R.id.foot_vp);
        final TextView url = (TextView) view.findViewById(R.id.foot_url);
        url.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", url.getText().toString());
                intent.putExtra("tag", true);
                context.startActivity(intent);
            }
        });

        List<DetailBean.HouseInfoImage> imgs = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            DetailBean.HouseInfoImage b = new DetailBean.HouseInfoImage();
            b.imgs = "http://img.my.csdn.net/5P3t8ColM6143773109169.png?imageView2/2/w/160";
            imgs.add(b);
        }
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(imgs);
        banner.isAutoPlay(true);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.start();
    }

}
