package com.huangjie.googleplay.fragment;

import android.view.View;
import android.widget.TextView;

import com.huangjie.googleplay.fragment.LoadingPager.LoadedResult;
import com.huangjie.googleplay.utils.UIUtils;

import java.util.Random;

/**
 * Created by 黄杰 on 2016/12/19.
 */
public class HomeFragment extends BaseFragment {
    @Override
    protected View onLoadSuccessView() {
        TextView tv = new TextView(UIUtils.getContext());
        tv.setText("首页");
        return tv;
    }

    @Override
    protected LoadedResult onLoadingData() {

        LoadedResult result[] = new LoadedResult[]{LoadedResult.EMPTY,LoadedResult.ERROR,LoadedResult.SUCCESS};
        Random rdm = new Random();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result[rdm.nextInt(result.length)];
    }
}
