package com.example.androidfirstlinecode.broadcast;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.androidfirstlinecode.R;

/**
 * 广播接收器
 *
 * 标准广播：是一个完全异步执行的广播，在广播发出后，所有的广播接收器几乎在同一时刻接收到这条广播消息，
 *          因此它们之间没有任何先后顺序可言。这种广播的效率会比较高，但同时也意味着它是无法没截断的
 *
 * 有序广播：是一种同步执行的广播，在广播发出之后，同一时刻只会有一个广播接收器能够收到这条广播消息，当这个广播接收器的逻辑执行完毕后，广播才会继续传递。
 *          所以此时的广播接收器是有先后顺序的，优先级高的广播接收器就可以先收到广播信息，并且前面的广播接收器还可以截断正在传递的广播，这样后面的广播接收器就无法接收到广播消息了
 *
 * 广播接收器可以自由的对感兴趣的广播进行注册，注册方式有两种：动态注册和静态注册
 * @author JinXin
 */
public class BroadcastTestActivity extends AppCompatActivity {

    private static final String TAG = "BroadcastTestActivity";
    /**
     * 动态注册广播接收器监听网络变化
     */
    private NetworkChangeReceiver networkChangeReceiver;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_test);

        btnSend = findViewById(R.id.btn_send_custom_broadcast);

        // 动态注册广播接收器监听网络变化
//        dynamicBroadcastReceiver();

        // 发送自定义广播（标准广播）
//        sendCustomBroadcast();

        // 发送有序广播
        sendOrderedBroadcast();
    }

    private void dynamicBroadcastReceiver() {
        // 隐式调用的IntentFilter匹配规则
        IntentFilter intentFilter = new IntentFilter();
        // action是一个字符串，系统预定义了一些action
        // android.net.conn.CONNECTIVITY_CHANGE是当网络状态发生变化时，系统所发出的广播
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        // 创建一个NetworkChangeReceiver的实例
        networkChangeReceiver = new NetworkChangeReceiver();
        // 注册广播接收器
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    private void sendCustomBroadcast() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 发送自定义广播逻辑
                Log.d(TAG, "sendCustomBroadcast: ");

                // 构建Intent对象，传入要发生的广播
                Intent intent = new Intent();
                intent.setAction("com.example.broadcasttest.MY_BROADCAST");
                // 在Android8.0上突破隐式广播的限制
                intent.addFlags(0x01000000);
                // 调用Context的sendBroadcast发送
                sendBroadcast(intent);

                // 这样所有监听com.example.broadcasttest.MY_BROADCAST这条广播的广播接收器就会收到消息
                // 此时发出去的广播就是一条标准广播
            }
        });
    }

    private void sendOrderedBroadcast(){
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 发送有序广播逻辑
                Log.d(TAG, "sendOrderedBroadcast: ");

                // 构建Intent对象，传入要发生的广播
                Intent intent = new Intent("com.example.broadcasttest.MY_BROADCAST");
                // 在Android8.0上突破隐式广播的限制
                intent.addFlags(0x01000000);
                // 调用Context的sendOrderedBroadcast发送
                // 两个参数 第一个：Intent； 第二个：权限相关字符串
                sendOrderedBroadcast(intent, null);

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (networkChangeReceiver != null) {
            unregisterReceiver(networkChangeReceiver);
        }

    }
}
