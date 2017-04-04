package com.huangjie.googleplay.holder;

import android.view.View;

import com.huangjie.googleplay.R;
import com.huangjie.googleplay.utils.UIUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by 黄杰 on 2017/3/22.
 * 加载更多的Holder
 */

public class LoadMoreHolder extends BaseHolder<Integer> {
    public static final int STATE_LOADING = 0;//正在加载中
    public static final int STATE_ERROR = 1;//加载失败
    public static final int STATE_EMPTY = 2;//没有加载更多的功能

    @ViewInject(R.id.item_loadmore_container_loading)
    private View mloadingView;
    @ViewInject(R.id.item_loadmore_container_retry)
    private View mErrorView;

    @Override
    protected View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_load_more, null);
        x.view().inject(this, view);
        return view;
    }

    @Override
    protected void refreshUI(Integer data) {
        switch (data) {
            case STATE_LOADING:
                //显示加载更多
                mloadingView.setVisibility(View.VISIBLE);
                mErrorView.setVisibility(View.GONE);
                break;
            case STATE_ERROR:
                //显示加载失败
                mloadingView.setVisibility(View.GONE);
                mErrorView.setVisibility(View.VISIBLE);
                break;
            case STATE_EMPTY:
                //不显示
                mloadingView.setVisibility(View.GONE);
                mErrorView.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 获取加载更多的状态
     * @return
     */
    public int getCurrentState(){
        return mData;
    }
}
