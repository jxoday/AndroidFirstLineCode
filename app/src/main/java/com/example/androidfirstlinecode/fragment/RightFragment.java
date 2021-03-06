package com.example.androidfirstlinecode.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidfirstlinecode.R;

/**
 * @author Administrator
 */
public class RightFragment extends Fragment {

    private static final String TAG = "RightFragment";

    // 当RightFragment第一次被加载到屏幕上时，会依次执行onAttach（）→ onCreate（）→ onCreateView（）→ onActivityCreated（）→ onStart（）→ onResume（）

    /**
     * 当 Fragment 和 Activity 建立关联时调用
     * @param context 上下文
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    /**
     * 当 fragment 创建视图调用，在onCreate 之后
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_right, container, false);
        return view;
    }

    /**
     * 当与 Fragment 相关联的Activity 完成 onCreate()之后调用
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    /**
     * 在 Fragment 中的布局被移除时调用
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    /**
     * 当 Fragment 和 Activity 解除关联时调用
     */
    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }

    /**
     * 在Fragment可以通过onSaveInstanceState()方法来保存数据，因为进入停止状态的碎片有可能在系统内存不足的时候被回收
     * 保存下来的数据在onCreate()、onCreateView()、onActivityCreated()这3个方法中都可以重新得到
     * @param outState
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
