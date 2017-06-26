package com.huangjie.googleplay.holder;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huangjie.googleplay.R;
import com.huangjie.googleplay.bean.AppDetailInfoBean;
import com.huangjie.googleplay.utils.Constans;
import com.huangjie.googleplay.utils.ImageOptionsHelper;
import com.huangjie.googleplay.utils.UIUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by 黄杰 on 2017/6/4.
 */

public class AppDetailSafeHolder extends BaseHolder<List<AppDetailInfoBean.SafeBean>> implements View.OnClickListener {
    @ViewInject(R.id.app_detail_safe_iv_arrow)
    private ImageView mIvArrow;
    @ViewInject(R.id.app_detail_safe_pic_container)
    private LinearLayout mPicContainer;
    @ViewInject(R.id.app_detail_safe_des_container)
    private LinearLayout mDesContainer;

    private boolean isOpened = true;

    @Override
    protected View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_safe, null);
        x.view().inject(this, view);
        view.setOnClickListener(this);
        return view;
    }

    @Override
    protected void refreshUI(List<AppDetailInfoBean.SafeBean> datas) {
        //清空
        mPicContainer.removeAllViews();
        mDesContainer.removeAllViews();

        //去遍历集合动态的加载View
        for (int i = 0; i < datas.size(); i++) {
            AppDetailInfoBean.SafeBean bean = datas.get(i);
            //给图片的容器添加view
            fillPicContainer(bean);
            //给描述的容器添加view
            fillDesContainer(bean);
        }
        toggle(false);
    }

    private void fillPicContainer(AppDetailInfoBean.SafeBean bean) {
        ImageView iv = new ImageView(UIUtils.getContext());
        x.image().bind(iv, Constans.ImageUrl + bean.getSafeUrl(), ImageOptionsHelper.getOptions());
        mPicContainer.addView(iv);
    }

    private void fillDesContainer(AppDetailInfoBean.SafeBean bean) {
        LinearLayout layout = new LinearLayout(UIUtils.getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        layout.setPadding(UIUtils.dp2px(8), UIUtils.dp2px(4), UIUtils.dp2px(8), UIUtils.dp2px(4));
        //需要加载图标
        ImageView iv = new ImageView(UIUtils.getContext());
        x.image().bind(iv, Constans.ImageUrl + bean.getSafeDesUrl(), ImageOptionsHelper.getOptions());
        layout.addView(iv);
        //加载文本
        TextView tv = new TextView(UIUtils.getContext());
        tv.setText(bean.getSafeDes());
        if (bean.getSafeDesColor() == 0) {
            tv.setTextColor(UIUtils.getColor(R.color.app_detail_safe_normal));
        } else {
            tv.setTextColor(UIUtils.getColor(R.color.app_detail_safe_warning));
        }
        layout.addView(tv);
        //将item加入
        mDesContainer.addView(layout);
    }

    @Override
    public void onClick(View v) {
        if (v == getRootView()) {
            toggle(true);
        }
    }

    //打开或是关闭
    private void toggle(boolean animated) {
        mDesContainer.measure(0, 0);
        int height = mDesContainer.getMeasuredHeight();
        if (isOpened) {
            if (animated) {
                doAnimation(height, 0);
            } else {
                ViewGroup.LayoutParams layoutParams = mDesContainer.getLayoutParams();
                layoutParams.height = 0;
                mDesContainer.setLayoutParams(layoutParams);
            }
        } else {
            if (animated) {
                doAnimation(0, height);
            } else {
                ViewGroup.LayoutParams layoutParams = mDesContainer.getLayoutParams();
                layoutParams.height = height;
                mDesContainer.setLayoutParams(layoutParams);
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
                ViewGroup.LayoutParams layoutParams = mDesContainer.getLayoutParams();
                layoutParams.height = value;
                mDesContainer.setLayoutParams(layoutParams);
            }
        });
        animator.start();
    }
}
