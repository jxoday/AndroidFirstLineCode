package com.example.androidfirstlinecode.filestore;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.androidfirstlinecode.R;

/**
 * SharedPreferences存储
 *
 * SharedPreferences是使用键值对的方式来存储数据
 *
 * 得到SharedPreferences对象的三种方法
 *      1.Context类中的getSharedPreferences()方法：此方法接收两个参数，第一参数用于指定SharedPreferences文件的名称，
 *   如果指定的文件不存在则会创建一个，SharedPreferences文件都是存放/data/data/<package name>/shared_prefs/目录下。
 *   第二个参数用于指定操作模式，目前只有MODE_PRIVATE这一种模式可选，它是默认的操作模式，和直接传入0效果是相同的，表示只
 *   有当前的应用程序才可以对这个SharedPreferences文件进行读写
 *      2.Activity类中的getPreferences()方法：只接收一个参数，因为使用这个方法时会自动将当前活动的类名作为SharedPreferences的文件名
 *      3.PreferenceManager类中的getDefaultSharedPreferences()方法：这是一个静态方法，它接收一个Context参数，并自动
 *   使用当前应用程序的包名作为前缀来命名SharedPreferences文件。得到了SharedPreferences对象之后，就可以开始向SharedPreferences
 *   文件中存储数据了，主要可以分为3步实现。
 *          (1)调用SharedPreferences对象的edit()方法来获取一个SharedPreferences.Editor对象
 *          (2)向SharedPreferences.Editor对象中添加数据，比如添加一个布尔型数据就使用putBoolean()方法，添加一个字符串
 *       则使用putString()方法，以此类推
 *          (3)调用apply()方法将添加的数据提交，从而完成数据存储操作。
 *
 * @author JinXin
 */
public class SharedPreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);

        Button saveDataBtn = findViewById(R.id.btn_save_data);
        saveDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SharedPreferences 存储数据
                SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                editor.putString("name", "JinXin");
                editor.putInt("age",18);
                editor.putBoolean("married", false);
                editor.apply();
                Toast.makeText(SharedPreferencesActivity.this, "SharedPreferences存储成功", Toast.LENGTH_LONG).show();
            }
        });
    }
}
