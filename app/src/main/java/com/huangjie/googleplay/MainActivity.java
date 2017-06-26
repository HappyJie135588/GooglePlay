package com.huangjie.googleplay;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.huangjie.googleplay.factory.FragmentFactory;
import com.huangjie.googleplay.fragment.BaseFragment;
import com.huangjie.googleplay.utils.LogUtils;
import com.huangjie.googleplay.utils.UIUtils;

public class MainActivity extends BaseAvtivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;//抽屉开关控件
    private PagerSlidingTabStrip mTabStrip;
    private ViewPager mPager;
    private String[] mTitles;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mDrawerToggle.onOptionsItemSelected(item)) {
                    return true;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        mTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mTabStrip.setTextColor(UIUtils.getColor(R.color.tab_text_normal), UIUtils.getColor(R.color.tab_text_selected));
        mPager = (ViewPager) findViewById(R.id.main_pager);
    }

    @Override
    protected void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                R.string.main_des_drawer_open,
                R.string.main_des_drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void initData() {
        mTitles = UIUtils.getStringArray(R.array.main_titles);
        mPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager()));
        mTabStrip.setViewPager(mPager);
        mTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //当选中的时候去加载数据
                BaseFragment fragment = FragmentFactory.getFragment(position);
                fragment.loadData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //FragmentPagerAdapter:在页面比较少的情况下使用,缓存的是fragment
    //FragmentStatePagerAdapter:在页面比较多的情况下使用,缓存的是状态
    class MainFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public MainFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            LogUtils.d("获取第" + position + "个页面");
            return FragmentFactory.getFragment(position);
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (mTitles != null) {
                return mTitles[position];
            }
            return super.getPageTitle(position);
        }

    }


}
