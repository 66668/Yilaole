package com.yilaole.adapter.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yilaole.bean.home.HomeTabBean;
import com.yilaole.ui.fragment.HomeInstitutionFragment;

import java.util.List;

/**
 * 主页嵌套 机构fragment 适配
 */

public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<HomeTabBean> tabList;
    private Context context;
    private String city;

    public HomeFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return HomeInstitutionFragment.newInstance(position, tabList.get(position).getType(),city);
    }

    public void setTabList(List<HomeTabBean> tabList) {
        this.tabList = tabList;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int getCount() {
        return tabList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabList.get(position).getName();
    }
}
