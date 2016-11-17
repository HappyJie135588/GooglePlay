package com.huangjie.googleplay.utils;

import android.content.Context;
import android.content.res.Resources;

import com.huangjie.googleplay.BaseApplication;

/**
 * Created by 黄杰 on 2016/11/17.
 * 提供UI操作的工具类
 */
public class UIUtils {

    public static Context getContext() {
        return BaseApplication.getContext();
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    public static String getPackageName(){
        return getContext().getPackageName();
    }
}
