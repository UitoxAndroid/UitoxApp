package com.uitox.fb.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uitox.fb.uitoxlibrary.ShowYourMessage;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends ActionBarActivity {

    private LoginButton loginButton;          // Facebook SDK的登入按鈕
    private TextView loginInfo;               // 顯示使用者資訊用
    private GraphUser user;                   // Facebook SDK登入後取得的使用者資訊物件
    private UiLifecycleHelper uiHelper;       // Facebook SDK介面幫助類別
    private ProfilePictureView userimage;     // Facebook SDK的照片元件

    private String IMEI, IMSI, SIM, COUNTRY, NETWORKOPERATION;
    private int PHONETYPE, NETWORK;

    /*
    onRestoreInstanceState
    系統只會在有實體狀態資料時呼叫這個方法、所以在此你不用檢查 savedInstanceState 是否為 null。
    永遠要呼叫上層類別方法它才能恢復 view 的狀態 super.onRestoreInstanceState(savedInstanceState);
    取回先前儲存的資訊 value = savedInstanceState.getInt(KEY);

    簡單來說，當使用者案HOME鍵卻又馬上回復Activity，就會出現這個部分
    http://www.cnblogs.com/hanyonglu/archive/2012/03/28/2420515.html
    */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ShowYourMessage.msgToShowShort(this, "onRestoreInstanceState!~1");
    }

    /*
    onSaveInstanceState
    每一個 View 物件都必需以 android:id 屬性指定個別的 id，系統才能自動儲存它們的狀態。
    想要儲存關於 activity 狀態以外的資料、你得覆寫這個方法。
    儲存額外資訊的寫法 savedInstanceState.putInt(KEY, VALUE) 添加完後才執行 super.onSaveInstanceState(savedInstanceState)。
    當使用者離開你的 activity 系統就會呼叫這個方法、並給提供一個 Bundle 物件然後儲存。
    如果系統將要重建 activity 實體，同一個 Bundle 物件會被丟給 onRestoreInstanceState() 和 onCreate()。

    總而言之，onSaveInstanceState()的調用遵循一個重要原則，
    即當系統存在“未經你許可”時銷毀了我們的activity的可能時，則onSaveInstanceState()會被系統調用，這是系統的責任，
    因為它必須要提供一個機會讓你保存你的數據（當然你不保存那就隨便你了）。
    如果調用，調用將發生在onPause()或onStop()方法之前。 （雖然測試時發現多數在onPause()前）
    */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ShowYourMessage.msgToShowShort(this, "onSaveInstanceState!~1");
    }

    /*
    onDestroy
    系統會在你的 activity 從記憶體中完全移除之前呼叫它、作為最後一個信號。
    大部份 APP 不需要實作這一個方法，因為本地類別參照隨著 activity 被銷毀、而且你的 activity 也應該在 onPause() 和 onStop() 之中進行大部份的清理動作。
    如果你的 activity 在 onCreate() 時使用了背景執行緒或其它長期運行的資源，要是沒有正確將其關閉、可能會造成記憶體洩漏，
    你應該在 onDestroy() 當中清光它們。
    通常系統會在 onPause() 和 onStop() 之後才執行 onDestroy()，如果你在 onCreate() 裡呼叫 finish()、這時候系統會立即執行 onDestroy()
    而不經過其它任何生命週期方法。
    每次使用者改變螢幕方向、你的 activity 都會被銷毀和重建。方向一改變、系統就重建前景的 activity
    系統會在一個 activity 處於停止狀態非常久、或正在前景執行的 activity 需要更多資源、因此清除背景程序以獲取更多記憶體的同時、銷毀已停止的 activity。
    如果由於系統的限制而銷毀了 activity(非正常行為)，系統會用鍵值對(Key/Value Pair)組成的集合、儲存佈局中每一個物件的資訊
    (例如文字框裡的內容、和捲軸位置)、在一個 Bundle 物件中。這組資料稱為 “實體狀態”。
    */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShowYourMessage.msgToShowShort(this, "onDestroy!~1");
    }

    /*
    onResume
    Activity 可以停駐在這個狀態、以延長生命週期
    在恢復狀態、activity 位於前景並且可接受使用者操作(有時也被稱為 Running 狀態)。
    你應該重新初始化一些你在 onPause() 時釋放的元件、以及執行一些每次來到恢復狀態都必需要有的動作
    (好比說播放動畫、初始化當 activity 得到使用者焦點時才用得到的元件)。
    */
    @Override
    protected void onResume() {
        super.onResume();

        // 紀錄應用程式被「安裝」和「執行」的事件。
        AppEventsLogger.activateApp(this);
        ShowYourMessage.msgToShowShort(this, "onResume!~1");
    }

    /*
    onPause
    Activity 可以停駐在這個狀態、以延長生命週期
    在暫停狀態、你的 activity 有部份正被另一個 activity 遮蔽，另一個 activity 在前景執行、且它是半透明或沒有覆蓋整個螢幕。
    已暫停的 activity 無法接受使用者互動且無法執行任何程式。
    這裡可以讓你停止一些在暫停狀態下、不應該繼續執行的動作(像是播放影片)、或保留任何應該永久保存的資訊(如果使用者短期內沒有返回你的 APP 的話)。
    停止動畫或其它消耗 CPU 的動作。
    提交未儲存的變更，但只在使用者能夠預期的時候才這麼作(像是電子郵件寫到一半時突然離開)。
    釋放系統資源，像是廣播接收、感應器(GPS)，或者當你 activity 暫停之後、使用者不再需要、且任何會影響電池壽命的資源。
    你應該避免在 onPause() 執行佔用 CPU 的工作、像是寫入資料庫，因為這會導致下一個 activity 出現時的過場動畫變慢。
    Activity 暫停的時候，activity 實體保持在記憶體裡、並在隨著 activity 恢復而還原。你不需要重新初始化任何元件。
    */
    @Override
    protected void onPause() {
        super.onPause();

        // 紀錄應用程式「結束」的事件。
        AppEventsLogger.deactivateApp(this);
        ShowYourMessage.msgToShowShort(this, "onPause!~1");
    }

    /*
    onStart
    這個狀態是短暫的、會很快的進入下一個狀態
    技術上來講、當 onStart() 被呼叫之後使用者就看得見 activity 了
    使用者返回你的 APP 前可能離開了很久、所以最好是在 onStart() 裡檢查必要的系統功能是否可用。
    */
    @Override
    protected void onStart() {
        super.onStart();
        ShowYourMessage.msgToShowShort(this, "onStart!~1");
    }

    /*
    onRestart
    這個狀態是短暫的、會很快的進入 onStart 狀態
    只有在 activity 從停止狀態改變成恢復狀態時才會呼叫 onRestart()，所以你可以執行一些 activity 由停止狀態恢復時必要的恢復工作。
    很少有 APP 需要用 onRestart() 來恢復 activity 的狀態，不過，
    因為在 onStop() 裡基本上就應該要清除所有使用中的資源、所以你也需要在 activity 重啟時再次實例化它們。
    另外、你還需要在 activity 第一次啟動時實例化它們。為了這個理由、你應該用 onStart() 去對應 onStop()，
    因為當建立 activity 以及由停止狀態重啟 activity 兩種情況，系統都會呼叫 onStart()。
    */
    @Override
    protected void onRestart() {
        super.onRestart();
        ShowYourMessage.msgToShowShort(this, "onRestart!~1");
    }

    /*
    onStop
    Activity 可以停駐在這個狀態、以延長生命週期
    一但 activity 完全被遮蓋、它就會停止。
    在停止狀態、你的 activity 是完全看不見的，它被移到背景。activity 實體與其狀態資訊如成員變數是被保留的，但它無法執行任何程式。
    雖然 onPause() 會在 onStop() 之前呼叫，你應該在 onStop() 中處理較大、較佔 CPU 的作業，例如寫入資料庫。
    系統會在 Activity 停止時、於記憶體中保留你的 Activity 實體，
    所以你可能不需要實作 onStop() 和 onRestart() 甚至不需要 onStart()。你可能只需在 onPause() 停止一些動作和釋放一些資源。
    應該釋放幾乎所有不再需要用到的系統資源。
    在某些極端的案例中，系統可能會直接殺掉你的 APP、而不去呼叫 onDestroy()。所以在 onStop() 釋放可能造成記憶體洩漏的資源是很重要的。
    Activity 停止的時候，activity 實體保持在記憶體裡、並在隨著 activity 恢復而還原。你不需要重新初始化任何元件。
    系統也會持續追蹤佈局中的每一個 View 的狀態，要是使用者曾在文字框裡填入資訊，這些內容會被自動保留(activity 恢復時文字框的內容還在)。
    所以你不需要特別去儲存或恢復它。
    即使系統銷毀了處於停止狀態中的 activity，它仍然保存著 View 物件的狀態(如文字框中的文字)在一個 Bundle 裡。
    */
    @Override
    protected void onStop() {
        super.onStop();
        ShowYourMessage.msgToShowShort(this, "onStop!~1");
    }

    /*
    onCreate
    這個狀態是短暫的、會很快的進入下一個狀態
    你必需實作這個方法來執行基本的初始化、並且它應該在整個 activity 生命中僅僅執行一次。
    你實作的 onCreate() 應該定義使用者界面以及宣告一些變數並加以實體化。
    讀取 savedInstanceState 之前需要檢查它是不是 null，如果是 null、表示系統正在建立新的 activity。如果不是 null、就表示正在恢復前一個 activity。
    相對於在這裡讀取 savedInstanceState、你也可以實作 onRestoreInstanceState，系統會在 onStart() 之後呼叫它。系統只會在有實體狀態資料時呼叫它。
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 關聯UI元件
        loginInfo = (TextView) findViewById(R.id.loginInfo);
        userimage = (ProfilePictureView) findViewById(R.id.profilePicture);
        loginButton = (LoginButton) findViewById(R.id.authButton);

        // 建立UiLifecycleHelper物件，並傳入Session.StatusCallback處理臉書登入狀態事件
        uiHelper = new UiLifecycleHelper(this, callback);

        // 設定需要取得的Facebook權限
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile", "user_friends"));

        // 設定收到來自Facebook的使用者資訊傾聽器
        loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                MainActivity.this.user = user;
                updateUI();
            }
        });

        // 設定Session.Callback物件給LoginButton
        loginButton.setSessionStatusCallback(callback);

        try {
            String jsonData = "[\n" +
                    "    {\n" +
                    "        \"chanel\":\"FM\",\n" +
                    "        \"week\":\"日\",\n" +
                    "        \"start_time\":\"09:00\",\n" +
                    "        \"end_time\":\"10:00\",\n" +
                    "        \"program\":\"勞動聲活\",\n" +
                    "        \"DJ\":\"【北市勞動局合作】\"\n" +
                    "    }\n" +
                    "]";
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<MyJsonObj>>() {
            }.getType();
            ArrayList<MyJsonObj> jsonArr = gson.fromJson(jsonData, listType);
            for (MyJsonObj obj : jsonArr) {
                Log.i("chanel", obj.getChanelStr());
                Log.i("start time", obj.getStartTime());
                Log.i("end time", obj.getEndTime());
                Log.i("week", obj.getWeekStr());
                Log.i("DJ", obj.getDjStr());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //取得手機相關資訊
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        //取得 IMEI 碼↓
        IMEI = telephonyManager.getDeviceId();
        Log.i("IMEI", IMEI);

        //取得 IMSI 碼↓
        IMSI = telephonyManager.getSubscriberId();
        Log.i("IMSI", IMSI);

        //取的 SIM 卡序號↓
        SIM = telephonyManager.getSimSerialNumber();
        Log.i("SIM", SIM);

        //取得 PhoneType↓ (NONE、GSM、CDMA、SIP)
        PHONETYPE = telephonyManager.getPhoneType();
        Log.i("PHONETYPE", Integer.toString(PHONETYPE));

        //取得目前網路類型↓ (UNKNOWN、GPRS、EDGE、HSDPA、CDMA、......)
        NETWORK = telephonyManager.getNetworkType();
        Log.i("NETWORK", Integer.toString(NETWORK));

        //取得國家代碼↓ (TW)
        COUNTRY = telephonyManager.getNetworkCountryIso();
        Log.i("COUNTRY", COUNTRY);

        //取得電信商名稱↓ (中華電信)
        NETWORKOPERATION = telephonyManager.getNetworkOperatorName();
        Log.i("NETWORKOPERATION", NETWORKOPERATION);

        ShowYourMessage.msgToShowShort(this, "onCreate!~1");
    }

    /*
    onActivityResult
    onActivityResult()發生在onResume()之前。
    在一個主界面(主Activity)上能連接往許多不同子功能模塊(子Activity上去)，當子模塊的事情做完之後就回到主界面，
    或許還同時返回一些子模塊完成的數據交給主Activity處理。
    這樣的數據交流就要用到回調函數onActivityResult
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 接收Facebook Activity的回傳值，並交給UiLifecycleHelper物件處理
        uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);

        ShowYourMessage.msgToShowShort(this, "onActivityResult!~1");
    }

    // Facebook SDK狀態Callbak物件
    Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            if (state.isOpened()) {
                Toast.makeText(MainActivity.this, "已登入。", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "已登出。", Toast.LENGTH_SHORT).show();
            }

            // 更新介面
            updateUI();
        }
    };

    // 使用者在Facebook上的執行的動作結果，例如發表文章、照片的成功或失敗
    private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
        @Override
        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
            Toast.makeText(MainActivity.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
            // 檢查使用者動作結果
            String completionGesture = FacebookDialog.getNativeDialogCompletionGesture(data);

            if (completionGesture.equals("post"))
                Toast.makeText(MainActivity.this, "張貼完成。", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity.this, "張貼取消。", Toast.LENGTH_SHORT).show();
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
        if (FacebookDialog.canPresentShareDialog(this, FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
            // 使用FacebookDialog.ShareDialog來張貼文章
            FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this)
                    .setLink("http://www.aaronlife.com") // 要張貼的連結
                    .setPicture("http://www.aaronlife.com/travel/images/travel_2014-09-16_13.jpg") // 照片連結
                    .build();

            uiHelper.trackPendingDialogCall(shareDialog.present()); // 切換到張貼狀態的Facebook Activity
        } else {
            Toast.makeText(this, "不支援Sharedialog", Toast.LENGTH_SHORT).show();
        }
    }

    public void postPhoto(View view) {
        // 判斷使用者安裝的Facebook App是否可以提供該功能
        if (FacebookDialog.canPresentShareDialog(this, FacebookDialog.ShareDialogFeature.PHOTOS)) {
            // 使用FacebookDialog.PhotoShareDialog來張貼文章
            FacebookDialog sharePhotoDialog = new FacebookDialog.PhotoShareDialogBuilder(this)
                    .addPhotos(Arrays.asList(((BitmapDrawable) getResources().getDrawable(R.drawable.icon)).getBitmap())) // 要張貼的照片
                    .build();

            uiHelper.trackPendingDialogCall(sharePhotoDialog.present()); // 切換到張貼照片的Facebook Activity
        } else {
            Toast.makeText(this, "不支援PhotoShareDialog", Toast.LENGTH_SHORT).show();
        }
    }

    public void create_activity(View view) {
        Intent resultIntent = new Intent(view.getContext(), EventActivity.class);
        resultIntent.putExtra("val_1", "i am val 1");
        resultIntent.putExtra("val_2", "i am vaal 2");
        startActivity(resultIntent);
        finish();
    }

    /*
    * 建立Menu
    * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*
    * 只要Menu有選項被點選就會呼叫這個函式
    * */
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
