package com.puxtech.weipan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by mac on 15/11/12.
 */

public class ZhangHuPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fgList;
    String[] CONTENT;//title

    public ZhangHuPagerAdapter(FragmentManager fm, ArrayList<Fragment> fgList, String[] CONTENT) {
        super(fm);
        this.CONTENT = CONTENT;
        this.fgList = fgList;
    }

    @Override
    public Fragment getItem(int position) {
        return fgList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return CONTENT[position % CONTENT.length];
    }

    @Override
    public int getCount() {
        return CONTENT.length;
    }
}

