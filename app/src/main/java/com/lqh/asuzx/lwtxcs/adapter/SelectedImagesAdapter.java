package com.lqh.asuzx.lwtxcs.adapter;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lqh.asuzx.lwtxcs.R;
import com.lqh.asuzx.lwtxcs.bean.PhotoUpImageItem;


import java.util.ArrayList;
/**
 * Created by lqh on 2017/4/28 0026.
 * 选择图片后回退主界面
 */
public class SelectedImagesAdapter extends BaseAdapter {

	private ArrayList<PhotoUpImageItem> arrayList;
	private LayoutInflater layoutInflater;
	private Context context;
	//第三方库加载可以显示完整图片 数量过多卡顿明显
//	private ImageLoader imageLoader;
//	private DisplayImageOptions options;
	public SelectedImagesAdapter(Context context,ArrayList<PhotoUpImageItem> arrayList){
		this.arrayList = arrayList;
		layoutInflater = LayoutInflater.from(context);
		this.context=context;

//		imageLoader = ImageLoader.getInstance();
//		// 使用DisplayImageOption.Builder()创建DisplayImageOptions
//		options = new DisplayImageOptions.Builder()
//				.showStubImage(R.drawable.album_default_loading_pic) // 设置图片下载期间显示的图片
//				.showImageForEmptyUri(R.drawable.album_default_loading_pic) // 设置图片Uri为空或是错误的时候显示的图片
//				.showImageOnFail(R.drawable.album_default_loading_pic) // 设置图片加载或解码过程中发生错误显示的图片
//				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
//				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
//				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
//				.bitmapConfig(Config.ARGB_8888)
//				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
//				.build(); // 创建配置过的DisplayImageOption对象
	}
	@Override
	public int getCount() {
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return arrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.selected_images_adapter_item, parent, false);
			holder = new Holder();
			holder.imageView = (ImageView) convertView.findViewById(R.id.selected_image_item);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		Glide.with(context).load(arrayList.get(position).getImagePath())
				.dontAnimate()
				.centerCrop()
				.placeholder(R.drawable.album_default_loading_pic)
				.into( holder.imageView);
		return convertView;
	}
	class Holder{
		ImageView imageView;
	}
}
