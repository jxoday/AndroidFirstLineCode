package com.example.androidfirstlinecode.content_provider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androidfirstlinecode.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 内容提供器
 *
 * 内容提供器的方法一般有两种
 *      一：使用现有的内容提供器来读取和操作相应程序中的数据
 *      二：创建自己的内容提供器给我们程序的数据提供外部访问接口
 *
 *   如果一个应用程序通过内容提供器对其数据提供了外部访问接口，那么任何其他的应用程序就都可以对这部分数据进行访问。
 * Android系统中自带的电话簿、短信、媒体库等程序都提供了类似的访问接口，这就使得第三方应用程序可以充分地利用这部
 * 分数据来实现更好的功能
 *
 * @author JinXin
 */
public class ContentResolverTestActivity extends AppCompatActivity {

    private List<String> contactsList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_resolver_test);

        initContentResolver();
    }

    /**
     * ContentResolver的基本用法
     *
     *   对于每一个应用程序来说，如果想要访问内容提供器中共享的数据，就一定要借助ContentResolver类，
     * 可以通过Context中的getContentResolver()方法获取到该类的实例。ContentResolver中提供了一系
     * 列的方法用于对数据进行CRUD操作，其中 insert()方法用于添加数据，update()方法用于更新数据，
     * delete()方法用于删除数据，query()方法用于查询数据。
     *
     *   ContentResolver中的增删改查方法接收的参数使用一个 Uri代替，这个参数被称为内容URI。内容URI
     * 给内容提供器中的数据建立了唯一标识符，它主要有两部分组成：authority 和 path。authority是用于
     * 对不同的应用程序做区分的，一般为了避免冲突，都会采用程序包名的方式来进行命名。path则是用于对同
     * 一应用程序中不同的表做区分的，通常都会添加到authority的后面。因此，内容URI最标准的格式写法如下：
     *      content://com.example.app.provider/table1
     *      content://com.example.app.provider/table2
     *
     *   在得到了内容URI字符串之后，还需要将它解析成Uri对象才可以作为参数传入
     *   解析方法：Uri uri = Uri.parse("content://com.example.app.provider/table1")
     *
     *   只需要调用Uri.parse()方法(),就可以将内容Uri字符串解析成Uri对象
     *   使用Uri对象查询table1表中的数据，代码如下所示：
     *      Cursor cursor = getContentResolver().query(
     *      uri,           from table_name             指定查询某个应用程序下的某一张表
     *      projection,    select column1, column2     指定查询的列名
     *      selection,     where column = value        指定where的约束条件
     *      selectionArgs, -                           为where中的占位符提供具体的值
     *      soreOrder);    order by column1, column2   指定查询结果的排序方式
     *
     *   查询数据：
     *      if(cursor != null){
     *          while (cursor.moveToNext()){
     *              String column1 = cursor.getString(cursor.getColumnIndex("column1"));
     *              int column2 = cursor.getInt(cursor.getColumnIndex("column2"));
     *          }
     *          cursor.close();
     *      }
     *   添加数据：
     *      ContentValues values = new ContentValues();
     *      values.put("column1", "text");
     *      values.put("column2", 2);
     *      getContentResolver(),insert(uri, values);
     *   更新数据：
     *      ContentValues values = new ContentValues();
     *      values.put("column1", "");
     *      getContentResolver().update(uri, values, "column1 = ? and column2 = ?", new String [] {"text", "1"};
     *   删除数据：
     *      getContentResolver().delete(uri, "column2 = ?", new String[] {"1"});
     */
    private void initContentResolver() {

        // 读取系统联系人
        ListView contactsView = findViewById(R.id.contacts_view);
        contactsList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactsList);
        contactsView.setAdapter(adapter);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            readContacts();
        }
    }

    private void readContacts() {
        Cursor cursor = null;
        try {
            // 查询联系人数据
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    null,
                    null,
                    null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // 获取联系人姓名
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    // 获取联系人手机号
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactsList.add(displayName + "\n" + number);
                }
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                } else {
                    Toast.makeText(this, "你拒绝开启权限", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }
}
