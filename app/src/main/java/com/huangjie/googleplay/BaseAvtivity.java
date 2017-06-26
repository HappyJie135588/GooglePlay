package com.huangjie.googleplay;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by 黄杰 on 2017/6/10.
 */

public abstract class BaseAvtivity extends AppCompatActivity {
    private static List<Activity> mActivities = new LinkedList<>();
    private static Activity mCurrentActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        synchronized (mActivities) {
            mActivities.add(this);
        }
        //初始化view
        initView();
        //初始化actionBar
        initToolbar();
        //初始化数据
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCurrentActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentActivity = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivities) {
            mActivities.remove(this);
        }
    }

    public static void exitApp() {
        ListIterator<Activity> iterator = mActivities.listIterator();
        while (iterator.hasNext()) {
            Activity next = iterator.next();
            next.finish();
        }
    }

    public static Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    protected abstract void initView();

    protected abstract void initToolbar();

    protected abstract void initData();
}
