package com.example.androidfirstlinecode.ffmpeg.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.androidfirstlinecode.R;
import com.example.androidfirstlinecode.ffmpeg.entity.AudioAlbum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JinXin
 */
public class AudioSelectAdapter extends BaseQuickAdapter<AudioAlbum, BaseViewHolder> {

    private static final String TAG = "AudioSelectAdapter";

    private int startPosition = -1;
    private int stopPosition = -1;
    /** 当前播放的音频对象 */
    private AudioAlbum currentStartAudioAlbum;

    public List<AudioAlbum> checkStatus =  new ArrayList<>(10);

    /**
     * 单选
     * @param audioAlbum
     */
    public void selectSingle(AudioAlbum audioAlbum){
        List<AudioAlbum> audioAlbumList = new ArrayList<>(checkStatus);
        List<AudioAlbum> adapterData = getData();
        checkStatus.clear();

        // 清除所有已选择的状态
        for (AudioAlbum album : audioAlbumList) {
            int oldActivePosition = adapterData.indexOf(album);
            notifyItemChanged(oldActivePosition);
        }
        // 判断数据是否存在列表
        int indexOf = adapterData.indexOf(audioAlbum);
        if (indexOf >= 0) {
            // 存在，更新状态为已选择
            checkStatus.add(audioAlbum);
            notifyItemChanged(indexOf);
        }
    }

    /**
     *  多选
     * @param audioAlbum
     */
    public void selectMultiple(AudioAlbum audioAlbum) {
        List<AudioAlbum> adapterData = getData();
        // 判断数据是否存在列表
        int indexOf = adapterData.indexOf(audioAlbum);
        if (indexOf < 0) {
            return;
        }
        // 判断数据是否已选择
        int position = checkStatus.indexOf(audioAlbum);
        if (position < 0) {
            checkStatus.add(audioAlbum);
            notifyItemChanged(indexOf);
        } else {
            checkStatus.remove(audioAlbum);
            notifyItemChanged(indexOf);
        }
    }

    public AudioSelectAdapter() {
        super(R.layout.item_audio_select);
    }

    @Override
    protected void convert(BaseViewHolder helper, AudioAlbum item) {
        TextView tvName = helper.getView(R.id.tv_name);
        TextView tvCreateTime = helper.getView(R.id.tv_create_time);
        TextView tvDuration = helper.getView(R.id.tv_duration);
        TextView tvFormat = helper.getView(R.id.tv_format);

        // 显示音频相关信息
        tvName.setText(item.getDisplayName());
        tvCreateTime.setText(TimeUtils.millis2String(item.getLastModified(),"yyyy-MM-dd"));
//        tvDuration.setText(DateUtil.convertAudioSecondsToTime(item.getDuration()));
        tvFormat.setText(item.getType());

        // 添加监听事件
        helper.addOnClickListener(R.id.iv_start);
        helper.addOnClickListener(R.id.iv_stop);

        showAndHideView(helper);

    }

    /**
     * 开始播放音频
     * @param newPosition
     * @param audioAlbum
     */
    public void startPlayAudio(int newPosition, AudioAlbum audioAlbum){
        currentStartAudioAlbum = audioAlbum;
        // 重置Position
        stopPosition = -1;

        int oldActivePosition = this.startPosition;
        this.startPosition = newPosition;

        notifyItemChanged(oldActivePosition);
        notifyItemChanged(newPosition);
    }

    /**
     * 停止播放音频
     * @param newPosition
     */
    public void stopPlayAudio(int newPosition){
        currentStartAudioAlbum = null;
        // 重置Position
        startPosition = -1;

        stopPosition = newPosition;
        notifyItemChanged(newPosition);
    }

    /**
     * 展示或隐藏布局
     * @param helper
     */
    private void showAndHideView(BaseViewHolder helper) {
        ImageView ivStart = helper.getView(R.id.iv_start);
        ImageView ivStop = helper.getView(R.id.iv_stop);

        int adapterPosition = helper.getAdapterPosition();

        if (startPosition >=0 ) {
            if (startPosition == adapterPosition) {
                ivStart.setVisibility(View.INVISIBLE);
                ivStop.setVisibility(View.VISIBLE);
            } else {
                ivStart.setVisibility(View.VISIBLE);
                ivStop.setVisibility(View.INVISIBLE);
            }
        }

        if (stopPosition >= 0) {
            if (stopPosition == adapterPosition) {
                ivStart.setVisibility(View.VISIBLE);
                ivStop.setVisibility(View.INVISIBLE);
            }
        }

    }

    /**
     * 判断当前播放的音频对象数据是否在列表中
     * @param audioAlbum
     */
    public void checkAudioPlayState(AudioAlbum audioAlbum) {
        List<AudioAlbum> audioAlbumList = getData();

        if (currentStartAudioAlbum == null) {
            return;
        }

        if(currentStartAudioAlbum.equals(audioAlbum)){
            // 当前播放的音频对象 和 列表中当前播放的音频对象相等，不做处理
            return;
        }

        // 列表中没有播放的音频数据，改变列表界面中音频对象的播放状态
        int oldPosition = audioAlbumList.indexOf(currentStartAudioAlbum);
        if (oldPosition >= 0) {
            stopPlayAudio(oldPosition);
        }
    }

    /**
     * 播放完成 改变列表界面中音频对象的播放状态
     * @param audioAlbum
     */
    public void playComplete(AudioAlbum audioAlbum) {
        List<AudioAlbum> audioAlbumList = getData();

        if (currentStartAudioAlbum == null) {
            return;
        }
        if(!currentStartAudioAlbum.equals(audioAlbum)){
            return;
        }
        int oldPosition = audioAlbumList.indexOf(currentStartAudioAlbum);
        if (oldPosition >= 0) {
            stopPlayAudio(oldPosition);
        }
    }

}
