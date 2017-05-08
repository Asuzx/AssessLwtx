package com.lqh.asuzx.lwtxcs.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.lqh.asuzx.lwtxcs.R;

/**
 * Created by lqh on 2017/4/28 0005.
 */

public class MyImageDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private Window window = null;
    private LinearLayout linearLayout;
    private ImageView iv;
    private String path;
    private int imgWidth, imgHeight;
    private BitmapFactory.Options options;
    public MyImageDialog(Context context, boolean cancelable,
                         DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public MyImageDialog(Context context, int cancelable, int x, int y, String path) {
        super(context, cancelable);
        this.context = context;
        windowDeploy(x, y);
        this.path = path;

    }


    public MyImageDialog(Context context) {
        super(context);
    }

    protected void onCreate(Bundle savedInstanceState) {
        //初始化布局
        View loadingview = LayoutInflater.from(getContext()).inflate(R.layout.imagedialogview, null);
        linearLayout = (LinearLayout) loadingview.findViewById(R.id.LinearLayout);
        iv = (ImageView) loadingview.findViewById(R.id.imageview_head_big);
        iv.setOnClickListener(this);
        linearLayout.setOnClickListener(this);

        try {
            imgWidth = context.getResources().getDisplayMetrics().widthPixels;
            imgHeight = context.getResources().getDisplayMetrics().heightPixels;
            options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);

            if (imgWidth > options.outWidth && imgHeight > options.outHeight) {
                //如果原始图片小于屏幕则放大
                float scale = (float) options.outWidth / options.outHeight;
                imgHeight = (int) (imgWidth / scale);
            } else if (options.outWidth>options.outHeight){
                // 超过屏幕 缩小  （横图）
                float scale = (float) options.outWidth / options.outHeight;
                imgHeight = (int) (imgWidth / scale);
                Log.e("缩小（横图）",imgWidth + ">>" + imgHeight);
            } else{
                float scale = (float) options.outHeight / options.outWidth;
                imgWidth = (int) (imgHeight / scale);
                Log.e("缩小（竖图）",imgWidth + ">>" + imgHeight);
            }
            if (imgHeight == 0) {//防止分辨率获取失败时。空指针异常
                imgHeight = 500;
            } else if (imgHeight > imgWidth * 3 && imgWidth != 0) {
                imgHeight = imgWidth * 3; // 防止图片过大
            }
        } catch (Exception e) {
            imgWidth = 400;
            imgHeight = 400;
        }
        Log.e("原始图片大小", "W:"+options.outWidth + "H:" + options.outHeight + ">>" + path + "w:" + imgWidth + "h:" + imgHeight);
        ViewGroup.LayoutParams ps = iv.getLayoutParams();
        ps.height = imgHeight;
        ps.width = imgWidth;
        iv.setLayoutParams(ps);
        Glide.with(context)
                .load(path)
                .override(imgWidth, imgHeight)
                .dontAnimate()
                .centerCrop()
                .placeholder(R.drawable.album_default_loading_pic)
                .into(iv);
        //设置dialog的布局
        setContentView(loadingview);
        //如果需要放大或者缩小时的动画，可以直接在此出对loadingview或iv操作，在下面SHOW或者dismiss中操作
        super.onCreate(savedInstanceState);
    }

    //设置窗口显示
    public void windowDeploy(int x, int y) {
        window = getWindow(); //得到对话框
        window.setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画
        window.setBackgroundDrawableResource(R.color.vifrification); //设置对话框背景为透明
        WindowManager.LayoutParams wl = window.getAttributes();
        //根据x，y坐标设置窗口需要显示的位置
        wl.x = x; //x小于0左移，大于0右移
        wl.y = y; //y小于0上移，大于0下移
//            wl.alpha = 0.6f; //设置透明度
//            wl.gravity = Gravity.BOTTOM; //设置重力
        window.setAttributes(wl);
    }

    public void show() {
        //设置触摸对话框意外的地方取消对话框
        setCanceledOnTouchOutside(true);
        super.show();
    }

    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
