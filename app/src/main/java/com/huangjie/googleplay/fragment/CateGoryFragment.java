package com.huangjie.googleplay.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.huangjie.googleplay.adapter.SuperBaseAdapter;
import com.huangjie.googleplay.bean.CateGoryBean;
import com.huangjie.googleplay.holder.BaseHolder;
import com.huangjie.googleplay.holder.CateGoryHolder;
import com.huangjie.googleplay.http.CateGoryProtocol;
import com.huangjie.googleplay.utils.ListViewFactory;

import java.util.List;

/**
 * Created by 黄杰 on 2017/5/2.
 */

public class CateGoryFragment extends BaseFragment {
    private CateGoryProtocol mProtocol;
    private List<CateGoryBean> mDatas;

    @Override
    protected View onLoadSuccessView() {
        ListView listView = ListViewFactory.getListView();
        listView.setAdapter(new CateGoryAdapter(listView, mDatas));
        return listView;
    }

    @Override
    protected LoadingPager.LoadedResult onLoadingData() {
        mProtocol = new CateGoryProtocol();
        try {
            mDatas = mProtocol.loadData(0);
            if (mDatas == null || mDatas.size() == 0) {
                return LoadingPager.LoadedResult.EMPTY;
            }
            return LoadingPager.LoadedResult.SUCCESS;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return LoadingPager.LoadedResult.ERROR;
        }
    }

    class CateGoryAdapter extends SuperBaseAdapter<CateGoryBean> {
        public CateGoryAdapter(AbsListView listView, List<CateGoryBean> datas) {
            super(listView, datas);
        }

        @Override
        protected BaseHolder<CateGoryBean> getItemHolder() {
            return new CateGoryHolder();
        }
    }

}
