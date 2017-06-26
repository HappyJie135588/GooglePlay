package com.huangjie.googleplay.holder;

import android.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.huangjie.googleplay.R;
import com.huangjie.googleplay.utils.Constans;
import com.huangjie.googleplay.utils.ImageOptionsHelper;
import com.huangjie.googleplay.utils.UIUtils;
import com.huangjie.googleplay.widget.RatioLayout;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by 黄杰 on 2017/6/10.
 */

public class AppDetailPicHolder extends BaseHolder<List<String>> {
    @ViewInject(R.id.app_detail_pic_iv_container)
    private LinearLayout mContainer;

    @Override
    protected View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_pic, null);
        x.view().inject(this, view);
        return view;
    }

    @Override
    protected void refreshUI(List<String> data) {
        for (int i = 0; i < data.size(); i++) {
            RatioLayout layout = new RatioLayout(UIUtils.getContext());
            layout.setReleative(RatioLayout.RELEATIVE_WIDTH);
            layout.setRatio(0.6f);
            ImageView iv = new ImageView(UIUtils.getContext());
            x.image().bind(iv, Constans.ImageUrl + data.get(i), ImageOptionsHelper.getOptions());
            layout.addView(iv);
            LayoutParams params = new LayoutParams(UIUtils.dp2px(320), LayoutParams.WRAP_CONTENT);
            if (i != 0) {
                params.leftMargin = UIUtils.dp2px(8);
            }
            mContainer.addView(layout, params);
        }
    }
}
