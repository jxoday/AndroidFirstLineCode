package com.example.androidfirstlinecode.sqllite.litepal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.androidfirstlinecode.R;

import org.litepal.tablemanager.Connector;

/**
 * @author JinXin
 */
public class LitePalTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lite_pal_test);

        Button createDatabaseBtn = findViewById(R.id.btn_create_database);
        createDatabaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 调用connector.getDatabase()方法创建数据库
                Connector.getDatabase();
            }
        });
    }
}
