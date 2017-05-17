package com.huangjie.googleplay.bean;


import java.util.List;

/**
 * Created by 黄杰 on 2017/3/17.
 * 主页对应的数据
 */
public class HomeBean {

    private List<String> picture;
    private List<AppInfoBean> list;

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }

    public List<AppInfoBean> getList() {
        return list;
    }

    public void setList(List<AppInfoBean> list) {
        this.list = list;
    }


}
