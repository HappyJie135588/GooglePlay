package com.huangjie.googleplay.bean;

/**
 * Created by 黄杰 on 2017/6/11.
 */

public class DownloadInfo {
    private String downloadUrl;
    private String savePath;
    private int state;            // 下载的状态
    private long progress;        // 当前的进度
    private long size;            // 应用的大小
    private String packageName;    // 当前下载的应用的包名
    private Runnable task;            // 用来记录下载任务

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Runnable getTask() {
        return task;
    }

    public void setTask(Runnable task) {
        this.task = task;
    }
}
