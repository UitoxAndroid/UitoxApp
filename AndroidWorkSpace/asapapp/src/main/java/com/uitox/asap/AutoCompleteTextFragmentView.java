package com.uitox.asap;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.uitox.asapapp.Movie;
import com.uitox.http.GsonRequest;
import com.uitox.http.NetParams;
import com.uitox.http.NetWorkTool;
import com.uitox.lib.ShowYourMessage;
import com.uitox.view.BaseFragmentView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AutoCompleteTextFragmentView extends BaseFragmentView {

    private View view;
    private AutoCompleteTextView auto_tv;
    private ArrayList<String> names;
    private ArrayAdapter<String> adapter_c;

    //要顯示的VIEW
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.autocomplete_text_fragment_view, container, false);
        auto_tv = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);
        auto_tv.setThreshold(0);
        names = new ArrayList<String>();
        auto_tv.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() <= 3) {
                    names = new ArrayList<String>();
                    updateList(s.toString());
                }
            }
        });
        auto_tv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShowYourMessage.msgToShowShort(getActivity(), "[OnItemClickListener]：" + names.get(position));
            }
        });
        return view;
    }

    //該VIEW要娶的資料
    @Override
    public void initData() {

    }

    //接收傳入參數
    @Override
    public void initBundle(Bundle bundle) {

    }

    @Override
    public void updateUI() {

    }

    public void updateList(String place) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("un", "852041173");
        hashMap.put("pw", "852041173abc");
        hashMap.put("str", place);
        GsonRequest<Movie> notifyRequest = new GsonRequest<Movie>(
                getActivity(),
                Request.Method.POST,
                NetParams.getSHIHJIEUrl("/phpinfo.php"),
                Movie.class,
                null,
                new GsonRequest.OnListResponseListener<Movie>() {
                    @Override
                    public void onResponse(List<Movie> response) {
                        for (Movie res : response) {
                            names.add(res.getTitle());
                        }

                        adapter_c = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, names) {
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                View view = super.getView(position, convertView, parent);
                                TextView text = (TextView) view.findViewById(android.R.id.text1);
                                text.setTextColor(Color.BLACK);
                                return view;
                            }
                        };
                        auto_tv.setAdapter(adapter_c);
                        adapter_c.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        ShowYourMessage.msgToShowShort(getActivity(), "ERROR2");
                    }
                }, null);
        NetWorkTool.getInstance(getActivity()).addToRequestQueue(notifyRequest);
    }
}
