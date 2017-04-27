package com.huangjie.googleplay.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.huangjie.googleplay.holder.BaseHolder;
import com.huangjie.googleplay.holder.LoadMoreHolder;
import com.huangjie.googleplay.manager.ThreadManager;
import com.huangjie.googleplay.utils.Constans;
import com.huangjie.googleplay.utils.UIUtils;

import java.util.List;

/**
 * Created by 黄杰 on 2017/2/6.
 * ListView的Adapter的鸡肋
 */

public abstract class SuperBaseAdapter<T> extends BaseAdapter implements AdapterView.OnItemClickListener {
    public static final int TYPE_LOAD_MORE = 0;
    public static final int TYPE_NORMAL_ITEM = 1;

    private List<T> mDatas;
    private LoadMoreHolder mLoadMoreHolder;
    private LoadMoreTask mLoadMoreTask;
    private AbsListView mListView;

    public SuperBaseAdapter(AbsListView listView,List<T> datas) {
        this.mDatas = datas;
        this.mListView =listView;
        mListView.setOnItemClickListener(this);
    }

    @Override
    public int getCount() {
        if (mDatas != null) {
            return mDatas.size() + 1;
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

    //adapter对应的item view的类型个数
    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;//添加加载更多的样式
    }

    //当前position是什么type类型,返回的类型必须从0开始\而且需要累加
    @Override
    public int getItemViewType(int position) {
        if (getCount() - 1 == position) {
            return TYPE_LOAD_MORE;
        } else {
            return TYPE_NORMAL_ITEM;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder = null;
        if (convertView == null) {
            if (getItemViewType(position) == TYPE_LOAD_MORE) {
                //加载更多的Holder
                holder = getLoadMoreHolder();
            } else {
                //普通的Holder
                holder = getItemHolder();
            }
            convertView = holder.getRootView();
        } else {
            holder = (BaseHolder) convertView.getTag();
        }
        if (getItemViewType(position) == TYPE_LOAD_MORE) {
            //给加载更多的Holder添加数据
            if (hasLoadMore()) {
                //有加载更多的功能
                //去网络加载数据,加载到mDatas
                performLoadMoreData();
            } else {
                //没有加载更多的功能
                mLoadMoreHolder.setData(LoadMoreHolder.STATE_EMPTY);
            }
        } else {
            //给普通Holder添加数据
            T data = mDatas.get(position);
            holder.setData(data);
        }
        return convertView;
    }

    public int getLoadMoreHolderCurrentState() {
        return mLoadMoreHolder.getCurrentState();
    }

    public void performLoadMoreData() {
        //只允许一个加载更多的线程
//        if (mLoadMoreTask != null) {
//            return;
//        }
        //设置状态为加载更多
        mLoadMoreHolder.setData(LoadMoreHolder.STATE_LOADING);
        mLoadMoreTask = new LoadMoreTask();
        //去网络加载数据,加载到mDatas
        ThreadManager.getLongPool().excute(mLoadMoreTask);
    }

    /**
     * 默认Adapter具备加载更多的功能,如果子类不需要有,就复写此方法
     *
     * @return
     */
    protected boolean hasLoadMore() {
        return true;
    }

    private BaseHolder getLoadMoreHolder() {
        if (mLoadMoreHolder == null) {
            mLoadMoreHolder = new LoadMoreHolder();
        }
        return mLoadMoreHolder;
    }

    protected abstract BaseHolder<T> getItemHolder();

    /**
     * 当加载更多时的回调,如果孩子有加载更多的功能,必须实现此方法
     *
     * @return
     */
    protected List<T> onLoadMoreData() throws Throwable {
        return null;
    }

    /**
     * 如果孩子有item的点击事件,复写此方法即可
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    public void onNoramlItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //position指的是listView的position(含头),不是Adapter的position
        if(mListView instanceof ListView){
              int count =  ((ListView) mListView).getHeaderViewsCount();
            position -= count;
        }
        if(getItemViewType(position)==TYPE_LOAD_MORE){
            //点击的是加载更多

            if(mLoadMoreHolder.getCurrentState()==LoadMoreHolder.STATE_ERROR){
                //去加载更多
                performLoadMoreData();
            }
        }else{
            //点击的是普通的item
            onNoramlItemClick(parent,view,position,id);
        }
    }


    class LoadMoreTask implements Runnable {

        @Override
        public void run() {
            //去网络加载数据,加载到mDatas
            List<T> mMoreDatas = null;
            int state = LoadMoreHolder.STATE_LOADING;
            try {
                Thread.sleep(1000);
                state = LoadMoreHolder.STATE_ERROR;
                mMoreDatas = onLoadMoreData();
                //如果数据为空-->服务器已经没数据了
                if (mMoreDatas == null || mMoreDatas.size() == 0) {
                    state = LoadMoreHolder.STATE_EMPTY;
                    return;
                }
                int size = mMoreDatas.size();
                if (size < Constans.PAGER_SIZE) {
                    //如果数据不为空-->小于一页的数据量,服务器已经没数据
                    state = LoadMoreHolder.STATE_EMPTY;
                } else {
                    //如果数据不为空-->等于一页的数据量,服务器有可能有更多的数据
                    state = LoadMoreHolder.STATE_LOADING;
                }

            } catch (Throwable e) {
                e.printStackTrace();
                state = LoadMoreHolder.STATE_ERROR;
            }

            final int finalState = state;
            final List<T> finalMMoreDatas = mMoreDatas;
            UIUtils.post(new Runnable() {
                @Override
                public void run() {
                    //UI更新
                    //更新加载更多
                    mLoadMoreHolder.setData(finalState);
                    //如果产生异常
                    if (finalState == LoadMoreHolder.STATE_ERROR) {
                        Toast.makeText(UIUtils.getContext(), "服务器异常", Toast.LENGTH_LONG).show();
                    }
                    //更新adapter
                    if (finalMMoreDatas != null) {
                        mDatas.addAll(finalMMoreDatas);
                        notifyDataSetChanged();
                    }
                }
            });
            //mLoadMoreTask = null;
        }
    }

}
