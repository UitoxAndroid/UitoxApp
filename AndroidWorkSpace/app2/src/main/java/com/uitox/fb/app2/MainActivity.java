package com.uitox.fb.app2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.uitox.com.uitox.adapter.MyAdapter;
import com.uitox.com.uitox.adapter.ViewHolder;
import com.uitox.fb.entity.Movie;
import com.uitox.fb.uitoxlibrary.ShowYourDialog;
import com.uitox.fb.uitoxlibrary.ShowYourMessage;
import com.uitox.http.GsonRequest;
import com.uitox.http.NetWorkTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private Gallery gallery;
    private List<Movie> movieList = new ArrayList<Movie>();
    private MyAdapter adapter;

    AutoCompleteTextView auto_tv;
    ArrayList<String> names;
    ArrayAdapter<String> adapter_c;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("首頁");
        setContentView(R.layout.list_view_layout);

        auto_tv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
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
                ShowYourMessage.msgToShowShort(getApplicationContext(), "[OnItemClickListener]：" + movieList.get(position).getTitle());
            }
        });


        listView = (ListView) findViewById(R.id.list);
        adapter = new MyAdapter<Movie>(this, movieList, R.layout.list_row) {
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
                ShowYourMessage.msgToShowShort(getApplicationContext(), "[OnItemClickListener]：" + movieList.get(position).getTitle());
            }
        });

        gallery = (Gallery) findViewById(R.id.gallery);
        gallery.setAdapter(adapter);
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShowYourMessage.msgToShowShort(getApplicationContext(), "[OnItemClickListener]：" + movieList.get(position).getTitle());
            }
        });
        //data
        getdate2();

        //tost
        ShowYourMessage.msgToShowShort(this, "i'm app 2");

        //dialog
        ShowYourDialog dialog = new ShowYourDialog(this);
        dialog.ShowDialog();
    }

    //單筆解析
    //大部分不會用在view列表才是
    public void getdate() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("un", "852041173");
        hashMap.put("pw", "852041173abc111");

        GsonRequest<Movie> notifyRequest = new GsonRequest<Movie>(
                Request.Method.POST,
                "http://www.shihjie.com/phpinfo.php",
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
                        ShowYourMessage.msgToShowShort(MainActivity.this, "ERROR");
                    }
                }, hashMap);
        NetWorkTool.getInstance(this).addToRequestQueue(notifyRequest);
    }

    //多筆解析
    //大部分會用在listview
    public void getdate2() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("un", "852041173");
        hashMap.put("pw", "852041173abc");

        GsonRequest<Movie> notifyRequest = new GsonRequest<Movie>(
                this,
                Request.Method.POST,
                "http://www.shihjie.com/phpinfo.php",
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
                        ShowYourMessage.msgToShowShort(MainActivity.this, "ERROR");
                    }
                }, hashMap);
        NetWorkTool.getInstance(this).addToRequestQueue(notifyRequest);
    }

    public void updateList(String place) {
        url = "http://www.shihjie.com/phpinfo.php";
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("un", "852041173");
        hashMap.put("pw", "852041173abc");
        hashMap.put("str", place);
        GsonRequest<Movie> notifyRequest = new GsonRequest<Movie>(
                this,
                Request.Method.POST,
                url,
                Movie.class,
                null,
                new GsonRequest.OnListResponseListener<Movie>() {
                    @Override
                    public void onResponse(List<Movie> response) {
                        for (Movie res : response) {
                            names.add(res.getTitle());
                        }

                        adapter_c = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, names) {
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
                        ShowYourMessage.msgToShowShort(MainActivity.this, "ERROR2");
                    }
                }, null);
        NetWorkTool.getInstance(this).addToRequestQueue(notifyRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
        NetWorkTool.getInstance(this).cancelAll(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        setSearch(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setSearch(Menu menu) {
        final MenuItem item = menu.findItem(R.id.action_search);
        SearchView sv = (SearchView) MenuItemCompat.getActionView(item);

        if (sv != null) {
            sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String Text) {
                    ShowYourMessage.msgToShowShort(MainActivity.this, Text);
                    MenuItemCompat.collapseActionView(item);
                    Intent intent = new Intent(MainActivity.this, SearchResaultActivity.class);
                    intent.putExtra("str", Text);
                    startActivity(intent);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String Text) {
                    ShowYourMessage.msgToShowShort(MainActivity.this, Text);
                    return false;
                }
            });
        }
    }
}
