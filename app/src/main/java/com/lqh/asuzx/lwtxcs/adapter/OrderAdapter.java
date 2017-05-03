package com.lqh.asuzx.lwtxcs.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lqh.asuzx.lwtxcs.databinding.OrderItemBinding;
import com.lqh.asuzx.lwtxcs.tools.ApkTools;
import com.lqh.asuzx.lwtxcs.tools.AppInfo;

import java.util.List;

/**
 * Created by lqh on 2017/4/26 0026.
 */

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<AppInfo> list;
    private int layoutId;//单布局
    private int variableId;
    private int variableIds;

    public OrderAdapter(Context context, List<AppInfo> list, int layoutId, int variableId,int variableIds) {
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
        this.variableId = variableId;
        this.variableIds = variableIds;
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
        OrderItemBinding binding = null;
                 if (convertView == null){
                         binding = DataBindingUtil.inflate(LayoutInflater.from(context),layoutId,parent,false);
                     } else {
                         binding = DataBindingUtil.getBinding(convertView);
                     }
                 binding.setVariable(variableId,list.get(position));
                 binding.setVariable(variableIds,new ApkTools());
                 return binding.getRoot();
    }
}
