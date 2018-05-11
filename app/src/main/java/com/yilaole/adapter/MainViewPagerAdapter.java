package com.yilaole.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.yilaole.base.BaseFragmentPagerAdapter;
import com.yilaole.ui.fragment.NavHomeFragment;
import com.yilaole.ui.fragment.NavMineFragment;
import com.yilaole.ui.fragment.NavNewsFragment;

/**
 * mainActivity中 fragment适配adapter、
 * 支持横竖屏切换
 */
public class MainViewPagerAdapter extends BaseFragmentPagerAdapter {

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    //
    @Override
    public void initFragments() {
        //主布局的fragment
        addFragment(NavHomeFragment.newInstance());
        //        addFragment(NavFilterFragment.newInstance());
        addFragment(NavNewsFragment.newInstance());
        addFragment(NavMineFragment.newInstance());
    }

    @Override
    protected String getKey() {
        return null;
    }

    //固定个数的fragment
    @Override
    protected int getFragmentCount() {
        return 3;
    }

}
