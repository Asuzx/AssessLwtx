package com.lqh.asuzx.lwtxcs.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lqh.asuzx.lwtxcs.R;
import com.lqh.asuzx.lwtxcs.adapter.FileAdapter;
import com.lqh.asuzx.lwtxcs.databinding.ActivityFileManagerBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by lqh on 2017/5/6 0007.
 * 自定义文件管理器。用来保证移植apk时可以手动选择
 */

public class FileManagerActivity extends Activity {
    private ActivityFileManagerBinding binding;
    private ArrayList<File> data = new ArrayList<>();
    private File[] files;
    private FileAdapter fileAdapter;

    private String rootpath;
    private Stack<String> nowPathStack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_file_manager);
        initView();
    }

    private void initView() {
        rootpath = Environment.getExternalStorageDirectory().toString();
        nowPathStack = new Stack<>();
        //获得本地文件信息列表，绑定到data
        files = Environment.getExternalStorageDirectory()
                .listFiles();
        //将根路径推入路径栈
        nowPathStack.push(rootpath);
        for (File f : files) {
            data.add(f);
        }
        binding.showtv.setText(getPathString());
        fileAdapter = new FileAdapter(this, data);
        binding.setAdapter(fileAdapter);
        binding.lv.setOnItemClickListener(new FileItemClickListener());
        binding.setMyOnclick(new MyOnclick());
    }

    //得到当前栈路径的String
    private String getPathString() {
        Stack<String> temp = new Stack<>();
        temp.addAll(nowPathStack);
        String result = "";
        while (temp.size() != 0) {
            result = temp.pop() + result;
        }
        return result;
    }


    //显示改变data之后的文件数据列表
    private void showChangge(String path) {
        binding.showtv.setText(path);
        files = new File(path).listFiles();
        data.clear();
        for (File f : files) {
            data.add(f);
        }
        files = fileAdapter.setfiledata(data);
    }


    //返回事件
    @Override
    public void onBackPressed() {
        if (nowPathStack.peek() == rootpath) {
                super.onBackPressed();
        } else {
            nowPathStack.pop();
            showChangge(getPathString());
        }

    }
    class FileItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(
                AdapterView<?> parent,
                View view,
                int position,
                long id) {

            File file = files[position];
            if (!file.isFile()) {
                //如果是文件夹
                // 清除列表数据
                // 获得目录中的内容，计入列表中
                // 适配器通知数据集改变
                nowPathStack.push("/" + file.getName());
                showChangge(getPathString());
            } else {
                //非文件夹  不做处理
            }
        }
    }
    public class MyOnclick {
        public void back() {
            onBackPressed();
        }

        public void selected() {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("savePath",  binding.showtv.getText().toString());
            setResult(3, resultIntent);//选择器返回3
            finish();
        }
    }
}
