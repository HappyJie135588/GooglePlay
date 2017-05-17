package com.huangjie.googleplay.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.huangjie.googleplay.AppDetailActivity;
import com.huangjie.googleplay.MainActivity;
import com.huangjie.googleplay.bean.AppInfoBean;
import com.huangjie.googleplay.holder.AppItemHolder;
import com.huangjie.googleplay.holder.BaseHolder;
import com.huangjie.googleplay.utils.UIUtils;

import java.util.List;

/**
 * Created by 黄杰 on 2017/5/17.
 */

public class AppListAdapter extends SuperBaseAdapter<AppInfoBean> {
    public AppListAdapter(AbsListView listView, List<AppInfoBean> datas) {
        super(listView, datas);
    }

    @Override
    protected BaseHolder<AppInfoBean> getItemHolder() {
        return new AppItemHolder();
    }

    @Override
    public void onNoramlItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onNoramlItemClick(parent, view, position, id);
        //跳转到应用详情页面
        Context context = UIUtils.getContext();
        Intent intent = new Intent(context, AppDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }
}
