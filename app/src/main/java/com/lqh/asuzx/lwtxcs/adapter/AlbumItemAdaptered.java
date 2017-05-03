package com.lqh.asuzx.lwtxcs.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.lqh.asuzx.lwtxcs.R;
import com.lqh.asuzx.lwtxcs.bean.PhotoUpImageItem;
import com.lqh.asuzx.lwtxcs.tools.MyAppcation;
import com.lqh.asuzx.lwtxcs.ui.MyImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * Created by lqh on 2017/4/28 0028.
 */

public class AlbumItemAdaptered  extends RecyclerView.Adapter<AlbumItemAdaptered.AlbumView> implements View.OnClickListener {
    private AlbumView albumView;
    private ImageLoader imageLoader;
    private List<PhotoUpImageItem> list;
    private DisplayImageOptions options;
    private LayoutInflater layoutInflater;
    private int imgWidth,imgHeight;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;


    public AlbumItemAdaptered(List<PhotoUpImageItem> list, Context context){
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
        imageLoader = ImageLoader.getInstance();
        // 使用DisplayImageOption.Builder()创建DisplayImageOptions
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.album_default_loading_pic) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.album_default_loading_pic) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.album_default_loading_pic) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build(); // 创建配置过的DisplayImageOption对象
    }
    @Override
    public AlbumView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showpic_item, parent, false);
        view.setOnClickListener(this);
        albumView = new AlbumView(view);
        return albumView;
    }

    @Override
    public void onBindViewHolder(final AlbumView holder, final int position) {

        holder.itemView.setTag(position);

        holder.imageView.setOnMeasureListener(new MyImageView.OnMeasureListener() {

            @Override
            public void onMeasureSize(int width, int height) {
                Bitmap bitmap = BitmapFactory.decodeFile(list.get(position).getImagePath());
                if (bitmap != null) {
                    imgWidth = width;
                    float scale = (float) bitmap.getWidth() / bitmap.getHeight();
                    imgHeight = (int) (width / scale);
                }else{
                    imgWidth = 0;
                    imgHeight =0 ;
                }
            }
        });
        holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(imgWidth, imgHeight));
        imageLoader.init(ImageLoaderConfiguration.createDefault(MyAppcation.getContextObject()));//防止Home直接启动 导致报错
        imageLoader.displayImage("file://"+list.get(position).getImagePath(), holder.imageView, options);
        holder.checkBox.setChecked(list.get(position).isSelected());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v);
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
    }

    public static class AlbumView extends RecyclerView.ViewHolder {
        MyImageView imageView;
        CheckBox checkBox;

        public AlbumView(View itemView) {
            super(itemView);
            imageView = (MyImageView) itemView.findViewById(R.id.image_item);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }
    }
}
