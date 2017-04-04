package com.huangjie.googleplay.utils;

/**
 * Created by 黄杰 on 2017/3/18.
 */

public interface Constans {
    //每页显示的数量
    int PAGER_SIZE = 20;
    //服务器IP地址
    String ServerIP = "192.168.4.197";
    //服务器地址
    String ServerUrl = "http://" + ServerIP + ":8080/GooglePlayServer/";

    /**
     * 主页接口
     * 请求方式: GET
     * URL:	服务器地址 + home
     * 请求参数：index(分页显示中的第几条，默认从0开始)
     * 例子： http://10.0.2.2:8080/GooglePlayServer/home?index=0
     */
    String HomeUrl = ServerUrl + "home";
    /**
     * 图片接口
     * 请求方式: GET
     * URL:	服务器地址 + image
     * 请求参数：name(下载的应用名称)，range(从什么位置开始下载)
     * 例子： http://10.0.2.2:8080/GooglePlayServer/image?name=
     */
    String ImageUrl = ServerUrl + "image?name=";

}
