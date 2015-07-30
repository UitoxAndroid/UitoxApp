package com.uitox.fb.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class EventActivity extends ActionBarActivity implements View.OnClickListener {

    private Toast toast;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        toast = Toast.makeText(EventActivity.this, "onRestoreInstanceState!~2", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        toast = Toast.makeText(EventActivity.this, "onSaveInstanceState!~2", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        toast = Toast.makeText(EventActivity.this, "onDestroy!~2", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        toast = Toast.makeText(EventActivity.this, "onResume!~2", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onPause() {
        super.onPause();

        toast = Toast.makeText(EventActivity.this, "onPause!~2", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        toast = Toast.makeText(EventActivity.this, "onStart!~2", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        toast = Toast.makeText(EventActivity.this, "onRestart!~2", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        toast = Toast.makeText(EventActivity.this, "onStop!~2", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Intent intent = getIntent();
        String val_1 = intent.getStringExtra("val_1");
        String val_2 = intent.getStringExtra("val_2");
        if (!(val_1.isEmpty() || val_2.isEmpty())) {
            toast = Toast.makeText(EventActivity.this, "val_1=>" + val_1 + ",val_2=>" + val_2, Toast.LENGTH_SHORT);
            toast.show();
        }

        //綁定
        Button bt_dial = (Button) findViewById(R.id.button1);
        Button bt_dia2 = (Button) findViewById(R.id.button2);
        Button bt_dia3 = (Button) findViewById(R.id.button3);

        //第一種
        bt_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast = Toast.makeText(EventActivity.this, "i'm function 1", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        //第二種
        bt_dia2.setOnClickListener(new bt_dia2());

        //第三種
        bt_dia3.setOnClickListener(this);

        toast = Toast.makeText(EventActivity.this, "onCreate!~2", Toast.LENGTH_SHORT);
        toast.show();
    }

    private class bt_dia2 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            toast = Toast.makeText(EventActivity.this, "i'm function 2", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //第三種
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button3:
                toast = Toast.makeText(EventActivity.this, "i'm function 3", Toast.LENGTH_SHORT);
                toast.show();
                break;
            default:
                break;
        }
    }

    //第四種--直接寫在XML
    public void bt_dia4(View view) {
        toast = Toast.makeText(EventActivity.this, "i'm function 4", Toast.LENGTH_SHORT);
        toast.show();
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
