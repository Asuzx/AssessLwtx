package com.lqh.asuzx.lwtxcs.tools;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.lqh.asuzx.lwtxcs.activity.FileManagerActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by lqh on 2017/4/26 0026.
 */

public class ApkTools {
    private static Activity activity;
    private static String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
    private static String apkPath;
    public ApkTools() {
    }

    public ApkTools(Activity activity) {
        this.activity = activity;
    }

    public void moveapk(String apkPath) {
//        这里想手动选择文件夹的，但是发现只能指定文件暂时放弃
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        this.activity.startActivityForResult(intent, 1);
        //移动根目录
//        copyFile(apkPath,PATH);

        this.apkPath = apkPath;
        Intent intent = new Intent(activity, FileManagerActivity.class);
        activity.startActivityForResult(intent, 3);
    }
    public void unapk(String packageName) {
//        Toast.makeText(MyAppcation.getContextObject(), "卸载apk"+packageName, Toast.LENGTH_SHORT).show();
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        this.activity.startActivityForResult(uninstallIntent,0);
    }

    public static void copyFile(String oldPath, String newPath) {
        oldPath = apkPath;
        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File temp = new File(oldPath);
            if (temp.isFile()) {
                FileInputStream input = new FileInputStream(temp);
                FileOutputStream output = new FileOutputStream(newPath + "/" +
                        (temp.getName()).toString());
                byte[] b = new byte[1024 * 5];
                int len;
                while ((len = input.read(b)) != -1) {
                    output.write(b, 0, len);
                }
                output.flush();
                output.close();
                input.close();
                Toast.makeText(activity, "提取apk成功\n路径:"+apkPath, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(activity, "提取apk失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }

    }
}
