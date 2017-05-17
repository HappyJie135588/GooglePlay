package com.huangjie.googleplay.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.huangjie.googleplay.adapter.SuperBaseAdapter;
import com.huangjie.googleplay.bean.SubjectBean;
import com.huangjie.googleplay.holder.BaseHolder;
import com.huangjie.googleplay.holder.SubjectHolder;
import com.huangjie.googleplay.http.SubjectProtocol;
import com.huangjie.googleplay.utils.ListViewFactory;

import java.util.List;

/**
 * Created by 黄杰 on 2017/4/30.
 */

public class SubjectFragment extends BaseFragment {

    private SubjectProtocol mProtocol;
    private List<SubjectBean> mDatas;

    @Override
    protected View onLoadSuccessView() {
        ListView listView = ListViewFactory.getListView();
        listView.setAdapter(new SubjectAdapter(listView,mDatas));
        return listView;
    }

    @Override
    protected LoadingPager.LoadedResult onLoadingData() {
        mProtocol = new SubjectProtocol();
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
    class SubjectAdapter extends SuperBaseAdapter<SubjectBean>{

        public SubjectAdapter(AbsListView listView, List<SubjectBean> datas) {
            super(listView, datas);
        }

        @Override
        protected BaseHolder<SubjectBean> getItemHolder() {
            return new SubjectHolder();
        }
    }
}
