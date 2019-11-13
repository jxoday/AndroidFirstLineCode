package com.example.androidfirstlinecode.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * 动态注册的广播接收器
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

        // 不能在onReceive()方法中添加过多的逻辑或者进行任何的耗时操作，因为在广播接收器中是不允许开启线程的
        // 当onReceive()方法运行了较长时间而没有结束时，程序就会报错
        // 广播接收器更多的是扮演一种打开程序其他组件的角色，比如创建一条状态栏通知，或者启动一个服务等

    }
}
