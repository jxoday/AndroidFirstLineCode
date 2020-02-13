package com.example.androidfirstlinecode.sqllite;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        Button addDataBtn = findViewById(R.id.btn_add_data);
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
                values.put("price", 66.66);
                // 插入第一条数据
                writableDatabase.insert("Book", null, values);

                values.clear();
                // 组装第二条数据
                values.put("name", "JinXinDatabaseTest2");
                values.put("author", "JinXin2");
                values.put("pages", 888);
                values.put("price", 88.88);
                // 插入第一条数据
                writableDatabase.insert("Book", null, values);

                Toast.makeText(DatabaseActivity.this, "插入数据完成", Toast.LENGTH_LONG).show();
            }
        });

        Button updateDataBtn = findViewById(R.id.btn_update_data);
        // 更新数据
        updateDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase writableDatabase = myDatabaseHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();

                contentValues.put("price", 99.99);
                // 将名字是JinXinDatabaseTest2的这本书的价格改成99.99
                writableDatabase.update("Book", contentValues, "name = ?", new String[]{"JinXinDatabaseTest2"});

                Toast.makeText(DatabaseActivity.this, "更新数据完成", Toast.LENGTH_LONG).show();
            }
        });

        Button deleteDataBtn = findViewById(R.id.btn_delete_data);
        // 删除数据
        deleteDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase writableDatabase = myDatabaseHelper.getWritableDatabase();
                // 删除页数小于777页的书
                writableDatabase.delete("Book", "pages < ?", new String[]{"777"});

                Toast.makeText(DatabaseActivity.this, "删除数据完成", Toast.LENGTH_LONG).show();
            }
        });
    }
}
