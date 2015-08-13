package com.example.babyandy.asap;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;
import com.uitox.http.ToJsonString;
import com.uitox.lib.ShowYourMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FriendFragment extends Fragment {

    private LoginButton loginButton;          // Facebook SDK的登入按鈕
    private TextView loginInfo;               // 顯示使用者資訊用
    private GraphUser user;                   // Facebook SDK登入後取得的使用者資訊物件
    private UiLifecycleHelper uiHelper;       // Facebook SDK介面幫助類別
    private ProfilePictureView userimage;     // Facebook SDK的照片元件
    private Button postPhoto,postStatus;
    public FriendFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page_2, container, false);

        // 關聯UI元件
        loginInfo = (TextView) view.findViewById(R.id.loginInfo);
        userimage = (ProfilePictureView) view.findViewById(R.id.profilePicture);
        loginButton = (LoginButton) view.findViewById(R.id.authButton);
        postPhoto = (Button) view.findViewById(R.id.postPhoto);
        postStatus = (Button) view.findViewById(R.id.postStatus);

        postPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                postPhoto(v);
            }
        });

        postStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                postStatus(v);
            }
        });
        loginButton.setFragment(this);
        // 建立UiLifecycleHelper物件，並傳入Session.StatusCallback處理臉書登入狀態事件
        uiHelper = new UiLifecycleHelper(getActivity(), callback);

        // 設定需要取得的Facebook權限
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile", "user_friends"));

        // 設定收到來自Facebook的使用者資訊傾聽器
        loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                FriendFragment.this.user = user;
                updateUI();
            }
        });

        // 設定Session.Callback物件給LoginButton
        loginButton.setSessionStatusCallback(callback);



        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("un", "852041173");
        hashMap.put("pw", "852041173abc111");

        ArrayList<String> list = new ArrayList<>();
        list.add("test1");
        list.add("test2");

        ToJsonString test = new ToJsonString();
        Log.i("testgson", test.toJson(hashMap));
        Log.i("testgson", test.toJson(list));

        HashMap<String, String> hashMap2 = new HashMap<>();
        hashMap2.put("un2", "852041173");
        hashMap2.put("pw2", "852041173abc111");


        ArrayList<String> list2 = new ArrayList<>();
        list2.add("test12");
        list2.add("test22");
        hashMap2.put("pw3", test.toJson(list2));
        Log.i("testgson", test.toJson(hashMap2));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // 紀錄應用程式被「安裝」和「執行」的事件。
        AppEventsLogger.activateApp(getActivity());
        ShowYourMessage.msgToShowShort(getActivity(), "onResume!~1");
    }

    @Override
    public void onPause() {
        super.onPause();

        // 紀錄應用程式「結束」的事件。
        AppEventsLogger.deactivateApp(getActivity());
        ShowYourMessage.msgToShowShort(getActivity(), "onPause!~1");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 接收Facebook Activity的回傳值，並交給UiLifecycleHelper物件處理
        uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);

        ShowYourMessage.msgToShowShort(getActivity(), "onActivityResult!~1");
    }

    // Facebook SDK狀態Callbak物件
    Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            if (state.isOpened()) {
                ShowYourMessage.msgToShowShort(getActivity(), "已登入");
            } else {
                ShowYourMessage.msgToShowShort(getActivity(), "已登出");
            }

            // 更新介面
            updateUI();
        }
    };

    // 使用者在Facebook上的執行的動作結果，例如發表文章、照片的成功或失敗
    private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
        @Override
        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
            ShowYourMessage.msgToShowShort(getActivity(), "Error: " + error.toString());
        }

        @Override
        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
            // 檢查使用者動作結果
            String completionGesture = FacebookDialog.getNativeDialogCompletionGesture(data);

            if (completionGesture.equals("post"))
                ShowYourMessage.msgToShowShort(getActivity(), "張貼完成");
            else
                ShowYourMessage.msgToShowShort(getActivity(), "張貼取消");
        }
    };

    // 更新介面
    private void updateUI() {
        Session session = Session.getActiveSession();

        // 取得使者登入狀態
        boolean enableButtons = (session != null && session.isOpened());

        if (enableButtons && user != null) {
            // 使用者資訊
            loginInfo.setText("ID: " + user.getId() + "\n" +
                    "Name: " + user.getName() + "\n" +
                    //"First Name: " + user.getFirstName() + "\n" +
                    //"Last Name:" + user.getLastName() + "\n" +
                    "email: " + user.getProperty("email"));

            // 取得使用者大頭照
            userimage.setProfileId(user.getId());
        } else if (session != null && session.isClosed()) {
            user = null;
            loginInfo.setText("尚未登入");
            userimage.setProfileId(null);
        } else {
            loginInfo.setText("登入錯誤");
        }
    }

    public void postStatus(View view) {
        // 判斷使用者安裝的Facebook App是否可以提供該功能
        if (FacebookDialog.canPresentShareDialog(getActivity(), FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
            // 使用FacebookDialog.ShareDialog來張貼文章
            FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(getActivity())
                    .setLink("http://www.aaronlife.com") // 要張貼的連結
                    .setPicture("http://www.aaronlife.com/travel/images/travel_2014-09-16_13.jpg") // 照片連結
                    .build();

            uiHelper.trackPendingDialogCall(shareDialog.present()); // 切換到張貼狀態的Facebook Activity
        } else {
            ShowYourMessage.msgToShowShort(getActivity(), "不支援Sharedialog");
        }
    }

    public void postPhoto(View view) {
        // 判斷使用者安裝的Facebook App是否可以提供該功能
        if (FacebookDialog.canPresentShareDialog(getActivity(), FacebookDialog.ShareDialogFeature.PHOTOS)) {
            // 使用FacebookDialog.PhotoShareDialog來張貼文章
            FacebookDialog sharePhotoDialog = new FacebookDialog.PhotoShareDialogBuilder(getActivity())
                    .addPhotos(Arrays.asList(((BitmapDrawable) getResources().getDrawable(R.drawable.icon)).getBitmap())) // 要張貼的照片
                    .build();

            uiHelper.trackPendingDialogCall(sharePhotoDialog.present()); // 切換到張貼照片的Facebook Activity
        } else {
            ShowYourMessage.msgToShowShort(getActivity(), "不支援PhotoShareDialog");
        }
    }
}
