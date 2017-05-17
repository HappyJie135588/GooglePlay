package com.huangjie.googleplay.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.huangjie.googleplay.adapter.AppListAdapter;
import com.huangjie.googleplay.adapter.SuperBaseAdapter;
import com.huangjie.googleplay.bean.AppInfoBean;
import com.huangjie.googleplay.bean.HomeBean;
import com.huangjie.googleplay.holder.AppItemHolder;
import com.huangjie.googleplay.holder.BaseHolder;
import com.huangjie.googleplay.http.GameProtocol;
import com.huangjie.googleplay.utils.ListViewFactory;

import java.util.List;

/**
 * Created by 黄杰 on 2017/4/29.
 */

public class GameFragment extends BaseFragment {
    private GameProtocol mProtocol;
    private List<AppInfoBean> mDatas;

    @Override
    protected View onLoadSuccessView() {
        ListView listView = ListViewFactory.getListView();
        listView.setAdapter(new GameAdapter(listView, mDatas));
        return listView;
    }

    @Override
    protected LoadingPager.LoadedResult onLoadingData() {
        mProtocol = new GameProtocol();
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

    class GameAdapter extends AppListAdapter {

        public GameAdapter(AbsListView listView, List<AppInfoBean> datas) {
            super(listView, datas);
        }

        @Override
        protected List<AppInfoBean> onLoadMoreData() throws Throwable {
            return mProtocol.loadData(mDatas.size());
        }
    }
}
