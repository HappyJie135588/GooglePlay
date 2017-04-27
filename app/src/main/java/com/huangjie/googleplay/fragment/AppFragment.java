package com.huangjie.googleplay.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.huangjie.googleplay.R;
import com.huangjie.googleplay.adapter.SuperBaseAdapter;
import com.huangjie.googleplay.bean.HomeBean;
import com.huangjie.googleplay.fragment.LoadingPager.LoadedResult;
import com.huangjie.googleplay.holder.AppItemHolder;
import com.huangjie.googleplay.holder.BaseHolder;
import com.huangjie.googleplay.holder.LoadMoreHolder;
import com.huangjie.googleplay.http.AppProtocol;
import com.huangjie.googleplay.utils.UIUtils;

import java.util.List;

/**
 * Created by 黄杰 on 2017/4/27.
 */

public class AppFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private AppAdapter mAppAdapter;
    private List<HomeBean.AppInfoBean> mDatas;
    private AppProtocol mProtocol;

    @Override
    protected View onLoadSuccessView() {
        ListView listView = new ListView(UIUtils.getContext());
        //属性设置
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setSelector(android.R.color.transparent);
        listView.setDividerHeight(0);
        listView.setScrollingCacheEnabled(false);
        listView.setBackgroundColor(UIUtils.getColor(R.color.bg));
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

    class AppAdapter extends SuperBaseAdapter<HomeBean.AppInfoBean> {

        public AppAdapter(AbsListView listView, List<HomeBean.AppInfoBean> datas) {
            super(listView, datas);
        }

        @Override
        protected BaseHolder<HomeBean.AppInfoBean> getItemHolder() {
            return new AppItemHolder();
        }

        @Override
        protected List<HomeBean.AppInfoBean> onLoadMoreData() throws Throwable {
            return mProtocol.loadData(mDatas.size());
        }

    }

}
