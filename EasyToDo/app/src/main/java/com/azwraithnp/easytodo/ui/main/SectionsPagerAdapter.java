package com.azwraithnp.easytodo.ui.main;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.azwraithnp.easytodo.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;
    private int currentPosition = -1;
    private Fragment currentFragment;


    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a MyTasksFragment (defined as a static inner class below).
        if(position == 0)
        {
            return MyTasksFragment.newInstance();
        }
        else
        {
            return CompletedFragment.newInstance();
        }
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        this.currentPosition = position;
        if (object instanceof Fragment) {
            this.currentFragment = (Fragment) object;
        }
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    public void showMenuPanel()
    {
        if(getCurrentPosition() == 0)
        {
            MyTasksFragment fragment = (MyTasksFragment) currentFragment;
            fragment.showMenu();
        }
        else
        {
            CompletedFragment fragment = (CompletedFragment) currentFragment;
            fragment.showMenu();
        }

    }

    public void showAddTaskPanel()
    {

        if(getCurrentPosition() == 0)
        {
            MyTasksFragment fragment = (MyTasksFragment) currentFragment;
            fragment.showAddTask();
        }
        else
        {
            CompletedFragment fragment = (CompletedFragment) currentFragment;
            fragment.showAddTask();
        }
    }

    public void showSortTaskPanel()
    {
        if(getCurrentPosition() == 0)
        {
            MyTasksFragment fragment = (MyTasksFragment) currentFragment;
            fragment.showSort();
        }
        else
        {
            CompletedFragment fragment = (CompletedFragment) currentFragment;
            fragment.showSort();
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}