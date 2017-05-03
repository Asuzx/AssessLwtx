package com.lqh.asuzx.lwtxcs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.lqh.asuzx.lwtxcs.R;
import com.lqh.asuzx.lwtxcs.adapter.AlbumItemAdaptered;
import com.lqh.asuzx.lwtxcs.bean.PhotoUpImageBucket;
import com.lqh.asuzx.lwtxcs.bean.PhotoUpImageItem;

import java.util.ArrayList;

public class AlbumItemActivity extends Activity implements OnClickListener {

    private GridView gridView;
    private TextView back, select_all;
    private PhotoUpImageBucket photoUpImageBucket;
    private ArrayList<PhotoUpImageItem> selectImages;

    private AlbumItemAdaptered adaptered;
    private RecyclerView recyclerView;
    private boolean checkall = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.album_item_images);
        setContentView(R.layout.show_image_activity);
        init();
        setListener();
    }

    private void init() {
        back = (TextView) findViewById(R.id.back);
        select_all = (TextView) findViewById(R.id.select_all);
        selectImages = new ArrayList<PhotoUpImageItem>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        Intent intent = getIntent();
        photoUpImageBucket = (PhotoUpImageBucket) intent.getSerializableExtra("imagelist");
//		adapter = new AlbumItemAdapter(photoUpImageBucket.getImageList(), AlbumItemActivity.this);
//		gridView.setAdapter(adapter);
        adaptered = new AlbumItemAdaptered(photoUpImageBucket.getImageList(), AlbumItemActivity.this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adaptered);
    }

    private void setListener() {
        back.setOnClickListener(this);
        select_all.setOnClickListener(this);
        adaptered.setOnItemClickListener(new AlbumItemAdaptered.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = (int) view.getTag();
                Log.e("选中", position + "");
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
                photoUpImageBucket.getImageList().get(position).setSelected(
                        !checkBox.isChecked());
                checkBox.setChecked(!checkBox.isChecked());
//				adaptered.notifyDataSetChanged();
                Toast.makeText(AlbumItemActivity.this, "postion=" + position,
                        Toast.LENGTH_SHORT).show();
//				photoUpImageBucket.getImageList().get(position).setSelected(
//						!photoUpImageBucket.getImageList().get(position).isSelected());
//				adapter.notifyDataSetChanged();
                if (photoUpImageBucket.getImageList().get(position).isSelected()) {
                    if (selectImages.contains(photoUpImageBucket.getImageList().get(position))) {

                    } else {
                        selectImages.add(photoUpImageBucket.getImageList().get(position));
                    }
                } else {
                    if (selectImages.contains(photoUpImageBucket.getImageList().get(position))) {
                        selectImages.remove(photoUpImageBucket.getImageList().get(position));
                    } else {

                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectIma", selectImages);
                setResult(1,resultIntent);
                finish();
                break;
            case R.id.select_all:
                if (checkall){
                    checkall = !checkall;
                    SetCheckAll(checkall);
                }else{
                    checkall = !checkall;
                    SetCheckAll(checkall);
                }
                adaptered.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }
    public void SetCheckAll(boolean status){
        for (int i=0;i<photoUpImageBucket.getImageList().size();i++) {
            photoUpImageBucket.getImageList().get(i).setSelected(
                    status);
            if (photoUpImageBucket.getImageList().get(i).isSelected()) {
                if (selectImages.contains(photoUpImageBucket.getImageList().get(i))) {

                } else {
                    selectImages.add(photoUpImageBucket.getImageList().get(i));
                }
            } else {
                if (selectImages.contains(photoUpImageBucket.getImageList().get(i))) {
                    selectImages.remove(photoUpImageBucket.getImageList().get(i));
                } else {

                }
            }
        }
    }

}
