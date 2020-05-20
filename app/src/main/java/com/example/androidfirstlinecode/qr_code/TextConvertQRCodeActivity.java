package com.example.androidfirstlinecode.qr_code;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.androidfirstlinecode.R;
import com.example.androidfirstlinecode.base.BaseApplication;
import com.example.androidfirstlinecode.qr_code.test.QRCodeUtils;
import com.example.androidfirstlinecode.qr_code.test.QrCodeConfig;
import com.example.androidfirstlinecode.qr_code.wrapper.QrCodeOptions;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * 文字转换二维码
 * @author JinXin
 */
public class TextConvertQRCodeActivity extends Activity {

    private static final String TAG = "TextConvertQRCode";
    private Bitmap qrCodeBitmap1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_convert_qr_code);

        initView();

    }

    private void initView() {

        // 编辑类型
        String editType = getIntent().getStringExtra("edit_type");

        // 换回
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final EditText editTextContent = findViewById(R.id.edit_content);
        final ImageView ivCode = findViewById(R.id.iv_code);
        final Button btnSave = findViewById(R.id.btn_save);

        // 转换
        findViewById(R.id.btn_convert).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String content = editTextContent.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    return;
                }

//                // 文本内容转换成二维码
//                Bitmap qrCodeBitmap = QRCodeUtils.createQRCodeBitmap(content, null);
//                if (qrCodeBitmap == null) {
//                    return;
//                }

//                String msg = "https://weixin.qq.com/r/FS9waAPEg178rUcL93oH";
//                int size = 500;
//                QrCodeGenWrapper.of(msg)
//                        .setW(size)
//                        .setH(size)
//                        .setErrorCorrection(ErrorCorrectionLevel.H)
//                        // 因为素材为png透明图，我们这里设置二维码的背景为透明，输出更加优雅
//                        .setDrawBgColor(ColorUtil.OPACITY)
//                        .setDetectImg("jihe/PDP.png")
//                        .setDrawStyle(QrCodeOptions.DrawStyle.IMAGE)
//                        .addImg(1, 1, "jihe/a.png")
//                        .addImg(3, 1, "jihe/b.png")
//                        .addImg(1, 3, "jihe/c.png")
//                        .addImg(3, 2, "jihe/e.png")
//                        .addImg(2, 3, "jihe/f.png")
//                        .addImg(2, 2, "jihe/g.png")
//                        .addImg(3, 4, "jihe/h.png")
//                        .setPicType("png")
//                        .asv("/tmp/imgQr1.png");

                // 设置精度参数
                Map<EncodeHintType, Object> hints = new HashMap<>(3);
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                hints.put(EncodeHintType.MARGIN, 0);
                // 绘制信息
                String path_1_1 = "qr_code/" + "1_1.png";
                Bitmap bitmap_1_1 = getImageFromAssetsFile(path_1_1);
                String path_1_2 = "qr_code/" + "1_2.png";
                Bitmap bitmap_1_2 = getImageFromAssetsFile(path_1_2);
                String path_1_3 = "qr_code/" + "1_3.png";
                Bitmap bitmap_1_3 = getImageFromAssetsFile(path_1_3);
                String path_2_1 = "qr_code/" + "2_1.png";
                Bitmap bitmap_2_1 = getImageFromAssetsFile(path_2_1);
                String path_2_2 = "qr_code/" + "2_2.png";
                Bitmap bitmap_2_2 = getImageFromAssetsFile(path_2_2);
                QrCodeOptions.DrawOptions drawOp = QrCodeOptions.DrawOptions.builder()
                        .drawImg(1, 1, bitmap_1_1)
                        .drawImg(2, 1, bitmap_1_2)
                        .drawImg(3, 1, bitmap_1_3)
                        .drawImg(2, 2, bitmap_2_2)
                        .drawImg(1, 2, bitmap_2_1)
                        .drawStyle(QrCodeOptions.DrawStyle.IMAGE).build();
                // 设置detect绘制信息
                String path_tl = "qr_code/" + "tl.png";
                Bitmap bitmap_tl = getImageFromAssetsFile(path_tl);
                QrCodeOptions.DetectOptions detectOp = QrCodeOptions.DetectOptions.builder()
                        .detectImg(bitmap_tl)
                        .inColor(Color.RED)
                        .outColor(Color.GRAY)
                        .build();
                QrCodeConfig qrCodeConfig = QrCodeConfig.builder()
                        .msg(content)
                        .w(688)
                        .h(688)
                        .hints(hints)
                        .bgImgOptions(null)
                        .logoOptions(null)
                        .drawOptions(drawOp)
                        .detectOptions(detectOp)
                        .picType("png")
                        .build();
                try {
                    qrCodeBitmap1 = QRCodeUtils.createQRCodeBitmap(qrCodeConfig);
                    ivCode.setImageBitmap(qrCodeBitmap1);
                } catch (WriterException e) {
                    e.printStackTrace();
                }


            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String photoPath = PathUtils.getExternalDcimPath() + "/Camera" + "/" + TimeUtils.getNowMills() + ".jpg";
                boolean save = ImageUtils.save(qrCodeBitmap1, photoPath, Bitmap.CompressFormat.JPEG, true);
                if (save) {
                    FileUtils.notifySystemToScan(photoPath);
                    ToastUtils.showShort(getString(R.string.toast_save_successfully));
                } else {
                    ToastUtils.showShort(getString(R.string.toast_share_fail));
                }
            }
        });
    }

    /**
     * 读取Assets文件夹中的图片资源
     * @param fileName 图片路径
     * @return
     */
    public Bitmap getImageFromAssetsFile(String fileName) {
        Bitmap image = null;
        AssetManager assetManager = BaseApplication.getAppContext().getResources().getAssets();
        try {
            InputStream inputStream = assetManager.open(fileName);
            image = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;

    }
}
