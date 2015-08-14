package com.example.babyandy.asap;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.uitox.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {


    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;
    private ViewPager mPageVp;
    private TextView mTabContactsTv, mTabChatTv, mTabFriendTv;
    private ImageView mTabLineIv;
    private ChatFragment ChatFragment;
    private FriendFragment FriendFragment;
    private ContactsFragment ContactsFragment;
    private int currentIndex;
    private int screenWidth;
    private ActionBar actionBar;
    private ListView lstDrawer;
    private DrawerLayout layDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private String[] drawer_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActionBar();
        findById();
        init();
        initTabLineWidth();
    }

    private void initActionBar(){
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private void findById() {
        mTabContactsTv = (TextView) this.findViewById(R.id.id_contacts_tv);
        mTabChatTv = (TextView) this.findViewById(R.id.id_chat_tv);
        mTabFriendTv = (TextView) this.findViewById(R.id.id_friend_tv);
        mTabLineIv = (ImageView) this.findViewById(R.id.id_tab_line_iv);
        mPageVp = (ViewPager) this.findViewById(R.id.view_page);
    }

    private void init() {
        ChatFragment = new ChatFragment();
        FriendFragment = new FriendFragment();
        ContactsFragment = new ContactsFragment();
        mFragmentList.add(ChatFragment);
        mFragmentList.add(FriendFragment);
        mFragmentList.add(ContactsFragment);

        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
        mPageVp.setAdapter(mFragmentAdapter);
        mPageVp.setCurrentItem(0);
        mPageVp.setOffscreenPageLimit(3);
        mPageVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }

            /**
             * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比
             * offsetPixels:当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int position, float offset, int offsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv.getLayoutParams();

                if (currentIndex == 0 && position == 0) {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex * (screenWidth / 3));
                } else if (currentIndex == 1 && position == 0) {
                    lp.leftMargin = (int) (-(1 - offset) * (screenWidth * 1.0 / 3) + currentIndex * (screenWidth / 3));
                } else if (currentIndex == 1 && position == 1) {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex * (screenWidth / 3));
                } else if (currentIndex == 2 && position == 1) {
                    lp.leftMargin = (int) (-(1 - offset) * (screenWidth * 1.0 / 3) + currentIndex * (screenWidth / 3));
                }
                mTabLineIv.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position) {
                    case 0:
                        mTabChatTv.setTextColor(Color.BLUE);
                        break;
                    case 1:
                        mTabFriendTv.setTextColor(Color.BLUE);
                        break;
                    case 2:
                        mTabContactsTv.setTextColor(Color.BLUE);
                        break;
                }
                currentIndex = position;
            }
        });
    }

    /**
     * 设置滑动条的宽度为屏幕的1/3(根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv.getLayoutParams();
        lp.width = screenWidth / 3;
        mTabLineIv.setLayoutParams(lp);
    }

    /**
     * 重置颜色
     */
    private void resetTextView() {
        mTabChatTv.setTextColor(Color.BLACK);
        mTabFriendTv.setTextColor(Color.BLACK);
        mTabContactsTv.setTextColor(Color.BLACK);
    }

    public void setPage(View v) {
        int viewpage = Integer.valueOf((String) v.getTag());
        mPageVp.setCurrentItem(viewpage);
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
