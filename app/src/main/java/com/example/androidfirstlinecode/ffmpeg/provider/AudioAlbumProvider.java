package com.example.androidfirstlinecode.ffmpeg.provider;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.Log;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PathUtils;
import com.example.androidfirstlinecode.base.BaseApplication;
import com.example.androidfirstlinecode.ffmpeg.entity.AudioAlbum;
import com.example.androidfirstlinecode.ffmpeg.util.AudioFileUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author JinXin
 */
public class AudioAlbumProvider {

    private static final String TAG = "AudioAlbumProvider";

    private static List<AudioAlbum> audioAlbumList = new ArrayList<>();
    private static List<AudioAlbum> mediaAudioList = new ArrayList<>();
    private static List<AudioAlbum> downloadAudioList = new ArrayList<>();
    private static List<AudioAlbum> recordAudioList = new ArrayList<>();
    private static List<AudioAlbum> cutAudioList = new ArrayList<>();
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",  Locale.getDefault());

    /** 查找录音音频文件过滤条件 */
    private static FileFilter mFilter = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return FileUtils.isFile(pathname)
                    ||pathname.getName().endsWith(".mp3")
                    ||pathname.getName().endsWith(".aac")
                    ||pathname.getName().endsWith(".m4a")
                    ||pathname.getName().endsWith(".flac")
                    ||pathname.getName().endsWith(".wma")
                    ||pathname.getName().endsWith(".wav");
        }
    };

    /**
     * 获取媒体库所有音频数据
     */
    public static List<AudioAlbum> getAllMediaAudioList(){
        List<AudioAlbum> audioAlbumList = new ArrayList<>();

        Cursor cursor = BaseApplication.getAppContext().getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,
                null, null);


        if (cursor.moveToFirst()) {
            do {
                // 歌曲文件的名称
                String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                // 歌曲的名称
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                // 歌曲的专辑名
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                // 歌曲的歌手名
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                // 歌曲文件的全路径
                String filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                // 歌曲的总播放时长
                long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                // 歌曲文件的大小
                String size = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                // 歌曲文件的类型
                String type = AudioFileUtils.determineFileType(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE)));
                // 最后一次修改时间
                long lastModified = FileUtils.getFileLastModified(filePath);

                if (duration == 0 || size == null) {
                    continue;
                }

                String year = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.YEAR));
                Log.d(TAG, "getAllMediaAudioList: " + displayName + " " + year);
                AudioAlbum audioAlbum = AudioAlbum.builder()
                        .displayName(displayName)
                        .title(title)
                        .album(album)
                        .artist(artist)
                        .filePath(filePath)
                        .duration(duration)
                        .size(size)
                        .type(type)
                        .lastModified(lastModified)
                        .build();

                audioAlbumList.add(audioAlbum);
            } while (cursor.moveToNext());

            cursor.close();
        }

        // 按照最近的修改时间排序
        Comparator<? super AudioAlbum> comparator = new Comparator<AudioAlbum>() {
            @Override
            public int compare(AudioAlbum o1, AudioAlbum o2) {
                return Long.compare(o2.getLastModified(),o1.getLastModified());
            }
        };
        Collections.sort(audioAlbumList,comparator);

        for (AudioAlbum audioAlbum : audioAlbumList) {
            Log.d(TAG, "getAllMediaAudioList: " + audioAlbum);
        }

        mediaAudioList.clear();
        mediaAudioList.addAll(audioAlbumList);

        return audioAlbumList;
    }

    /**
     * 媒体库音频数据模糊查询
     * @param displayName
     * @return
     */
    public static List<AudioAlbum> searchMediaAudioList(String displayName){
        List<AudioAlbum> searchMediaAudioList = new ArrayList<>();
        Pattern pattern = Pattern.compile(displayName);
        for(int i=0; i < mediaAudioList.size(); i++){
            Matcher matcher = pattern.matcher(((AudioAlbum)mediaAudioList.get(i)).getDisplayName());
            if(matcher.find()){
                searchMediaAudioList.add(mediaAudioList.get(i));
            }
        }
        for (AudioAlbum audioAlbum : searchMediaAudioList) {
            Log.d(TAG, "searchMediaAudioList: " + audioAlbum);
        }
        return searchMediaAudioList;
    }

    /**
     * 获取下载的音乐
     *
     * @return
     */
    public static List<AudioAlbum> getAllDownloadAudioList() {
        // qq音乐下载路径
        String qqMusicPath = PathUtils.getExternalStoragePath() + "/qqmusic/song/";
        // 网易云音乐下载路径
        String neteasePath = PathUtils.getExternalStoragePath() + "/netease/cloudmusic/Music/";
        // 酷狗音乐下载路径
        String kgMusicPath = PathUtils.getExternalStoragePath() + "/download/kgmusic/";

        //根据qq音乐、网易云音乐、 酷狗音乐下载路径路径模糊查询音频
        List<AudioAlbum> mediaAudio = getMediaAudio(new String[]{"%" + qqMusicPath + "%", "%" + neteasePath + "%", "%" + kgMusicPath + "%"});
        // 按照最近的修改时间排序
        Comparator<? super AudioAlbum> comparator = new Comparator<AudioAlbum>() {
            @Override
            public int compare(AudioAlbum o1, AudioAlbum o2) {
                return Long.compare(o2.getLastModified(), o1.getLastModified());
            }
        };
        Collections.sort(mediaAudio, comparator);

        downloadAudioList.clear();
        downloadAudioList.addAll(mediaAudio);
        return mediaAudio;
    }

    /**
     * 根据qq音乐、网易云音乐、 酷狗音乐下载路径路径模糊查询音频
     * @param paths
     * @return
     */
    private static List<AudioAlbum> getMediaAudio(String[] paths){
        List<AudioAlbum> audioAlbumList = new ArrayList<>();

        Cursor cursor = BaseApplication.getAppContext().getContentResolver()
                .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Audio.Media.DATA + " like? or " + MediaStore.Audio.Media.DATA + " like? or " + MediaStore.Audio.Media.DATA + " like?" , paths, null);

        if (cursor == null) {
            Log.d(TAG, "getMediaAudio: cursor == null");
            return audioAlbumList;
        }
        if (cursor.moveToFirst()) {
            do {
                // 歌曲文件的名称
                String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                // 歌曲的名称
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                // 歌曲的专辑名
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                // 歌曲的歌手名
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                // 歌曲文件的全路径
                String filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                // 歌曲的总播放时长
                long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                // 歌曲文件的大小
                String size = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                // 歌曲文件的类型
                String type = AudioFileUtils.determineFileType(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE)));
                // 最后一次修改时间
                long lastModified = FileUtils.getFileLastModified(filePath);

                if (duration == 0 || size == null) {
                    continue;
                }

                String year = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.YEAR));
                AudioAlbum audioAlbum = AudioAlbum.builder()
                        .displayName(displayName)
                        .title(title)
                        .album(album)
                        .artist(artist)
                        .filePath(filePath)
                        .duration(duration)
                        .size(size)
                        .type(type)
                        .lastModified(lastModified)
                        .build();

                audioAlbumList.add(audioAlbum);
            } while (cursor.moveToNext());

            cursor.close();
        }

        for (AudioAlbum audioAlbum : audioAlbumList) {
            Log.d(TAG, "getMediaAudio: " + audioAlbum);
        }
        return audioAlbumList;
    }

    /**
     * 下载音频数据模糊查询
     * @param displayName
     * @return
     */
    public static List<AudioAlbum> searchDownloadAudioList(String displayName){
        List<AudioAlbum> searchDownloadAudioList = new ArrayList<>();
        Pattern pattern = Pattern.compile(displayName);
        for(int i=0; i < downloadAudioList.size(); i++){
            Matcher matcher = pattern.matcher(((AudioAlbum)downloadAudioList.get(i)).getDisplayName());
            if(matcher.find()){
                searchDownloadAudioList.add(downloadAudioList.get(i));
            }
        }
        for (AudioAlbum audioAlbum : searchDownloadAudioList) {
            Log.d(TAG, "searchDownloadAudioList: " + audioAlbum);
        }
        return searchDownloadAudioList;
    }

    /**
     * 获取所有录音音频
     * @return
     */
    public static List<AudioAlbum> getAllRecordAudio(){
        List<AudioAlbum> audioAlbumList = new ArrayList<>();

        String recordFilePath = AudioFileUtils.getRecordFilePath();
        Log.d(TAG, "getAllRecordAudio: " + recordFilePath);
        List<File> fileList = FileUtils.listFilesInDirWithFilter(recordFilePath, mFilter);
        if (fileList == null) {
            return audioAlbumList;
        }
        for (File file : fileList) {
            try {
                // 最后修改时间
                long lastModified = FileUtils.getFileLastModified(file);
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(file.getAbsolutePath());
                mediaPlayer.prepare();
                long duration = mediaPlayer.getDuration();
                String type = FileUtils.getFileExtension(file.getAbsolutePath());
                AudioAlbum audioAlbum = AudioAlbum.builder()
                        .displayName(file.getName())
                        .title(file.getName())
                        .filePath(file.getAbsolutePath())
                        .duration(duration)
                        .size(FileUtils.getFileSize(file))
                        .type(type)
                        .lastModified(lastModified)
                        .build();
                audioAlbumList.add(audioAlbum);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 按照最近的修改时间排序
        Comparator<? super AudioAlbum> comparator = new Comparator<AudioAlbum>() {
            @Override
            public int compare(AudioAlbum o1, AudioAlbum o2) {
                return Long.compare(o2.getLastModified(), o1.getLastModified());
            }
        };
        Collections.sort(audioAlbumList, comparator);

        recordAudioList.clear();
        recordAudioList.addAll(audioAlbumList);
        return audioAlbumList;
    }

    /**
     * 录音音频数据模糊查询
     * @param displayName
     * @return
     */
    public static List<AudioAlbum> searchRecordAudioList(String displayName){
        List<AudioAlbum> searchRecordAudioList = new ArrayList<>();
        Pattern pattern = Pattern.compile(displayName);
        for(int i=0; i < recordAudioList.size(); i++){
            Matcher matcher = pattern.matcher(((AudioAlbum)recordAudioList.get(i)).getDisplayName());
            if(matcher.find()){
                searchRecordAudioList.add(recordAudioList.get(i));
            }
        }
        return searchRecordAudioList;
    }

    /**
     * 获取所有剪裁音频
     * @return
     */
    public static List<AudioAlbum> getAllCutAudio(){
        List<AudioAlbum> audioAlbumList = new ArrayList<>();

        String cutAudioPath = PathUtils.getExternalStoragePath() + "/media/audio/songcutter";
        if (!FileUtils.createOrExistsDir(cutAudioPath)) {
            return audioAlbumList;
        }
        Cursor cursor = BaseApplication.getAppContext().getContentResolver()
                .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Audio.Media.DATA + " like?" , new String[]{"%" + cutAudioPath + "%" }, null);
        if (cursor == null) {
            Log.d(TAG, "getMediaAudio: cursor == null");
            return audioAlbumList;
        }
        if (cursor.moveToFirst()) {
            do {
                // 歌曲文件的名称
                String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                // 歌曲的名称
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                // 歌曲的专辑名
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                // 歌曲的歌手名
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                // 歌曲文件的全路径
                String filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                // 歌曲的总播放时长
                long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                // 歌曲文件的大小
                String size = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                // 歌曲文件的类型
                String type = AudioFileUtils.determineFileType(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE)));
                // 最后一次修改时间
                long lastModified = FileUtils.getFileLastModified(filePath);

                if (duration == 0 || size == null) {
                    continue;
                }

                AudioAlbum audioAlbum = AudioAlbum.builder()
                        .displayName(displayName)
                        .title(title)
                        .album(album)
                        .artist(artist)
                        .filePath(filePath)
                        .duration(duration)
                        .size(size)
                        .type(type)
                        .lastModified(lastModified)
                        .build();

                audioAlbumList.add(audioAlbum);
            } while (cursor.moveToNext());

            cursor.close();
        }

        // 按照最近的修改时间排序
        Comparator<? super AudioAlbum> comparator = new Comparator<AudioAlbum>() {
            @Override
            public int compare(AudioAlbum o1, AudioAlbum o2) {
                return Long.compare(o2.getLastModified(), o1.getLastModified());
            }
        };
        Collections.sort(audioAlbumList, comparator);

        cutAudioList.clear();
        cutAudioList.addAll(audioAlbumList);
        return audioAlbumList;
    }

    /**
     * 剪裁音频数据模糊查询
     * @param displayName
     * @return
     */
    public static List<AudioAlbum> searchCutAudioList(String displayName){
        List<AudioAlbum> searchCutAudioList = new ArrayList<>();
        Pattern pattern = Pattern.compile(displayName);
        for(int i=0; i < cutAudioList.size(); i++){
            Matcher matcher = pattern.matcher(((AudioAlbum)cutAudioList.get(i)).getDisplayName());
            if(matcher.find()){
                searchCutAudioList.add(cutAudioList.get(i));
            }
        }
        return searchCutAudioList;
    }
}
