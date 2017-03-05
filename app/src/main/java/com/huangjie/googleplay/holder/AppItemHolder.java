package com.huangjie.googleplay.holder;


import android.view.View;
import android.widget.TextView;

import com.huangjie.googleplay.BaseApplication;
import com.huangjie.googleplay.R;

import org.w3c.dom.Text;

/**
 * Created by 黄杰 on 2017/2/9.
 * 首页,应用,游戏页面listView对应的item的holder
 */

public class AppItemHolder extends BaseHolder<String> {
    private TextView tmp_tv_1;
    private TextView tmp_tv_2;

    //T ->item对应的数据
    @Override
    protected View initView() {
        View view = View.inflate(BaseApplication.getContext(), R.layout.item_tmp, null);
        tmp_tv_1 = (TextView) view.findViewById(R.id.tmp_tv_1);
        tmp_tv_2 = (TextView) view.findViewById(R.id.tmp_tv_2);
        return view;
    }

    @Override
    protected void refreshUI(String data) {
        tmp_tv_1.setText(data);
        tmp_tv_2.setText(data);
    }
}
