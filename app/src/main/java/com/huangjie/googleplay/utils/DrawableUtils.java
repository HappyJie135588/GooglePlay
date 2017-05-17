package com.huangjie.googleplay.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by 黄杰 on 2017/5/2.
 */

public class DrawableUtils {

    public static GradientDrawable getShape(int shape, int raduis, int argb) {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setCornerRadius(UIUtils.dp2px(4));
        gd.setColor(argb);
        return gd;
    }

    public static StateListDrawable getSelector(Drawable pressedBg, Drawable normalBg) {
        StateListDrawable selector = new StateListDrawable();
        selector.addState(new int[]{android.R.attr.state_pressed}, pressedBg);
        selector.addState(new int[]{}, normalBg);
        return selector;
    }
}
