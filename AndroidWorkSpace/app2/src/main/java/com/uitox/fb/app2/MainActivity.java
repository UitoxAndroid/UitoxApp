package com.uitox.fb.app2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_layout);

        listView = (ListView) findViewById(R.id.list);
        adapter = new MyAdapter<Movie>(this, movieList, R.layout.list_row) {
            @Override
            public void convert(ViewHolder holder, Movie movie,View convertView) {
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
    public void getdate(){
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
                        for(String res: genre){
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
                },hashMap);
        NetWorkTool.getInstance(this).addToRequestQueue(notifyRequest);
    }

    //多筆解析
    //大部分會用在listview
    public void getdate2(){
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
                        for(Movie res: response){
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
                },hashMap);
        NetWorkTool.getInstance(this).addToRequestQueue(notifyRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
        NetWorkTool.getInstance(this).cancelAll(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
