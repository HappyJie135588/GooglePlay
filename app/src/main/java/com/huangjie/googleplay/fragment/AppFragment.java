package com.huangjie.googleplay.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.huangjie.googleplay.adapter.AppListAdapter;
import com.huangjie.googleplay.adapter.SuperBaseAdapter;
import com.huangjie.googleplay.bean.AppInfoBean;
import com.huangjie.googleplay.fragment.LoadingPager.LoadedResult;
import com.huangjie.googleplay.holder.AppItemHolder;
import com.huangjie.googleplay.holder.BaseHolder;
import com.huangjie.googleplay.holder.LoadMoreHolder;
import com.huangjie.googleplay.http.AppProtocol;
import com.huangjie.googleplay.utils.ListViewFactory;

import java.util.List;

/**
 * Created by 黄杰 on 2017/4/27.
 */

public class AppFragment extends BaseFragment {
    private AppAdapter mAppAdapter;
    private List<AppInfoBean> mDatas;
    private AppProtocol mProtocol;

    @Override
    protected View onLoadSuccessView() {
        ListView listView = ListViewFactory.getListView();
        //设置数据
        mAppAdapter = new AppFragment.AppAdapter(listView, mDatas);
        listView.setAdapter(mAppAdapter);
        return listView;
    }

    @Override
    protected LoadedResult onLoadingData() {
        mProtocol = new AppProtocol();
        try {
            mDatas = mProtocol.loadData(0);
            if (mDatas == null || mDatas.size() == 0) {
                return LoadedResult.EMPTY;
            }
            return LoadedResult.SUCCESS;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return LoadedResult.ERROR;
        }
    }

    class AppAdapter extends AppListAdapter {

        public AppAdapter(AbsListView listView, List<AppInfoBean> datas) {
            super(listView, datas);
        }

        @Override
        protected List<AppInfoBean> onLoadMoreData() throws Throwable {
            return mProtocol.loadData(mDatas.size());
        }

    }

}
