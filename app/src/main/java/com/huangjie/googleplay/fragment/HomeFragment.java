package com.huangjie.googleplay.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.huangjie.googleplay.R;
import com.huangjie.googleplay.adapter.SuperBaseAdapter;
import com.huangjie.googleplay.bean.HomeBean;
import com.huangjie.googleplay.fragment.LoadingPager.LoadedResult;
import com.huangjie.googleplay.holder.AppItemHolder;
import com.huangjie.googleplay.holder.BaseHolder;
import com.huangjie.googleplay.holder.LoadMoreHolder;
import com.huangjie.googleplay.http.HomeProtocol;
import com.huangjie.googleplay.utils.UIUtils;

import java.util.List;

/**
 * Created by 黄杰 on 2016/12/19.
 */
public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private List<HomeBean.AppInfoBean> mDatas;
    private List<String> mPictures;
    private HomeAdapter mHomeAdapter;
    private HomeProtocol mProtocol;

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
        mHomeAdapter = new HomeAdapter(listView, mDatas);
        listView.setAdapter(mHomeAdapter);
        listView.setOnItemClickListener(this);
        return listView;
    }

    @Override
    protected LoadedResult onLoadingData() {
        //##3.去网络加载数据
        mProtocol = new HomeProtocol();
        try {
            HomeBean homeBean = mProtocol.loadData(0);
            if (homeBean == null) {
                return LoadedResult.EMPTY;
            }
            mDatas = homeBean.getList();
            mPictures = homeBean.getPicture();
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
        if (mHomeAdapter.getItemViewType(position) == HomeAdapter.TYPE_LOAD_MORE) {
            //点击的是加载更多

            if (mHomeAdapter.getLoadMoreHolderCurrentState() == LoadMoreHolder.STATE_ERROR) {
                //去加载更多
                mHomeAdapter.performLoadMoreData();
            }
        }
    }

    public class HomeAdapter extends SuperBaseAdapter<HomeBean.AppInfoBean> {


        public HomeAdapter(AbsListView listView, List<HomeBean.AppInfoBean> datas) {
            super(listView, datas);
        }

        @Override
        protected BaseHolder<HomeBean.AppInfoBean> getItemHolder() {
            return new AppItemHolder();
        }

        @Override
        protected List<HomeBean.AppInfoBean> onLoadMoreData() throws Throwable {
            return loadMoreData(mDatas.size());
        }

    }

    private List<HomeBean.AppInfoBean> loadMoreData(int index) throws Throwable {
        HomeBean homeBean = mProtocol.loadData(index);
        if (homeBean == null) {
            return null;
        } else {
            return homeBean.getList();
        }
    }

}
