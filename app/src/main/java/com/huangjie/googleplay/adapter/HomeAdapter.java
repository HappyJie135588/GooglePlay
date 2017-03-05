package com.huangjie.googleplay.adapter;

import com.huangjie.googleplay.holder.AppItemHolder;
import com.huangjie.googleplay.holder.BaseHolder;

import java.util.List;

/**
 * Created by 黄杰 on 2017/3/5.
 */

public class HomeAdapter extends SuperBaseAdapter<String> {
    public HomeAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    protected BaseHolder<String> getItemHolder() {
        return new AppItemHolder();
    }
}
