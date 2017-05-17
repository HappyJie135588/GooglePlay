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

public class AppFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private AppAdapter mAppAdapter;
    private List<AppInfoBean> mDatas;
    private AppProtocol mProtocol;

    @Override
    protected View onLoadSuccessView() {
        ListView listView = ListViewFactory.getListView();
        //设置数据
        mAppAdapter = new AppFragment.AppAdapter(listView, mDatas);
        listView.setAdapter(mAppAdapter);
        listView.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mAppAdapter.getItemViewType(position) == AppAdapter.TYPE_LOAD_MORE) {
            //点击的是加载更多

            if (mAppAdapter.getLoadMoreHolderCurrentState() == LoadMoreHolder.STATE_ERROR) {
                //去加载更多
                mAppAdapter.performLoadMoreData();
            }
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
