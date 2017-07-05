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
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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
 * Created by 萌 on 2017/3/27.
 */

public class ReleaseActivity extends AppCompatActivity implements View.OnClickListener {
    @ViewInject(R.id.bt_colseRelease)
    private Button bt_backup;
    @ViewInject(R.id.bt_sendRelease)
    private Button bt_send;
    @ViewInject(R.id.ib_camera_Release)
    private ImageButton ib_camera;
    @ViewInject(R.id.tv_number)
    private TextView tv_number;
    @ViewInject(R.id.et_InputRelease)
    private EditText et_inputRelease;
    @ViewInject(R.id.ib_photo_album)
    private ImageButton ib_Photo_album;
    @ViewInject(R.id.iv_picture)
    private ImageView iv_picture;
    @ViewInject(R.id.et_topic)
    private EditText topic;


    private int Number = 0;
    private static final int UPDATE_TEXT = 1;


    private Uri imageUri;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    private String noteusername;
    private String uid;
    private String noteemail;
    private UserInfo userinfo;
    private File outputImage;
    private Bitmap bitmap;
    private String noteimage;

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
        setContentView(R.layout.activity_release);
        ViewUtils.inject(this);
        bt_backup.setOnClickListener(this);
        bt_send.setOnClickListener(this);
        ib_camera.setOnClickListener(this);
        ib_Photo_album.setOnClickListener(this);
        et_inputRelease.addTextChangedListener(watcher);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT:
                    tv_number.setText(Number + "");
                    break;
                default:
                    break;
            }

        }
    };

    private void compress(File file) {
        File oldfile = file;
        newFile = CompressHelper.getDefault(this).compressToFile(oldfile);
        cc = "1";

//        newFile = new CompressHelper.Builder(ReleaseActivity.this)
//                .setMaxWidth(720)  // 默认最大宽度为720
//                .setMaxHeight(960) // 默认最大高度为960
//                .setQuality(80)    // 默认压缩质量为80
//                .setFileName(noteimage) // 设置你需要修改的文件名
//                .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
//                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
//                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
//                .build()
//                .compressToFile(oldfile);
    }

    private TextWatcher watcher = new TextWatcher() {
        private int MaxNumber = 140;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.e("---------->", et_inputRelease.getText().toString());

            System.out.println("1--------->" + s);
            System.out.println("2--------->" + count);
            if (count >= 140) {
                Toast.makeText(ReleaseActivity.this, "输入字数已经达到最大字数", Toast.LENGTH_SHORT).show();
            } else {

                Number = MaxNumber - count;


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = UPDATE_TEXT;
                        mHandler.sendMessage(msg);

                    }
                }).start();
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            System.out.println("5--------->" + s);
            System.out.println("6--------->" + s.getFilters().length);

        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_colseRelease:
                finish();
                break;
            case R.id.bt_sendRelease:
                showWaitingDialog();
                final String notehead = topic.getText().toString().trim();
                if (!notehead.isEmpty()) {
                    aa = "1";

                }

                final String notedetail = et_inputRelease.getText().toString().trim();
                if (!notedetail.isEmpty()) {
                    bb = "1";
                }


                Intent intent2 = getIntent();
                Bundle bundle = intent2.getExtras();
                final String noteusername = bundle.getString("noteusername");
                final String noteemail = bundle.getString("noteemail");
                final String uid = bundle.getString("uid");
                state = "1";


                String url = "http://47.93.5.144/niceo2o/user_getAndroidNote";
//                String url = "http://192.168.31.249:8080/niceo2o/user_getAndroidNote";
                if (aa.equals("1") && bb.equals("1") && cc.equals("1")) {
                    HttpUtils utils = new HttpUtils();
                    RequestParams params = new RequestParams("utf-8");
                    params.addBodyParameter("noteusername", noteusername);
                    params.addBodyParameter("notehead", notehead);
                    params.addBodyParameter("uid", uid);
                    params.addBodyParameter("notedetail", notedetail);
                    params.addBodyParameter("noteemail", noteemail);
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
                                Intent intent = new Intent();
                                intent.putExtra("data_return1", "1");
                                setResult(RESULT_OK);
                                finish();
                            } else {
                                Toast.makeText(ReleaseActivity.this, "上传失败,请重新上传", Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onFailure(HttpException error, String msg) {

                            Toast.makeText(ReleaseActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (aa.equals("1") && bb.equals("1") && cc.equals("0")) {
                    Toast.makeText(ReleaseActivity.this, "亲,别忘了添加配图哦", Toast.LENGTH_SHORT).show();
                } else if (aa.equals("0") && bb.equals("1") && cc.equals("1")) {
                    Toast.makeText(ReleaseActivity.this, "亲,给个标题嘛", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ReleaseActivity.this, "亲,内容不能为空哦", Toast.LENGTH_SHORT).show();
                }


                waitingDialog.dismiss();


                break;
            case R.id.ib_camera_Release:

                if (ContextCompat.checkSelfPermission(ReleaseActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ReleaseActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    Camera();
                }

                break;
            case R.id.ib_photo_album:
                //相册
                Intent intent3 = new Intent(Intent.ACTION_GET_CONTENT);
                intent3.setType("image/*");
                startActivityForResult(intent3, CHOOSE_PHOTO);
                break;
            default:
                break;
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
            imageUri = FileProvider.getUriForFile(ReleaseActivity.this, "com.guguxiaoyuan.nice.fileprovider", outputImage);
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
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Camera();
               }else {
                    Toast.makeText(this,"请从设置页面打开相机权限",Toast.LENGTH_SHORT).show();
                }
        }
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

                        state = "1";


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

                        state = "1";


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            default:
                break;
        }

    }


    private void showWaitingDialog() {
        waitingDialog = new ProgressDialog(ReleaseActivity.this);
//        waitingDialog.setTitle("");
        waitingDialog.setMessage("正在上传...");
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(false);
        waitingDialog.show();

    }


}
