package com.lqh.asuzx.lwtxcs.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lqh.asuzx.lwtxcs.R;
import com.lqh.asuzx.lwtxcs.bean.PhotoUpImageItem;

import java.util.List;

/**
 * Created by lqh on 2017/4/28 0028.
 * 选择图片
 * ImageLoader/DisplayImageOptions 第三方jar 另一种实现方法。卡顿明显不推荐使用
 */

public class AlbumItemAdaptered extends RecyclerView.Adapter<AlbumItemAdaptered.AlbumView> implements View.OnClickListener {
//    private ImageLoader imageLoader;
//    private DisplayImageOptions options;
    private Context context;
    private AlbumView albumView;
    private List<PhotoUpImageItem> list;
    private LayoutInflater layoutInflater;
    private int imgWidth, imgHeight;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;


    public AlbumItemAdaptered(List<PhotoUpImageItem> list, Context context) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
//        imageLoader = ImageLoader.getInstance();
//        // 使用DisplayImageOption.Builder()创建DisplayImageOptions
//        options = new DisplayImageOptions.Builder()
//                .showStubImage(R.drawable.album_default_loading_pic) // 设置图片下载期间显示的图片
//                .showImageForEmptyUri(R.drawable.album_default_loading_pic) // 设置图片Uri为空或是错误的时候显示的图片
//                .showImageOnFail(R.drawable.album_default_loading_pic) // 设置图片加载或解码过程中发生错误显示的图片
//                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
//                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
//                // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
//                .bitmapConfig(Bitmap.Config.ARGB_8888)
//                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
//                .build(); // 创建配置过的DisplayImageOption对象
    }

    @Override
    public AlbumView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item_images_item_view, parent, false);
        view.setOnClickListener(this);
        albumView = new AlbumView(view);
        return albumView;
    }

    @Override
    public void onBindViewHolder(final AlbumView holder, final int position) {
        holder.itemView.setTag(position);
        //自定义Imageview 获取实例大小等比缩放。 弃用。
//        holder.imageView.setOnMeasureListener(new MyImageView.OnMeasureListener() {
//
//            @Override
//            public void onMeasureSize(int width, int height) {
//                Bitmap bitmap = BitmapFactory.decodeFile(list.get(position).getImagePath());
//
//                if (bitmap != null) {
//                    imgWidth = width;
//                    float scale = (float) bitmap.getWidth() / bitmap.getHeight();
//                    imgHeight = (int) (width / scale);
//                } else {
//                    imgWidth = 0;
//                    imgHeight = 0;
//                }
//            }
//        });

        //获取原始图片bitmip宽高等比缩放。图片可以显示完全但是 速度过慢。
//        Bitmap bitmap = BitmapFactory.decodeFile(list.get(position).getImagePath());
//        if (bitmap != null) {
//            float scale = (float) bitmap.getWidth() / bitmap.getHeight();
//            imgHeight = (int) (imgWidth / scale);
//        }else {
//            imgHeight = 300;
//        }

//        holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(imgWidth, imgHeight));
//        imageLoader.displayImage("file://" + list.get(position).getImagePath(), holder.imageView, options);
//        imageLoader.init(ImageLoaderConfiguration.createDefault(MyAppcation.getContextObject()));//防止直接启动 导致报错
        imgWidth = list.get(position).getmWidth();
        imgHeight = list.get(position).getmHeight();
        Glide.with(context).load(list.get(position).getImagePath())
                .override(imgWidth, imgHeight)
                .dontAnimate()
                .centerCrop()
                .placeholder(R.drawable.album_default_loading_pic)
                .into(holder.imageView);
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
        ImageView imageView;
        CheckBox checkBox;

        public AlbumView(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_item);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }
    }
}
