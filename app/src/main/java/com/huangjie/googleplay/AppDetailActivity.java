package com.huangjie.googleplay;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.huangjie.googleplay.bean.AppDetailInfoBean;
import com.huangjie.googleplay.fragment.LoadingPager;
import com.huangjie.googleplay.holder.AppDetailBottomHolder;
import com.huangjie.googleplay.holder.AppDetailDesHolder;
import com.huangjie.googleplay.holder.AppDetailInfoHolder;
import com.huangjie.googleplay.holder.AppDetailPicHolder;
import com.huangjie.googleplay.holder.AppDetailSafeHolder;
import com.huangjie.googleplay.http.AppDetailProtocol;
import com.huangjie.googleplay.manager.DownloadManager;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * Created by 黄杰 on 2017/5/17.
 */

public class AppDetailActivity extends BaseAvtivity {
    public static String KEY_PACKAGENAME = "packageName";
    private LinearLayout rootView;
    private Toolbar toolbar;
    private LoadingPager mLoadingPager;
    private AppDetailProtocol mProtocol;
    private AppDetailInfoBean mData;
    private AppDetailBottomHolder mBottomHolder;

    @ViewInject(R.id.app_detail_container_bottom)
    private FrameLayout mContainerBottom;
    @ViewInject(R.id.app_detail_container_info)
    private FrameLayout mContainerInfo;
    @ViewInject(R.id.app_detail_container_safe)
    private FrameLayout mContainerSafe;
    @ViewInject(R.id.app_detail_container_pic)
    private FrameLayout mContainerPic;
    @ViewInject(R.id.app_detail_container_des)
    private FrameLayout mContainerDes;

    @Override
    protected void initView() {
        rootView = new LinearLayout(this);
        rootView.setOrientation(LinearLayout.VERTICAL);
        toolbar = new Toolbar(this);
        toolbar.setBackgroundResource(R.color.colorPrimary);
        rootView.addView(toolbar);
        mLoadingPager = new LoadingPager(this) {
            @Override
            protected View initSuccessView() {
                return onSuccessView();
            }

            @Override
            protected LoadedResult onLoadData() {
                return performLoadingData();
            }
        };
        rootView.addView(mLoadingPager);
        setContentView(rootView);
    }

    @Override
    protected void initToolbar() {
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initData() {
        mLoadingPager.loadData();
    }

    private LoadingPager.LoadedResult performLoadingData() {
        String packageName = getIntent().getStringExtra(KEY_PACKAGENAME);
        mProtocol = new AppDetailProtocol(packageName);
        try {
            mData = mProtocol.loadData(0);
            if (mData == null) {
                return LoadingPager.LoadedResult.EMPTY;
            }
            return LoadingPager.LoadedResult.SUCCESS;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return LoadingPager.LoadedResult.ERROR;
        }
    }

    private View onSuccessView() {
        View view = View.inflate(this, R.layout.activity_app_detail, null);
        x.view().inject(this, view);

        //挂载对应的holder
        //1,信息部分
        AppDetailInfoHolder infoHolder = new AppDetailInfoHolder();
        mContainerInfo.addView(infoHolder.getRootView());//加载视图
        infoHolder.setData(mData);//加载数据
        //2,安全部分
        AppDetailSafeHolder safeHolder = new AppDetailSafeHolder();
        mContainerSafe.addView(safeHolder.getRootView());
        safeHolder.setData(mData.getSafe());
        //3,图片部分
        AppDetailPicHolder picHolder = new AppDetailPicHolder();
        mContainerPic.addView(picHolder.getRootView());
        picHolder.setData(mData.getScreen());
        //4,描述部分
        AppDetailDesHolder desHolder = new AppDetailDesHolder();
        mContainerDes.addView(desHolder.getRootView());
        desHolder.setData(mData);
        //5,下载部分
        mBottomHolder = new AppDetailBottomHolder();
        mContainerBottom.addView(mBottomHolder.getRootView());
        mBottomHolder.setData(mData);
        //通过Activity去注册监听下载
        mBottomHolder.startObserver();

        return view;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBottomHolder != null) {
            mBottomHolder.startObserver();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mBottomHolder != null) {
            mBottomHolder.stopObserver();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
