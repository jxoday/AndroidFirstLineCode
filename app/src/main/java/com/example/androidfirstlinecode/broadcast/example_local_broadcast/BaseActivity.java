package com.example.androidfirstlinecode.broadcast.example_local_broadcast;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Activity 基类
 * @author Administrator
 */
public class BaseActivity extends AppCompatActivity {

    private ForceOfflineReceiver forceOfflineReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcastbestpractice.FORCE_OFFLINE");
        forceOfflineReceiver = new ForceOfflineReceiver();
        registerReceiver(forceOfflineReceiver, intentFilter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (forceOfflineReceiver != null) {
            unregisterReceiver(forceOfflineReceiver);
            forceOfflineReceiver = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
