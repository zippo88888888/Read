package com.official.read.util;

import android.content.Context;
import android.widget.ImageView;

import com.official.read.content.bean.DetailBean;
import com.youth.banner.loader.ImageLoader;

/**
 * com.official.read.util
 * Created by ZP on 2017/8/16.
 * description:
 * version: 1.0
 */

public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object obj, ImageView imageView) {
        String url;
        if (obj instanceof DetailBean.HouseInfoImage) {
            DetailBean.HouseInfoImage bean = (DetailBean.HouseInfoImage) obj;
            url = bean.imgs;
        } else {
            url = obj.toString();
        }
        com.official.read.util.ImageLoader.loadImage(imageView, url);
    }
}
