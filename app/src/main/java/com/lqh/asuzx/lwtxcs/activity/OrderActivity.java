package com.lqh.asuzx.lwtxcs.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;
import com.lqh.asuzx.lwtxcs.R;
import com.lqh.asuzx.lwtxcs.adapter.OrderAdapter;
import com.lqh.asuzx.lwtxcs.databinding.ActivityOrderBinding;
import com.lqh.asuzx.lwtxcs.tools.ApkTools;
import com.lqh.asuzx.lwtxcs.tools.AppInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by lqh on 2017/4/26 0026.
 * 任务二获取非系统应用列表
 */

public class OrderActivity extends Activity{
    private ActivityOrderBinding binding;
    private ArrayList<AppInfo> appList;
    private AppInfo tmpInfo;
    private ApkTools apkTools;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order);
        ini();
    }

    private void ini() {
        apkTools = new ApkTools(OrderActivity.this);
        appList = new ArrayList<AppInfo>(); //用来存储获取的应用信息数据
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            tmpInfo = new AppInfo();
            tmpInfo.appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
            tmpInfo.packageName = packageInfo.packageName;
            tmpInfo.versionName = packageInfo.versionName;
            tmpInfo.versionCode = packageInfo.versionCode;
            tmpInfo.appPath =packageInfo.applicationInfo.sourceDir;
            tmpInfo.appIcon = packageInfo.applicationInfo.loadIcon(getPackageManager());
//Only display the non-system app info
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                appList.add(tmpInfo);//如果非系统应用，则添加至appList
            }
        }
        OrderAdapter orderAdapter = new OrderAdapter(OrderActivity.this,appList,R.layout.order_item, BR.data, BR.apktools);
        binding.setAdapter(orderAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

            ini();
            Toast.makeText(this, "成功卸载", Toast.LENGTH_SHORT).show();

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
}
