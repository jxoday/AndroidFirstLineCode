package com.example.androidfirstlinecode.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * @author JinXin
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // 获取系统服务类
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取网络信息 注：访问系统网络状态需要声明权限 android.permission.ACCESS_NETWORK_STATE
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        // isConnected判断网络是否已连接
        if (networkInfo != null && networkInfo.isConnected()) {
            Toast.makeText(context, " 网络已连接", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context," 网络没有连接", Toast.LENGTH_SHORT).show();
        }

    }
}
