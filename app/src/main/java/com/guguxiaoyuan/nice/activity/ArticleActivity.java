package com.guguxiaoyuan.nice.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.guguxiaoyuan.nice.R;
import com.guguxiaoyuan.nice.bean.UserInfo;
import com.guguxiaoyuan.nice.csviewpager.SwipeBackLayout;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nanchen.compresshelper.CompressHelper;
import com.nanchen.compresshelper.FileUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by 萌 on 2017/5/16.
 */

public class ArticleActivity extends AppCompatActivity {


    @ViewInject(R.id.et_article_InputRelease)
    private EditText et_inputRelease;
    @ViewInject(R.id.iv_article_picture)
    private ImageView iv_picture;
    @ViewInject(R.id.et_article_topic)
    private EditText topic;



    private Uri imageUri;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    private UserInfo userinfo;
    private File outputImage;
    private Bitmap bitmap;


    private File oldFile;
    private File newFile;
    private String state;
    private ProgressDialog waitingDialog;
    private String aa = "0";//0为空,1为有数据
    private String bb = "0";
    private String cc = "0";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackLayout layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
                R.layout.swipeback, null);
        layout.attachToActivity(this);

        setContentView(R.layout.activity_article);
        ViewUtils.inject(this);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


    }

    private void compress(File file) {
        File oldfile = file;
        cc="1";
        newFile = CompressHelper.getDefault(this).compressToFile(oldfile);

    }

    public void articleClose(View view) {
        finish();

    }

    public void articleSend(View view) {

        String notehead = topic.getText().toString().trim();
        if (!notehead.isEmpty()) {
            aa = "1";
        }
        String notedetail = et_inputRelease.getText().toString().trim();
        if (!notedetail.isEmpty()) {
            bb = "1";
        }
        Intent intent2 = getIntent();
        Bundle bundle = intent2.getExtras();
        String noteusername = bundle.getString("noteusername");
        state = "2";


        if (aa.equals("1") && bb.equals("1") && cc.equals("1")) {
            showWaitingDialog();
            String url = "http://47.93.5.144/niceo2o/android_AndroidSentHeartMood";
            HttpUtils utils = new HttpUtils();
            RequestParams params = new RequestParams("utf-8");
            params.addBodyParameter("ausername", noteusername);
            params.addBodyParameter("head", notehead);
            params.addBodyParameter("detail", notedetail);
            params.addBodyParameter("state", state);
            params.addBodyParameter("file", newFile, "image/jpg");
            utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {

                    Gson gson = new Gson();
                    userinfo = gson.fromJson(responseInfo.result, UserInfo.class);
//                        Log.e("+++++++++++++++++++++", noteimage);
                    Log.e("+++++++++++++++++++++", "成功了");
                    String result = userinfo.getResult();
                    if (result.equals("1")) {
                        Intent intent=new Intent();
                        intent.putExtra("data_return3","3");
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        Toast.makeText(ArticleActivity.this, "上传失败,请重新上传", Toast.LENGTH_SHORT).show();
                    }
                    waitingDialog.dismiss();


                }

                @Override
                public void onFailure(HttpException error, String msg) {
                    waitingDialog.dismiss();
                    Toast.makeText(ArticleActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    Log.e("R+++++++++++++++++++++", "ReleaseActivity失败了");
                }
            });
        }

    }

    public void articleTakephoto(View view) {
        if (ContextCompat.checkSelfPermission(ArticleActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ArticleActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            Camera();
        }

    }
    public void Camera() {
        //创建File对象,用于存储拍照后的图片
        outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(ArticleActivity.this, "com.guguxiaoyuan.nice.fileprovider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        //启动照相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Camera();
                }else {
                    Toast.makeText(this,"请从设置页面打开相机权限",Toast.LENGTH_SHORT).show();
                }
        }
    }

    public void articlealbum(View view) {
        //相册
        Intent intent3 = new Intent(Intent.ACTION_GET_CONTENT);
        intent3.setType("image/*");
        startActivityForResult(intent3, CHOOSE_PHOTO);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
//                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        compress(outputImage);
                        iv_picture.setImageBitmap(BitmapFactory.decodeFile(newFile.getAbsolutePath()));
                        state = "2";

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        oldFile = FileUtil.getTempFile(this, data.getData());
                        compress(oldFile);
                        iv_picture.setImageBitmap(BitmapFactory.decodeFile(newFile.getAbsolutePath()));
                        state = "2";

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            default:
                break;
        }

    }


    private void showWaitingDialog() {
        waitingDialog = new ProgressDialog(ArticleActivity.this);
//        waitingDialog.setTitle("");
        waitingDialog.setMessage("正在上传...");
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(false);
        waitingDialog.show();

    }


}
