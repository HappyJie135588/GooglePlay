package com.huangjie.googleplay.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;

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
}
