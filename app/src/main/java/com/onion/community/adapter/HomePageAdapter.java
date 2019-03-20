package com.onion.community.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by zhangqi on 2016/10/12.
 */

public class HomePageAdapter extends FragmentPagerAdapter {

    private List<Fragment> mPageList;

    public HomePageAdapter(FragmentManager fm, List<Fragment> pageList) {
        super(fm);
        mPageList = pageList;
    }

    @Override
    public Fragment getItem(int position) {
        return mPageList.get(position);
    }

    @Override
    public int getCount() {
        return mPageList.size();
    }
}
