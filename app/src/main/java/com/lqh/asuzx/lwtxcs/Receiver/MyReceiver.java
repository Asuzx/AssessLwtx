package com.lqh.asuzx.lwtxcs.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.lqh.asuzx.lwtxcs.activity.OrderActivity;

/**
 * Created by lqh on 2017/4/28 0004.
 * 监听卸载广播。刷新 应用界面。
 */

public class MyReceiver extends BroadcastReceiver {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_REMOVED)) {
            String packageName = intent.getData().getSchemeSpecificPart();

            Log.e(TAG, "--------卸载成功" + packageName);
            Toast.makeText(context, "卸载成功" + packageName, Toast.LENGTH_LONG).show();
            try {
                OrderActivity.Notify();
            }catch (Exception e){
                Log.e("Exception","防止Activity未启动意外报错");
            }

        }
    }
}
