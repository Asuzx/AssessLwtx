package com.lqh.asuzx.lwtxcs.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;
import com.lqh.asuzx.lwtxcs.R;
import com.lqh.asuzx.lwtxcs.adapter.OrderAdapter;
import com.lqh.asuzx.lwtxcs.databinding.ActivityOrderBinding;
import com.lqh.asuzx.lwtxcs.tools.ApkTools;
import com.lqh.asuzx.lwtxcs.tools.AppInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by lqh on 2017/4/26 0026.
 * 任务二获取非系统应用列表
 */

public class OrderActivity extends Activity{
    private static ActivityOrderBinding binding;
    private static ArrayList<AppInfo> appList;
    private static AppInfo tmpInfo;
    private static ApkTools apkTools;
    private  static  OrderAdapter orderAdapter;
    private static  Activity activity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order);
        activity = OrderActivity.this;
        ini();
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static void ini() {
        apkTools = new ApkTools(activity);//传递activity  全局
        appList = new ArrayList<AppInfo>(); //用来存储获取的应用信息数据
        List<PackageInfo> packages = activity.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            tmpInfo = new AppInfo();
            tmpInfo.appName = packageInfo.applicationInfo.loadLabel(activity.getPackageManager()).toString();
            tmpInfo.packageName = packageInfo.packageName;
            tmpInfo.versionName = packageInfo.versionName;
            tmpInfo.versionCode = packageInfo.versionCode;
            tmpInfo.appPath =packageInfo.applicationInfo.sourceDir;
            tmpInfo.appIcon = packageInfo.applicationInfo.loadIcon(activity.getPackageManager());
            getPkgSize(activity ,packageInfo.packageName,tmpInfo);
//Only display the non-system app info
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                appList.add(tmpInfo);//如果非系统应用，则添加至appList
            }
        }
        Log.e("数量",appList.size()+"");
             orderAdapter = new OrderAdapter(activity,appList,R.layout.order_item, BR.data, BR.apktools);
        binding.setAdapter(orderAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3){
            String Path = (String) data.getSerializableExtra("savePath");
            apkTools.copyFile(null,Path+ "/");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        apkTools = null;
        binding = null;
        appList.clear();
        appList = null;
        tmpInfo = null;
    }
    public static void  Notify(){
        //通知界面更新
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ini();
            }
        });
    }

    public static void getPkgSize(final Context context, String pkgName, final AppInfo appInfo) {
        // getPackageSizeInfo是PackageManager中的一个private方法，所以需要通过反射的机制来调用
        Method method;
        try {
            method = PackageManager.class.getMethod("getPackageSizeInfo",
                    new Class[]{String.class, IPackageStatsObserver.class});
            // 调用 getPackageSizeInfo 方法，需要两个参数：1、需要检测的应用包名；2、回调
            method.invoke(context.getPackageManager(),
                    pkgName,
                    new IPackageStatsObserver.Stub() {
                        @Override
                        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {
                            if (succeeded && pStats != null) {
                                synchronized (AppInfo.class) {
                                    appInfo.setCacheSize( pStats.cacheSize);
                                    appInfo.setDataSize(pStats.dataSize);
                                    appInfo.setCodeSize(pStats.codeSize);
                                    appInfo.setAppSize(pStats.cacheSize + pStats.codeSize + pStats.dataSize);
//                                    PkgSize.put("CacheSize", pStats.cacheSize);
//                                    PkgSize.put("DataSize", pStats.dataSize);
//                                    PkgSize.put("CodeSize", pStats.codeSize);
//                                    PkgSize.put("AppSize", pStats.cacheSize + pStats.codeSize + pStats.dataSize);
                                }
                                }
                            }
                        }
                    );
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
