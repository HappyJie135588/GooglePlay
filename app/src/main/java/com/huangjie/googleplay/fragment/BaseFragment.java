package com.huangjie.googleplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.huangjie.googleplay.fragment.LoadingPager.LoadedResult;
import com.huangjie.googleplay.utils.UIUtils;

/**
 * Created by 黄杰 on 2016/12/19.
 */

public abstract class BaseFragment extends Fragment {

    private LoadingPager mLoadingPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mLoadingPager == null) {
            mLoadingPager = new LoadingPager(UIUtils.getContext()) {
                @Override
                protected View initSuccessView() {
                    return onLoadSuccessView();
                }

                @Override
                protected LoadedResult onLoadData() {
                    return onLoadingData();
                }
            };
        }
        return mLoadingPager;
    }

    public void loadData() {
        if (mLoadingPager != null) {
            mLoadingPager.loadData();
        }
    }

    protected abstract View onLoadSuccessView();

    protected abstract LoadedResult onLoadingData();
}
