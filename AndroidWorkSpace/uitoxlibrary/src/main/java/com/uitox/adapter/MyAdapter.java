package com.uitox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class MyAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected int layoutId;

    //建構子
    public MyAdapter(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        this.mDatas = datas;
        this.layoutId = layoutId;
        mInflater = LayoutInflater.from(context);
    }

    //取得項目(Item)的數量。通常數量就是從建構子傳入的陣列或是集合大小。
    @Override
    public int getCount() {
        return mDatas.size();
    }

    //取得在這個position位置上的項目(Item)。position通常是資料在陣列或是集合上的位置。
    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    //取得這個position位置上項目(Item)的ID，一般用position的值即可。
    @Override
    public long getItemId(int position) {
        return position;
    }

    //通常會設定與回傳convertView作為顯示在這個position位置的項目(Item)的View。
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, position);
        convert(holder, getItem(position), convertView, position);
        return holder.getConvertView();
    }

    //必須實作的部分
    public abstract void convert(ViewHolder holder, T t, View convertView,int position);
}