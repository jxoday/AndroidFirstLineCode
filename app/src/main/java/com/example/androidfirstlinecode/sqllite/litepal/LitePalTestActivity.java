package com.example.androidfirstlinecode.sqllite.litepal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

                Toast.makeText(LitePalTestActivity.this, "创建数据库成功", Toast.LENGTH_LONG).show();
            }
        });

        // 添加数据
        Button addDataBtn = findViewById(R.id.btn_add_data);
        addDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setName("第二本书");
                book.setAuthor("JinXin");
                book.setPages(222);
                book.setPrice(22.2);
                book.setPress("西安电子大学出版社");

                // save()方法从LitePalSupport继承而来
                book.save();

                Toast.makeText(LitePalTestActivity.this, "添加数据成功", Toast.LENGTH_LONG).show();
            }
        });

        // 更新数据
        Button updateBtn = findViewById(R.id.btn_update_data);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setPrice(33);

                // 表示更新书名为“第二本书”的书籍价格为33
//                book.updateAll("name = ?", "第二本书");
                // 表示更新书名为“第二本书”并且作者为Jinxin的书籍价格为33
//                book.updateAll("name = ? and author = ?", "第二本书", "JinXin");

                // 设置默认值统一使用setToDefault()方法 int类型的默认值是0、boolean类型的默认值是false、String类型的默认值是null
                // 表示将所有书籍的页数更新为0
                book.setToDefault("pages");
                book.updateAll();

                Toast.makeText(LitePalTestActivity.this, "更新数据成功", Toast.LENGTH_LONG).show();
            }
        });
    }
}
