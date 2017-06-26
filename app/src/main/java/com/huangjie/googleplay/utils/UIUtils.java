package com.huangjie.googleplay.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.util.DisplayMetrics;

import com.huangjie.googleplay.BaseApplication;
import com.huangjie.googleplay.holder.HomePictureHolder;

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

    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    public static String getPackageName() {
        return getContext().getPackageName();
    }

    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    public static Handler getMainHandler() {
        return BaseApplication.getMainHandler();
    }

    public static long getMainThreadId() {
        return BaseApplication.getMainThreadId();
    }

    /**
     * 让task在主线程中执行
     */
    public static void post(Runnable task) {
        //当前线程的id
        int myTid = android.os.Process.myTid();
        if (myTid == getMainThreadId()) {
            //在主线程中执行
            task.run();
        } else {
            //在子线程中执行
            getMainHandler().post(task);
        }

    }

    /**
     * dp转px
     *
     * @param dp
     * @return
     */
    public static int dp2px(int dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float density = metrics.density;
        return (int) (dp * density + 0.5f);
    }

    /**
     * px转dp
     *
     * @param px
     * @return
     */
    public static int dx2dp(int px) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float density = metrics.density;
        return (int) (px / density + 0.5f);
    }

    /**
     * 执行延时任务
     *
     * @param task
     * @param delayed
     */
    public static void postDelayed(Runnable task, int delayed) {
        getMainHandler().postDelayed(task, delayed);
    }

    /**
     * 移除任务
     *
     * @param task
     */
    public static void removeCallBacks(Runnable task) {
        getMainHandler().removeCallbacks(task);
    }

    public static String getString(int id, Object... formatArgs) {
        return getResources().getString(id, formatArgs);
    }
}
