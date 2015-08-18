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
        selectedTop.add(new TopMenu("", "1", 0, "com.uitox.asap.ChatFragment"));
        selectedTop.add(new TopMenu("", "2", 0, "com.uitox.asap.ChatFragment"));
        selectedTop.add(new TopMenu("", "3", 0, "com.uitox.asap.ChatFragment"));
        selectedTop.add(new TopMenu("", "4", 0, "com.uitox.asap.ChatFragment"));
        selectedTop.add(new TopMenu("", "5", 0, "com.uitox.asap.ChatFragment"));
        selectedTop.add(new TopMenu("", "6", 0, "com.uitox.asap.ChatFragment"));
        selectedTop.add(new TopMenu("", "7", 0, "com.uitox.asap.ChatFragment"));
        selectedTop.add(new TopMenu("", "8", 0, "com.uitox.asap.ChatFragment"));
        selectedTop.add(new TopMenu("", "9", 0, "com.uitox.asap.ChatFragment"));
        selectedTop.add(new TopMenu("", "10", 0, "com.uitox.asap.ChatFragment"));
        selectedTop.add(new TopMenu("", "11", 0, "com.uitox.asap.ContactsFragment"));
        selectedTop.add(new TopMenu("", "12", 0, "com.uitox.asap.ContactsFragment"));
        selectedTop.add(new TopMenu("", "13", 0, "com.uitox.asap.ContactsFragment"));
        selectedTop.add(new TopMenu("", "14", 0, "com.uitox.asap.ContactsFragment"));
        selectedTop.add(new TopMenu("", "15", 0, "com.uitox.asap.ContactsFragment"));
        selectedTop.add(new TopMenu("", "16", 0, "com.uitox.asap.ContactsFragment"));
        selectedTop.add(new TopMenu("", "17", 0, "com.uitox.asap.ContactsFragment"));
        selectedTop.add(new TopMenu("", "18", 0, "com.uitox.asap.ContactsFragment"));
        selectedTop.add(new TopMenu("", "19", 0, "com.uitox.asap.ContactsFragment"));
        selectedTop.add(new TopMenu("", "20", 0, "com.uitox.asap.ContactsFragment"));
    }

    public static List<TopMenu> getSelectedTopMenu() {
        return selectedTop;
    }
}
