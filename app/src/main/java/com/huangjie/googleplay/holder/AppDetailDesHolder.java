package com.huangjie.googleplay.holder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.huangjie.googleplay.R;
import com.huangjie.googleplay.bean.AppDetailInfoBean;
import com.huangjie.googleplay.utils.UIUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by 黄杰 on 2017/6/10.
 */

public class AppDetailDesHolder extends BaseHolder<AppDetailInfoBean> implements View.OnClickListener {
    @ViewInject(R.id.app_detail_des_tv_des)
    private TextView mTvDes;

    @ViewInject(R.id.app_detail_des_tv_author)
    private TextView mTvAuthor;

    @ViewInject(R.id.app_detail_des_iv_arrow)
    private ImageView mIvArrow;

    private boolean isOpened = true;
    private int mDesHeight;
    private int mDesWdith;

    @Override
    protected View initView() {
        final View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_des, null);
        x.view().inject(this, view);
        view.setOnClickListener(this);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mDesHeight = mTvDes.getMeasuredHeight();
                mDesWdith = mTvDes.getMeasuredWidth();
                toggle(false);//先关闭
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        return view;
    }

    @Override
    protected void refreshUI(AppDetailInfoBean data) {
        mTvDes.setText(data.getDes());
        mTvAuthor.setText("作者:" + data.getAuthor());
    }

    @Override
    public void onClick(View v) {
        if (v == getRootView()) {
            toggle(true);
        }
    }

    private void toggle(boolean animated) {
        //全部展开时的高度
        //7行时的高度
        int shortHeight = getShortHeight(mData.getDes());
        if (shortHeight >= mDesHeight) {
            return;
        }
        if (isOpened) {
            //如果是打开的就关闭
            if (animated) {
                //动画关闭
                doAnimation(mDesHeight, shortHeight);
            } else {
                ViewGroup.LayoutParams params = mTvDes.getLayoutParams();
                params.height = shortHeight;
                mTvDes.setLayoutParams(params);
            }
        } else {
            //如果是关闭的就打开
            if (animated) {
                //动画打开
                doAnimation(shortHeight, mDesHeight);
            } else {
                ViewGroup.LayoutParams params = mTvDes.getLayoutParams();
                params.height = mDesHeight;
                mTvDes.setLayoutParams(params);
            }
        }
        //给箭头设置动画
        if (isOpened) {
            if (animated) {
                ObjectAnimator.ofFloat(mIvArrow, "rotation", -180, 0).start();
            } else {
                mIvArrow.setRotation(0);
            }
        } else {
            if (animated) {
                ObjectAnimator.ofFloat(mIvArrow, "rotation", 0, 180).start();
            } else {
                mIvArrow.setRotation(180);
            }
        }
        isOpened = !isOpened;
    }

    private void doAnimation(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = mTvDes.getLayoutParams();
                params.height = value;
                mTvDes.setLayoutParams(params);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ViewParent view = getRootView().getParent();
                while (!(view instanceof ScrollView)) {
                    view = view.getParent();
                }
                ScrollView scrollView = (ScrollView) view;
                //让ScrollView滚动到底部
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
        animator.start();
    }

    private int getShortHeight(String des) {
        TextView tv = new TextView(UIUtils.getContext());
        tv.setText(des);
        tv.setMaxLines(7);//设置7行数据
        tv.measure(View.MeasureSpec.makeMeasureSpec(mDesWdith, View.MeasureSpec.EXACTLY), 0);
        return tv.getMeasuredHeight();

    }
}
