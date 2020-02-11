package com.example.androidfirstlinecode.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * 本地广播接收器
 * @author Administrator
 */
public class LocalReceiver extends BroadcastReceiver {
    private static final String TAG = "LocalReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: ");
        Toast.makeText(context,"接受本地广播",Toast.LENGTH_LONG).show();
    }
}
