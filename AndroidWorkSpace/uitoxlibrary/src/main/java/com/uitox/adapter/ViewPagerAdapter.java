package com.uitox.adapter;

import android.content.Context;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by babyandy on 2015/8/16.
 */
public class ViewPagerAdapter extends ViewPager {

    public ViewPagerAdapter(Context context) {
        super(context);
    }

    public ViewPagerAdapter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof SurfaceView || v instanceof PagerTabStrip) {
            return(true);
        }
        return(super.canScroll(v, checkV, dx, x, y));
    }
}
