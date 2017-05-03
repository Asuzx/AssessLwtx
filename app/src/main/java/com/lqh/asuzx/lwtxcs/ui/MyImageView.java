package com.lqh.asuzx.lwtxcs.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
/**
 * Created by lqh on 2017/4/26 0026.
 * 自定义imageview 处理oom异常
 */
public class MyImageView extends ImageView {
	private OnMeasureListener onMeasureListener;
	public void setOnMeasureListener(OnMeasureListener onMeasureListener) {
		this.onMeasureListener = onMeasureListener;
	}

	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		if(onMeasureListener != null){
			onMeasureListener.onMeasureSize(getMeasuredWidth(), getMeasuredHeight());
		}
	}

	public interface OnMeasureListener{
		public void onMeasureSize(int width, int height);
	}
	
}
