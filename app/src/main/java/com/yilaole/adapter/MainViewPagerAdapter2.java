package com.yilaole.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yilaole.ui.fragment.NavHomeFragment;
import com.yilaole.ui.fragment.NavMineFragment;
import com.yilaole.ui.fragment.NavNewsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * mainActivity中 fragment适配adapter
 * 不支持横竖屏切换
 */
public class MainViewPagerAdapter2 extends FragmentPagerAdapter {
    protected List<Fragment> fragments = new ArrayList<>();

    public MainViewPagerAdapter2(FragmentManager fm) {
        super(fm);

        fragments.clear();
        fragments.add(NavHomeFragment.newInstance());
//        fragments.add(NavFilterFragment.newInstance());
        fragments.add(NavNewsFragment.newInstance());
        fragments.add(NavMineFragment.newInstance());

    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


}
