package com.uitox.asap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uitox.adapter.BaseFragment;

public class CategoryFragment extends BaseFragment {
    private String channelName;
    private View view;

    //要顯示的VIEW
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.page_test, container, false);
        TextView tv = (TextView) view.findViewById(R.id.pagetest);
        tv.setText(channelName);
        view = tv;
        return view;
    }

    //該VIEW要娶的資料
    @Override
    public void initData() {

    }

    //接收傳入參數
    @Override
    public void initBundle(Bundle bundle) {
        channelName = bundle.getString("name");
    }

    @Override
    public void updateUI() {

    }
}
