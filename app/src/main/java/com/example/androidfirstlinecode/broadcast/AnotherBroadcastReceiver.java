package com.example.androidfirstlinecode.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * @author JinXin
 */
public class AnotherBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "AnotherBroadcastReceive";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: ");
        Toast.makeText(context, "received in MyBrodcastReceive", Toast.LENGTH_LONG).show();
        // 表示将这条广播截断，后面的广播接收器无法在接收到这条广播
        abortBroadcast();
    }
}
