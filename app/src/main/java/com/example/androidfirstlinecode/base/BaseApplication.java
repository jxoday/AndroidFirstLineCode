package com.example.androidfirstlinecode.base;

import android.app.Application;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;

import org.litepal.LitePal;

import io.microshow.rxffmpeg.RxFFmpegInvoke;


/**
 * @author JinXin
 */
public class BaseApplication extends Application {

    private static volatile BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        //保存APP实例
        instance = this;

        LitePal.initialize(this);

        RxFFmpegInvoke.getInstance().setDebug(true);

        PermissionUtils.permission(PermissionConstants.STORAGE, PermissionConstants.PHONE)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                    }

                    @Override
                    public void onDenied() {
                    }
                })
                .request();

    }

    /**
     * the main context of the Application
     * @return application context
     */
    public static BaseApplication getAppContext() {
        return instance;
    }
}
