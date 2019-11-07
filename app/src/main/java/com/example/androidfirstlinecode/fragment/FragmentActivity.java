package com.example.androidfirstlinecode.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.androidfirstlinecode.R;

/**
 * Fragment简单用法以及动态添加Fragment
 * @author Administrator
 */
public class FragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AnotherRightFragment());
            }
        });

        replaceFragment(new RightFragment());
    }

    private void replaceFragment(Fragment fragment) {
        // 获取FragmentManager
        FragmentManager manager = getSupportFragmentManager();
        // 开启Fragment事务 通过调用beginTransaction开启
        FragmentTransaction transaction = manager.beginTransaction();
        // 向容器内替换fragment 需要传入容器id和待添加的fragment实例
        transaction.replace(R.id.fl_right, fragment);
        // 在提交事务之前调用FragmentTransaction的addToBackStack()方法 模拟返回栈
        // 可以接收一个名字用于返回栈的状态，一般传入null即可
        transaction.addToBackStack(null);
        // 提交事务
        transaction.commit();
    }
}
