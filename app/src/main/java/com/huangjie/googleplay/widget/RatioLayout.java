package com.huangjie.googleplay.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.huangjie.googleplay.R;

/**
 * Created by 黄杰 on 2017/4/30.
 */

public class RatioLayout extends FrameLayout {
    public static final int RELEATIVE_WIDTH = 0;
    public static final int RELEATIVE_HEIGHT = 1;
    private float mRatio;
    private int mReleative = RELEATIVE_WIDTH;//相对宽或是高来计算

    public RatioLayout(Context context) {
        this(context, null);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        mRatio = ta.getFloat(R.styleable.RatioLayout_ratio, 0);
        mReleative = ta.getInt(R.styleable.RatioLayout_relative, RELEATIVE_WIDTH);
        ta.recycle();
    }

    public void setRatio(float ratio) {
        this.mRatio = ratio;
    }

    public void setReleative(int releative) {
        this.mReleative = releative;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY && mRatio != 0 && mReleative == RELEATIVE_WIDTH) {
            //通过宽高比和宽度值来确定高度
            int width = widthSize - getPaddingLeft() - getPaddingRight();
            int height = (int) (width / mRatio + 0.5f);
            //给孩子设置宽高
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);
            //给自己设置宽度和高度
            setMeasuredDimension(widthSize, height + getPaddingTop() + getPaddingBottom());
        } else if (heightMode == MeasureSpec.EXACTLY && mRatio != 0 && mReleative == RELEATIVE_HEIGHT) {
            //通过高度计算宽度的值
            //已知高度,宽高比,计算宽度的值
            int height = heightSize - getPaddingTop() - getPaddingBottom();
            int width = (int) (height * mRatio + 0.5f);
            //给孩子设置宽高
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);
            //给自己设置宽高
            setMeasuredDimension(width + getPaddingLeft() + getPaddingRight(), heightSize);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}