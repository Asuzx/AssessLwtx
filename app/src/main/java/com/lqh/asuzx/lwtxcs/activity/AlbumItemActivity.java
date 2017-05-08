package com.lqh.asuzx.lwtxcs.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.Toast;

import com.lqh.asuzx.lwtxcs.R;
import com.lqh.asuzx.lwtxcs.adapter.AlbumItemAdaptered;
import com.lqh.asuzx.lwtxcs.bean.PhotoUpImageBucket;
import com.lqh.asuzx.lwtxcs.bean.PhotoUpImageItem;
import com.lqh.asuzx.lwtxcs.databinding.ActivityAlbumItemBinding;

import java.util.ArrayList;

public class AlbumItemActivity extends Activity {
    private ActivityAlbumItemBinding binding;

    private PhotoUpImageBucket photoUpImageBucket;
    private ArrayList<PhotoUpImageItem> selectImages;

    private AlbumItemAdaptered adaptered;
    private boolean checkall = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.album_item_images);
        binding = DataBindingUtil.setContentView(AlbumItemActivity.this, R.layout.activity_album_item);
        binding.setMyOnclick(new MyOnclick());
        init();
        setListener();
    }

    private void init() {

        selectImages = new ArrayList<PhotoUpImageItem>();
        Intent intent = getIntent();
        photoUpImageBucket = (PhotoUpImageBucket) intent.getSerializableExtra("imagelist");

        adaptered = new AlbumItemAdaptered(photoUpImageBucket.getImageList(), AlbumItemActivity.this);
        binding.recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        RecyclerView.RecycledViewPool pool = binding.recycler.getRecycledViewPool();
        pool.setMaxRecycledViews(0,10);
        binding.recycler.setRecycledViewPool(pool);
//        binding.recycler.setAdapter(adaptered);
        binding.setAdapter(adaptered);
    }

    private void setListener() {
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
//                Toast.makeText(AlbumItemActivity.this, "postion=" + position,
//                        Toast.LENGTH_SHORT).show();
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

    public void SetCheckAll(boolean status) {
        for (int i = 0; i < photoUpImageBucket.getImageList().size(); i++) {
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode== KeyEvent.KEYCODE_BACK){
            Intent resultIntent = new Intent();
            resultIntent.putExtra("selectIma", selectImages);
            setResult(1, resultIntent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public class MyOnclick {
        public void back() {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("selectIma", selectImages);
            setResult(1, resultIntent);
            finish();
        }

        public void select_all() {
            if (checkall) {
                checkall = !checkall;
                SetCheckAll(checkall);
            } else {
                checkall = !checkall;
                SetCheckAll(checkall);
            }
            adaptered.notifyDataSetChanged();
        }
    }

}
