package com.uitox.asap;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.uitox.adapter.MyAdapter;
import com.uitox.adapter.ViewHolder;
import com.uitox.fb.entity.Movie;
import com.uitox.http.GsonRequest;
import com.uitox.http.NetParams;
import com.uitox.http.NetWorkTool;
import com.uitox.lib.ShowYourDialog;
import com.uitox.lib.ShowYourMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactsFragment extends Fragment {
    private ListView listView;
    private List<Movie> movieList = new ArrayList<Movie>();
    private MyAdapter adapter;
    private Gallery gallery;
    AutoCompleteTextView auto_tv;
    ArrayList<String> names;
    ArrayAdapter<String> adapter_c;
    String url;
    ScrollView sv;

    public ContactsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_3, container, false);

        sv = (ScrollView) view.findViewById(R.id.scrollView);

        auto_tv = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView2);
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
                ShowYourMessage.msgToShowShort(getActivity(), "[OnItemClickListener]：" + movieList.get(position).getTitle());
            }
        });

        listView = (ListView) view.findViewById(R.id.listView2);
        adapter = new MyAdapter<Movie>(getActivity(), movieList, R.layout.list_row) {
            @Override
            public void convert(ViewHolder holder, Movie movie, View convertView) {
                holder.setText(R.id.title, movie.getTitle())
                        .setText(R.id.releaseYear, String.valueOf(movie.getYear()))
                        .setText(R.id.rating, String.valueOf(movie.getRating()))
                        .setText(R.id.genre, movie.getGenre())
                        .setImageUrl(R.id.thumbnail, movie.getImageUrl());
            }
        };
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShowYourMessage.msgToShowShort(getActivity(), "[OnItemClickListener]：" + movieList.get(position).getTitle());
            }
        });

        GridView grid = (GridView) view.findViewById(R.id.gridView);
        grid.setAdapter(adapter);

        gallery = (Gallery) view.findViewById(R.id.gallery2);
        gallery.setAdapter(adapter);
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShowYourMessage.msgToShowShort(getActivity(), "[OnItemClickListener]：" + movieList.get(position).getTitle());
            }
        });
        //data
        getdate2();

        //tost
        ShowYourMessage.msgToShowShort(getActivity(), "i'm app 2");

        //dialog
        ShowYourDialog dialog = new ShowYourDialog(getActivity());
        dialog.ShowDialog();

        return view;
    }

    //單筆解析
    //大部分不會用在view列表才是
    public void getdate() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("un", "852041173");
        hashMap.put("pw", "852041173abc111");

        GsonRequest<Movie> notifyRequest = new GsonRequest<Movie>(
                Request.Method.POST,
                NetParams.getSHIHJIEUrl("/phpinfo.php"),
                Movie.class,
                null,
                new Response.Listener<Movie>() {
                    @Override
                    public void onResponse(Movie response) {
                        Log.i("test", response.getTitle());
                        ArrayList<String> genre = response.getGenre();
                        for (String res : genre) {
                            Log.i("test", res);
                        }
                        movieList.add(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        ShowYourMessage.msgToShowShort(getActivity(), "ERROR");
                    }
                }, hashMap);
        NetWorkTool.getInstance(getActivity()).addToRequestQueue(notifyRequest);
    }

    //多筆解析
    //大部分會用在listview
    public void getdate2() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("un", "852041173");
        hashMap.put("pw", "852041173abc");

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
                            movieList.add(res);
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        ShowYourMessage.msgToShowShort(getActivity(), "ERROR");
                    }
                }, hashMap);
        NetWorkTool.getInstance(getActivity()).addToRequestQueue(notifyRequest);
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

    @Override
    public void onStop() {
        super.onStop();
        NetWorkTool.getInstance(getActivity()).cancelAll(this);
    }
}