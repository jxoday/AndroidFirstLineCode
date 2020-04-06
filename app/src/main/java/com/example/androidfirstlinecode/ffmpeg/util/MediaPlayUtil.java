package com.example.androidfirstlinecode.ffmpeg.util;

import android.util.Log;

import com.ywl5320.bean.TimeBean;
import com.ywl5320.libenum.MuteEnum;
import com.ywl5320.libmusic.WlMusic;
import com.ywl5320.listener.OnCompleteListener;
import com.ywl5320.listener.OnErrorListener;
import com.ywl5320.listener.OnInfoListener;
import com.ywl5320.listener.OnLoadListener;
import com.ywl5320.listener.OnPreparedListener;
import com.ywl5320.listener.OnRecordListener;
import com.ywl5320.listener.OnShowPcmDataListener;
import com.ywl5320.listener.OnVolumeDBListener;


/**
 * @author JinXin
 */
public class MediaPlayUtil {

    private static final String TAG = "MediaPlayUtil";

    private static WlMusic wlMusic = WlMusic.getInstance();
    private static int volume = 0;

    public static void startAudio(String path){
        Log.d(TAG, "startAudio: " + path);

        Log.d(TAG, "startAudio: " + wlMusic);
        // 是否返回音频PCM数据
        wlMusic.setCallBackPcmData(true);
        // 是否返回音频分贝大小
        wlMusic.setShowPCMDB(true);
        // 设置不间断循环播放音频
        wlMusic.setPlayCircle(false);
        // 设置播放速度 (1.0正常) 范围：0.25---4.0f
        wlMusic.setPlaySpeed(1.0f);
        // 设置播放速度 (1.0正常) 范围：0.25---4.0f
        wlMusic.setPlayPitch(1.0f);
        // 设置立体声（左声道、右声道和立体声）
        wlMusic.setMute(MuteEnum.MUTE_CENTER);
        // 设定恒定采样率（null为取消）
        wlMusic.setConvertSampleRate(null);

        // 设置音频源
        if (wlMusic.isPlaying()) {
            wlMusic.playNext(path);
        } else {
            wlMusic.setSource(path);
        }
        wlMusic.prePared();

        wlMusic.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared() {
                Log.d(TAG, "onPrepared: " + wlMusic.getVolume());
                // 设置音量 65%
                wlMusic.setVolume(wlMusic.getVolume());
                wlMusic.start();
                FadeIn.volumeGradient(wlMusic, wlMusic.getVolume());
            }
        });

        wlMusic.setOnErrorListener(new OnErrorListener() {
            @Override
            public void onError(int code, String msg) {
                Log.d(TAG, "onError: " + "code :" + code + ", msg :" + msg);
            }
        });

        wlMusic.setOnLoadListener(new OnLoadListener() {
            @Override
            public void onLoad(boolean load) {
                Log.d(TAG, "onLoad: ");
            }
        });

        wlMusic.setOnInfoListener(new OnInfoListener() {
            @Override
            public void onInfo(TimeBean timeBean) {
                Log.d(TAG, "onInfo: " + timeBean.getCurrSecs() + ", total:" + timeBean.getTotalSecs());
            }
        });

        wlMusic.setOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });

        wlMusic.setOnVolumeDBListener(new OnVolumeDBListener() {
            @Override
            public void onVolumeDB(int db) {
                Log.d(TAG, "onVolumeDB: " + db);
            }
        });

        wlMusic.setOnRecordListener(new OnRecordListener() {
            @Override
            public void onRecordTime(int scds) {
                Log.d(TAG, "onRecordTime: is:" + scds);
            }

            @Override
            public void onRecordComplete() {
                Log.d(TAG, "onRecordComplete: ");
            }

            @Override
            public void onRecordPauseResume(boolean pause) {
                Log.d(TAG, "onRecordPauseResume: ");
            }
        });

        wlMusic.setOnShowPcmDataListener(new OnShowPcmDataListener() {
            @Override
            public void onPcmInfo(int samplerate, int bit, int channels) {
                Log.d(TAG, "onPcmInfo: " + samplerate + "---" + bit + "---" + channels);
            }

            @Override
            public void onPcmData(byte[] pcmdata, int size, long clock) {
                Log.d(TAG, "onPcmData: " + "pcm size is : " + size + " time is : " + clock);
            }
        });

    }

    public static void addVolume(int volume){
        MediaPlayUtil.volume = volume + MediaPlayUtil.volume ;

        if (MediaPlayUtil.volume > 100) {
            return;
        }
//        wlMusic.setVolume(MediaPlayUtil.volume);

    }

    public static void deleteVolume(int volume){
        MediaPlayUtil.volume = MediaPlayUtil.volume - volume ;

        if (MediaPlayUtil.volume < 0) {
            return;
        }
//        wlMusic.setVolume(MediaPlayUtil.volume);

    }
}
