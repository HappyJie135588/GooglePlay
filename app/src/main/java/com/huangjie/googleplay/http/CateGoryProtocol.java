package com.huangjie.googleplay.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huangjie.googleplay.bean.CateGoryBean;

import java.util.List;

/**
 * Created by 黄杰 on 2017/5/2.
 */

public class CateGoryProtocol extends BaseProtocol<List<CateGoryBean>> {

    @Override
    protected String getInterfaceKey() {
        return "category";
    }

    @Override
    protected List<CateGoryBean> parseJson(String json) {
        return new Gson().fromJson(json, new TypeToken<List<CateGoryBean>>() {
        }.getType());
    }
}
