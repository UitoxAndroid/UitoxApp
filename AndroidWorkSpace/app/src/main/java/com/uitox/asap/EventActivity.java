package com.uitox.asap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.uitox.fb.uitoxlibrary.ShowYourMessage;


public class EventActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ShowYourMessage.msgToShowShort(this, "onRestoreInstanceState!~2");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ShowYourMessage.msgToShowShort(this, "onSaveInstanceState!~2");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShowYourMessage.msgToShowShort(this, "onDestroy!~2");
    }

    @Override
    protected void onResume() {
        super.onResume();
        ShowYourMessage.msgToShowShort(this, "onResume!~2");
    }

    @Override
    protected void onPause() {
        super.onPause();
        ShowYourMessage.msgToShowShort(this, "onPause!~2");
    }

    @Override
    protected void onStart() {
        super.onStart();
        ShowYourMessage.msgToShowShort(this, "onStart!~2");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ShowYourMessage.msgToShowShort(this, "onRestart!~2");
    }

    @Override
    protected void onStop() {
        super.onStop();
        ShowYourMessage.msgToShowShort(this, "onStop!~2");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Intent intent = getIntent();
        String val_1 = intent.getStringExtra("val_1");
        String val_2 = intent.getStringExtra("val_2");
        if (!(val_1.isEmpty() || val_2.isEmpty())) {
            ShowYourMessage.msgToShowShort(this, "val_1=>" + val_1 + ",val_2=>" + val_2);
        }

        //綁定
        Button bt_dial = (Button) findViewById(R.id.button1);
        Button bt_dia2 = (Button) findViewById(R.id.button2);
        Button bt_dia3 = (Button) findViewById(R.id.button3);

        //第一種
        bt_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowYourMessage.msgToShowShort(EventActivity.this, "i'm function 1");
            }
        });

        //第二種
        bt_dia2.setOnClickListener(new bt_dia2());

        //第三種
        bt_dia3.setOnClickListener(this);

        ShowYourMessage.msgToShowShort(this, "onCreate!~2");
    }

    private class bt_dia2 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            ShowYourMessage.msgToShowShort(EventActivity.this, "i'm function 2");
        }
    }

    //第三種
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button3:
                ShowYourMessage.msgToShowShort(this, "i'm function 3");
                break;
            default:
                break;
        }
    }

    //第四種--直接寫在XML
    public void bt_dia4(View view) {
        ShowYourMessage.msgToShowShort(this, "i'm function 4");
    }

    public void bt_dia5(View view) {
        Intent resultIntent = new Intent(view.getContext(), ArrayListAcrivity.class);
        startActivity(resultIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
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
