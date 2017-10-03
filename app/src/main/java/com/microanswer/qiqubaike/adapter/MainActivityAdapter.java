package com.microanswer.qiqubaike.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.microanswer.qiqubaike.interfacee.MainPageAdapter;

/**
 * Created by Micro on 2017-10-3.
 */

public class MainActivityAdapter extends FragmentPagerAdapter {

    private MainPageAdapter mainPageAdapter;

    public MainActivityAdapter(MainPageAdapter mainPageAdapter) {
        super(mainPageAdapter.getSupportFragmentManager());
        this.mainPageAdapter = mainPageAdapter;
    }

    @Override
    public Fragment getItem(int position) {
        return mainPageAdapter.getFragment(position);
    }

    @Override
    public int getCount() {
        return this.mainPageAdapter.getPageCount();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.mainPageAdapter.getFragmentTitle(position);
    }
}
