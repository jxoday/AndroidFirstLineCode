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

    // Fragment状态
    // 1.运行状态 ：当一个碎片是可见的，并且它所关联的活动正处于运行状态时，该碎片也处于运行状态

    // 2.暂停状态 ：当一个活动进入暂停状态时（由于另一个未占满屏幕的活动被添加到了栈顶），与它相关联的可见碎片就会进入到暂停状态

    // 3.停止状态 ：当一个活动进入停止状态时，与它相关联的碎片就会进入到停止状态，
    //             或者通过调用FragmentTransaction 的 remove(), replace()方法将碎片从活动中移除，但如果在事务提交之前调用addToBackStack()方法，这时的碎片也会进入到停止状态。总的来说，进入停止状态的碎片对用于来说是完全不可见的，有可能会被系统回收

    // 4.销毁状态 : 碎片总是依附于活动而存在的，因此当活动被销毁时，与它相关联的碎片就会进入到销毁状态。
    //             或者通过调用FragmentTransaction 的 remove()、replace()方法将碎片从活动中移除，但在事务提交之前并没有调用addToBackStack()方法，这时的碎片也会进入到销毁状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 2.由于AnotherRightFragment替换了RightFragment 此时RightFragment进入了停止状态、因此会依次执行onPause() → onStop() → onDestroyView()
                replaceFragment(new AnotherRightFragment());
            }
        });

        // 1.当RightFragment第一次被加载到屏幕上时，会依次执行onAttach() → onCreate() → onCreateView() → onActivityCreated() → onStart() → onResume()
        replaceFragment(new RightFragment());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 3.按下Back键，RightFragment会重新回到屏幕，依次执行onCreateView() → onActivityCreated() → onStart() → onResume()
        // 此时onCreate()和onCreateView()方法并不会执行，因为addToBackStack（）方法使得RightFragment和它的视图并没有销毁

        // 4.再次按下Back键，依次执行onPause() → onStop() → onDestroyView() → onDestroy() → onDetach()
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
