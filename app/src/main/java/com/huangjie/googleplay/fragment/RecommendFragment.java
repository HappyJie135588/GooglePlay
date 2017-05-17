package com.huangjie.googleplay.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.huangjie.googleplay.http.RecommendProtocol;
import com.huangjie.googleplay.utils.UIUtils;
import com.huangjie.googleplay.widget.ShakeListener;
import com.huangjie.googleplay.widget.StellarMap;

import java.util.List;
import java.util.Random;

/**
 * Created by 黄杰 on 2017/5/17.
 */

public class RecommendFragment extends BaseFragment {
    private RecommendProtocol mProtocol;
    private List<String> mDatas;
    private StellarMap mRootView;
    private ShakeListener mShake;
    private RecommendAdapter mAdapter;

    @Override
    protected View onLoadSuccessView() {
        mRootView = new StellarMap(UIUtils.getContext());
        //设置样式
        mRootView.setInnerPadding(UIUtils.dp2px(10), UIUtils.dp2px(10), UIUtils.dp2px(10), UIUtils.dp2px(10));
        //设置数据的方法
        mAdapter = new RecommendAdapter();
        mRootView.setAdapter(mAdapter);
        //设置随机摆放区域
        mRootView.setRegularity(15, 20);
        //设置默认选中页
        mRootView.setGroup(0, true);
        //设置摇一摇
        mShake = new ShakeListener(UIUtils.getContext());
        mShake.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                //当摇一摇时候的回调
                int currentGroup = mRootView.getCurrentGroup();
                if (currentGroup == mAdapter.getGroupCount() - 1) {
                    currentGroup = 0;
                } else {
                    currentGroup++;
                }
                mRootView.setGroup(currentGroup, true);

            }
        });

        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mShake != null) {
            mShake.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mShake != null) {
            mShake.pause();
        }
    }

    @Override
    protected LoadingPager.LoadedResult onLoadingData() {
        mProtocol = new RecommendProtocol();
        try {
            mDatas = mProtocol.loadData(0);
            if (mDatas == null || mDatas.size() == 0) {
                return LoadingPager.LoadedResult.EMPTY;
            }
            return LoadingPager.LoadedResult.SUCCESS;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return LoadingPager.LoadedResult.ERROR;
        }
    }

    class RecommendAdapter implements StellarMap.Adapter {
        private final static int PER_PAGE_SIZE = 15;

        //有几个页面
        @Override
        public int getGroupCount() {
            int size = mDatas.size();
            int count = size / PER_PAGE_SIZE;
            if (size % PER_PAGE_SIZE > 0) {
                count++;
            }
            return count;
        }

        //第group页面有几条数据
        @Override
        public int getCount(int group) {
            int size = mDatas.size();
            if (group == getGroupCount() - 1) {
                if (size % PER_PAGE_SIZE > 0) {
                    return size % PER_PAGE_SIZE;
                }
            }
            return PER_PAGE_SIZE;
        }

        @Override
        public View getView(int group, int position, View convertView) {
            //返回随机大小随机颜色的textView
            TextView tv = new TextView(UIUtils.getContext());
            int index = PER_PAGE_SIZE * group + position;
            String data = mDatas.get(index);
            //设置数据
            tv.setText(data);
            //设置颜色
            Random rdm = new Random();
            int alpha = 0xff;
            int red = rdm.nextInt(170) + 30;
            int green = rdm.nextInt(170) + 30;
            int blue = rdm.nextInt(170) + 30;
            int argb = Color.argb(alpha, red, green, blue);
            tv.setTextColor(argb);
            //设置大小
            tv.setTextSize(rdm.nextInt(10) + 14);

            return tv;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            return 0;
        }
    }
}
