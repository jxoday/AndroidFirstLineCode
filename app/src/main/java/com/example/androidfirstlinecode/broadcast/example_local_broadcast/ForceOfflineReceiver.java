package com.example.androidfirstlinecode.broadcast.example_local_broadcast;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

/**
 * 接收离线广播
 * @author JinXin
 */
public class ForceOfflineReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        Toast.makeText(context,"接收强制下线广播", Toast.LENGTH_LONG).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("警告");
        builder.setMessage("你被迫离线。请尝试重新登录.");
        builder.setCancelable(false);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 销毁所有活动
                ActivityCollector.finishAll();
                Intent intent = new Intent(context, LoginActivity.class);
                // 启动LoginActivity
                context.startActivity(intent);

            }
        });
        builder.show();
    }
}
