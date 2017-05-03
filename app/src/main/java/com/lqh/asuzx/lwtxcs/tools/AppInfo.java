package com.lqh.asuzx.lwtxcs.tools;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by lqh on 2017/4/26 0026.
 */

public class AppInfo {
    public String appName="";
    public String appPath="";
    public String packageName="";
    public String versionName="";
    public int versionCode=0;
    public Drawable appIcon=null;

    public Bitmap getCamera() {
        return camera;
    }

    public void setCamera(Bitmap camera) {
        this.camera = camera;
    }

    public Bitmap camera ;
    public void print()
    {
        Log.v("app","Name:"+appName+" Package:"+packageName);
        Log.v("app","Name:"+appName+" appPath:"+versionName);
        Log.v("app","Name:"+appName+" versionName:"+versionName);
        Log.v("app","Name:"+appName+" versionCode:"+versionCode);
    }

}

