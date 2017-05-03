package com.lqh.asuzx.lwtxcs.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.lqh.asuzx.lwtxcs.R;
import com.lqh.asuzx.lwtxcs.adapter.CameraAdapter;
import com.lqh.asuzx.lwtxcs.adapter.SelectedImagesAdapter;
import com.lqh.asuzx.lwtxcs.bean.PhotoUpImageBucket;
import com.lqh.asuzx.lwtxcs.bean.PhotoUpImageItem;
import com.lqh.asuzx.lwtxcs.databinding.ActivityHomeBinding;
import com.lqh.asuzx.lwtxcs.tools.AppInfo;
import com.lqh.asuzx.lwtxcs.ui.CustomProgressDialog;
import com.lqh.asuzx.lwtxcs.utils.PhotoUpAlbumHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lqh on 2017/4/26 0026.
 * 主界面Activity
 */

public class HomeActivity extends Activity {
    private ActivityHomeBinding binding;
    private Bitmap bm;//返回拍照所得的bm

    private List<PhotoUpImageBucket> listed;
    private PhotoUpAlbumHelper photoUpAlbumHelper;

    private ArrayList<PhotoUpImageItem> arrayList;
    private SelectedImagesAdapter adapter;//存储拍照返回的照片
    private ArrayList<AppInfo> cameraAppList = new ArrayList<AppInfo>();
    private CustomProgressDialog customProgressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        binding.setShow(new ShowImages());

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (customProgressDialog != null)
            customProgressDialog.dismiss();
        customProgressDialog = CustomProgressDialog.createDialog(HomeActivity.this);
        loadData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            cameraAppList = new ArrayList<AppInfo>();
            arrayList = (ArrayList<PhotoUpImageItem>) data.getSerializableExtra("selectIma");
            adapter = new SelectedImagesAdapter(HomeActivity.this,
                    arrayList);
            binding.selectedImagesGridv.setAdapter(adapter);
        } else {
            if (data == null) {
                Toast.makeText(this, "未选择照片", Toast.LENGTH_SHORT).show();
            } else if (data.getExtras() != null) {
                Bundle bundle = data.getExtras();
                bm = (Bitmap) bundle.get("data");
                if (bm != null)
                    bm.recycle();
                bm = (Bitmap) data.getExtras().get("data");
                if (bm != null) {
                    AppInfo appInfo = new AppInfo();
                    appInfo.setCamera(bm);
                    cameraAppList.add(appInfo);
                    CameraAdapter cameraAdapter = new CameraAdapter(this, cameraAppList);
                    binding.selectedImagesGridv.setAdapter(cameraAdapter);
                }
            }
        }
    }

    //拍照动作
    private void intentCamera() {
        try {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, 0);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    //初始化
    private void loadData() {
        photoUpAlbumHelper = PhotoUpAlbumHelper.getHelper();
        photoUpAlbumHelper.init(HomeActivity.this);
        photoUpAlbumHelper.setGetAlbumList(new PhotoUpAlbumHelper.GetAlbumList() {
            @Override
            public void getAlbumList(List<PhotoUpImageBucket> list) {
                listed = list;
            }
        });
        photoUpAlbumHelper.execute(false);

    }


    public class ShowImages {
        public void image(View view) {
            //获取系统图片
            customProgressDialog.show();//activity 启动过慢时候 概率性出现动画无法播放。
            Intent intent = new Intent(HomeActivity.this, AlbumItemActivity.class);
            intent.putExtra("imagelist", listed.get(0));
            startActivityForResult(intent, 1);
//            startActivity(new Intent(HomeActivity.this, AlbumsActivity.class));
        }

        public void camera(View view) {
            //拍照
            intentCamera();
        }

        public void order(View view) {
            //获取系统应用
            customProgressDialog.show();
            startActivity(new Intent(HomeActivity.this, OrderActivity.class));
        }

    }
}
