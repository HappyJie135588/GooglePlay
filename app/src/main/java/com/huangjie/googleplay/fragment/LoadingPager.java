package com.huangjie.googleplay.fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.huangjie.googleplay.R;
import com.huangjie.googleplay.manager.ThreadManager;
import com.huangjie.googleplay.utils.UIUtils;

/**
 * Created by 黄杰 on 2016/12/19.
 */

public abstract class LoadingPager extends FrameLayout implements View.OnClickListener {
    private static final int STATE_NONE = 0;//初始状态
    private static final int STATE_LOADING = 1;//加载中的状态
    private static final int STATE_EMPTY = 2;//空的状态
    private static final int STATE_ERROR = 3;//错误的状态
    private static final int STATE_SUCCESS = 4;//成功的状态

    private View mLoadingView;
    private View mEmptyView;
    private View mErrorView;
    private View mSuccessView;

    private View mRetryView;

    private int mCurrentState = STATE_NONE;//默认是加载中的状态

    public LoadingPager(Context context) {
        super(context);
        initView();
    }

    public LoadingPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        //加载中
        if (mLoadingView == null) {
            mLoadingView = View.inflate(getContext(), R.layout.pager_loading, null);
            //添加到容器中
            addView(mLoadingView);
        }
        //空页面
        if (mEmptyView == null) {
            mEmptyView = View.inflate(getContext(), R.layout.pager_empty, null);
            //添加到容器中
            addView(mEmptyView);
        }
        //错误页面
        if (mErrorView == null) {
            mErrorView = View.inflate(getContext(), R.layout.pager_error, null);
            //添加到容器中
            addView(mErrorView);
            mRetryView = mErrorView.findViewById(R.id.error_btn_retry);
            mRetryView.setOnClickListener(this);
        }
        safeUpdateUI();
    }

    private void safeUpdateUI() {
        UIUtils.post(new Runnable() {
            @Override
            public void run() {
                updateUI();
            }
        });
    }

    private void updateUI() {
        mLoadingView.setVisibility(mCurrentState == STATE_NONE || mCurrentState == STATE_LOADING ? View.VISIBLE : View.GONE);
        mEmptyView.setVisibility(mCurrentState == STATE_EMPTY ? View.VISIBLE : View.GONE);
        mErrorView.setVisibility(mCurrentState == STATE_ERROR ? View.VISIBLE : View.GONE);
        if (mCurrentState == STATE_SUCCESS && mSuccessView == null) {
            mSuccessView = initSuccessView();
            addView(mSuccessView);
        }
        if (mSuccessView != null) {
            mSuccessView.setVisibility(mCurrentState == STATE_SUCCESS ? View.VISIBLE : View.GONE);
        }
    }

    //加载数据
    public void loadData() {
        //如果现在是成功状态就不去加载
        if (mCurrentState != STATE_SUCCESS && mCurrentState != STATE_LOADING) {
            mCurrentState = STATE_LOADING;
            safeUpdateUI();
            //创建的线程
            ThreadManager.getLongPool().excute(new LoadDataTask());
        }
    }

    protected abstract View initSuccessView();

    protected abstract LoadedResult onLoadData();

    @Override
    public void onClick(View v) {
        if (v == mRetryView) {
            loadData();
        }
    }

    class LoadDataTask implements Runnable {

        @Override
        public void run() {
            mCurrentState = onLoadData().getState();
            safeUpdateUI();
        }
    }

    public enum LoadedResult {
        EMPTY(STATE_EMPTY), ERROR(STATE_ERROR), SUCCESS(STATE_SUCCESS);
        private int state;

        private LoadedResult(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }
}
