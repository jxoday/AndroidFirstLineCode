package io.microshow.rxffmpeg;

import android.util.Log;

/**
 * @author JinXin
 */
public class RxFFmpegUtil {

    private static final String TAG = "RxFFmpegUtil";

    public static void runFFmpeg(String[] commands ){
        Log.d(TAG, "runFFmpeg: " + commands);
        RxFFmpegInvoke.getInstance().runCommandRxJava(commands).subscribe(new RxFFmpegSubscriber() {
            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish: 处理成功");
            }

            @Override
            public void onProgress(int progress, long progressTime) {
                Log.d(TAG, "onProgress: " + "已处理progressTime="+(double)progressTime/1000000+"秒");
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: 已取消");
            }

            @Override
            public void onError(String message) {
                Log.d(TAG, "onError: " + "出错了 onError：" + message);
            }
        });
    }
}
