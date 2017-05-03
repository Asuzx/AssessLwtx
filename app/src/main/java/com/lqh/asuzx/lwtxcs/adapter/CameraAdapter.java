package com.lqh.asuzx.lwtxcs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lqh.asuzx.lwtxcs.R;
import com.lqh.asuzx.lwtxcs.tools.AppInfo;

import java.util.List;

/**
 * Created by lqh on 2017/4/26 0026.
 * 拍照的返回类
 */

public class CameraAdapter extends BaseAdapter {
    private Context context;
    private List<AppInfo> list;

    public CameraAdapter(Context context, List<AppInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.selected_images_adapter_item,null);
        ImageView image = (ImageView) view.findViewById(R.id.selected_image_item);
        image.setImageBitmap(list.get(position).getCamera());
        return view;
    }
}
