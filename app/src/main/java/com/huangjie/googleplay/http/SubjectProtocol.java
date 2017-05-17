package com.huangjie.googleplay.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huangjie.googleplay.bean.SubjectBean;

import java.util.List;

/**
 * Created by 黄杰 on 2017/4/30.
 */

public class SubjectProtocol extends BaseProtocol<List<SubjectBean>> {
    @Override
    protected String getInterfaceKey() {
        return "subject";
    }

    @Override
    protected List<SubjectBean> parseJson(String json) {
        return new Gson().fromJson(json, new TypeToken<List<SubjectBean>>() {
        }.getType());
    }
}
