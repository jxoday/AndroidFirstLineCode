package com.example.androidfirstlinecode.fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.androidfirstlinecode.R;

/**
 * Fragment 生命周期
 * @author Administrator
 */
public class FragmentLifecycleActivity extends AppCompatActivity {

    // Fragment状态
    // 1.运行状态 ：当一个碎片是可见的，并且它所关联的活动正处于运行状态时，该碎片也处于运行状态
    // 2.暂停状态 ：当一个活动进入暂停状态时（由于另一个未占满屏幕的活动被添加到了栈顶），与它相关联的可见碎片就会进入到暂停状态
    // 3.停止状态 ：当一个活动进入停止状态时，与它相关联的碎片就会进入到停止状态，或者通过调用FragmentTransaction 的 remove(), replace()方法将碎片从活动中移除，
    //          但如果在事务提交之前调用addToBackStack()方法，这时的碎片也会进入到停止状态。总的来说，进入停止状态的碎片对用于来说是完全不可见的，有可能会被系统回收
    // 4.销毁状态 : 碎片总是依附于活动而存在的，因此当活动被销毁时，与它相关联的碎片就会进入到销毁状态。或者通过调用FragmentTransaction 的 remove()、replace()方法将碎片从活动中移除，
    //          但在事务提交之前并没有调用addToBackStack()方法，这时的碎片也会进入到销毁状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_lifecycle);
    }
}
