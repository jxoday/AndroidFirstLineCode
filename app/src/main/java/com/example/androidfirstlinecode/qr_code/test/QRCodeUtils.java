package com.example.androidfirstlinecode.qr_code.test;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;

import com.example.androidfirstlinecode.qr_code.entity.DotSize;
import com.example.androidfirstlinecode.qr_code.wrapper.BitMatrixEx;
import com.example.androidfirstlinecode.qr_code.wrapper.QrCodeOptions;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 *二维码工具类
 * @author JinXin
 */
public class QRCodeUtils {

    private static final String TAG = "QRCodeUtils";

    /**
     *
     * @param content 字符串内容
     * @param bitmapBlack 用来代替黑色色块的图片（传null时不代替）
     * @return
     */
    public static Bitmap createQRCodeBitmap(String content, Bitmap bitmapBlack) {
//        bitmapBlack = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
//        GradientDrawable aDrawable=new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
//
//                new int[]{Color.parseColor("#FEAC5E"),Color.parseColor("#4BC0C8")});
//        aDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
//        aDrawable.setBounds(0, 0, bitmapBlack.getHeight() , bitmapBlack.getHeight());
//        Canvas canvas = new Canvas(bitmapBlack);
//        aDrawable.draw(canvas);
        try {
            // 1.设置二维码相关配置,生成BitMatrix(位矩阵)对象
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            // 字符转码格式设置
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            // 容错率设置 容错率 L：7% M：15% Q：25% H：35%, 传null时，默认使用 “L”
            // 容错率，也就是纠错水平，二维码破损一部分也能扫码就归功于容错率，容错率可分为L、 M、 Q、 H四个等级
            // 容错率越高，二维码能存储的内容也随之变小
            hints.put(EncodeHintType.ERROR_CORRECTION, "M");

            // 二维码和边框的空白区域宽度设置 1~6
            hints.put(EncodeHintType.MARGIN, "1");

            // 2.将配置参数传入到QRCodeWriter的encode方法生成BitMatrix(位矩阵)对象
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, 300, 300, hints);
            Log.d(TAG, "createQRCodeBitmap: " + bitMatrix);
            // 3.创建像素数组,并根据BitMatrix(位矩阵)对象为数组元素赋颜色值
            if (bitmapBlack != null) {
                //从当前位图按一定的比例创建一个新的位图
                bitmapBlack = Bitmap.createScaledBitmap(bitmapBlack, 300, 300, false);
            }
            int[] pixels = new int[300 * 300];
            for (int y = 0; y < 300; y++) {
                for (int x = 0; x < 300; x++) {
                    // bitMatrix.get(x,y)方法返回true是黑色色块，false是白色色块
                    // 黑色色块像素设置
                    if (bitMatrix.get(x, y)) {
                        //图片不为null，则将黑色色块换为新位图的像素。
                        if (bitmapBlack != null) {
                            Log.d(TAG, "createQRCodeBitmap: " + x + " " + y * 300);
                            pixels[y * 300 + x] = bitmapBlack.getPixel(x, y);
                        } else {
                            pixels[y * 300 + x] = Color.RED;
                        }
                    } else {
                        // 白色色块像素设置
                        pixels[y * 300 + x] = Color.WHITE;
                    }
                }
            }

            // 4.创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,并返回Bitmap对象
            Bitmap bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, 300, 0, 0, 300, 300);

            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return
     */
    public static Bitmap createQRCodeBitmap(QrCodeConfig qrCodeConfig)  throws WriterException{
        BitMatrixEx bitMatrix = encode(qrCodeConfig);
        return toBufferedImage(qrCodeConfig, bitMatrix);
    }

    /**
     * 对 zxing 的 QRCodeWriter 进行扩展, 解决白边过多的问题
     * <p/>
     * 源码参考 {@link com.google.zxing.qrcode.QRCodeWriter#encode(String, BarcodeFormat, int, int, Map)}
     */
    public static BitMatrixEx encode(QrCodeConfig qrCodeConfig) throws WriterException {
        ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;
        int quietZone = 1;
        if (qrCodeConfig.getHints() != null) {
            if (qrCodeConfig.getHints().containsKey(EncodeHintType.ERROR_CORRECTION)) {
                errorCorrectionLevel = ErrorCorrectionLevel
                        .valueOf(qrCodeConfig.getHints().get(EncodeHintType.ERROR_CORRECTION).toString());
            }
            if (qrCodeConfig.getHints().containsKey(EncodeHintType.MARGIN)) {
                quietZone = Integer.parseInt(qrCodeConfig.getHints().get(EncodeHintType.MARGIN).toString());
            }

            if (quietZone > 4) {
                quietZone = 4;
            } else if (quietZone < 0) {
                quietZone = 0;
            }
        }

        QRCode code = Encoder.encode(qrCodeConfig.getMsg(), errorCorrectionLevel, qrCodeConfig.getHints());
        return renderResult(code, qrCodeConfig.getW(), qrCodeConfig.getH(), quietZone);
    }


    /**
     * 对 zxing 的 QRCodeWriter 进行扩展, 解决白边过多的问题
     *
     * @param code
     * @param width
     * @param height
     * @param quietZone 取值 [0, 4]
     * @return
     */
    private static BitMatrixEx renderResult(QRCode code, int width, int height, int quietZone) {
        ByteMatrix input = code.getMatrix();
        if (input == null) {
            throw new IllegalStateException();
        }

        // xxx 二维码宽高相等, 即 qrWidth == qrHeight
        int inputWidth = input.getWidth();
        int inputHeight = input.getHeight();
        int qrWidth = inputWidth + (quietZone * 2);
        int qrHeight = inputHeight + (quietZone * 2);


        // 白边过多时, 缩放
        int minSize = Math.min(width, height);
        int scale = calculateScale(qrWidth, minSize);
        if (scale > 0) {
            int padding, tmpValue;
            // 计算边框留白
            padding = (minSize - qrWidth * scale) / 4 * quietZone;
            tmpValue = qrWidth * scale + padding;
            if (width == height) {
                width = tmpValue;
                height = tmpValue;
            } else if (width > height) {
                width = width * tmpValue / height;
                height = tmpValue;
            } else {
                height = height * tmpValue / width;
                width = tmpValue;
            }
        }

        int outputWidth = Math.max(width, qrWidth);
        int outputHeight = Math.max(height, qrHeight);

        int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);
        int leftPadding = (outputWidth - (inputWidth * multiple)) / 2;
        int topPadding = (outputHeight - (inputHeight * multiple)) / 2;


        BitMatrixEx res = new BitMatrixEx();
        res.setByteMatrix(input);
        res.setLeftPadding(leftPadding);
        res.setTopPadding(topPadding);
        res.setMultiple(multiple);

        res.setWidth(outputWidth);
        res.setHeight(outputHeight);
        return res;
    }

    /**
     * 如果留白超过15% , 则需要缩放
     * (15% 可以根据实际需要进行修改)
     *
     * @param qrCodeSize 二维码大小
     * @param expectSize 期望输出大小
     * @return 返回缩放比例, <= 0 则表示不缩放, 否则指定缩放参数
     */
    private static int calculateScale(int qrCodeSize, int expectSize) {
        if (qrCodeSize >= expectSize) {
            return 0;
        }

        int scale = expectSize / qrCodeSize;
        int abs = expectSize - scale * qrCodeSize;
        if (abs < expectSize * 0.15) {
            return 0;
        }

        return scale;
    }

    /**
     * 根据二维码配置 & 二维码矩阵生成二维码图片
     *
     * @param qrCodeConfig
     * @param bitMatrix
     * @return
     * @throws IOException
     */
    public static Bitmap toBufferedImage(QrCodeConfig qrCodeConfig, BitMatrixEx bitMatrix) {
        int qrCodeWidth = bitMatrix.getWidth();
        int qrCodeHeight = bitMatrix.getHeight();
        Bitmap qrCode = drawQrInfo(qrCodeConfig, bitMatrix);

        // 若二维码的实际宽高和预期的宽高不一致, 则缩放
        int realQrCodeWidth = qrCodeConfig.getW();
        int realQrCodeHeight = qrCodeConfig.getH();
        if (qrCodeWidth != realQrCodeWidth || qrCodeHeight != realQrCodeHeight) {
            qrCode = Bitmap.createScaledBitmap(qrCode, 688, 688, false);
        }
        return qrCode;
    }

    /**
     * 根据二维码矩阵，生成对应的二维码推片
     *
     * @param qrCodeConfig
     * @param bitMatrix
     * @return
     */
    public static Bitmap drawQrInfo(QrCodeConfig qrCodeConfig, BitMatrixEx bitMatrix) {
        int qrWidth = bitMatrix.getWidth();
        int qrHeight = bitMatrix.getHeight();
        int infoSize = bitMatrix.getMultiple();
        Bitmap qrImg = Bitmap.createBitmap(qrWidth, qrHeight, Bitmap.Config.ARGB_8888);


        // 绘制的背景色
        int bgColor = qrCodeConfig.getDrawOptions().getBgColor();
        // 绘制前置色
        int preColor = qrCodeConfig.getDrawOptions().getPreColor();

        // 探测图形外圈的颜色
        int detectOutColor = qrCodeConfig.getDetectOptions().getOutColor();
        // 探测图形内圈的颜色
        int detectInnerColor = qrCodeConfig.getDetectOptions().getInColor();

        if (detectInnerColor != 0 || detectOutColor != 0) {
            if (detectInnerColor == 0) {
                detectInnerColor = detectOutColor;
            } else if (detectOutColor == 0) {
                detectOutColor = detectInnerColor;
            }
        }


        int leftPadding = bitMatrix.getLeftPadding();
        int topPadding = bitMatrix.getTopPadding();

        Canvas g2 = new Canvas(qrImg);
        g2.drawColor(Color.WHITE);
        // 新建空白画布,绘制白色背景
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        g2.drawBitmap(qrImg,0,0,paint);

        // 探测图形的大小
        int detectCornerSize = bitMatrix.getByteMatrix().get(0, 5) == 1 ? 7 : 5;

        int matrixW = bitMatrix.getByteMatrix().getWidth();
        int matrixH = bitMatrix.getByteMatrix().getHeight();

        QrCodeOptions.DrawStyle drawStyle = qrCodeConfig.getDrawOptions().getDrawStyle();
        for (int x = 0; x < matrixW; x++) {
            for (int y = 0; y < matrixH; y++) {
                if (bitMatrix.getByteMatrix().get(x, y) == 0) {
                    continue;
                }

                if (inDetectCornerArea(x, y, matrixW, matrixH, detectCornerSize)) {
                    // 绘制三个位置探测图形
                    drawDetectImg(qrCodeConfig, g2, bitMatrix, matrixW, matrixH, leftPadding, topPadding, infoSize,
                            detectCornerSize, x, y, detectOutColor, detectInnerColor);
                } else {
                    // 绘制二维码的点图
                    drawQrDotImg(qrCodeConfig, drawStyle, g2, bitMatrix, leftPadding, topPadding, infoSize, x, y);
                }
            }
        }
        return qrImg;
    }

    /**
     * 判断 (x,y) 对应的点是否处于二维码矩阵的探测图形内
     *
     * @param x                目标点的x坐标
     * @param y                目标点的y坐标
     * @param matrixW          二维码矩阵宽
     * @param matrixH          二维码矩阵高
     * @param detectCornerSize 探测图形的大小
     * @return
     */
    private static boolean inDetectCornerArea(int x, int y, int matrixW, int matrixH, int detectCornerSize) {
        if (x < detectCornerSize && y < detectCornerSize) {
            // 左上角
            return true;
        }

        if (x < detectCornerSize && y >= matrixH - detectCornerSize) {
            // 左下角
            return true;
        }

        if (x >= matrixW - detectCornerSize && y < detectCornerSize) {
            // 右上角
            return true;
        }

        return false;
    }

    /**
     * 绘制探测图形
     *
     * @param qrCodeConfig     绘制参数
     * @param g2               二维码画布
     * @param bitMatrix        二维码矩阵
     * @param matrixW          二维码矩阵宽
     * @param matrixH          二维码矩阵高
     * @param leftPadding      二维码左边留白距离
     * @param topPadding       二维码上边留白距离
     * @param infoSize         二维码矩阵中一个点对应的像素大小
     * @param detectCornerSize 探测图形大小
     * @param x                目标点x坐标
     * @param y                目标点y坐标
     * @param detectOutColor   探测图形外边圈的颜色
     * @param detectInnerColor 探测图形内部圈的颜色
     */
    private static void drawDetectImg(QrCodeConfig qrCodeConfig, Canvas g2, BitMatrixEx bitMatrix, int matrixW,
                                      int matrixH, int leftPadding, int topPadding, int infoSize, int detectCornerSize, int x, int y,
                                      int detectOutColor, int detectInnerColor) {

        if (qrCodeConfig.getDetectOptions().getDetectImg() != null) {
            // 新建空白画布,绘制白色背景
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            // 使用探测图形的图片来渲染
            Rect rect = new Rect(leftPadding + x * infoSize,
                    topPadding + y * infoSize, (leftPadding + x * infoSize) + infoSize * detectCornerSize, (topPadding + y * infoSize) + infoSize * detectCornerSize);
            g2.drawBitmap(qrCodeConfig.getDetectOptions().getDetectImg(), null, rect, paint);

            // 图片直接渲染完毕之后，将其他探测图形的点设置为0，表示不需要再次渲染
            for (int addX = 0; addX < detectCornerSize; addX++) {
                for (int addY = 0; addY < detectCornerSize; addY++) {
                    bitMatrix.getByteMatrix().set(x + addX, y + addY, 0);
                }
            }
            return;
        }

        Paint paint = new Paint();
        if (inOuterDetectCornerArea(x, y, matrixW, matrixH, detectCornerSize)) {
            // 外层的框
            paint.setColor(detectOutColor);
        } else {
            // 内层的框
            paint.setColor(detectInnerColor);
        }

        g2.drawRect(new Rect(leftPadding + x * infoSize, topPadding + y * infoSize, (leftPadding + x * infoSize) + infoSize, (topPadding + y * infoSize) + infoSize), paint);
    }

    /**
     * 绘制二维码中的像素点图形
     *
     * @param qrCodeConfig 绘制参数
     * @param drawStyle    绘制的图形样式
     * @param g2           二维码画布
     * @param bitMatrix    二维码矩阵
     * @param leftPadding  二维码左边留白距离
     * @param topPadding   二维码上边留白距离
     * @param infoSize     二维码矩阵中一个点对应的像素大小
     * @param x            目标点x坐标
     * @param y            目标点y坐标
     */
    private static void drawQrDotImg(QrCodeConfig qrCodeConfig, QrCodeOptions.DrawStyle drawStyle, Canvas g2,
                                     BitMatrixEx bitMatrix, int leftPadding, int topPadding, int infoSize, int x, int y) {

        if (drawStyle != QrCodeOptions.DrawStyle.IMAGE) {
            drawGeometricFigure(qrCodeConfig, drawStyle, g2, bitMatrix, leftPadding, topPadding, infoSize, x, y);
        } else {
            drawSpecialImg(qrCodeConfig, drawStyle, g2, bitMatrix, leftPadding, topPadding, infoSize, x, y);
        }
    }

    /**
     * 绘制自定义的几种几何图形
     *
     * @param qrCodeConfig 绘制参数
     * @param drawStyle    绘制的图形样式
     * @param g2           二维码画布
     * @param bitMatrix    二维码矩阵
     * @param leftPadding  二维码左边留白距离
     * @param topPadding   二维码上边留白距离
     * @param infoSize     二维码矩阵中一个点对应的像素大小
     * @param x            目标点x坐标
     * @param y            目标点y坐标
     */
    private static void drawGeometricFigure(QrCodeConfig qrCodeConfig, QrCodeOptions.DrawStyle drawStyle,
                                            Canvas g2, BitMatrixEx bitMatrix, int leftPadding, int topPadding, int infoSize, int x, int y) {
        if (!qrCodeConfig.getDrawOptions().isEnableScale()) {
            // 用几何图形进行填充时，如果不支持多个像素点渲染一个几何图形时，直接返回即可
            drawStyle.draw(g2, leftPadding + x * infoSize, topPadding + y * infoSize, (leftPadding + x * infoSize) + infoSize, (topPadding + y * infoSize) + infoSize,
                    qrCodeConfig.getDrawOptions().getImage(1, 1));
            return;
        }

        // todo 针对非图片的二维码扩展，先保留原来的扩展逻辑，后续优化
        boolean row2 = x + 1 < bitMatrix.getByteMatrix().getWidth() && bitMatrix.getByteMatrix().get(x + 1, y) == 1;
        boolean col2 = y + 1 < bitMatrix.getByteMatrix().getHeight() && bitMatrix.getByteMatrix().get(x, y + 1) == 1;

        if (row2 && col2 && bitMatrix.getByteMatrix().get(x + 1, y + 1) == 1 &&
                drawStyle.expand(QrCodeOptions.ExpandType.SIZE4)) {
            // 四个相等
            bitMatrix.getByteMatrix().set(x + 1, y, 0);
            bitMatrix.getByteMatrix().set(x + 1, y + 1, 0);
            bitMatrix.getByteMatrix().set(x, y + 1, 0);
            drawStyle.draw(g2, leftPadding + x * infoSize, topPadding + y * infoSize, infoSize << 1, infoSize << 1,
                    qrCodeConfig.getDrawOptions().getImage(2, 2));
        } else if (row2 && drawStyle.expand(QrCodeOptions.ExpandType.ROW2)) {
            // 横向相同
            bitMatrix.getByteMatrix().set(x + 1, y, 0);
            drawStyle.draw(g2, leftPadding + x * infoSize, topPadding + y * infoSize, infoSize << 1, infoSize,
                    qrCodeConfig.getDrawOptions().getImage(2, 1));
        } else if (col2 && drawStyle.expand(QrCodeOptions.ExpandType.COL2)) {
            // 列的两个
            bitMatrix.getByteMatrix().set(x, y + 1, 0);
            drawStyle.draw(g2, leftPadding + x * infoSize, topPadding + y * infoSize, infoSize, infoSize << 1,
                    qrCodeConfig.getDrawOptions().getImage(1, 2));
        } else {
            drawStyle.draw(g2, leftPadding + x * infoSize, topPadding + y * infoSize, infoSize, infoSize,
                    qrCodeConfig.getDrawOptions().getImage(1, 1));
        }
    }


    /**
     * 绘制指定的图片
     *
     * @param qrCodeConfig 绘制参数
     * @param drawStyle    绘制的图形样式
     * @param g2           二维码画布
     * @param bitMatrix    二维码矩阵
     * @param leftPadding  二维码左边留白距离
     * @param topPadding   二维码上边留白距离
     * @param infoSize     二维码矩阵中一个点对应的像素大小
     * @param x            目标点x坐标
     * @param y            目标点y坐标
     */
    private static void drawSpecialImg(QrCodeConfig qrCodeConfig, QrCodeOptions.DrawStyle drawStyle, Canvas g2,
                                       BitMatrixEx bitMatrix, int leftPadding, int topPadding, int infoSize, int x, int y) {
        // 针对图片扩展的方式，支持更加灵活的填充方式
        int maxRow = getMaxRow(bitMatrix.getByteMatrix(), x, y);
        int maxCol = getMaxCol(bitMatrix.getByteMatrix(), x, y);
        List<DotSize> availableSize = getAvailableSize(bitMatrix.getByteMatrix(), x, y, maxRow, maxCol);
        // 获取对应的图片
        Bitmap drawImg;
        for (DotSize dotSize : availableSize) {
            Log.d(TAG, "drawSpecialImg: " + dotSize);
            drawImg = qrCodeConfig.getDrawOptions().getImgMapper().get(dotSize);
            if (drawImg == null) {
                continue;
            }
            // 新建空白画布,绘制白色背景
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            // 开始绘制，并将已经绘制过得地方置空
            Rect rect = new Rect(leftPadding + x * infoSize, topPadding + y * infoSize,  (leftPadding + x * infoSize) + dotSize.getCol() * infoSize, (topPadding + y * infoSize) + dotSize.getRow() * infoSize);
            g2.drawBitmap(drawImg, null, rect, paint);
            for (int col = 0; col < dotSize.getCol(); col++) {
                for (int row = 0; row < dotSize.getRow(); row++) {
                    bitMatrix.getByteMatrix().set(x + col, y + row, 0);
                }
            }
            return;
        }

        // 如果上面全部没有满足，则使用兜底的绘制
        drawStyle.draw(g2, leftPadding + x * infoSize, topPadding + y * infoSize, (leftPadding + x * infoSize) + infoSize, (topPadding + y * infoSize) + infoSize,
                qrCodeConfig.getDrawOptions().getImage(1, 1));
    }

    /**
     * 获取矩阵中从(x,y)出发最大连续为1的行数
     *
     * @param bitMatrix 矩阵
     * @param x         起始点x
     * @param y         起始点y
     * @return
     */
    private static int getMaxRow(ByteMatrix bitMatrix, int x, int y) {
        int cnt = 1;
        while (++y < bitMatrix.getHeight()) {
            if (bitMatrix.get(x, y) == 0) {
                break;
            }
            ++cnt;
        }
        return cnt;
    }

    /**
     * 获取矩阵中从(x,y)出发最大连续为1的列数
     *
     * @param bitMatrix 矩阵
     * @param x         起始点x
     * @param y         起始点y
     * @return
     */
    private static int getMaxCol(ByteMatrix bitMatrix, int x, int y) {
        int cnt = 1;
        while (++x < bitMatrix.getWidth()) {
            if (bitMatrix.get(x, y) == 0) {
                break;
            }
            ++cnt;
        }
        return cnt;
    }

    private static List<DotSize> getAvailableSize(ByteMatrix bitMatrix, int x, int y, int maxRow, int maxCol) {
        if (maxRow == 1) {
            return Collections.singletonList(DotSize.create(1, maxCol));
        }

        if (maxCol == 1) {
            return Collections.singletonList(DotSize.create(maxRow, 1));
        }

        List<DotSize> container = new ArrayList<>();

        int col = 1;
        int lastRow = maxRow;
        while (col < maxCol) {
            int offset = 0;
            int row = 1;
            while (++offset < lastRow) {
                if (bitMatrix.get(x + col, y + offset) == 0) {
                    break;
                }
                ++row;
            }
            ++col;
            lastRow = row;
            container.add(new DotSize(row, col));
        }



        Comparator<? super DotSize> comparator = new Comparator<DotSize>() {
            @Override
            public int compare(DotSize o1, DotSize o2) {
                return o2.size() - o1.size();
            }
        };
        Collections.sort(container,comparator);
        return container;
    }

    /**
     * 判断 (x,y) 对应的点是否为二维码举证探测图形中外面的框, 这个方法的调用必须在确认(x,y)对应的点在探测图形内
     *
     * @param x                目标点的x坐标
     * @param y                目标点的y坐标
     * @param matrixW          二维码矩阵宽
     * @param matrixH          二维码矩阵高
     * @param detectCornerSize 探测图形的大小
     * @return
     */
    private static boolean inOuterDetectCornerArea(int x, int y, int matrixW, int matrixH, int detectCornerSize) {
        if (x == 0 || x == detectCornerSize - 1 || x == matrixW - 1 || x == matrixW - detectCornerSize || y == 0 ||
                y == detectCornerSize - 1 || y == matrixH - 1 || y == matrixH - detectCornerSize) {
            // 外层的框
            return true;
        }

        return false;
    }
}
