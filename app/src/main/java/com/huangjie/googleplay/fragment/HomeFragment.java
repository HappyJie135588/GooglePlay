package com.huangjie.googleplay.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.huangjie.googleplay.adapter.AppListAdapter;
import com.huangjie.googleplay.adapter.SuperBaseAdapter;
import com.huangjie.googleplay.bean.AppInfoBean;
import com.huangjie.googleplay.bean.HomeBean;
import com.huangjie.googleplay.fragment.LoadingPager.LoadedResult;
import com.huangjie.googleplay.holder.AppItemHolder;
import com.huangjie.googleplay.holder.BaseHolder;
import com.huangjie.googleplay.holder.HomePictureHolder;
import com.huangjie.googleplay.holder.LoadMoreHolder;
import com.huangjie.googleplay.http.HomeProtocol;
import com.huangjie.googleplay.utils.ListViewFactory;

import java.util.List;

/**
 * Created by 黄杰 on 2016/12/19.
 */
public class HomeFragment extends BaseFragment {
    private List<AppInfoBean> mDatas;
    private List<String> mPictures;
    private HomeAdapter mHomeAdapter;
    private HomeProtocol mProtocol;

    @Override
    protected View onLoadSuccessView() {
        ListView listView = ListViewFactory.getListView();
        //给ListView添加头
        HomePictureHolder holder = new HomePictureHolder();
        listView.addHeaderView(holder.getRootView());
        holder.setData(mPictures);



        //设置数据
        mHomeAdapter = new HomeAdapter(listView, mDatas);
        listView.setAdapter(mHomeAdapter);
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

    public class HomeAdapter extends AppListAdapter {


        public HomeAdapter(AbsListView listView, List<AppInfoBean> datas) {
            super(listView, datas);
        }

        @Override
        protected List<AppInfoBean> onLoadMoreData() throws Throwable {
            return loadMoreData(mDatas.size());
        }
    }

    private List<AppInfoBean> loadMoreData(int index) throws Throwable {
        HomeBean homeBean = mProtocol.loadData(index);
        if (homeBean == null) {
            return null;
        } else {
            return homeBean.getList();
        }
    }

}
