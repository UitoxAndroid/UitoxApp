package com.uitox.asap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.uitox.adapter.FragmentAdapter;
import com.uitox.lib.ShowYourMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    //導覽列容器
    private NavigationDrawerFragment mNavigationDrawerFragment;

    //儲存action bar title
    private CharSequence mTitle;

    //action bar
    private ActionBar actionBar;

    //VIEWPAGER要用的容器
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;
    private ViewPager mPageVp;

    //返回建關閉flag
    private static Boolean isExit = false;
    private static Boolean hasTask = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //產生一個導覽列容器
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        //預設取APP名字
        mTitle = getTitle();

        // 綁定導覽
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        //產生VIEWPAGER
        mPageVp = (ViewPager) findViewById(R.id.view_pager);
        mFragmentList.add(new ChatFragment());
        mFragmentList.add(new FriendFragment());
        mFragmentList.add(new ContactsFragment());
        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
        mPageVp.setAdapter(mFragmentAdapter);
        mPageVp.setCurrentItem(0);
        mPageVp.setOffscreenPageLimit(3);
        mPageVp.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //無論選到哪個都變換TITLE
                onSectionAttached(position);

                //更改ACTIONBAR TITLE
                actionBar.setTitle(mTitle);
                invalidateOptionsMenu();
            }
        });
    }

    //選到選單時要觸發的事件
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        //更改原本寫法,因為要用VIEWPAGER的方式,因此直接調用mPageVp.setCurrentItem切換既可
        if (mPageVp != null) {
            mPageVp.setCurrentItem(position);
        }

        //無論選到哪個都變換TITLE
        onSectionAttached(position);
    }

    //選到選單後要顯示的title
    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = "首頁";
                break;
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    //重新配置actionbar
    public void restoreActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(mTitle);
    }

    //產生右上角的menu選單
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    //當右上角的選單被選取後的觸發事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // 指定要呼叫的 Activity Class
            Intent newAct = new Intent();
            newAct.setClass( MainActivity.this, SettingActivity.class );

            // 呼叫新的 Activity Class
            startActivity( newAct );
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //設定時間監聽返回鍵
    Timer tExit = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            isExit = false;
            hasTask = true;
         }
    };

    //返回健要連按兩次才會退出,其餘失效
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit == false) {
                isExit = true;
                ShowYourMessage.msgToShowShort(this,"再按一次後退鍵退出應用程式");
                if (!hasTask) {
                    tExit.schedule(task, 2000);
                }
            } else {
                finish();
            }
        }
        return false;
    }
}
