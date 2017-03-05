package com.huangjie.googleplay.manager;
/**
 * Created by 黄杰 on 2017/2/6.
 * 线程池的管理
 */

public class ThreadManager {
    private static ThreadPoolProxy mLongPool;
    /**
     * 获得耗时操作的线程池
     * @return
     */
    public synchronized static ThreadPoolProxy getLongPool() {
        return new ThreadPoolProxy(5,5,0);
    }
}
