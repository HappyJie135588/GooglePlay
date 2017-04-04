package com.huangjie.googleplay.utils;

import com.huangjie.googleplay.R;

import org.xutils.image.ImageOptions;

/**
 * Created by 黄杰 on 2017/3/22.
 */

public class ImageOptionsHelper {
    private static ImageOptions options;

    static {
        options = new ImageOptions.Builder()
                //设置加载过程中的图片
                .setLoadingDrawableId(R.mipmap.ic_default)
                //设置加载失败后的图片
                .setFailureDrawableId(R.mipmap.ic_default)
                //设置使用缓存
                .setUseMemCache(true)
                //设置显示圆形图片
                .setCircular(false)
                //设置支持gif
                .setIgnoreGif(true)
                .build();
    }

    public static ImageOptions getOptions() {
        return options;
    }

}
