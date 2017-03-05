package com.huangjie.googleplay.fragment;

import android.view.View;
import android.widget.ListView;

import com.huangjie.googleplay.adapter.HomeAdapter;
import com.huangjie.googleplay.fragment.LoadingPager.LoadedResult;
import com.huangjie.googleplay.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 黄杰 on 2016/12/19.
 */
public class HomeFragment extends BaseFragment {
    private List<String> mDatas;//假数据,数据模拟

    @Override
    protected View onLoadSuccessView() {
        ListView listView = new ListView(UIUtils.getContext());
        //属性设置
//        listView.setCacheColorHint(Color.TRANSPARENT);
//        listView.setSelector(android.R.color.transparent);
//        listView.setDividerHeight(0);
//        listView.setScrollingCacheEnabled(false);
        //设置数据
        listView.setAdapter(new HomeAdapter(mDatas));
        return listView;
    }

    @Override
    protected LoadedResult onLoadingData() {
        //##1.随机数的模拟
//        LoadedResult result[] = new LoadedResult[]{LoadedResult.EMPTY, LoadedResult.ERROR, LoadedResult.SUCCESS};
//        Random rdm = new Random();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return result[rdm.nextInt(result.length)];
        //##2.模拟假数据
        mDatas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mDatas.add(i + "");
        }
        return LoadedResult.SUCCESS;
    }

}
