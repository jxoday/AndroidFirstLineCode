package com.example.androidfirstlinecode.ffmpeg.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AudioAlbum {

    private Integer id;

    /**
     * 歌曲文件的名称
     */
    private String displayName;

    /**
     * 歌曲的名称
     */
    private String title;
    /**
     * 歌曲的专辑名
     */
    private String album;
    /**
     * 歌曲的歌手名
     */
    private String artist;
    /**
     * 歌曲文件的全路径
     */
    private String filePath;
    /**
     * 时长
     */
    private Long duration;
    /**
     * 歌曲文件的大小
     */
    private String size;
    /**
     * 歌曲文件的类型
     */
    private String type;
    /**
     * 最后一次修改的时间
     */
    private Long lastModified;
    /**
     * 音频展示列表类型 media、download、record、cut
     */
    private String showListType;
    /**
     * LocalFile转换成AudioAlbum
     * 用作保存LocalFile的parentPath数据
     */
    private String parentPath;
}
