package com.uitox.asap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.uitox.adapter.MyAdapter;
import com.uitox.adapter.ViewHolder;
import com.uitox.asapapp.Movie;
import com.uitox.http.GsonRequest;
import com.uitox.http.NetParams;
import com.uitox.http.NetWorkTool;
import com.uitox.lib.ShowYourMessage;
import com.uitox.view.BaseFragmentView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListViewANDScrollViewFragmentView extends BaseFragmentView {
    private ListView listView;
    private List<Movie> movieList = new ArrayList<Movie>();
    private MyAdapter adapter;
    private ScrollView sv;

    public ListViewANDScrollViewFragmentView() {
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_and_scrollview_fragment_view, container, false);
        sv = (ScrollView) view.findViewById(R.id.scrollView);
        listView = (ListView) view.findViewById(R.id.listView2);
        adapter = new MyAdapter<Movie>(getActivity(), movieList, R.layout.list_row) {
            @Override
            public void convert(ViewHolder holder, Movie movie, View convertView, final int position) {
                holder.setText(R.id.title, movie.getTitle())
                        .setText(R.id.releaseYear, String.valueOf(movie.getYear()))
                        .setText(R.id.rating, String.valueOf(movie.getRating()))
                        .setText(R.id.genre, movie.getGenre())
                        .setImageUrl(R.id.thumbnail, movie.getImageUrl())
                        .setImageButtonReturnView(R.id.imageButton, R.drawable.ic_cart).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ShowYourMessage.msgToShowShort(getActivity(), String.valueOf(Integer.valueOf(position)));
                            }
                        });
            }
        };
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShowYourMessage.msgToShowShort(getActivity(), "[OnItemClickListener]：" + movieList.get(position).getTitle());
            }
        });
        return view;
    }

    @Override
    public void initData() {
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

    @Override
    public void initBundle(Bundle bundle) {

    }

    @Override
    public void onStop() {
        super.onStop();
        NetWorkTool.getInstance(getActivity()).cancelAll(this);
    }

    @Override
    public void updateUI() {

    }
}
