package com.uitox.asap;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.uitox.asapapp.TopMenu;

import java.util.List;

public class CategoryFragment extends Fragment {
    private String channelName;
    private View view;
    private RadioGroup tab = null;

    //存放topmenu和viewpage的LIST
    List<TopMenu> TopMenuList;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {//优化View减少View的创建次数
            view = inflater.inflate(R.layout.page_1, container, false);
        }

        //如果View已经添加到容器中，要进行删除，负责会报错
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    //接收參數
    @Override
    public void setArguments(Bundle bundle) {
        channelName = bundle.getString("name");
    }

}
