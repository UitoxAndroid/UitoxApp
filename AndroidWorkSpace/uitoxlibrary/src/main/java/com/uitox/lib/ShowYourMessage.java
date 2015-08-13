package com.uitox.lib;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by babyandy on 2015/7/31.
 */
public class ShowYourMessage {
    static public void msgToShowLong(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    static public void msgToShowShort(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
