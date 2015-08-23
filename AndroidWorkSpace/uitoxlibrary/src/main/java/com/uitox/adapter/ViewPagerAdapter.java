package com.uitox.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by babyandy on 2015/8/23.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private FragmentAdapter mFragmentAdapter;
    public ViewPagerAdapter(FragmentAdapter FragmentAdapter) {
        this.mFragmentAdapter = FragmentAdapter;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //container.removeView(list.get(position));
    }

}
