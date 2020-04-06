package com.example.androidfirstlinecode;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.TimeUtils;

import java.io.FileNotFoundException;
import java.io.IOException;

import io.microshow.rxffmpeg.RxFFmpegInvoke;
import io.microshow.rxffmpeg.RxFFmpegSubscriber;

/**
 * @author JinXin
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String videoOutputPath = PathUtils.getExternalDcimPath() + "/Camera";
        String outFile = videoOutputPath+ "/" + TimeUtils.getNowMills() + ".mp4";

        // 图片显示0.5秒后消失
//        String filter =  "-i " + url  + " -i " + "/storage/emulated/0/DCIM/Screenshots/Screenshot_2020-03-17-12-15-23-633_com.orangemedia.avatar.jpg "+ "-filter_complex " +  "[0:v][1:v]overlay=10:10:enable=between(t\\,0\\,0.5) -codec:a copy " + outFile ;

        // 视频
//        String filter = "ffmpeg -y -i /storage/emulated/0/VideoWatermark/20190512222838.mp4 -loop 1 -framerate 25 -t 3.0 -i /storage/emulated/0/DCIM/Screenshots/Screenshot_2020-03-17-12-15-23-633_com.orangemedia.avatar.jpg -f lavfi -t 3.0 -i anullsrc=channel_layout=stereo:sample_rate=44100 -filter_complex [0:v]scale=iw:ih[outv0];[1:v]scale=iw:ih[outv1];[outv0][outv1]concat=n=2:v=1:a=0:unsafe=1[outv];[0:a][2:a]concat=n=2:v=0:a=1[outa] -map [outv] -map [outa] -r 25 -b 1M -f mp4 -vcodec libx264 -c:a aac -pix_fmt yuv420p -s 960x540 -preset superfast " + outFile;
        String filter = "ffmpeg -y -loop 1 -framerate 1 -t 3 -i /storage/emulated/0/DCIM/Screenshots/Screenshot_2020-03-17-12-15-23-633_com.orangemedia.avatar.jpg -i /storage/emulated/0/VideoWatermark/20190512222838.mp4 -f lavfi -t 3.0 -i anullsrc=channel_layout=stereo:sample_rate=44100 -filter_complex [0:v]scale=iw:ih[outv0];[1:v]scale=iw:ih[outv1];[outv0][outv1]concat=n=2:v=1:a=0:unsafe=1[outv];[2:a][1:a]concat=n=2:v=0:a=1[outa] -map [outv] -map [outa] -r 15 -b 1M -f mp4 -vcodec libx264 -c:a aac -pix_fmt yuv420p -s 960x540 -preset superfast " + outFile;


        String[] commands = filter.split(" ");

        RxFFmpegInvoke.getInstance().runCommandRxJava(commands).subscribe(new RxFFmpegSubscriber() {
            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish: ");
            }

            @Override
            public void onProgress(int progress, long progressTime) {
                Log.d(TAG, "onProgress: " + progress);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: ");
            }

            @Override
            public void onError(String message) {
                Log.d(TAG, "onError: " + message);
            }
        });


    }
}
