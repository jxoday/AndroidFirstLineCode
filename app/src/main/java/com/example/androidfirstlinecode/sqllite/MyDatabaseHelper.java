package com.example.androidfirstlinecode.sqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * SQLiteOpenHelper中有两个构造方法可供重写，一般适用参数少一点的那个构造方法即可。
 *
 * 构建出SQLiteOpenHelper的实例之后，在调用它的getWritableDatabase()方法或getReadableDatabase()方法
 * 就能创建数据库了，数据库文件会存放在/data/data/<package name>/databases/目录下。此时，重写的onCreate()方法
 * 也会得到执行，所以通常会在这里去处理一些创建表的逻辑
 *
 * getWritableDatabase()方法和getReadableDatabase()方法都可以创建或打开一个现有的数据库
 * (如果数据库已存在则直接打开，否则创建一个新的数据库)，
 * 并返回一个可对数据库进行读写操作的对象。不同的是，当数据库不可写入的时候（如磁盘空间已满），
 * getReadableDatabase()方法返回的对象将以只读的方式去打开数据库，
 * 而getWritableDatabase()方法则将出现异常
 *
 * @author JinXin
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context mContext;

    /**
     * integer 表示整型
     * real 表示浮点型
     * text 表示文本类型
     * blob 表示二进制类型
     */
    public static final String CREATE_BOOK = "create table Book (" +
            "id integer primary key autoincrement, " +
            "author text, " +
            "price real, " +
            "pages integer, " +
            "name text)";

    /**
     * @param context 上下文
     * @param name 数据库名称
     * @param factory 允许在查询数据库的时候返回一个自定义的Cursor，一般都是传入null
     * @param version 当前数据库的版本号，可用于对数据库进行升级操作
     */
    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    /**
     * 创建数据库
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // execSQL执行建表语句
        db.execSQL(CREATE_BOOK);
        Toast.makeText(mContext, "创建表", Toast.LENGTH_LONG).show();
    }

    /**
     * 升级数据库
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
