package com.example.androidfirstlinecode.sqllite.litepal;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.androidfirstlinecode.R;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.util.List;

/**
 * @author JinXin
 */
public class LitePalTestActivity extends AppCompatActivity {

    private static final String TAG = "LitePalTestActivity";

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
                book.setName("第一本书");
                book.setAuthor("JinXin");
                book.setPages(111);
                book.setPrice(11);
                book.setPress("西安电子大学出版社");

                // save()方法从LitePalSupport继承而来
                book.save();

                Book book1 = new Book();
                book1.setName("第二本书");
                book1.setAuthor("JinXin");
                book1.setPages(222);
                book1.setPrice(22.2);
                book1.setPress("西安电子大学出版社");

                // save()方法从LitePalSupport继承而来
                book1.save();

                Book book2 = new Book();
                book2.setName("第三本书");
                book2.setAuthor("JinXin");
                book2.setPages(333);
                book2.setPrice(33);
                book2.setPress("西安电子大学出版社");

                // save()方法从LitePalSupport继承而来
                book2.save();

                Book book3 = new Book();
                book3.setName("第4本书");
                book3.setAuthor("JinXin");
                book3.setPages(444);
                book3.setPrice(44);
                book3.setPress("西安电子大学出版社");

                // save()方法从LitePalSupport继承而来
                book3.save();

                Book book4 = new Book();
                book4.setName("第5本书");
                book4.setAuthor("JinXin");
                book4.setPages(555);
                book4.setPrice(55);
                book4.setPress("西安电子大学出版社");

                // save()方法从LitePalSupport继承而来
                book4.save();

                Book book5 = new Book();
                book5.setName("第6本书");
                book5.setAuthor("JinXin");
                book5.setPages(666);
                book5.setPrice(66);
                book5.setPress("西安电子大学出版社");

                // save()方法从LitePalSupport继承而来
                book5.save();

                Book book6 = new Book();
                book6.setName("第二本书");
                book6.setAuthor("JinXin");
                book6.setPages(666);
                book6.setPrice(66);
                book6.setPress("西安电子大学出版社");

                // save()方法从LitePalSupport继承而来
                book6.save();

                Book book7 = new Book();
                book7.setName("第二本书");
                book7.setAuthor("JinXin");
                book7.setPages(777);
                book7.setPrice(77);
                book7.setPress("西安电子大学出版社");

                // save()方法从LitePalSupport继承而来
                book7.save();

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

        // 删除数据
        Button deleteBtn = findViewById(R.id.btn_delete_data);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 第一个删除指定删除哪张表中的数据；后面的参数用于指定约束条件
                // 如果不指定约束条件，表示删除表中所有数据
                LitePal.deleteAll(Book.class, "price < ?", "25");

                Toast.makeText(LitePalTestActivity.this, "删除数据成功", Toast.LENGTH_LONG).show();
            }
        });

        // 查询数据
        Button queryBtn = findViewById(R.id.btn_query_data);
        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 查询所有数据
                List<Book> bookList = LitePal.findAll(Book.class);
                for (Book book : bookList) {
                    Log.d(TAG, "查询所有数据: " + book.toString());
                }

                // 查询第一条数据
                Book firstBook = LitePal.findFirst(Book.class);
                Log.d(TAG, "查询第一条数据: " + firstBook.toString());

                // 查询最后一数据
                Book findBook = LitePal.findLast(Book.class);
                Log.d(TAG, "查询最后一数据: " + findBook.toString());

                // select()方法用于指定查询哪几列的数据  查询 name 和 author 两列数据
                List<Book> queryNameOrAuthorList = LitePal.select("name", "author").find(Book.class);
                for (Book book : queryNameOrAuthorList) {
                    Log.d(TAG, "查询 name 和 author 两列数据: " + book.toString());
                }

                // where()方法用于指定查询的约束条件， 对应了SQL当中的where关键字。比如只查页数大于400的数据
                List<Book> queryPagesList = LitePal.where("pages > ?", "400").find(Book.class);
                for (Book book : queryPagesList) {
                    Log.d(TAG, "只查页数大于400的数据: " + book.toString());
                }

                // order()方法用于指定结果的排序方式。 比如查询结果按照书价从高到低排序  desc表示降序排列 asc或者不写表示升序排列
                List<Book> priceDescList = LitePal.order("price desc").find(Book.class);
                for (Book book : priceDescList) {
                    Log.d(TAG, "查询结果按照书价从高到低排序: " + book.toString());
                }

                // limit()方法用于指定查询结果的数量。  比如只查表中的前3条数据
                List<Book> queryThreeBookList = LitePal.limit(3).find(Book.class);
                for (Book book : queryThreeBookList) {
                    Log.d(TAG, "只查表中的前3条数据: " + book.toString());
                }

                // offset()方法用于指定查询结果的偏移量。  比如查询表中的第2条、第3条、第4条数据
                List<Book> offsetBookList = LitePal.limit(3).offset(1).find(Book.class);
                for (Book book : offsetBookList) {
                    Log.d(TAG, "查询表中的第2条、第3条、第4条数据: " + book.toString());
                }

                // 5个查询方法组合完成复杂查询操作  比如查询第2~10条满足页数大于400这个条件的name，author和page这3列数据，并按升序排列
                List<Book> books = LitePal.select("name", "author", "pages")
                        .where("pages > ?", "400")
                        .order("pages")
                        .limit(10)
                        .offset(1)
                        .find(Book.class);
                for (Book book : books) {
                    Log.d(TAG, "5个查询方法组合完成复杂查询操作: " + book.toString());
                }

                // litePal使用原生SQL语句查询
                Cursor cursor = LitePal.findBySQL("select * from Book where pages > ? and price > ?", "400", "40");
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));

                        Log.d(TAG, "查询数据: " +
                                "name = " + name + " " +
                                "author = " + author + " " +
                                "pages = " + pages + " " +
                                "price = " + price);
                    }while (cursor.moveToNext());
                }


                Toast.makeText(LitePalTestActivity.this, "查询数据完成", Toast.LENGTH_LONG).show();
            }
        });
    }
}
