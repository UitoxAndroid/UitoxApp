package com.uitox.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.uitox.http.NetWorkTool;
import com.uitox.uitoxlibrary.R;

import java.util.List;

public class ViewHolder {
    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    private ImageLoader imageLoader;
    private Context context;

    public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        this.context = context;
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);

    }

    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public TextView setTextReturnView(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return tv;
    }

    public ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    //特殊
    public ViewHolder setText(int viewId, List text) {
        TextView tv = getView(viewId);
        String genreStr = "";
        for (Object str : text) {
            genreStr += str + ", ";
        }
        genreStr = genreStr.length() > 0 ? genreStr.substring(0,
                genreStr.length() - 2) : genreStr;
        tv.setText(genreStr);
        return this;
    }

    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bm);
        return this;
    }

    public ImageButton setImageButtonReturnView(int viewId, int imageButton) {
        ImageButton ib = getView(viewId);
        ib.setBackgroundResource(imageButton);
        ib.setFocusable(false); //當view裡有Button,CheckBox等控件，focus會默認給他們，所以要將子控件focusable設為false
        return ib;
    }

    public ViewHolder setImageButton(int viewId, int imageButton) {
        ImageButton ib = getView(viewId);
        ib.setImageResource(imageButton);
        return this;
    }

    public ViewHolder setImageUrl(int viewId, String url) {
        if (imageLoader == null) {
            imageLoader = NetWorkTool.getInstance(context).getImageLoader();
        }
        NetworkImageView iv = getView(viewId);
        iv.setDefaultImageResId(R.drawable.loading);
        iv.setImageUrl(url, imageLoader);
        return this;
    }

    public View getConvertView() {
        return mConvertView;
    }

}