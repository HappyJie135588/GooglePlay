package com.huangjie.googleplay.http;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.huangjie.googleplay.bean.AppInfoBean;
import com.huangjie.googleplay.bean.HomeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 黄杰 on 2017/4/29.
 */

public class GameProtocol extends BaseProtocol<List<AppInfoBean>> {
    @Override
    protected String getInterfaceKey() {
        return "game";
    }

    @Override
    protected List<AppInfoBean> parseJson(String json) {
        //JsonElement
        //JsonObject
        //JsonArray
        //JsonPrimitive
        List<AppInfoBean> list = null;

        //新建解析器
        JsonParser parser = new JsonParser();
        //解析json
        JsonElement rootElement = parser.parse(json);
        //将根节点具体化类型
        JsonArray rootArray = rootElement.getAsJsonArray();
        for (int i = 0; i < rootArray.size(); i++) {
            AppInfoBean appInfoBean = new AppInfoBean();
            JsonElement itemElement = rootArray.get(i);
            //将根节点具体化类型
            JsonObject itemObject = itemElement.getAsJsonObject();
            //获取子节点的元素

            JsonPrimitive idJson = itemObject.getAsJsonPrimitive("id");
            appInfoBean.setId(idJson.getAsLong());

            JsonPrimitive nameJson = itemObject.getAsJsonPrimitive("name");
            appInfoBean.setName(nameJson.getAsString());

            JsonPrimitive packageNameJson = itemObject.getAsJsonPrimitive("packageName");
            appInfoBean.setPackageName(packageNameJson.getAsString());

            JsonPrimitive iconUrlJson = itemObject.getAsJsonPrimitive("iconUrl");
            appInfoBean.setIconUrl(iconUrlJson.getAsString());

            JsonPrimitive starsJson = itemObject.getAsJsonPrimitive("stars");
            appInfoBean.setStars(starsJson.getAsFloat());

            JsonPrimitive sizeJson = itemObject.getAsJsonPrimitive("size");
            appInfoBean.setSize(sizeJson.getAsLong());

            JsonPrimitive downloadUrlJson = itemObject.getAsJsonPrimitive("downloadUrl");
            appInfoBean.setDownloadUrl(downloadUrlJson.getAsString());

            JsonPrimitive desJson = itemObject.getAsJsonPrimitive("des");
            appInfoBean.setDes(desJson.getAsString());

            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(appInfoBean);
        }
        return list;
    }
}
