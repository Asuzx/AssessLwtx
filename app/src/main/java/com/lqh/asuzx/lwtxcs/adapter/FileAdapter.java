package com.lqh.asuzx.lwtxcs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lqh.asuzx.lwtxcs.R;
import com.lqh.asuzx.lwtxcs.utils.FileSortFactory;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by nangua on 2016/5/27.
 */
public class FileAdapter extends BaseAdapter {
    ArrayList<File> filedata;
    Context context;

    //排序方法
    int sortWay = FileSortFactory.SORT_BY_FOLDER_AND_NAME;


    public FileAdapter(Context context, ArrayList<File> data) {
        this.context = context;
        this.filedata = data;
    }

    public File[] setfiledata(ArrayList<File> data) {
        this.filedata = data;
        sort();
        this.notifyDataSetChanged();
        File[] files = new File[filedata.size()];
        for (int i = 0; i < files.length; i++) {
            files[i] = filedata.get(i);
        }
        return files;
    }

    /**
     * 将文件列表排序
     */
    private void sort() {
        Collections.sort(this.filedata, FileSortFactory.getWebFileQueryMethod(sortWay));
    }

    @Override
    public void notifyDataSetChanged() {
        //重新排序
        sort();
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return filedata.size();
    }

    @Override
    public Object getItem(int position) {
        return filedata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        File file = filedata.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_file_cell, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (file.isDirectory()) {
            viewHolder.fileImage.setImageResource(R.mipmap.folder);
            viewHolder.fileSize.setText("文件夹");
        } else {
            viewHolder.fileImage.setImageResource(R.mipmap.file);
            viewHolder.fileSize.setText(generateSize(file));
        }
        //将position与ibMore绑定

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        viewHolder.fileName.setText(file.getName());
        viewHolder.fileTime.setText(format.format(new Date(file.lastModified())));

        return convertView;
    }

    public static String generateSize(File file) {
        if (file.isFile()) {
            long result = file.length();
            long gb = 2 << 29;
            long mb = 2 << 19;
            long kb = 2 << 9;
            if (result < kb) {
                return result + "B";
            } else if (result >= kb && result < mb) {
                return String.format("%.2fKB", result / (double) kb);
            } else if (result >= mb && result < gb) {
                return String.format("%.2fMB", result / (double) mb);
            } else if (result >= gb) {
                return String.format("%.2fGB", result / (double) gb);
            }
        }
        return null;
    }

    public static class ViewHolder {
        ImageView fileImage;
        TextView fileName;
        TextView fileSize;
        TextView fileTime;

        public ViewHolder(View v) {
            fileImage = (ImageView) v.findViewById(R.id.file_image);
            fileName = (TextView) v.findViewById(R.id.file_name);
            fileSize = (TextView) v.findViewById(R.id.file_size);
            fileTime = (TextView) v.findViewById(R.id.file_time);
        }
    }

}
