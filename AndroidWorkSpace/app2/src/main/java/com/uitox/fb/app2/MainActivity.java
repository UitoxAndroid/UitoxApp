package com.uitox.fb.app2;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.uitox.fb.uitoxlibrary.ShowYourDialog;
import com.uitox.fb.uitoxlibrary.ShowYourMessage;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShowYourMessage.msgToShowShort(this, "i'm app 2");
        //我的容器
        listView = (ListView) findViewById(R.id.listView2);

        //所有整理好資料
        list = new ArrayList<String>();
        for (int i = 0; i < 5000; i++) {
            list.add("item " + i);
        }

        //將容器調適為我們可用格式
        Adapter adapter = new Adapter(this);
        listView.setAdapter(adapter);

        ShowYourDialog dialog = new ShowYourDialog(this);
        dialog.ShowDialog();
    }

    /*
    建議實作BaseAdapter的方式
    先釐清BaseAdapter的資料項目(Item)數量是否會變動
    固定的項目數量

    一開始在實體化BaseAdapter時，將資料以固定陣列等方式傳進BaseAdapter的建構子中即可。
    變動的項目數量

    一開始在實體化BaseAdapter時，將資料以可動陣列或是集合的方式傳進BaseAdapter的建構子中即可，往後如果項目數量有變動的話，
    直接改變陣列或是集合後，使用BaseAdapter提供的notifyDataSetChanged方法來通知View資料已改變。千萬不要重新建立一個新的陣列或是集合，
    再使用setAdapter的方式將重新new出來的BaseAdapter指定給View，這種作法是效能最差的。
    如果預期資料變化不會很大，同一個View應只在初始化時，new出一個BaseAdapter並使用setAdapter方法指定給View

    如果預期資料變化不會太大，則只在Activity的onCreate階段或是Fragment的onCreateView階段時，new出一個BaseAdapter並使用setAdapter方法將BaseAdapter指定給View。
    如果後來資料有變動，使用BaseAdapter的notifyDataSetChanged方法來通知View進行更新即可。為了方便，可以在setAdapter前後，將BaseAdapter的參考以物件變數記下。

    使用setAdapter方法後，View的狀態會重置，焦點將會移動到第一個項目；
    使用notifyDataSetChanged方法後，View的狀態不會重置，僅改變有變動的部份，焦點會保留在原本的位置。避免一直重新inflate Layout

    就是本文一開始提到的懶人實作法出現的問題。事實上，BaseAdapter會紀錄不同位置下的convertView物件。
    此處所說的位置並不是只資料項目在陣列或是集合內的position，而是資料項目在View上顯示的位置。
    也就是說，先前已經inflate過Layout，是會儲存在記憶體中的。getView方法所傳入的convertView參數，就是先前這個View的位置上使用getView方法傳回的View，如果沒有，就是null。
    所以，我們可以利用這已經inflate的convertView，再另外紀錄這convertView下的View的參考，就不用在getView時每次都去inflate Layout和findViewByID，或是addView了！

    紀錄convertView下的View的參考的方式，可以另外實作一個class來紀錄，可以利用View所提供的Tag功能，將另外實作並實體化出的類別物件存進View的Tag欄位。
    */
    private class Adapter extends BaseAdapter {

        private LayoutInflater mInflater = null;
        private Context context;
        private int counter = 0; // 紀錄getView重新排版(inflate)的次數．此為研究觀察用，在實際應用時不需紀錄次數

        //建構子
        private Adapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        //取得項目(Item)的數量。通常數量就是從建構子傳入的陣列或是集合大小。
        @Override
        public int getCount() {

            return list.size();
        }

        //取得在這個position位置上的項目(Item)。position通常是資料在陣列或是集合上的位置。
        @Override
        public Object getItem(int position) {

            return null;
        }

        //取得這個position位置上項目(Item)的ID，一般用position的值即可。
        @Override
        public long getItemId(int position) {

            return position;
        }

        //通常會設定與回傳convertView作為顯示在這個position位置的項目(Item)的View。
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //第一種寫法
            /*counter++; //次數+1
            convertView = mInflater.inflate(R.layout.list_view, parent, false);

            String text = (String) list.get(position).toString();

            TextView tv = (TextView) convertView.findViewById(R.id.textView1);
            tv.setText(text);

            Log.i("-getView-", String.valueOf(counter));*/


            //第二種寫法
            Elements elements = null;
            System.out.println("getView " + position + " " + convertView);
            Log.i("-getView-", "getView " + position + " " + convertView);

            if (convertView == null) {
                elements = new Elements();
                //我抽屜裡的設計圖
                convertView = mInflater.inflate(R.layout.list_view, null);
                //裡面有XX元件( 如果有而外需求，必須在 Elements 內添加才可以使用 )
                elements.str1 = (TextView) convertView.findViewById(R.id.textView1);
                convertView.setTag(elements);
            } else {
                elements = (Elements) convertView.getTag();
            }

            //將資料依序放入我擺放的位置
            elements.str1.setText((String) list.get(position).toString());


            return convertView;
        }

    }

    //每格要擺放的元件( 如有而外需求可再添加 )
    private class Elements {
        TextView str1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
