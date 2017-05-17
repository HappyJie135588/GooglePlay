package com.huangjie.googleplay.holder;


import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.huangjie.googleplay.BaseApplication;
import com.huangjie.googleplay.R;
import com.huangjie.googleplay.bean.AppInfoBean;
import com.huangjie.googleplay.utils.Constans;
import com.huangjie.googleplay.utils.ImageOptionsHelper;
import com.huangjie.googleplay.utils.StringUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by 黄杰 on 2017/2/9.
 * 首页,应用,游戏页面listView对应的item的holder
 */

public class AppItemHolder extends BaseHolder<AppInfoBean> {
    @ViewInject(R.id.item_appinfo_iv_icon)
    private ImageView mIvIcon;
    @ViewInject(R.id.item_appinfo_rb_stars)
    private RatingBar mRbStar;
    @ViewInject(R.id.item_appinfo_tv_des)
    private TextView mTvDes;
    @ViewInject(R.id.item_appinfo_tv_size)
    private TextView mTvSize;
    @ViewInject(R.id.item_appinfo_tv_title)
    private TextView mTvTitle;

    @Override
    protected View initView() {
        View view = View.inflate(BaseApplication.getContext(), R.layout.item_app_info, null);
        x.view().inject(this, view);
        return view;
    }

    @Override
    protected void refreshUI(AppInfoBean data) {
        //给View设置数据
        x.image().bind(mIvIcon, Constans.ImageUrl + data.getIconUrl(), ImageOptionsHelper.getOptions());
        //mIvIcon.setImageResource(R.mipmap.ic_default);
        mRbStar.setRating(data.getStars());
        mTvDes.setText(data.getDes());
        mTvSize.setText(StringUtils.formatFileSize(data.getSize()));
        mTvTitle.setText(data.getName());
    }
}
