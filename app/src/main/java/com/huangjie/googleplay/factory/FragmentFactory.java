package com.huangjie.googleplay.factory;

import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;

import com.huangjie.googleplay.fragment.BaseFragment;
import com.huangjie.googleplay.fragment.HomeFragment;
import com.huangjie.googleplay.utils.LogUtils;

/**
 * Created by 黄杰 on 2016/12/19.
 */

public class FragmentFactory {

    //private static Map<Integer, Fragment> mCaches = new HashMap<>();
    private static SparseArrayCompat<BaseFragment> mCaches = new SparseArrayCompat<>();

    public static BaseFragment getFragment(int position) {
        //去缓存中取
        BaseFragment fragment = mCaches.get(position);
        if (fragment != null) {
            LogUtils.d("使用" + position + "的缓存");
            return fragment;
        }
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new HomeFragment();
                break;
            case 2:
                fragment = new HomeFragment();
                break;
            case 3:
                fragment = new HomeFragment();
                break;
            case 4:
                fragment = new HomeFragment();
                break;
            case 5:
                fragment = new HomeFragment();
                break;
            case 6:
                fragment = new HomeFragment();
                break;
        }
        //缓存起来
        LogUtils.d("为" + position + "的缓存");
        mCaches.put(position, fragment);
        return fragment;
    }
}
