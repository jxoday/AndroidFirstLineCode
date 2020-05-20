package com.example.androidfirstlinecode.qr_code.utils;

import android.graphics.Color;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.ImageUtils;

/**
 * @author JinXin 2020/4/26
 */
public class ColorUtil {
    /**
     * 全透明颜色
     */
    public static int OPACITY = Color.TRANSPARENT;


    /**
     * 米黄色
     */
    public static int OFF_WHITE = Color.RED;


    /**
     * 将Color对象转为html对应的颜色配置信息
     *
     * 如  Color.RED  ->  #f00
     *
     * @param color
     * @return
     */
    public static String int2htmlColor(int color) {
        return ColorUtils.int2ArgbString(color);
    }
}
