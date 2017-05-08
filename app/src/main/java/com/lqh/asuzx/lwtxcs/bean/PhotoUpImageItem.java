package com.lqh.asuzx.lwtxcs.bean;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.Serializable;

public class PhotoUpImageItem implements Serializable {

    //图片ID
    private String imageId;
    //原图路径
    private String imagePath;

    //是否被选择
    private boolean isSelected = false;
    //宽高比例
    private int mHeight;


    private int mWidth;

    public int getmWidth() {
        return mWidth;
    }
    BitmapFactory.Options options;
    public void setmWidth(int mWidth) {
        this.mWidth = mWidth;
        if (this.mWidth == 0) {//防止分辨率获取失败时。空指针异常
            this.mWidth = 400;
        }
    }

    public int getmHeight() {
        return mHeight;
    }

    public void setmHeight() {
        options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeFile(imagePath, options);
            float scale = (float) options.outWidth / options.outHeight;
            this.mHeight = (int) (mWidth / scale);
            options = null;
        } catch (Exception e) {
            this.mHeight = 500;
        }
        if (mHeight == 0) {//防止分辨率获取失败时。空指针异常
            mHeight = 500;
        } else if (mHeight > mWidth * 3 && mWidth != 0) {
            mHeight = mWidth * 3; // 防止图片过大
        }

    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

}
