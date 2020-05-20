//package com.example.androidfirstlinecode.qr_code.wrapper;
//
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.util.Log;
//
//import com.blankj.utilcode.util.ImageUtils;
//import com.example.androidfirstlinecode.qr_code.utils.ColorUtil;
//import com.google.zxing.EncodeHintType;
//import com.google.zxing.WriterException;
////import com.google.zxing.client.j2se.MatrixToImageConfig;
//import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.Map;
//
//import lombok.ToString;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * Created by yihui on 2017/7/17.
// */
//public class QrCodeGenWrapper {
//
//    private static final String TAG = "QrCodeGenWrapper";
//
//    public static Builder of(String content) {
//        return new Builder().setMsg(content);
//    }
//
////    private static ByteArrayOutputStream asGif(QrCodeOptions qrCodeOptions) throws WriterException {
////        BitMatrixEx bitMatrix = QrCodeGenerateHelper.encode(qrCodeOptions);
////        List<ImmutablePair<BufferedImage, Integer>> list = QrCodeGenerateHelper.toGifImages(qrCodeOptions, bitMatrix);
////        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
////        GifHelper.saveGif(list, outputStream);
////        return outputStream;
////    }
//
//    private static Bitmap asBufferedImage(QrCodeOptions qrCodeOptions) throws WriterException, IOException {
//        BitMatrixEx bitMatrix = QrCodeGenerateHelper.encode(qrCodeOptions);
//        return QrCodeGenerateHelper.toBufferedImage(qrCodeOptions, bitMatrix);
//    }
//
////    private static String asString(QrCodeOptions qrCodeOptions) throws WriterException, IOException {
////        if (qrCodeOptions.gifQrCode()) {
////            // 动态二维码生成
////            try (ByteArrayOutputStream outputStream = asGif(qrCodeOptions)) {
////                return Base64Util.encode(outputStream);
////            }
////        }
////
////        // 普通二维码，直接输出图
////        Bitmap bufferedImage = asBufferedImage(qrCodeOptions);
////        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
////            ImageIO.write(bufferedImage, qrCodeOptions.getPicType(), outputStream);
////            return Base64Util.encode(outputStream);
////        }
////    }
//
//    private static boolean asFile(QrCodeOptions qrCodeOptions, String absFileName) throws WriterException, IOException {
//        File file = new File(absFileName);
//        FileWriteUtil.mkDir(file.getParentFile());
//
////        if (qrCodeOptions.gifQrCode()) {
////            // 保存动态二维码
////            try (ByteArrayOutputStream output = asGif(qrCodeOptions)) {
////                FileOutputStream out = new FileOutputStream(file);
////                out.write(output.toByteArray());
////                out.flush();
////                out.close();
////            }
////
////            return true;
////        }
//
//        Bitmap bufferedImage = asBufferedImage(qrCodeOptions);
//
//        return true;
//    }
//
//
//    @ToString
//    @Slf4j
//    public static class Builder {
////        private static final MatrixToImageConfig DEFAULT_CONFIG = new MatrixToImageConfig();
//
//        /**
//         * The message to put into QrCode
//         */
//        private String msg;
//
//        /**
//         * qrcode image width
//         */
//        private Integer w;
//
//
//        /**
//         * qrcode image height
//         */
//        private Integer h;
//
//
//        /**
//         * qrcode message's code, default UTF-8
//         */
//        private String code = "utf-8";
//
//
//        /**
//         * 0 - 4
//         */
//        private Integer padding;
//
//
//        /**
//         * error level, default H
//         */
//        private ErrorCorrectionLevel errorCorrection = ErrorCorrectionLevel.H;
//
//
//        /**
//         * output qrcode image type, default png
//         */
//        private String picType = "png";
//
//
//        private QrCodeOptions.BgImgOptions.BgImgOptionsBuilder bgImgOptions;
//
//        private QrCodeOptions.LogoOptions.LogoOptionsBuilder logoOptions;
//
//        private QrCodeOptions.DrawOptions.DrawOptionsBuilder drawOptions;
//
//        private QrCodeOptions.DetectOptions.DetectOptionsBuilder detectOptions;
//
//
//        public Builder() {
//            // 背景图默认采用覆盖方式
//            bgImgOptions =
//                    QrCodeOptions.BgImgOptions.builder().bgImgStyle(QrCodeOptions.BgImgStyle.OVERRIDE).opacity(0.85f);
//
//
//            // 默认采用普通格式的logo， 无边框
//            logoOptions = QrCodeOptions.LogoOptions.builder().logoStyle(QrCodeOptions.LogoStyle.NORMAL).border(false)
//                    .rate(12);
//
//
//            // 绘制信息，默认黑白方块
//            drawOptions =
//                    QrCodeOptions.DrawOptions.builder().drawStyle(QrCodeOptions.DrawStyle.RECT).bgColor(Color.WHITE)
//                            .preColor(Color.BLACK).enableScale(false);
//
//            // 探测图形
//            detectOptions = QrCodeOptions.DetectOptions.builder();
//        }
//
//
//        public String getMsg() {
//            return msg;
//        }
//
//        public Builder setMsg(String msg) {
//            this.msg = msg;
//            return this;
//        }
//
//        public Integer getW() {
//            return w == null ? (h == null ? 200 : h) : w;
//        }
//
//        public Builder setW(Integer w) {
//            if (w != null && w <= 0) {
//                throw new IllegalArgumentException("生成二维码的宽必须大于0");
//            }
//            this.w = w;
//            return this;
//        }
//
//        public Integer getH() {
//            return h == null ? (w == null ? 200 : w) : h;
//        }
//
//        public Builder setH(Integer h) {
//            if (h != null && h <= 0) {
//                throw new IllegalArgumentException("生成功能二维码的搞必须大于0");
//            }
//            this.h = h;
//            return this;
//        }
//
//        public Builder setCode(String code) {
//            this.code = code;
//            return this;
//        }
//
//        public Integer getPadding() {
//            if (padding == null) {
//                return 1;
//            }
//
//            if (padding < 0) {
//                return 0;
//            }
//
//            if (padding > 4) {
//                return 4;
//            }
//
//            return padding;
//        }
//
//        public Builder setPadding(Integer padding) {
//            this.padding = padding;
//            return this;
//        }
//
//        public Builder setPicType(String picType) {
//            this.picType = picType;
//            return this;
//        }
//
//        public Builder setErrorCorrection(ErrorCorrectionLevel errorCorrection) {
//            this.errorCorrection = errorCorrection;
//            return this;
//        }
//
//
//        /////////////// logo 相关配置 ///////////////
//
//        public Builder setLogo(String logo) throws IOException {
//            return setLogo(ImageUtils.getBitmap(logo));
//        }
//
//        public Builder setLogo(Bitmap img) {
//            logoOptions.logo(img);
//            return this;
//        }
//
//        public Builder setLogoStyle(QrCodeOptions.LogoStyle logoStyle) {
//            logoOptions.logoStyle(logoStyle);
//            return this;
//        }
//
//        public Builder setLogoBgColor(Integer color) {
//            if (color == null) {
//                return this;
//            }
//
//            return setLogoBgColor(color);
//        }
//
//        /**
//         * logo 背景颜色
//         *
//         * @param color
//         * @return
//         */
//        public Builder setLogoBgColor(Color color) {
//            logoOptions.border(true);
//            logoOptions.borderColor(color);
//            return this;
//        }
//
//        public Builder setLogoBorderBgColor(Integer color) {
//            if (color == null) {
//                return this;
//            }
//            return setLogoBorderBgColor(color);
//        }
//
//        /**
//         * logo 外层边框颜色
//         *
//         * @param color
//         * @return
//         */
//
//        public Builder setLogoBorderBgColor(Color color) {
//            logoOptions.border(true);
//            logoOptions.outerBorderColor(color);
//            return this;
//        }
//
//        public Builder setLogoBorder(boolean border) {
//            logoOptions.border(border);
//            return this;
//        }
//
//        public Builder setLogoRate(int rate) {
//            logoOptions.rate(rate);
//            return this;
//        }
//
//        /**
//         * logo透明度
//         * @param opacity
//         * @return
//         */
//        public Builder setLogoOpacity(float opacity) {
//            logoOptions.opacity(opacity);
//            return this;
//        }
//
//        ///////////////// logo配置结束 ///////////////
//
//
//        // ------------------------------------------
//
//
//        /////////////// 背景 相关配置 ///////////////
//
//        public Builder setBgImg(String bgImg) throws IOException {
//            try {
//                return setBgImg(FileReadUtil.getStreamByFileName(bgImg));
//            } catch (IOException e) {
//                log.error("load backgroundImg error! e:{}", e);
//                throw new IOException("load backgroundImg error!", e);
//            }
//        }
//
//
//        public Builder setBgImg(InputStream inputStream) throws IOException {
////            try {
////                ByteArrayInputStream target = IoUtil.toByteArrayInputStream(inputStream);
////                MediaType media = MediaType.typeOfMagicNum(FileReadUtil.getMagicNum(target));
////                if (media == MediaType.ImageGif) {
////                    GifDecoder gifDecoder = new GifDecoder();
////                    gifDecoder.read(target);
////                    bgImgOptions.gifDecoder(gifDecoder);
////                    return this;
////                } else {
////                    return setBgImg(ImageIO.read(target));
////                }
////            } catch (IOException e) {
////                log.error("load backgroundImg error! e:{}", e);
////                throw new IOException("load backgroundImg error!", e);
////            }
//        }
//
//
//        public Builder setBgImg(Bitmap bufferedImage) {
//            bgImgOptions.bgImg(bufferedImage);
//            return this;
//        }
//
//
//        public Builder setBgStyle(QrCodeOptions.BgImgStyle bgImgStyle) {
//            bgImgOptions.bgImgStyle(bgImgStyle);
//            return this;
//        }
//
//
//        public Builder setBgW(int w) {
//            bgImgOptions.bgW(w);
//            return this;
//        }
//
//        public Builder setBgH(int h) {
//            bgImgOptions.bgH(h);
//            return this;
//        }
//
//
//        public Builder setBgOpacity(float opacity) {
//            bgImgOptions.opacity(opacity);
//            return this;
//        }
//
//
//        public Builder setBgStartX(int startX) {
//            bgImgOptions.startX(startX);
//            return this;
//        }
//
//        public Builder setBgStartY(int startY) {
//            bgImgOptions.startY(startY);
//            return this;
//        }
//
//
//        /////////////// logo 配置结束 ///////////////
//
//
//        // ------------------------------------------
//
//
//        /////////////// 探测图形 相关配置 ///////////////
//        public Bitmap setDetectImg(String detectImg){
//            return ImageUtils.getBitmap(detectImg);
//        }
//
//        public Builder setDetectOutColor(Integer outColor) {
//            if (outColor == null) {
//                return this;
//            }
//
//            return setDetectOutColor(outColor);
//        }
//
//        public Builder setDetectOutColor(Integer outColor) {
//            detectOptions.outColor(outColor);
//            return this;
//        }
//
//        public Builder setDetectInColor(Integer inColor) {
//            if (inColor == null) {
//                return this;
//            }
//
//            return setDetectInColor(ColorUtil.int2color(inColor));
//        }
//
//        public Builder setDetectInColor(Color inColor) {
//            detectOptions.inColor(inColor);
//            return this;
//        }
//
//        /////////////// 探测图形 配置结束 ///////////////
//
//
//        // ------------------------------------------
//
//
//        /////////////// 二维码绘制 相关配置 ///////////////
//
//        public Builder setDrawStyle(String style) {
//            return setDrawStyle(QrCodeOptions.DrawStyle.getDrawStyle(style));
//        }
//
//
//        public Builder setDrawStyle(QrCodeOptions.DrawStyle drawStyle) {
//            drawOptions.drawStyle(drawStyle);
//            return this;
//        }
//
//
//        public Builder setDrawPreColor(int color) {
//            return setDrawPreColor(color);
//        }
//
//
//        public Builder setDrawPreColor() {
//            return setDrawPreColor();
//        }
//
//        public Builder setDrawPreColor(Integer color) {
//            drawOptions.preColor(color);
//            return this;
//        }
//
//        public Builder setDrawBgColor(Integer color) {
//            return setDrawBgColor(color);
//        }
//
//        public Builder setDrawBgColor(int color) {
//            drawOptions.bgColor(color);
//            return this;
//        }
//
//        public Builder setDrawEnableScale(boolean enable) {
//            drawOptions.enableScale(enable);
//            return this;
//        }
//
//
//        public Builder setDrawImg(String img) throws IOException {
//            try {
//                return setDrawImg(ImageLoadUtil.getImageByPath(img));
//            } catch (IOException e) {
//                log.error("load draw img error! e: {}", e);
//                throw new IOException("load draw img error!", e);
//            }
//        }
//
//        public Builder setDrawImg(InputStream input) throws IOException {
//            try {
//                return setDrawImg(ImageIO.read(input));
//            } catch (IOException e) {
//                log.error("load draw img error! e: {}", e);
//                throw new IOException("load draw img error!", e);
//            }
//        }
//
//        public Builder setDrawImg(Bitmap img) {
//            addImg(1, 1, img);
//            return this;
//        }
//
//
//        public Builder addImg(int row, int col, Bitmap img) {
//            if (img == null) {
//                return this;
//            }
//            drawOptions.enableScale(true);
//            drawOptions.drawImg(row, col, img);
//            return this;
//        }
//
//        public Builder addImg(int row, int col, String img) {
//            return addImg(row, col, ImageUtils.getBitmap(img));
//
//        /////////////// 二维码绘制 配置结束 ///////////////
//
//
//        private void validate() {
//            if (msg == null || msg.length() == 0) {
//                throw new IllegalArgumentException("生成二维码的内容不能为空!");
//            }
//        }
//
//
//        private QrCodeOptions build() {
//            this.validate();
//
//            QrCodeOptions qrCodeConfig = new QrCodeOptions();
//            qrCodeConfig.setMsg(getMsg());
//            qrCodeConfig.setH(getH());
//            qrCodeConfig.setW(getW());
//
//
//            // 设置背景信息
//            QrCodeOptions.BgImgOptions bgOp = bgImgOptions.build();
//            if (bgOp.getBgImg() == null && bgOp.getGifDecoder() == null) {
//                qrCodeConfig.setBgImgOptions(null);
//            } else {
//                qrCodeConfig.setBgImgOptions(bgOp);
//            }
//
//
//            // 设置logo信息
//            QrCodeOptions.LogoOptions logoOp = logoOptions.build();
//            if (logoOp.getLogo() == null) {
//                qrCodeConfig.setLogoOptions(null);
//            } else {
//                qrCodeConfig.setLogoOptions(logoOp);
//            }
//
//
//            // 绘制信息
//            QrCodeOptions.DrawOptions drawOp = drawOptions.build();
//            qrCodeConfig.setDrawOptions(drawOp);
//
//
//            // 设置detect绘制信息
//            QrCodeOptions.DetectOptions detectOp = detectOptions.build();
//            if (detectOp.getOutColor() == null && detectOp.getInColor() == null) {
//                detectOp.setInColor(drawOp.getPreColor());
//                detectOp.setOutColor(drawOp.getPreColor());
//            } else if (detectOp.getOutColor() == null) {
//                detectOp.setOutColor(detectOp.getOutColor());
//            } else if (detectOp.getInColor() == null) {
//                detectOp.setInColor(detectOp.getInColor());
//            }
//            qrCodeConfig.setDetectOptions(detectOp);
//
//
//            if (qrCodeConfig.getBgImgOptions() != null &&
//                    qrCodeConfig.getBgImgOptions().getBgImgStyle() == QrCodeOptions.BgImgStyle.PENETRATE) {
//                // 透传，用背景图颜色进行绘制时
//                drawOp.setPreColor(ColorUtil.OPACITY);
//                qrCodeConfig.getBgImgOptions().setOpacity(1);
//                qrCodeConfig.getDetectOptions().setInColor(ColorUtil.OPACITY);
//                qrCodeConfig.getDetectOptions().setOutColor(ColorUtil.OPACITY);
//            }
//
//            // 设置输出图片格式
//            qrCodeConfig.setPicType(picType);
//
//            // 设置精度参数
//            Map<EncodeHintType, Object> hints = new HashMap<>(3);
//            hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrection);
//            hints.put(EncodeHintType.CHARACTER_SET, code);
//            hints.put(EncodeHintType.MARGIN, this.getPadding());
//            qrCodeConfig.setHints(hints);
//
//            return qrCodeConfig;
//        }
//
//
////        public String asString() throws IOException, WriterException {
////            return QrCodeGenWrapper.asString(build());
////        }
//
//
//        public Bitmap asBufferedImage() throws IOException, WriterException {
//            return QrCodeGenWrapper.asBufferedImage(build());
//        }
//
//        public ByteArrayOutputStream asStream() throws WriterException, IOException {
//            QrCodeOptions options = build();
////            if (options.gifQrCode()) {
////                return QrCodeGenWrapper.asGif(options);
////            } else {
//            Bitmap img = QrCodeGenWrapper.asBufferedImage(options);
//                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                ImageIO.write(img, options.getPicType(), outputStream);
//                return outputStream;
////            }
//        }
//
//        public boolean asFile(String absFileName) throws IOException, WriterException {
//            return QrCodeGenWrapper.asFile(build(), absFileName);
//        }
//    }
//}
