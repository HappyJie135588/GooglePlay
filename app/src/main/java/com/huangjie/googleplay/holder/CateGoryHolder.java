package com.huangjie.googleplay.holder;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.huangjie.googleplay.BaseApplication;
import com.huangjie.googleplay.R;
import com.huangjie.googleplay.adapter.SuperBaseAdapter;
import com.huangjie.googleplay.bean.CateGoryBean;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by 黄杰 on 2017/5/15.
 */

public class CateGoryHolder extends BaseHolder<CateGoryBean> {
    @ViewInject(R.id.tv_title)
    private TextView mTv;
    @ViewInject(R.id.lv)
    private ListView mLv;
    @Override
    protected View initView() {
        View view = View.inflate(BaseApplication.getContext(), R.layout.list_category, null);
        x.view().inject(this, view);
        return view;
    }

    @Override
    protected void refreshUI(CateGoryBean data) {
        mTv.setText(data.getTitle());
        mLv.setAdapter(new CategoryAdapter(mLv,data.getInfos()));
    }
    class CategoryAdapter extends SuperBaseAdapter<CateGoryBean.InfosBean>{

        public CategoryAdapter(AbsListView listView, List<CateGoryBean.InfosBean> datas) {
            super(listView, datas);
        }

        @Override
        protected BaseHolder<CateGoryBean.InfosBean> getItemHolder() {
            return new CateGoryItemHolder();
        }
    }
}
