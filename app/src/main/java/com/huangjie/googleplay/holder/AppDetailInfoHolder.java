package com.huangjie.googleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.huangjie.googleplay.R;
import com.huangjie.googleplay.bean.AppDetailInfoBean;
import com.huangjie.googleplay.utils.Constans;
import com.huangjie.googleplay.utils.ImageOptionsHelper;
import com.huangjie.googleplay.utils.StringUtils;
import com.huangjie.googleplay.utils.UIUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by 黄杰 on 2017/5/23.
 */

public class AppDetailInfoHolder extends BaseHolder<AppDetailInfoBean> {
    @ViewInject(R.id.app_detail_info_iv_icon)
    private ImageView mIvIcon;

    @ViewInject(R.id.app_detail_info_rb_star)
    private RatingBar mRbStar;

    @ViewInject(R.id.app_detail_info_tv_downloadnum)
    private TextView mTvDownloadnum;

    @ViewInject(R.id.app_detail_info_tv_name)
    private TextView mTvName;

    @ViewInject(R.id.app_detail_info_tv_size)
    private TextView mTvSize;

    @ViewInject(R.id.app_detail_info_tv_time)
    private TextView mTvTime;

    @ViewInject(R.id.app_detail_info_tv_version)
    private TextView mTvVersion;

    @Override
    protected View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_info, null);
        x.view().inject(this, view);
        return view;
    }

    @Override
    protected void refreshUI(AppDetailInfoBean data) {
// 设置数据
        mRbStar.setRating(data.getStars());

        String downloadNum = UIUtils.getString(R.string.app_detail_info_downloadnum, data.getDownloadNum());
        String size = UIUtils.getString(R.string.app_detail_info_size, StringUtils.formatFileSize(data.getSize()));
        String time = UIUtils.getString(R.string.app_detail_info_time, data.getDate());
        String version = UIUtils.getString(R.string.app_detail_info_version, data.getVersion());
        mTvName.setText(data.getName());
        mTvDownloadnum.setText(downloadNum);
        mTvSize.setText(size);
        mTvTime.setText(time);
        mTvVersion.setText(version);

        // 设置icon
        x.image().bind(mIvIcon, Constans.ImageUrl + data.getIconUrl(), ImageOptionsHelper.getOptions());
    }
}
