package com.yilaole.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 */

public abstract class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    protected List<Fragment> fragments = new ArrayList<>();
    private FragmentManager fragmentManager;


    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentManager = fm;
        fragments.clear();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    protected void addFragment(Fragment fragment) {
        if (fragment != null && !fragments.contains(fragment)) {
            fragments.add(fragment);
        }
    }

    /**
     * 初始化所有fragment，并添加进list
     */
    public abstract void initFragments();

    /**
     * 保存fragment和获取fragment使用的key
     *
     * @return
     */
    protected abstract String getKey();

    /**
     * fragment的个数，使用常量
     *
     * @return
     */
    protected abstract int getFragmentCount();

    /**
     * 页面恢复数据时恢复fragment
     */
    public void restoreFragments(Bundle savedInstanceState) {
        for (int i = 0; i < getFragmentCount(); i++) {
            Fragment fragment = fragmentManager.getFragment(savedInstanceState, getKey() + i);
            addFragment(fragment);
        }
    }

    /**
     * onSaveInstanceState时保存fragment
     *
     * @param outState
     */
    public void saveFragments(Bundle outState) {
        for (int i = 0; i < getFragmentCount(); i++) {
            if (i < fragments.size()) {
                fragmentManager.putFragment(outState, getKey() + i, getItem(i));
            }
        }
    }

    /**
     * 移除fragment
     */
    public void clear() {
        if (fragmentManager != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null) {
                    if (fragmentManager.findFragmentByTag(fragment.getTag()) != null) {
                        fragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss();
                    }
                }
            }
        }
        fragments.clear();
    }
}
