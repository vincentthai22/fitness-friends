package com.example.vincent.fitnessfriends;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

/**
 * Created by Vincent on 11/1/2016.
 */

public class FragmentPageAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[] { "Profile", "Friends", "Routines", "Search" };
    private Context context;

    public FragmentPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {                                              //cases are respective to the tabTitles array.
            case 0:                                                     //make a new xml file to change the layout of
                return ProfileFragment.newInstance(position + 1);       //a desired individual fragment.
            default:
                return FriendsFragment.newInstance(position + 1);       //Ex: case 0 is respective to index 0 which is
        }                                                               //profile frag. Within ProfileFragment.java the
    }                                                                   //fragment is assigned a separate view (xml file).

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
