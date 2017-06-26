package com.huangjie.googleplay.manager;

import com.huangjie.googleplay.bean.AppDetailInfoBean;
import com.huangjie.googleplay.bean.DownloadInfo;
import com.huangjie.googleplay.utils.CommonUtils;
import com.huangjie.googleplay.utils.Constans;
import com.huangjie.googleplay.utils.FileUtils;
import com.huangjie.googleplay.utils.LogUtils;
import com.huangjie.googleplay.utils.UIUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by 黄杰 on 2017/6/11.
 */

public class DownloadManager {
    // 未下载 下载 去下载
    // 下载中 显示进度条 去暂停下载
    // 等待 等待中... 取消下载
    // 暂停 继续下载 去下载
    // 下载成功 安装 去安装
    // 下载失败 重试 去下载
    // 已安装 打开 去启动
    public static final int STATE_UNDOWNLOAD = 0;
    public static final int STATE_DOWNLOADING = 1;
    public static final int STATE_WATITTING = 2;
    public static final int STATE_PAUSE = 3;
    public static final int STATE_DOWNLOADED = 4;// 已经下载但是没有安装
    public static final int STATE_FAILED = 5;
    public static final int STATE_INSTALLED = 6;

    private static DownloadManager instance;
    private ThreadPoolProxy mDownloadPool;//下载的线程池
    //用来记录下载的信息
    private Map<String, DownloadInfo> mDownloadInfos = new HashMap<>();

    private List<DownloadObserver> mObservers = new LinkedList<>();
    private Callback.Cancelable cancelable;

    private DownloadManager() {
        mDownloadPool = ThreadManager.getDownloadPool();
    }

    public static DownloadManager getInstance() {
        if (instance == null) {
            synchronized (DownloadManager.class) {
                if (instance == null) {
                    instance = new DownloadManager();
                }
            }
        }
        return instance;
    }

    public DownloadInfo getDownloadInfo(AppDetailInfoBean data) {

        //获取应用程序是否安装
        if (CommonUtils.isInstalled(UIUtils.getContext(), data.getPackageName())) {
            //安装状态
            DownloadInfo info = generateDownloadInfo(data);
            info.setState(STATE_INSTALLED);
            return info;
        }
        //已经下载了但是没有安装
        File apkFile = getApkFile(data.getPackageName());
        if (apkFile.exists()) {
            if (apkFile.length() == data.getSize()) {
                //我们缓存的目录里面已经有个了这个apk并且大小要一致
                DownloadInfo info = generateDownloadInfo(data);
                info.setState(STATE_DOWNLOADED);
                return info;
            }
        }

        //正在下载
        DownloadInfo info = mDownloadInfos.get(data.getPackageName());
        //还没有下载
        if (info == null) {
            info = generateDownloadInfo(data);
            info.setState(STATE_UNDOWNLOAD);
            return info;
        } else {
            return info;
        }
    }

    //下载应用
    public void download(AppDetailInfoBean data) {
        DownloadInfo info = generateDownloadInfo(data);
        //#################################################
        // 状态的变化:未下载
        //#################################################
        info.setState(STATE_UNDOWNLOAD);
        notifyDownloadStateChanged(info);

        //#################################################
        // 状态的变化:等待状态
        //#################################################
        info.setState(STATE_WATITTING);
        notifyDownloadStateChanged(info);

        //保存记录下载的信息
        mDownloadInfos.put(data.getPackageName(), info);
        DownloadTask task = new DownloadTask(info);
        info.setTask(task);
        //开线程去下载
        mDownloadPool.excute(new DownloadTask(info));//将任务加到任务队列中
    }

    public DownloadInfo generateDownloadInfo(AppDetailInfoBean bean) {
        DownloadInfo info = new DownloadInfo();
        info.setDownloadUrl(bean.getDownloadUrl());
        info.setSavePath(getApkFile(bean.getPackageName()).getAbsolutePath());
        info.setSize(bean.getSize());
        info.setPackageName(bean.getPackageName());
        return info;
    }

    public File getApkFile(String packageName) {
        String dir = FileUtils.getDir("download");
        return new File(dir, packageName + ".apk");
    }


    class DownloadTask implements Runnable {
        private DownloadInfo info;

        public DownloadTask(DownloadInfo info) {
            this.info = info;
        }

        @Override
        public void run() {
            //#################################################
            // 状态的变化:下载中
            //#################################################
            info.setState(STATE_DOWNLOADING);
            notifyDownloadStateChanged(info);
            //实现下载的功能
            File saveFile = new File(info.getSavePath());
            long range = 0;
//            if (saveFile.exists()) {
//                range = saveFile.length();
//            }
            String url = Constans.DownloadUrl;
            RequestParams params = new RequestParams(url);
            params.addQueryStringParameter("name", info.getDownloadUrl());
            params.addQueryStringParameter("range", "" + range);//断点下载
            params.setSaveFilePath(info.getSavePath());

            cancelable = x.http().get(params, new Callback.ProgressCallback<File>() {
                @Override
                public void onSuccess(File result) {
                    LogUtils.d("下载完毕");
                    //#################################################
                    // 状态的变化:下载成功
                    //#################################################
                    info.setState(STATE_DOWNLOADED);
                    notifyDownloadStateChanged(info);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    ex.printStackTrace();
                    //#################################################
                    // 状态的变化:下载失败
                    //#################################################
                    info.setState(STATE_FAILED);
                    notifyDownloadStateChanged(info);
                }

                @Override
                public void onCancelled(CancelledException cex) {
                }

                @Override
                public void onFinished() {
                }

                @Override
                public void onWaiting() {
                }

                @Override
                public void onStarted() {
                }

                @Override
                public void onLoading(long total, long current, boolean isDownloading) {
                    info.setProgress(current);
                    notifyDownloadProgressChanged(info);
                    LogUtils.d("Progress:" + current);
                }
            });
        }
    }

    /**
     * 暂停应用
     *
     * @param mData
     */
    public void pause(AppDetailInfoBean mData) {
        DownloadInfo info = mDownloadInfos.get(mData.getPackageName());
        if (info == null) {
            return;
        }
        //#################################################
        // 状态的变化:暂停
        //#################################################
        info.setState(STATE_PAUSE);
        notifyDownloadStateChanged(info);
        cancelable.cancel();
    }

    /**
     * 取消下载应用
     *
     * @param mData
     */
    public void cancel(AppDetailInfoBean mData) {
        DownloadInfo info = mDownloadInfos.get(mData.getPackageName());
        if (info == null) {
            return;
        }
        if (info.getTask() != null) {
            mDownloadPool.remove(info.getTask());
        }
        info.setState(STATE_UNDOWNLOAD);
        info.setTask(null);
        notifyDownloadStateChanged(info);
    }

    /**
     * 安装应用
     *
     * @param bean
     */
    public void install(AppDetailInfoBean bean) {
        File apkFile = getApkFile(bean.getPackageName());
        if (!apkFile.exists()) {
            return;
        }
        CommonUtils.installApp(UIUtils.getContext(), apkFile);
    }

    /**
     * 打开应用
     *
     * @param bean
     */
    public void open(AppDetailInfoBean bean) {
        CommonUtils.openApp(UIUtils.getContext(), bean.getPackageName());
    }

    /**
     * 通知观察者数据改变
     *
     * @param info
     */
    public void notifyDownloadStateChanged(DownloadInfo info) {
        ListIterator<DownloadObserver> iterator = mObservers.listIterator();
        while (iterator.hasNext()) {
            DownloadObserver observer = iterator.next();
            observer.onDownloadStateChanged(this, info);
        }
    }

    /**
     * 通知观察者数据改变
     *
     * @param info
     */
    public void notifyDownloadProgressChanged(DownloadInfo info) {
        ListIterator<DownloadObserver> iterator = mObservers.listIterator();
        while (iterator.hasNext()) {
            DownloadObserver observer = iterator.next();
            observer.onDownloadProgressChanged(this, info);
        }
    }

    /**
     * 删除观察者
     *
     * @param o
     */
    public synchronized void deleteObserver(DownloadObserver o) {
        mObservers.remove(o);
    }

    /**
     * 添加观察者
     *
     * @param o
     */
    public synchronized void addObserver(DownloadObserver o) {
        if (o == null)
            throw new NullPointerException();
        if (!mObservers.contains(o)) {
            mObservers.add(o);
        }
    }

    //观察者
    public interface DownloadObserver {
        /**
         * 当状态改变时的回调
         *
         * @param manager
         * @param info
         */
        void onDownloadStateChanged(DownloadManager manager, DownloadInfo info);

        /**
         * 当进度改变时的回调
         */
        void onDownloadProgressChanged(DownloadManager manager, DownloadInfo info);
    }
}
