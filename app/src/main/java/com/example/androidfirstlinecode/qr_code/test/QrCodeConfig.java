package com.example.androidfirstlinecode.qr_code.test;

import android.graphics.Bitmap;

import com.blankj.utilcode.util.StringUtils;
import com.example.androidfirstlinecode.qr_code.wrapper.QrCodeOptions;
import com.google.zxing.EncodeHintType;

import java.util.HashMap;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

/**
 * @author JinXin 2020/4/26
 */
@Data
@Builder
public class QrCodeConfig {

    /**
     * 塞入二维码的信息
     */
    private String msg;

    /**
     * 生成二维码的宽
     */
    private Integer w;


    /**
     * 生成二维码的高
     */
    private Integer h;


    /**
     * 二维码信息(即传统二维码中的黑色方块) 绘制选项
     */
    private QrCodeOptions.DrawOptions drawOptions;


    /**
     * 背景图样式选项
     */
    private QrCodeOptions.BgImgOptions bgImgOptions;

    /**
     * logo 样式选项
     */
    private QrCodeOptions.LogoOptions logoOptions;


    /**
     * todo 后续可以考虑三个都可以自配置
     * <p>
     * 三个探测图形的样式选项
     */
    private QrCodeOptions.DetectOptions detectOptions;


    private Map<EncodeHintType, Object> hints;


    /**
     * 生成二维码图片的格式 png, jpg
     */
    private String picType;

    /**
     * 探测图形的配置信息
     */
    @Builder
    @Data
    public static class DetectOptions {
        private int outColor;

        private int inColor;

        /**
         * 探测图形，优先级高于颜色的定制（即存在图片时，用图片绘制探测图形）
         */
        private Bitmap detectImg;
    }

    private static Map<String, QrCodeOptions.DrawStyle> map;

    static {
        map = new HashMap<>(10);
        for (QrCodeOptions.DrawStyle style : QrCodeOptions.DrawStyle.values()) {
            map.put(style.name(), style);
        }
    }

    public static QrCodeOptions.DrawStyle getDrawStyle(String name) {
        if (StringUtils.isEmpty(name)) { // 默认返回矩形
            return QrCodeOptions.DrawStyle.RECT;
        }


        QrCodeOptions.DrawStyle style = map.get(name.toUpperCase());
        return style == null ? QrCodeOptions.DrawStyle.RECT : style;
    }
}
