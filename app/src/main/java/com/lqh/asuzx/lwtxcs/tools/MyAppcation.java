package com.lqh.asuzx.lwtxcs.tools;

import android.app.Application;
import android.content.Context;

import com.lqh.asuzx.lwtxcs.ui.CrashHandler;

/**
 * Created by lqh on 2017/4/26 0026.
 */

public class MyAppcation extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(context);
    }

    //返回
    public static Context getContextObject(){
        return context;
    }
}
