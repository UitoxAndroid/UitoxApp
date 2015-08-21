package com.uitox.asap;

/**
 * Created by babyandy on 2015/8/21.
 */

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.uitox.gcm.GCM;
import com.uitox.gcm.GcmBroadcastReceiver;

public class BroadcastReceiver extends GcmBroadcastReceiver {
    public static final int NOTIFICATION_ID = 0;
    @Override
    public void Notification(Context context, Intent intent, Bundle extras) {
        Intent i = new Intent(context, MainActivity.class);
        i.setAction("android.intent.action.MAIN");
        i.addCategory("android.intent.category.LAUNCHER");
        GCM.sendLocalNotification(context, NOTIFICATION_ID,
                R.drawable.icon, "GCM 通知", extras.getString("message"), "magiclen.org", false,
                PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT));
    }
}