package com.huangjie.googleplay.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huangjie.googleplay.bean.HomeBean;

import java.util.List;

/**
 * Created by 黄杰 on 2017/4/27.
 */

public class AppProtocol extends BaseProtocol<List<HomeBean.AppInfoBean>> {
    @Override
    protected String getInterfaceKey() {
        return "app";
    }

    @Override
    protected List<HomeBean.AppInfoBean> parseJson(String json) {
        return new Gson().fromJson(json,new TypeToken<List<HomeBean.AppInfoBean>>(){}.getType());
    }
}
