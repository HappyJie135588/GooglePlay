package com.huangjie.googleplay.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by 黄杰 on 2017/6/11.
 */

public class ProgressButton extends Button {
    private int mProgress;
    private int mMax;
    private Drawable mProgressDrawable;
    private boolean mProgressEnable;

    public ProgressButton(Context context) {
        super(context);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        invalidate();
    }

    public void setMax(int max) {
        this.mMax = max;
    }

    public void setProgressDrawable(Drawable drawable) {
        this.mProgressDrawable = drawable;
    }

    public void setProgressEnable(boolean enable) {
        this.mProgressEnable = enable;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //先画进度
        if (mProgressEnable) {
            int width = getMeasuredWidth();
            int right = 0;
            if (mMax == 0) {
                right = (int) (width * mProgress / 100f + 0.5f);
            } else {
                right = (int) (width * mProgress * 1f/ mMax  + 0.5f);
            }
            mProgressDrawable.setBounds(0, 0,right,getMeasuredHeight());
            mProgressDrawable.draw(canvas);
        }
        //再画文本
        super.onDraw(canvas);//画文本
    }
}
