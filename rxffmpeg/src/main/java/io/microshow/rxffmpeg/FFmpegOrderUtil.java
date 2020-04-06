package io.microshow.rxffmpeg;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 *
 */
public class FFmpegOrderUtil {

    private static final String TAG = "FFmpegOrderUtil";

    /**
     * 使用ffmpeg命令行进行音频转码
     *
     * @param srcFile    源文件
     * @param targetFile 目标文件（后缀指定转码格式）
     * @return 转码后的文件
     */

    public static String[] transformAudio(String srcFile, String targetFile) {
        String command = "ffmpeg -y -i %s %s";
        command = String.format(Locale.getDefault(), command, srcFile, targetFile);
        // 以空格分割为字符串数组
        return command.split(" ");
    }

    /**
     * 使用ffmpeg命令行进行音频剪切
     *
     * @param srcFile    源文件
     * @param startTime  剪切的开始时间(单位为秒)
     * @param duration   剪切时长(单位为秒)
     * @param targetFile 目标文件
     * @return 剪切后的文件
     */
    public static String[] cutAudio(String srcFile, int startTime, int duration,
                                    String targetFile) {
//        String command = "ffmpeg -y -i %s -vn -acodec copy -ss %d -t %d %s";
//        command = String.format(Locale.getDefault(), command, srcFile, startTime, duration, targetFile);
        RxFFmpegCommandList cmdlist = new RxFFmpegCommandList();
        cmdlist.append("-i");
        cmdlist.append(srcFile);
        cmdlist.append("-vn");
        cmdlist.append("-acodec");
        cmdlist.append("copy");
        cmdlist.append("-ss");
        cmdlist.append(String.valueOf(startTime));
        cmdlist.append("-t");
        cmdlist.append(String.valueOf(duration));
        cmdlist.append(targetFile);
        return cmdlist.build();
    }

    /**
     * @param srcFile    源文件
     * @param startTime  剪切的开始时间(单位为秒)
     * @param endTime    剪切的结束时间(单位为秒)
     * @param targetFile 目标文件
     * @return 剪切后的文件
     */
    public static String[] cutAudio(String srcFile, String startTime, String endTime,
                                    String targetFile) {
        String command = "ffmpeg -y -i %s -vn -acodec copy -ss %s -t %s %s";
        command = String.format(Locale.getDefault(), command, srcFile, startTime, endTime, targetFile);
        return command.split(" ");
    }

    /**
     * 使用ffmpeg命令行进行音频合并
     *
     * @param srcFile    源文件
     * @param appendFile 待追加的文件
     * @param targetFile 目标文件
     * @return 合并后的文件
     */
    public static String[] concatAudio(List<String> srcFile, String appendFile, String targetFile) {
        //ffmpeg -y -i concat:input1.mp3|input2.mp3 -acodec copy output.mp3
//        String command = "ffmpeg -y -i concat:%s|%s -acodec copy %s";
        RxFFmpegCommandList cmdlist = new RxFFmpegCommandList();
        cmdlist.append("-i");
        cmdlist.append("concat:");
        for (int i = 0; i < srcFile.size(); i++) {
            cmdlist.append(srcFile.get(i));
            if (i < srcFile.size() - 1) {
                cmdlist.append("|");
            }
        }
        cmdlist.append("-acodec");
        cmdlist.append("copy");
        cmdlist.append(targetFile);
        return cmdlist.build();
    }


    /**
     * 使用ffmpeg命令行进行音频混合
     *
     * @param srcFile    源文件
     * @param mixFile    待混合文件
     * @param targetFile 目标文件
     * @return 混合后的文件
     */
    public static String[] mixAudio(List<String> srcFile, String mixFile, String targetFile) {
        //ffmpeg -i INPUT1 -i INPUT2 -i INPUT3 -filter_complex amix=inputs=3:duration=first:dropout_transition=3 OUTPUT
//        String command = "ffmpeg -y -i %s -i %s -filter_complex amix=inputs=2:duration=shortest %s";
//        command = String.format(Locale.getDefault(), command, srcFile, mixFile, targetFile);

        RxFFmpegCommandList cmdlist = new RxFFmpegCommandList();
        for (String file : srcFile) {
            cmdlist.append("-i");
            cmdlist.append(file);
        }
        cmdlist.append("-filter_complex");
        cmdlist.append("amix=inputs=" + srcFile.size() +":duration=shortest");
        cmdlist.append(targetFile);
        return cmdlist.build();
    }


    /**
     * @param audio   源文件
     * @param reduce  音量(单位dB)
     * @param outPath 输出地址
     * @return 命令
     */
    public static String[] changeVolume(String audio, int reduce, String outPath) {
        String cmd = "ffmpeg -y -i %s -af volume=%sdB %s";
        String command = String.format(cmd, audio, reduce, outPath);
        Log.d("FFMEPG", command);
        return command.split(" ");
    }


    /**
     * @param audio   源文件
     * @param reduce  音量倍数
     * @param outPath 输出地址
     * @return 命令
     */
    public static String[] changeVolume(String audio, float reduce, String outPath) {
        String cmd = "ffmpeg -y -i %s -af volume=%s %s";
        String command = String.format(cmd, audio, reduce, outPath);
        Log.d("FFMEPG", command);
        return command.split(" ");
    }


    /**
     * 使用ffmpeg命令行进行抽取音频
     *
     * @param srcFile    原文件
     * @param targetFile 目标文件
     * @return 抽取后的音频文件
     */
    public static String[] extractAudio(String srcFile, String targetFile) {
        //-vn:video not
        String command = "ffmpeg -y -i %s -acodec copy -vn %s";
        command = String.format(Locale.getDefault(), command, srcFile, targetFile);
        return command.split(" ");
    }

    /**
     * 音频解码
     *
     * @param srcFile 源文件
     * @param targetFile 目标文件
     * @param sampleRate 采样率
     * @param channel 声道:单声道为1/立体声道为2
     * @return 音频解码的命令行
     */
    public static String[] decodeAudio(String srcFile, String targetFile, int sampleRate,
                                       int channel) {
        String command = "ffmpeg -y -i %s -acodec pcm_s16le -ar %d -ac %d -f s16le %s";
        command = String.format(Locale.getDefault(), command, srcFile, sampleRate, channel, targetFile);
        return command.split(" ");
    }

    /**
     * 音频编码
     *
     * @param srcFile    源文件pcm裸流
     * @param targetFile 编码后目标文件
     * @param sampleRate 采样率
     * @param channel    声道:单声道为1/立体声道为2
     * @return 音频编码的命令行
     */
    @SuppressLint("DefaultLocale")
    public static String[] encodeAudio(String srcFile, String targetFile, int sampleRate,
                                       int channel) {
        String command = "ffmpeg -y -f s16le -ar %d -ac %d -acodec pcm_s16le -i %s %s";
        command = String.format(Locale.getDefault(), command, sampleRate, channel, srcFile, targetFile);
        return command.split(" ");
    }

    /**
     * 倍速播放
     * @param srcFile 源文件
     * @param targetFile 输出文件
     * @return 倍速播放命令行
     */
    public static String[] videoSpeed2(String srcFile, String targetFile){
        String command = "ffmpeg -y -i %s -filter_complex [0:v]setpts=0.5*PTS[v];[0:a]atempo=2.0[a] -map [v] -map [a] %s";
        command = String.format(Locale.getDefault(), command,srcFile,targetFile);

        RxFFmpegCommandList cmdlist = new RxFFmpegCommandList();
        cmdlist.append("-i");
        cmdlist.append(srcFile);
        cmdlist.append("-filter_complex");
        cmdlist.append("[0:v]setpts=0.5*PTS[v];[0:a]atempo=2.0[a]");
        cmdlist.append("-map");
        cmdlist.append("[v]");
        cmdlist.append("-map");
        cmdlist.append("[a]");
        cmdlist.append(targetFile);
        return cmdlist.build();
    }

    /**
     * 音频前5s淡入
     * @param srcFile 源文件
     * @param targetFile 输出文件
     * @return 音频前5s淡入命令行
     */
    public static String [] audioFadeIn(String srcFile, String targetFile){
        return audioFadeIn(srcFile, targetFile,0,5);
    }

    /**
     * 音频淡入
     * @param srcFile 源文件
     * @param targetFile 输出文件
     * @param start 开始位置(s)
     * @param duration 持续时间(s)
     * @return 音频淡入命令行
     */
    public static String[] audioFadeIn(String srcFile, String targetFile, int start, int duration){
        String command = "ffmpeg -y -i %s -filter_complex afade=t=in:ss=%d:d=%d %s";
        command = String.format(Locale.getDefault(), command,srcFile,start,duration,targetFile);

        RxFFmpegCommandList cmdlist = new RxFFmpegCommandList();
        cmdlist.append("-i");
        cmdlist.append(srcFile);
        cmdlist.append("-filter_complex");
        cmdlist.append("afade=t=in:ss=" + start + ":d=" + duration);
        cmdlist.append(targetFile);
        return cmdlist.build();
    }


    /**
     * 音频淡出
     * @param srcFile 音频源文件
     * @param targetFile 输出文件
     * @param start 开始位置(s)
     * @param duration 持续时间(s)
     * @return 音频淡出命令行
     */
    public static String[] audioFadeOut(String srcFile, String targetFile, int start, int duration){
//        String command = "ffmpeg -y -i %s -filter_complex afade=t=in:ss=%d:d=%d %s";

        RxFFmpegCommandList cmdlist = new RxFFmpegCommandList();
        cmdlist.append("-i");
        cmdlist.append(srcFile);
        cmdlist.append("-filter_complex");
        cmdlist.append("[0:a]afade=t=in:ss=" + start + ":d=" + duration + "[a1];");
        cmdlist.append("[a1]afade=t=out:ss=" + 30 + ":d=" + duration);
        cmdlist.append("-preset");
        cmdlist.append("superfast");
        cmdlist.append(targetFile);
        return cmdlist.build();
    }



    /**
     * 将音频进行fdk_aac编码
     * @param srcFile 音频源文件
     * @param targetFile 音频输出文件（m4a或aac）
     * @return 将音频进行fdk_aac编码命令
     */
    public static String[] audio2Fdkaac(String srcFile,String targetFile){
        String command = "ffmpeg -y -i %s -c:a libfdk_aac %s";
        command = String.format(Locale.getDefault(), command, srcFile, targetFile);
        return command.split(" ");
    }


    /**
     * 将音频进行VBR MP3编码
     * @param srcFile 音频源文件
     * @param targetFile 音频输出文件（mp3）
     * @return 将音频进行VBR MP3编码命令
     */
    public static String[] audio2Mp3lame(String srcFile,String targetFile){
        String command = "ffmpeg -y -i %s -codec:a libmp3lame -qscale:a 2 %s";
        command = String.format(Locale.getDefault(), command, srcFile, targetFile);
        return command.split(" ");
    }
}
