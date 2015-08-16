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
        selectedTop.add(new TopMenu("", "首頁DEMO1", 0, "com.uitox.asap.CategoryFragment"));
        selectedTop.add(new TopMenu("", "FB DEMO2", 0, "com.uitox.asap.CategoryFragment"));
        selectedTop.add(new TopMenu("", "列表DEMO3", 0, "com.uitox.asap.CategoryFragment"));
        selectedTop.add(new TopMenu("", "分類DEMO4", 0, "com.uitox.asap.ChatFragment"));
        selectedTop.add(new TopMenu("", "分類DEMO5", 0, "com.uitox.asap.ContactsFragment"));
        selectedTop.add(new TopMenu("", "分類DEMO6", 0, "com.uitox.asap.FriendFragment"));
        selectedTop.add(new TopMenu("", "分類DEMO7", 0, "com.uitox.asap.CategoryFragment"));
        selectedTop.add(new TopMenu("", "分類DEMO8", 0, "com.uitox.asap.CategoryFragment"));
    }

    public static List<TopMenu> getSelectedTopMenu() {
        return selectedTop;
    }
}
