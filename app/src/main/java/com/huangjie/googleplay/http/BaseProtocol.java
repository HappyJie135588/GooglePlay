package com.huangjie.googleplay.http;

import com.huangjie.googleplay.utils.Constans;
import com.huangjie.googleplay.utils.FileUtils;
import com.huangjie.googleplay.utils.IOUtils;
import com.huangjie.googleplay.utils.LogUtils;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by 黄杰 on 2017/4/10.
 */

public abstract class BaseProtocol<T> {
    protected abstract String getInterfaceKey();

    protected abstract T parseJson(String json);


    public T loadData(int index) throws Throwable {
        //1.到本地中取缓存数据
        T data = getDataFromLocal(index);
        if (data != null) {
            LogUtils.d("使用本地缓存");
            return data;
        }
        //2.到网络中取数据
        return getDataFromNet(index);
    }

    /**
     * 去本地获取缓存
     *
     * @param index
     * @return
     * @throws Throwable
     */
    private T getDataFromLocal(int index) throws Throwable {
        File file = getCacheFile(index);
        if (!file.exists()) {
            return null;
        }
        //读取file,拿到json字符
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String timeString = reader.readLine();
            long time = Long.valueOf(timeString);
            if (System.currentTimeMillis() > time + Constans.refresh_delay) {
                //数据过期,无效
                return null;
            }
            //将json解析返回
            String json = reader.readLine();//
            return parseJson(json);
        } finally {
            IOUtils.close(reader);
        }
    }

    private T getDataFromNet(int index) throws Throwable {
        String url = Constans.ServerUrl +getInterfaceKey();
        RequestParams params = new RequestParams(url);
        params.addQueryStringParameter("index", index + "");
        String result = x.http().getSync(params, String.class);
        LogUtils.d(result.toString());
        //存储到本地
        LogUtils.d("存储到本地");
        write2Local(index, result);
        return parseJson(result);
    }

    private void write2Local(int index, String json) throws Exception {
        File file = getCacheFile(index);
        //将json字符写入文件中
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            //  //第一行:存储时间戳-->保存缓存的时间
            //  //第二行:存储json字符
            writer.write("" + System.currentTimeMillis());
            writer.write("\r\n");//换行
            writer.write(json);
        } finally {
            IOUtils.close(writer);
        }

    }

    private File getCacheFile(int index) {
        //到文件当中读取缓存
        //文件存储的路径以及格式
        //1.缓存数据的时效性
        //  //通过文件内容格式来确定时效性
        //  //第一行:存储时间戳-->保存缓存的时间
        //  //第二行:存储json字符
        //2.文件地址的存放和命名
        //  //存储的路径 sd->Android/data/包名/json/
        //  //文件名称: InterfaceKey + "." + index
        String dir = FileUtils.getDir("json");
        String name = getInterfaceKey() + "." + index;
        return new File(dir, name);
    }
}
