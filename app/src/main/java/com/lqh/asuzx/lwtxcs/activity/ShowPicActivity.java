package com.lqh.asuzx.lwtxcs.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.lqh.asuzx.lwtxcs.R;

import java.util.List;
import java.util.TreeMap;

/**
 * Created by lqh on 2017/4/26 0026.
 */

public class ShowPicActivity extends Activity {


    private ProgressDialog mProgressDialog;
    private final static int SCAN_OK = 1;


    //图片选择
    private TreeMap<String, List<String>> mGruopMap = new TreeMap<String, List<String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


}
