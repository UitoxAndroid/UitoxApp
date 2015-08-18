package com.uitox.asap;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uitox.adapter.BaseFragment;
import com.uitox.http.ToJsonString;

import java.util.ArrayList;
import java.util.HashMap;

public class FriendFragment extends BaseFragment {

    public FriendFragment() {
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_2, container, false);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("un", "852041173");
        hashMap.put("pw", "852041173abc111");

        ArrayList<String> list = new ArrayList<>();
        list.add("test1");
        list.add("test2");

        ToJsonString test = new ToJsonString();
        Log.i("testgson", test.toJson(hashMap));
        Log.i("testgson", test.toJson(list));

        HashMap<String, String> hashMap2 = new HashMap<>();
        hashMap2.put("un2", "852041173");
        hashMap2.put("pw2", "852041173abc111");

        ArrayList<String> list2 = new ArrayList<>();
        list2.add("test12");
        list2.add("test22");
        hashMap2.put("pw3", test.toJson(list2));
        Log.i("testgson", test.toJson(hashMap2));

        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initBundle(Bundle bundle) {

    }

}
