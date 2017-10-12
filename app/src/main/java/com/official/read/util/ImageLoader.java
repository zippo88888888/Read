package com.official.read.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.official.read.R;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * com.official.read.util
 * Created by ZP on 2017/7/13.
 * description:
 * version: 1.0
 */

public class ImageLoader {

    /**
     * 加载图片
     */
    public static void loadImage(ImageView pic, String url) {
        Glide.with(pic.getContext().getApplicationContext())
                .load(url)
                .placeholder(R.drawable.placeholder_pic)
                .error(R.drawable.error)
                .into(pic);
    }

}
