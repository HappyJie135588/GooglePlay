package com.huangjie.googleplay.utils;

import android.graphics.Color;
import android.widget.ListView;

import com.huangjie.googleplay.R;

/**
 * Created by 黄杰 on 2017/4/29.
 */

public class ListViewFactory {
    public static ListView getListView() {
        ListView listView = new ListView(UIUtils.getContext());
        //属性设置
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setSelector(android.R.color.transparent);
        listView.setDividerHeight(0);
        listView.setScrollingCacheEnabled(false);
        listView.setBackgroundColor(UIUtils.getColor(R.color.bg));
        return listView;
    }
}
