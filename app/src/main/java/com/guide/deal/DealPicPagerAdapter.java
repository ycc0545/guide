package com.guide.deal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class DealPicPagerAdapter extends FragmentPagerAdapter {
    private String[] urlList;

    public DealPicPagerAdapter(FragmentManager fm, String[] urlList) {
        super(fm);
        this.urlList = urlList;
    }


    @Override
    public int getCount() {
        return urlList == null ? 0 : urlList.length;
    }


    @Override
    public Fragment getItem(int position) {
        final String url = urlList[position];
        return DealPicFragment.newInstance(url);
    }


}
