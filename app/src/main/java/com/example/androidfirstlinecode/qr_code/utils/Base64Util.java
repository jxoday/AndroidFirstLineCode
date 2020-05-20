package com.example.androidfirstlinecode.qr_code.utils;

import android.graphics.Bitmap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * @author JinXin 2020/4/26
 */
public class Base64Util {
//    public static String encode(Bitmap bufferedImage, String imgType) throws IOException {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ImageIO.write(bufferedImage, imgType, outputStream);
//        return encode(outputStream);
//    }
//
//    public static String encode(ByteArrayOutputStream outputStream) {
//        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
//    }
//
//
//    public static Bitmap decode2Img(String base64) throws IOException {
//        byte[] bytes = new byte[0];
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            bytes = Base64.getDecoder().decode(base64.getBytes("utf-8"));
//        }
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
//        return ImageIO.read(inputStream);
//    }
}
