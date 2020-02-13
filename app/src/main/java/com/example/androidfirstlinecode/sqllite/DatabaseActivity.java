package com.example.androidfirstlinecode.sqllite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.androidfirstlinecode.R;

/**
 * @author JinXin
 */
public class DatabaseActivity extends AppCompatActivity {

    private MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        Button createDatabaseBtn = findViewById(R.id.btn_create_database);

        // 新建数据库
//        myDatabaseHelper = new MyDatabaseHelper(this, "BookStore.db", null ,1);
//
//        createDatabaseBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myDatabaseHelper.getWritableDatabase();
//            }
//        });

        // 升级数据库
        myDatabaseHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);
        createDatabaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDatabaseHelper.getWritableDatabase();
            }
        });
    }
}
