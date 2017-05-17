package com.huangjie.googleplay.factory;

import android.support.v4.util.SparseArrayCompat;

import com.huangjie.googleplay.fragment.AppFragment;
import com.huangjie.googleplay.fragment.BaseFragment;
import com.huangjie.googleplay.fragment.CateGoryFragment;
import com.huangjie.googleplay.fragment.GameFragment;
import com.huangjie.googleplay.fragment.HomeFragment;
import com.huangjie.googleplay.fragment.HotFragment;
import com.huangjie.googleplay.fragment.RecommendFragment;
import com.huangjie.googleplay.fragment.SubjectFragment;
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
                //首页
                fragment = new HomeFragment();
                break;
            case 1:
                //应用
                fragment = new AppFragment();
                break;
            case 2:
                //游戏
                fragment = new GameFragment();
                break;
            case 3:
                //专题
                fragment = new SubjectFragment();
                break;
            case 4:
                //推荐
                fragment = new RecommendFragment();
                break;
            case 5:
                //分类
                fragment = new CateGoryFragment();
                break;
            case 6:
                //排行
                fragment = new HotFragment();
                break;
        }
        //缓存起来
        LogUtils.d("为" + position + "的缓存");
        mCaches.put(position, fragment);
        return fragment;
    }
}
