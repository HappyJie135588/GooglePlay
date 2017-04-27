package com.huangjie.googleplay.http;

import com.google.gson.Gson;
import com.huangjie.googleplay.bean.HomeBean;

/**
 * Created by 黄杰 on 2017/4/5.
 */

public class HomeProtocol extends BaseProtocol<HomeBean> {

    @Override
    protected String getInterfaceKey() {
        return "home";
    }

    @Override
    protected HomeBean parseJson(String json) {
        return new Gson().fromJson(json, HomeBean.class);
    }
}
