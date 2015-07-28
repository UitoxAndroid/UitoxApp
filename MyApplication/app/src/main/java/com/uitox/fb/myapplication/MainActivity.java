package com.uitox.fb.myapplication;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;

import java.util.Arrays;

public class MainActivity extends ActionBarActivity {

    private LoginButton loginButton;          // Facebook SDK的登入按鈕
    private TextView loginInfo;               // 顯示使用者資訊用
    private GraphUser user;                   // Facebook SDK登入後取得的使用者資訊物件
    private UiLifecycleHelper uiHelper;       // Facebook SDK介面幫助類別
    private ProfilePictureView userimage;     // Facebook SDK的照片元件

    @Override
    protected void onResume()
    {
        super.onResume();

        // 紀錄應用程式被「安裝」和「執行」的事件。
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        // 紀錄應用程式「結束」的事件。
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 關聯UI元件
        loginInfo = (TextView)findViewById(R.id.loginInfo);
        userimage = (ProfilePictureView) this.findViewById(R.id.profilePicture);
        loginButton = (LoginButton) findViewById(R.id.authButton);

        // 建立UiLifecycleHelper物件，並傳入Session.StatusCallback處理臉書登入狀態事件
        uiHelper = new UiLifecycleHelper(this, callback);

        // 設定需要取得的Facebook權限
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile", "user_friends"));

        // 設定收到來自Facebook的使用者資訊傾聽器
        loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback()
        {
            @Override
            public void onUserInfoFetched(GraphUser user)
            {
                MainActivity.this.user = user;
                updateUI();
            }
        });

        // 設定Session.Callback物件給LoginButton
        loginButton.setSessionStatusCallback(callback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // 接收Facebook Activity的回傳值，並交給UiLifecycleHelper物件處理
        uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);
    }

    // Facebook SDK狀態Callbak物件
    Session.StatusCallback callback = new Session.StatusCallback()
    {
        @Override
        public void call(Session session, SessionState state, Exception exception)
        {
            if (state.isOpened())
            {
                Toast.makeText(MainActivity.this, "已登入。", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(MainActivity.this, "已登出。", Toast.LENGTH_SHORT).show();
            }

            // 更新介面
            updateUI();
        }
    };

    // 使用者在Facebook上的執行的動作結果，例如發表文章、照片的成功或失敗
    private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback()
    {
        @Override
        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data)
        {
            Toast.makeText(MainActivity.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data)
        {
            // 檢查使用者動作結果
            String completionGesture = FacebookDialog.getNativeDialogCompletionGesture(data);

            if(completionGesture.equals("post"))
                Toast.makeText(MainActivity.this, "張貼完成。", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity.this, "張貼取消。", Toast.LENGTH_SHORT).show();
        }
    };

    // 更新介面
    private void updateUI()
    {
        Session session = Session.getActiveSession();

        // 取得使者登入狀態
        boolean enableButtons = (session != null && session.isOpened());

        if(enableButtons && user != null)
        {
            // 使用者資訊
            loginInfo.setText("ID: " + user.getId() + "\n" +
                    "Name: " + user.getName() + "\n" +
                    "First Name: " + user.getFirstName() + "\n" +
                    "Last Name:" + user.getLastName() + "\n" +
                    "email: " + user.getProperty("email"));

            // 取得使用者大頭照
            userimage.setProfileId(user.getId());
        }
        else if (session != null && session.isClosed())
        {
            user = null;
            loginInfo.setText("尚未登入");
            userimage.setProfileId(null);
        }
        else
        {
            loginInfo.setText("登入錯誤");
        }
    }

    public void postStatus(View view)
    {
        // 判斷使用者安裝的Facebook App是否可以提供該功能
        if(FacebookDialog.canPresentShareDialog(this, FacebookDialog.ShareDialogFeature.SHARE_DIALOG))
        {
            // 使用FacebookDialog.ShareDialog來張貼文章
            FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this)
                    .setLink("http://www.aaronlife.com") // 要張貼的連結
                    .setPicture("http://www.aaronlife.com/travel/images/travel_2014-09-16_13.jpg") // 照片連結
                    .build();

            uiHelper.trackPendingDialogCall(shareDialog.present()); // 切換到張貼狀態的Facebook Activity
        }
        else
        {
            Toast.makeText(this, "不支援Sharedialog", Toast.LENGTH_SHORT).show();
        }
    }

    public void postPhoto(View view)
    {
        // 判斷使用者安裝的Facebook App是否可以提供該功能
        if(FacebookDialog.canPresentShareDialog(this, FacebookDialog.ShareDialogFeature.PHOTOS))
        {
            // 使用FacebookDialog.PhotoShareDialog來張貼文章
            FacebookDialog sharePhotoDialog = new FacebookDialog.PhotoShareDialogBuilder(this)
                    .addPhotos(Arrays.asList(((BitmapDrawable) getResources().getDrawable(R.drawable.icon)).getBitmap())) // 要張貼的照片
                    .build();

            uiHelper.trackPendingDialogCall(sharePhotoDialog.present()); // 切換到張貼照片的Facebook Activity
        }
        else
        {
            Toast.makeText(this, "不支援PhotoShareDialog", Toast.LENGTH_SHORT).show();
        }
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
