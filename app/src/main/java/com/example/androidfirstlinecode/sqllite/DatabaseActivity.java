package com.example.androidfirstlinecode.sqllite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.androidfirstlinecode.R;

/**
 * @author JinXin
 */
public class DatabaseActivity extends AppCompatActivity {

    private static final String TAG = "DatabaseActivity";

    private MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        final Button createDatabaseBtn = findViewById(R.id.btn_create_database);

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

        Button queryDataBtn = findViewById(R.id.btn_query_data);
        // 查询数据
        queryDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase writableDatabase = myDatabaseHelper.getWritableDatabase();
                // 查询Book表中所有的数据
                // 第一个参数指定表名；   from table_name
                // 第二个参数指定去查询哪几列，不指定则默认查询所有列；   select column1, column2
                // 第三、四个参数用于月数查询某一行或某几行的数据；     where column = value、-
                // 第五个参数用于指定需要去group by的列，不指定则表示不对查询结果进行group by操作； group by column
                // 第六个参数用于对group by之后的数据进行进一步的过滤，不指定则表示不过滤； having column = value
                // 第七个参数用户指定查询结果的排序方式，不指定则表示使用默认的排序方式；  order by column1, column2
                Cursor cursor = writableDatabase.query("Book", null, null, null, null, null, null);

                // 将数据的指针移动到第一行的位置
                if (cursor.moveToFirst()) {
                    do {
                        // 遍历Cursor对象，取出数据并打印
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));

                        Log.d(TAG, "查询数据: " +
                                "name = " + name + " " +
                                "author = " + author + " " +
                                "pages = " + pages + " " +
                                "price = " + price);
                    } while (cursor.moveToNext());
                }

                Toast.makeText(DatabaseActivity.this, "查询数据完成", Toast.LENGTH_LONG).show();
            }
        });
    }
}
