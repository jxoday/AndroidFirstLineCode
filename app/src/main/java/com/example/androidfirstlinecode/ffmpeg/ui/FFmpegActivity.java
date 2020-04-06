package com.example.androidfirstlinecode.ffmpeg.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.androidfirstlinecode.R;
import com.example.androidfirstlinecode.ffmpeg.entity.AudioAlbum;
import com.example.androidfirstlinecode.ffmpeg.provider.AudioAlbumProvider;
import com.example.androidfirstlinecode.ffmpeg.util.MediaPlayUtil;

import java.util.Arrays;

import io.microshow.rxffmpeg.FFmpegOrderUtil;
import io.microshow.rxffmpeg.RxFFmpegUtil;

import static org.litepal.LitePalApplication.getContext;

/**
 * @author JinXin
 */
public class FFmpegActivity extends AppCompatActivity {

    private static final String TAG = "FFmpegActivity";
    private AudioSelectAdapter adapter;
    private AudioAlbum audioAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ffmpeg);

        initView();
    }

    private void initView() {

        RecyclerView recyclerMedia = findViewById(R.id.recycler_media);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerMedia.setLayoutManager(layoutManager);
        adapter = new AudioSelectAdapter();
        recyclerMedia.setAdapter(adapter);
        PermissionUtils.permission(PermissionConstants.STORAGE, PermissionConstants.PHONE)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        // 添加数据
                        adapter.setNewData(AudioAlbumProvider.getAllMediaAudioList());
                    }

                    @Override
                    public void onDenied() {
                    }
                })
                .request();

        // 点击事件
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                audioAlbum = (AudioAlbum) baseQuickAdapter.getItem(position);
                int viewId = view.getId();
                switch (viewId) {
                    case R.id.iv_start:
                        Log.d(TAG, "onItemChildClick: 点击播放 " + audioAlbum.getDisplayName());
                        adapter.startPlayAudio(position, audioAlbum);
                        MediaPlayUtil.startAudio(audioAlbum.getFilePath());
                        break;
                    case R.id.iv_stop:
                        Log.d(TAG, "onItemChildClick: 点击暂停" + audioAlbum.getDisplayName());
                        adapter.stopPlayAudio(position);
                        break;
                    default:
                        break;
                }
            }
        });

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayUtil.addVolume(20);
            }
        });

        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayUtil.deleteVolume(20);
            }
        });

        findViewById(R.id.btn_transition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (audioAlbum == null) {
                    return;
                }

                String srcFile = audioAlbum.getFilePath();
                String targetFile = PathUtils.getExternalStoragePath() + "/androidFirstLine/cut/" + TimeUtils.getNowMills() + ".mp3";
                FileUtils.createOrExistsDir(PathUtils.getExternalStoragePath() + "/androidFirstLine/cut");
                Log.d(TAG, "onClick: " + srcFile + " " + targetFile);
                String[] cutAudio = FFmpegOrderUtil.audioFadeOut(srcFile, targetFile, 0 , 5);
                Log.d(TAG, "onClick: " + Arrays.toString(cutAudio));
                RxFFmpegUtil.runFFmpeg(cutAudio);
            }
        });
    }
}

