package com.huangjie.googleplay.holder;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import com.huangjie.googleplay.R;
import com.huangjie.googleplay.bean.AppDetailInfoBean;
import com.huangjie.googleplay.bean.DownloadInfo;
import com.huangjie.googleplay.manager.DownloadManager;
import com.huangjie.googleplay.manager.DownloadManager.DownloadObserver;
import com.huangjie.googleplay.utils.UIUtils;
import com.huangjie.googleplay.widget.ProgressButton;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by 黄杰 on 2017/6/11.
 */

public class AppDetailBottomHolder extends BaseHolder<AppDetailInfoBean> implements View.OnClickListener, DownloadObserver {
    @ViewInject(R.id.app_detail_download_btn_download)
    private ProgressButton mProgressButton;
    private DownloadInfo mInfo;

    @Override
    protected View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_bottom, null);
        x.view().inject(this, view);
        mProgressButton.setOnClickListener(this);
        //设置进度条样式
        mProgressButton.setProgressDrawable(new ColorDrawable(Color.BLUE));
        return view;
    }

    @Override
    protected void refreshUI(AppDetailInfoBean data) {
        mInfo = DownloadManager.getInstance().getDownloadInfo(data);
        //根据下载信息的状态来给用户提示
        safeRefreshState();
    }

    private void safeRefreshState() {
        UIUtils.post(new Runnable() {
            @Override
            public void run() {
                refreshState();
            }
        });
    }

    public void startObserver() {
        DownloadManager.getInstance().addObserver(this);
        //主动的获取一下状态,更新UI
        mInfo = DownloadManager.getInstance().getDownloadInfo(mData);
        //根据下载信息的状态来给用户提示
        safeRefreshState();
    }

    public void stopObserver() {
        DownloadManager.getInstance().deleteObserver(this);
    }

    private void refreshState() {
        int state = mInfo.getState();
        mProgressButton.setBackgroundResource(R.drawable.progress_loading_normal);
        switch (state) {
            case DownloadManager.STATE_UNDOWNLOAD:
                // 未下载 下载 去下载
                mProgressButton.setText("下载");
                break;
            case DownloadManager.STATE_DOWNLOADING:
                // 下载中 显示进度条 去暂停下载
                mProgressButton.setProgressEnable(true);
                mProgressButton.setMax((int) mInfo.getSize());
                mProgressButton.setProgress((int) mInfo.getProgress());
                int progress = (int) (mInfo.getProgress() * 100 / mInfo.getSize() + 0.5f);
                mProgressButton.setText(progress + "%");
                //修改进度button的背景
                mProgressButton.setBackgroundResource(R.drawable.progress_loading_bg);
                break;
            case DownloadManager.STATE_WATITTING:
                // 等待 等待中... 取消下载
                mProgressButton.setText("等待中...");
                break;
            case DownloadManager.STATE_PAUSE:
                // 暂停 继续下载 去下载
                mProgressButton.setText("继续下载");
                break;
            case DownloadManager.STATE_DOWNLOADED:
                // 下载成功 安装 去安装
                mProgressButton.setText("安装");
                break;
            case DownloadManager.STATE_FAILED:
                // 下载失败 重试 去下载
                mProgressButton.setText("重试");
                break;
            case DownloadManager.STATE_INSTALLED:
                // 已安装 打开 去启动
                mProgressButton.setText("打开");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mProgressButton) {
            clickProgressButton();
        }
    }

    //点击的操作
    private void clickProgressButton() {
        //行为逻辑操作
        int state = mInfo.getState();
        switch (state) {
            case DownloadManager.STATE_UNDOWNLOAD:
                // 未下载 下载 去下载
                doDownload();
                break;
            case DownloadManager.STATE_DOWNLOADING:
                // 下载中 显示进度条 去暂停下载
                doPause();
                break;
            case DownloadManager.STATE_WATITTING:
                // 等待 等待中... 取消下载
                doCancel();
                break;
            case DownloadManager.STATE_PAUSE:
                // 暂停 继续下载 去下载
                doDownload();
                break;
            case DownloadManager.STATE_DOWNLOADED:
                // 下载成功 安装 去安装
                doInstall();
                break;
            case DownloadManager.STATE_FAILED:
                // 下载失败 重试 去下载
                doDownload();
                break;
            case DownloadManager.STATE_INSTALLED:
                // 已安装 打开 去启动
                doOpen();
                break;
        }
    }

    //下载
    private void doDownload() {
        DownloadManager.getInstance().download(mData);
    }

    //暂停
    private void doPause() {
        DownloadManager.getInstance().pause(mData);

    }

    //取消
    private void doCancel() {
        DownloadManager.getInstance().cancel(mData);

    }

    //安装
    private void doInstall() {
        DownloadManager.getInstance().install(mData);
    }

    //启动
    private void doOpen() {
        DownloadManager.getInstance().open(mData);
    }

    @Override
    public void onDownloadStateChanged(DownloadManager manager, DownloadInfo info) {
        if (mData.getPackageName().equals(info.getPackageName())) {
            this.mInfo = info;
            safeRefreshState();
        }
    }

    @Override
    public void onDownloadProgressChanged(DownloadManager manager, DownloadInfo info) {
        if (mData.getPackageName().equals(info.getPackageName())) {
            this.mInfo = info;
            safeRefreshState();
        }
    }
}
