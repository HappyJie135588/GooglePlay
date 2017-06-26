package com.huangjie.googleplay.http;

import com.google.gson.Gson;
import com.huangjie.googleplay.bean.AppDetailInfoBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 黄杰 on 2017/5/18.
 */

public class AppDetailProtocol extends BaseProtocol<AppDetailInfoBean> {
    private String mPackageName;

    public AppDetailProtocol(String mPackageName) {
        this.mPackageName = mPackageName;
    }

    @Override
    protected String getInterfaceKey() {
        return "detail";
    }

    @Override
    protected Map<String, String> getParams() {
        Map<String, String> map = new HashMap<>();
        map.put("packageName", mPackageName);
        return map;
    }

    @Override
    protected AppDetailInfoBean parseJson(String json) {
        return new Gson().fromJson(json, AppDetailInfoBean.class);
    }
}
