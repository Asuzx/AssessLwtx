package com.lqh.asuzx.lwtxcs.tools;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.format.Formatter;
import android.util.Log;

/**
 * Created by lqh on 2017/4/26 0026.
 */

public class AppInfo {
    public String appName = "";//名称
    public String appPath = "";//路径
    public String packageName = "";//包名
    public String versionName = "";//版本名称
    public int versionCode = 0;//版本号
    public Drawable appIcon = null;//图片

    public String CacheSize = null;
    public String DataSize = null;
    public String CodeSize = null;
    public String AppSize = null;
    public Bitmap camera;

    public Bitmap getCamera() {
        return camera;
    }

    public void setCamera(Bitmap camera) {
        this.camera = camera;
    }
    public String getCacheSize() {
        return CacheSize;
    }

    public void setCacheSize(long cacheSize) {
        CacheSize = Formatter.formatFileSize(MyAppcation.getContextObject(), cacheSize);
    }

    public String getDataSize() {
        return DataSize;
    }

    public void setDataSize(long dataSize) {
        DataSize = Formatter.formatFileSize(MyAppcation.getContextObject(), dataSize);
    }

    public String getCodeSize() {
        return CodeSize;
    }

    public void setCodeSize(long codeSize) {
        CodeSize = Formatter.formatFileSize(MyAppcation.getContextObject(), codeSize);
    }

    public String getAppSize() {
        return AppSize;
    }

    public void setAppSize(long appSize) {
        AppSize = Formatter.formatFileSize(MyAppcation.getContextObject(), appSize);//工具类 根据文件大小自动转化为KB, MB, GB
    }


    public void print() {
        Log.v("app", "Name:" + appName + " Package:" + packageName);
        Log.v("app", "Name:" + appName + " appPath:" + versionName);
        Log.v("app", "Name:" + appName + " versionName:" + versionName);
        Log.v("app", "Name:" + appName + " versionCode:" + versionCode);
    }



}

