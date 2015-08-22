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
        selectedTop.add(new TopMenu("", "1", 0, "com.uitox.asap.ChatFragmentView"));
        selectedTop.add(new TopMenu("", "2", 0, "com.uitox.asap.ContactsFragmentView"));
        selectedTop.add(new TopMenu("", "3", 0, "com.uitox.asap.FriendFragmentView"));
        selectedTop.add(new TopMenu("", "4", 0, "com.uitox.asap.CategoryFragmentView"));
        selectedTop.add(new TopMenu("", "5", 0, "com.uitox.asap.MapsActivity"));

    }

    public static List<TopMenu> getSelectedTopMenu() {
        return selectedTop;
    }
}
