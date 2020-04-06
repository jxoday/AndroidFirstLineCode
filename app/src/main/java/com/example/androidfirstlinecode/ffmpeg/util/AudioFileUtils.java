package com.example.androidfirstlinecode.ffmpeg.util;

import android.media.MediaMetadataRetriever;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.RomUtils;

/**
 * @author JinXin
 */
public class AudioFileUtils {

    private static final String TAG = "AudioFileUtils";

    /**
     * 判断文件格式
     * @param mimeType
     * @return
     */
    public static String determineFileType(String mimeType){
        String type = "";
        switch (mimeType) {
            case "audio/x-mpeg":
                type = ".mp3";
                break;
            case "audio/x-aac":
                type = ".aac";
                break;
            case "audio/mp4a-latm":
                type = ".m4a";
                break;
            case "audio/flac":
                type = ".flac";
                break;
            case "audio/x-ms-wma":
                type = ".wma";
                break;
            case "audio/x-wav":
                type = ".wav";
                break;
            default:
                type = ".mp3";
                break;
        }
        return type;
    }


    /**
     * 根据音频路径加载封面
     * @param mediaUri
     */
    private static void loadingCover(String mediaUri) {
        MediaMetadataRetriever mediaMetadataRetriever=new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(mediaUri);
        byte[] picture = mediaMetadataRetriever.getEmbeddedPicture();
    }

//    public static void notifySystemToScan(){
//        MediaScannerConnection.scanFile(BaseApplication.getAppContext(), new String[] { Environment
//                .getExternalStorageDirectory().getAbsolutePath() }, null, null);
//    }

    public static String getRecordFilePath(){

        String storagePath = PathUtils.getExternalStoragePath();

        if (RomUtils.isXiaomi()) {
            //小米
            String recorderPath = storagePath + "/MIUI/sound_recorder/call_rec";

            if (FileUtils.isFileExists(recorderPath)) {
                return storagePath + "/MIUI/sound_recorder/call_rec";
            } else {
                return storagePath + "/MIUI/sound_recorder";
            }

        } else if (RomUtils.isHuawei()) {
            //华为
            if (FileUtils.isFileExists(storagePath + "/record")) {
                return storagePath + "/record";
            } else {
                return storagePath + "/sounds/callRecord";
            }

        } else if (RomUtils.isMeizu()) {
            //魅族
            return storagePath + "/Recorder";

        } else if (RomUtils.isOppo()) {
            //oppo
            return storagePath + "/Recordings";

        } else if (RomUtils.isVivo()) {
            //vivo
            return storagePath + "/Record/Call";

        }
        return "";
    }
}
