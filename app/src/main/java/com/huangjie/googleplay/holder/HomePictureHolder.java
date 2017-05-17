package com.huangjie.googleplay.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.huangjie.googleplay.R;
import com.huangjie.googleplay.utils.Constans;
import com.huangjie.googleplay.utils.ImageOptionsHelper;
import com.huangjie.googleplay.utils.UIUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 黄杰 on 2017/4/29.
 */

public class HomePictureHolder extends BaseHolder<List<String>> {
    @ViewInject(R.id.item_home_picture_pager)
    private ViewPager mPager;
    @ViewInject(R.id.item_home_picture_container_indicator)
    private LinearLayout mPointContainer;

    private List<String> mPictures;

    private AutoSwitchTask mSwitchTask;

    @Override
    protected View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_home_picture, null);
        x.view().inject(this, view);
        return view;
    }

    @Override
    protected void refreshUI(List<String> data) {
        mPictures = data;
        mPager.setAdapter(new HomePictureAdapter());
        mPointContainer.removeAllViews();
        for (int i = 0; i < mPictures.size(); i++) {
            View view = new View(UIUtils.getContext());
            view.setBackgroundResource(R.mipmap.indicator_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIUtils.dp2px(6), UIUtils.dp2px(6));
            if (i != 0) {
                params.leftMargin = UIUtils.dp2px(8);
                params.bottomMargin = UIUtils.dp2px(8);
            } else {
                view.setBackgroundResource(R.mipmap.indicator_selected);//设置默认选中

            }
            mPointContainer.addView(view, params);
        }
        //给ViewPager设置中间选中的值
        int middle = Integer.MAX_VALUE / 2;
        final int extra = middle % mPictures.size();
        mPager.setCurrentItem(middle - extra);
        //开始轮播任务
        if (mSwitchTask == null) {
            mSwitchTask = new AutoSwitchTask();
        }
        mSwitchTask.start();
        //给ViewPager设置touch的监听
        mPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //希望轮播停止
                        mSwitchTask.stop();
                        break;
                    case MotionEvent.ACTION_MOVE:

                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        //希望播放
                        mSwitchTask.start();
                        break;
                }
                return false;
            }
        });

        //设置ViewPager的监听
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position = position % mPictures.size();
                int count = mPointContainer.getChildCount();
                for (int i = 0; i < count; i++) {
                    if (i == position) {
                        mPointContainer.getChildAt(i).setBackgroundResource(R.mipmap.indicator_selected);
                    } else {
                        mPointContainer.getChildAt(i).setBackgroundResource(R.mipmap.indicator_normal);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class AutoSwitchTask implements Runnable {
        //开始轮播
        public void start() {
            stop();
            UIUtils.postDelayed(this, 2000);
        }

        //停止轮播
        public void stop() {
            UIUtils.removeCallBacks(this);
        }

        @Override
        public void run() {
            //让ViewPager选中下一个
            int item = mPager.getCurrentItem();
            mPager.setCurrentItem(++item);
            UIUtils.postDelayed(this, 2000);
        }
    }

    class HomePictureAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % mPictures.size();
            ImageView iv = new ImageView(UIUtils.getContext());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(R.mipmap.ic_default);
            x.image().bind(iv, Constans.ImageUrl + mPictures.get(position), ImageOptionsHelper.getOptions());
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
