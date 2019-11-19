package com.example.androidfirstlinecode.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 发送标准广播
 * 定义一个广播接收器
 *
 * @author JinXin
 */
public class MyBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "received in MyBrodcastReceive", Toast.LENGTH_SHORT).show();
    }
}
