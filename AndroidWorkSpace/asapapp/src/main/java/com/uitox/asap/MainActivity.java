package com.uitox.asap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.uitox.adapter.FragmentAdapter;
import com.uitox.asapapp.TopMenu;
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

    //存放topmenu和viewpage的LIST
    List<TopMenu> TopMenuList;

    //VIEWPAGER要用的容器
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;
    private ViewPager mPageVp;

    //動態產生tab
    private RadioGroup tab = null;
    private HorizontalScrollView hvChannel;

    //返回建關閉flag
    private static Boolean isExit = false;
    private static Boolean hasTask = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    //初始化VIEW
    private void initView() {
        //產生一個導覽列容器
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        //預設取APP名字
        mTitle = getTitle();

        // 綁定導覽
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        //產生VIEWPAGER
        mPageVp = (ViewPager) findViewById(R.id.view_pager);

        //tab
        hvChannel = (HorizontalScrollView) findViewById(R.id.hvChannel);
        tab = (RadioGroup) findViewById(R.id.tab);

        //取得topmenu
        TopMenuList = GetTopMenu.getSelectedTopMenu();

        //初始化tab
        initTab();

        //初始化VIEWPAGE
        initViewPager();

        //會自動打開....所以關閉
        mNavigationDrawerFragment.close();
    }

    //動態產生topmenu
    private void initTab() {
        for (int i = 0; i < TopMenuList.size(); i++) {
            RadioButton rb = (RadioButton) LayoutInflater.from(this).inflate(R.layout.top_menu_row, null);
            rb.setId(i);
            rb.setText(TopMenuList.get(i).getName());
            rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowYourMessage.msgToShowShort(MainActivity.this, "我切換頁面了(" + TopMenuList.get(v.getId()).getName() + ")");
                }
            });
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.FILL_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            tab.addView(rb, params);
        }

        //tab事件
        tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ShowYourMessage.msgToShowShort(MainActivity.this, "我切換頁面了(" + TopMenuList.get(checkedId).getName() + ")");
            }
        });
    }

    //動態產生VIEWPAGE
    private void initViewPager() {
        for (int i = 0; i < TopMenuList.size(); i++) {
            try {
                //取得entity
                String classname = TopMenuList.get(i).getClassname();

                //動態實例物件
                Fragment frag = (Fragment) Class.forName(classname).newInstance();

                //針對這個class傳遞資料
                Bundle bundle = new Bundle();
                bundle.putString("name", TopMenuList.get(i).getName());
                frag.setArguments(bundle);

                //塞進FragmentList
                mFragmentList.add(frag);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
        mPageVp.setAdapter(mFragmentAdapter);
        mPageVp.setCurrentItem(0);
        mPageVp.setOffscreenPageLimit(2);
        mPageVp.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //無論選到哪個都變換TITLE
                onSectionAttached(position);

                setTab(position);

                //更改ACTIONBAR TITLE
                actionBar.setTitle(mTitle);
                invalidateOptionsMenu();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //滑動tab
    private void setTab(int idx) {
        RadioButton rb = (RadioButton) tab.getChildAt(idx);
        rb.setChecked(true);
        int left = rb.getLeft();
        int width = rb.getMeasuredWidth();
        DisplayMetrics metrics = new DisplayMetrics();
        super.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        int len = left + width / 2 - screenWidth / 2;
        hvChannel.smoothScrollTo(len, 0);//滑動ScroollView
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
        if (TopMenuList != null) {
            mTitle = TopMenuList.get(number).getName();
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
            newAct.setClass(MainActivity.this, SettingActivity.class);

            // 呼叫新的 Activity Class
            startActivity(newAct);
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
                ShowYourMessage.msgToShowShort(this, "再按一次後退鍵退出應用程式");
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
