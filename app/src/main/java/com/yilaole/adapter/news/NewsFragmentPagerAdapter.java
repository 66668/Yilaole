package com.yilaole.adapter.news;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yilaole.bean.news.NewsTabBean;
import com.yilaole.ui.fragment.NewsFragment;

import java.util.List;

/**
 * 资讯fragment嵌套子fragment的适配
 */

public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<NewsTabBean> tablist;

    private Context context;

    public NewsFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    public List<NewsTabBean> getList() {
        return tablist;
    }

    public void setList(List<NewsTabBean> list) {
        this.tablist = list;
    }

    @Override
    public Fragment getItem(int position) {
        //资讯fragment
        return NewsFragment.newInstance(position, tablist.get(position));
    }

    @Override
    public int getCount() {
        return tablist.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tablist.get(position).getName();
    }
}
