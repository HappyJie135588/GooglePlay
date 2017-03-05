package com.huangjie.googleplay.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huangjie.googleplay.R;
import com.huangjie.googleplay.holder.BaseHolder;
import com.huangjie.googleplay.utils.UIUtils;

import java.util.List;

/**
 * Created by 黄杰 on 2017/2/6.
 * ListView的Adapter的鸡肋
 */

public abstract class SuperBaseAdapter<T> extends BaseAdapter {
    private List<T> mDatas;

    public SuperBaseAdapter(List<T> datas) {
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mDatas != null) {
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder = null;
        if (convertView == null) {
            holder = getItemHolder();
            convertView = holder.getRootView();
        } else {
            holder = (BaseHolder) convertView.getTag();
        }
        T data = mDatas.get(position);
        holder.setData(data);
        return convertView;
    }

    protected abstract BaseHolder<T> getItemHolder();
}
