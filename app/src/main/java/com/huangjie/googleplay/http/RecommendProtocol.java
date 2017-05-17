package com.huangjie.googleplay.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huangjie.googleplay.bean.AppInfoBean;

import java.util.List;

/**
 * Created by 黄杰 on 2017/4/27.
 */

public class RecommendProtocol extends BaseProtocol<List<String>> {
    @Override
    protected String getInterfaceKey() {
        return "recommend";
    }

    @Override
    protected List<String> parseJson(String json) {
        return new Gson().fromJson(json,new TypeToken<List<String>>(){}.getType());
    }
}
