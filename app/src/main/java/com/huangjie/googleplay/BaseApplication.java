package com.huangjie.googleplay;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import org.xutils.x;

/**
 * Created by 黄杰 on 2016/11/17.
 */
public class BaseApplication extends Application {

    private static Context mContext;//上下文
    private static Thread mMainThread;
    private static long mMainThreadId;
    private static Looper mMainLooper;
    private static Handler mMainHandler;


    //应用程序的入口
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.

        //上下文
        mContext = getApplicationContext();

        //主线程
        mMainThread = Thread.currentThread();

        //主线程id
        //mMainThreadId = mMainThread.getId();
        mMainThreadId = android.os.Process.myTid();

        mMainLooper = getMainLooper();

        mMainHandler = new Handler();

    }

    public static Context getContext() {
        return mContext;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public static Handler getMainHandler() {
        return mMainHandler;
    }
}
