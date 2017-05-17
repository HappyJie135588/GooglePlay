package com.huangjie.googleplay.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.Shape;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.huangjie.googleplay.R;
import com.huangjie.googleplay.http.HotProtocol;
import com.huangjie.googleplay.utils.DrawableUtils;
import com.huangjie.googleplay.utils.UIUtils;
import com.huangjie.googleplay.widget.FlowLayout;

import java.util.List;
import java.util.Random;

/**
 * Created by 黄杰 on 2017/4/30.
 */

public class HotFragment extends BaseFragment {
    private HotProtocol mProtocol;
    private List<String> mDatas;

    @Override
    protected View onLoadSuccessView() {
        //UI展示
        ScrollView rootView = new ScrollView(UIUtils.getContext());
        //加载流式布局
        FlowLayout layout = new FlowLayout(UIUtils.getContext());
        layout.setSpace(UIUtils.dp2px(4), UIUtils.dp2px(4));
        layout.setPadding(UIUtils.dp2px(4), UIUtils.dp2px(4), UIUtils.dp2px(4), UIUtils.dp2px(4));
        rootView.addView(layout);
        //给流式加载数据
        for (int i = 0; i < mDatas.size(); i++) {
            final String data = mDatas.get(i);
            TextView tv = new TextView(UIUtils.getContext());
            tv.setText(data);
            tv.setTextSize(UIUtils.dp2px(16));
            tv.setTextColor(Color.WHITE);
            tv.setGravity(Gravity.CENTER);
            int padding = UIUtils.dp2px(4);
            tv.setPadding(padding, padding, padding, padding);

            Random random = new Random();
            int alpha = 0xff;
            int red = random.nextInt(170) + 30;//0-255 30->200
            int green = random.nextInt(170) + 30;
            int blue = random.nextInt(170) + 30;
            int argb = Color.argb(alpha, red, green, blue);
            int shape = GradientDrawable.RECTANGLE;
            int raduis = UIUtils.dp2px(4);
            //获得默认时的样式的drawable
            GradientDrawable normalBg = DrawableUtils.getShape(shape, raduis, argb);
            GradientDrawable pressedBg = DrawableUtils.getShape(shape, raduis, Color.GRAY);
            StateListDrawable selector = DrawableUtils.getSelector(pressedBg, normalBg);
            tv.setBackgroundDrawable(selector);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(), data, Toast.LENGTH_SHORT).show();
                }
            });
            layout.addView(tv);
        }
        return rootView;
    }

    @Override
    protected LoadingPager.LoadedResult onLoadingData() {
        mProtocol = new HotProtocol();
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
}
