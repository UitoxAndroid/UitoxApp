package com.uitox.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uitox.lib.ShowYourMessage;

/**
 * Created by babyandy on 2015/8/17.
 */
public abstract class BaseFragment extends Fragment {

    //http://blog.csdn.net/manoel/article/details/7577353
    //Fragment生命週期

    private View rootView;
    protected Context context;
    private Boolean hasInitData = false;
    private Boolean hasInitBundle = false;
    private ViewGroup parent;
    protected boolean isVisible;

    //初始化已經完成
    private boolean isPrepared;

    //是否加載過一次
    private boolean mHasLoadedOnce;

    //開始準備產生
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    //產生畫面布局
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = initView(inflater, container, savedInstanceState);
            isPrepared = true;
        }

        parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    //判斷目前畫面是否為可見
    //設定一個flag
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    //可見的時候加載數據
    protected void onVisible() {
        lazyLoad();
    }


    //不可見的時候先不做事情
    protected void onInvisible() {

    }

    //開始跑資料
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }

        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    initData(); //開始做資料綁定
                    hasInitData = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccess) {
                if (isSuccess) {
                    mHasLoadedOnce = true;
                    updateUI();
                } else {
                    ShowYourMessage.msgToShowShort(getActivity(),"讀取資料ERROR");
                }
            }
        }.execute();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!hasInitData) {
            initData();
            hasInitData = true;
        }
    }

    //接收參數
    @Override
    public void setArguments(Bundle bundle) {
        if (!hasInitBundle) {
            initBundle(bundle);
            hasInitBundle = true;
        }
    }

    //結束時要把rootView刪掉
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) rootView.getParent()).removeView(rootView);
    }

    //對VIEW的操作
    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    //必須實作讀取資料
    public abstract void initData();

    //必須實作的接收參數
    public abstract void initBundle(Bundle bundle);

    public abstract void updateUI();
}
