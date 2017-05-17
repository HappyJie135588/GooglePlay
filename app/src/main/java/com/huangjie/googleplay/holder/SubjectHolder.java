package com.huangjie.googleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huangjie.googleplay.R;
import com.huangjie.googleplay.bean.SubjectBean;
import com.huangjie.googleplay.utils.Constans;
import com.huangjie.googleplay.utils.ImageOptionsHelper;
import com.huangjie.googleplay.utils.UIUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by 黄杰 on 2017/4/30.
 */

public class SubjectHolder extends BaseHolder<SubjectBean> {
    @ViewInject(R.id.item_subject_iv_icon)
    private ImageView mIvIcon;
    @ViewInject(R.id.item_subject_iv_title)
    private TextView mTvTitle;

    @Override
    protected View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_subject, null);
        x.view().inject(this, view);
        return view;
    }

    @Override
    protected void refreshUI(SubjectBean data) {
        mTvTitle.setText(data.getDes());
        x.image().bind(mIvIcon, Constans.ImageUrl + data.getUrl(), ImageOptionsHelper.getOptions());
    }
}
