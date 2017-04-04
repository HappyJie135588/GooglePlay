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

    public static class AppInfoBean {
        /**
         * id : 1525490
         * name : 有缘网
         * packageName : com.youyuan.yyhl
         * iconUrl : app/com.youyuan.yyhl/icon.jpg
         * stars : 4
         * size : 3876203
         * downloadUrl : app/com.youyuan.yyhl/com.youyuan.yyhl.apk
         * des : 产品介绍：有缘是时下最受大众单身男女亲睐的婚恋交友软件。有缘网专注于通过轻松、
         */

        private long id;
        private String name;
        private String packageName;
        private String iconUrl;
        private float stars;
        private long size;
        private String downloadUrl;
        private String des;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public float getStars() {
            return stars;
        }

        public void setStars(float stars) {
            this.stars = stars;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }
}
