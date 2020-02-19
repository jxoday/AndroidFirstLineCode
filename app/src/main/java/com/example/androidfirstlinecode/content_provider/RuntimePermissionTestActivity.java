package com.example.androidfirstlinecode.content_provider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.androidfirstlinecode.R;

/**
 * @author JinXin
 */
public class RuntimePermissionTestActivity extends AppCompatActivity {

    private static final String TAG = "RuntimePermissionTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runtime_permission_test);

        Button makeCallBtn = findViewById(R.id.btn_make_call);
        makeCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 判断权限是否申请
                if (ContextCompat.checkSelfPermission(RuntimePermissionTestActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    // Android6.0及以上系统在使用危险权限时，都必须进行运行时权限处理
                    // 申请权限授权
                    ActivityCompat.requestPermissions(RuntimePermissionTestActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    call();
                }

            }
        });
    }

    private void call(){
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:10086"));
            startActivity(intent);
        } catch (SecurityException e) {
            Log.d(TAG, "onClick: 拨打电话失败");
        }
    }

    /**
     * 权限申请结果回调
     * @param requestCode 结果码
     * @param permissions
     * @param grantResults 授权结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call();
                }
                break;
            default:
                break;
        }
    }
}
