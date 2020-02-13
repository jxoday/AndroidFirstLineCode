package com.example.androidfirstlinecode.sqllite;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
        Button addDataBtn = findViewById(R.id.btn_add_data);

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

        // 添加数据
        addDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase writableDatabase = myDatabaseHelper.getWritableDatabase();
                // 使用ContentValues对要添加的数据进行组装
                ContentValues values = new ContentValues();
                // 组装第一条数据
                values.put("name", "JinXinDatabaseTest");
                values.put("author", "JinXin");
                values.put("pages", 666);
                values.put("price", "66.66");
                // 插入第一条数据
                writableDatabase.insert("Book", null, values);

                values.clear();
                // 组装第二条数据
                values.put("name", "JinXinDatabaseTest2");
                values.put("author", "JinXin2");
                values.put("pages", 888);
                values.put("price", "88.88");
                // 插入第一条数据
                writableDatabase.insert("Book", null, values);

                Toast.makeText(DatabaseActivity.this, "插入数据完成", Toast.LENGTH_LONG).show();
            }
        });
    }
}
