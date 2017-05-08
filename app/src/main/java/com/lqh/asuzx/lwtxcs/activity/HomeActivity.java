package com.lqh.asuzx.lwtxcs.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.lqh.asuzx.lwtxcs.R;
import com.lqh.asuzx.lwtxcs.adapter.CameraAdapter;
import com.lqh.asuzx.lwtxcs.adapter.SelectedImagesAdapter;
import com.lqh.asuzx.lwtxcs.bean.PhotoUpImageBucket;
import com.lqh.asuzx.lwtxcs.bean.PhotoUpImageItem;
import com.lqh.asuzx.lwtxcs.databinding.ActivityHomeBinding;
import com.lqh.asuzx.lwtxcs.tools.AppInfo;
import com.lqh.asuzx.lwtxcs.ui.CustomProgressDialog;
import com.lqh.asuzx.lwtxcs.ui.MyImageDialog;
import com.lqh.asuzx.lwtxcs.tools.PhotoUpAlbumHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lqh on 2017/4/26 0026.
 * 主界面Activity
 * CustomProgressDialog 等待框
 * photoUpAlbumHelper 加载图片线程
 * listed 存放图片。传递给下一个Activity显示
 * bm 拍照返回的对象
 * power 如果是图片选择器返回的  则添加新的adapter 可进行图片预览
 * verification 当activity第一次启动 图片线程未执行完成 点击图片选择按钮，将在图片加载完成后执行跳转
 * myImageDialog 图片预览的Dialog
 */

public class HomeActivity extends Activity implements AdapterView.OnItemClickListener {
    private ActivityHomeBinding binding;
    private Bitmap bm;//返回拍照所得的bm

    private List<PhotoUpImageBucket> listed = null;
    private PhotoUpAlbumHelper photoUpAlbumHelper;

    private ArrayList<PhotoUpImageItem> arrayList;
    private SelectedImagesAdapter selectedImagesAdapter;//存储拍照返回的照片
    private ArrayList<AppInfo> cameraAppList = new ArrayList<AppInfo>();
    private CustomProgressDialog customProgressDialog = null;
    private MyImageDialog myImageDialog;
    private boolean power = true;
    private boolean verification = false; //

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
        verification = false;
        if (resultCode == 1) {
            cameraAppList = new ArrayList<AppInfo>();
            arrayList = (ArrayList<PhotoUpImageItem>) data.getSerializableExtra("selectIma");
            selectedImagesAdapter = new SelectedImagesAdapter(HomeActivity.this,
                    arrayList);
//            binding.selectedImagesGridv.setAdapter(selectedImagesAdapter);
            binding.setShowAdapter(selectedImagesAdapter);
            power = true;
        } else {
            if (data == null) {
                Toast.makeText(this, "无照片", Toast.LENGTH_SHORT).show();
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
//                    binding.selectedImagesGridv.setAdapter(cameraAdapter);
                    binding.setShowAdapter(cameraAdapter);
                }
            }
            power = false;
        }
        binding.gridview.setOnItemClickListener(this);
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
                handler.sendEmptyMessage(0);//所有图片加载完成 跳转到图片选择器
            }
        });
        photoUpAlbumHelper.execute(false);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        view.setDrawingCacheEnabled(true);
        if (power) {
            myImageDialog = new MyImageDialog(HomeActivity.this, R.style.dialogWindowAnim, 0, 0, arrayList.get(position).getImagePath());
            myImageDialog.show();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (verification && listed.size() != 0) {
                Intent intent = new Intent(HomeActivity.this, AlbumItemActivity.class);
                intent.putExtra("imagelist", listed.get(0));
                startActivityForResult(intent, 1);
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
//        Log.e("缓存路径",MyAppcation.getContextObject().getCacheDir().toString());
//        DataCleanManager.cleanApplicationData(HomeActivity.this, MyAppcation.getContextObject().getCacheDir().toString());
    }

    public class ShowImages {
        public void image(View view) {
            //获取系统图片
            customProgressDialog.show();//activity 启动过慢时候 概率性出现动画无法播放。
            if (listed == null) {
                verification = true;
                Toast.makeText(HomeActivity.this, "请稍等图片加载中。。。", Toast.LENGTH_SHORT).show();
            } else if (listed.size() == 0) {
                Toast.makeText(HomeActivity.this, "系统无图片", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(HomeActivity.this, AlbumItemActivity.class);
                intent.putExtra("imagelist", listed.get(0));
                startActivityForResult(intent, 1);
            }

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
