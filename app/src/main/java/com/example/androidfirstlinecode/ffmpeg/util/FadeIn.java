package com.example.androidfirstlinecode.ffmpeg.util;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import com.example.androidfirstlinecode.base.BaseApplication;
import com.ywl5320.libmusic.WlMusic;

/**
 * 音量淡入
 */
public class FadeIn {

    private static final String TAG = "FadeIn";

    private static final int ACTION_RESTORE_VALUE = 1000;

    @SuppressLint("HandlerLeak")
    private static Handler handler = new Handler(BaseApplication.getAppContext().getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == ACTION_RESTORE_VALUE) {

            }
        }
    };

    public static void volumeGradient(final WlMusic music, final int to) {

        new Handler(BaseApplication.getAppContext().getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ValueAnimator animator = ValueAnimator.ofInt(0, to);
                animator.setDuration(6000); // 淡入时间
                animator.setInterpolator(new LinearInterpolator());
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator it) {
                        int volume = (int) it.getAnimatedValue();
                        Log.d(TAG, "onAnimationUpdate: " + volume);
                        music.setVolume(volume);
                        try {
                            // 此时可能 mediaPlayer 状态发生了改变
                            //,所以用try catch包裹,一旦发生错误,立马取消
                        } catch (Exception e) {
                            it.cancel();
                        }
                    }

                });

                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        Log.d(TAG, "onAnimationStart: ");
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Log.d(TAG, "onAnimationEnd: ");
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        Log.d(TAG, "onAnimationCancel: ");
                    }
                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        Log.d(TAG, "onAnimationRepeat: ");
                    }
                });
                animator.start();
            }
        }, 0);
    }
}
