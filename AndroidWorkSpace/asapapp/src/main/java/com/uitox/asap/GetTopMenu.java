package com.uitox.asap;

import com.uitox.asapapp.TopMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by babyandy on 2015/8/15.
 */
public class GetTopMenu {
    private static List<TopMenu> selectedTop = new ArrayList<TopMenu>();

    static {
        selectedTop.add(new TopMenu("", "Map Demo", 0, "com.uitox.asap.MapsActivity"));
        selectedTop.add(new TopMenu("", "輪播 Demo", 0, "com.uitox.asap.BannerFragmentView"));
        selectedTop.add(new TopMenu("", "ListView + ScrollView Demo", 0, "com.uitox.asap.ListViewANDScrollViewFragmentView"));
        selectedTop.add(new TopMenu("", "AutoComplete Demo", 0, "com.uitox.asap.AutoCompleteTextFragmentView"));
        selectedTop.add(new TopMenu("", "ListView", 0, "com.uitox.asap.ListViewFragmentView"));
        selectedTop.add(new TopMenu("", "多選單 Demo", 0, "com.uitox.asap.MultipleLevelFragmentView"));
    }

    public static List<TopMenu> getSelectedTopMenu() {
        return selectedTop;
    }
}
