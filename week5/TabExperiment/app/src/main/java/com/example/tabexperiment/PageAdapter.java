package com.example.tabexperiment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {
    int mNumofTabs;
    public PageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumofTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new TabFragment1();
            case 1:return new TabFragment2();
            case 2:return new TabFragment3();
            default:return null;
        }
    }

    @Override
    public int getCount() {
        return mNumofTabs;
    }
}
