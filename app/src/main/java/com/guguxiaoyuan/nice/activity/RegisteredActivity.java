package com.guguxiaoyuan.nice.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

/**
 * Created by 萌 on 2017/4/6.
 */


public class RegisteredActivity extends AppCompatActivity {

    @ViewInject(R.id.et_name)
    private EditText name;
    @ViewInject(R.id.et_email)
    private EditText email;
    @ViewInject(R.id.et_password)
    private EditText password;
    @ViewInject(R.id.et_re_password)
    private EditText repassword;
    @ViewInject(R.id.registe)
    private Button registe;
    private String Name;
    private String Email;
    private String Password;
    private String RePassword;
    private UserInfo register;
    private ProgressDialog waitingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackLayout layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
                R.layout.swipeback, null);
        layout.attachToActivity(this);
        setContentView(R.layout.activity_aboutus);
        setContentView(R.layout.activity_registered);
        ViewUtils.inject(this);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


        registe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = name.getText().toString().trim();
                Email = email.getText().toString().trim();
                Password = password.getText().toString().trim();
                RePassword = repassword.getText().toString().trim();

                infoJudge();

            }
        });

    }

    private void infoJudge() {
        if (Name.isEmpty()) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
        } else if (Email.isEmpty()) {
            Toast.makeText(this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
        } else if (Password.isEmpty()) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
        } else if (RePassword.isEmpty()) {
            Toast.makeText(this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
        } else if (!Password.equals(RePassword)) {
            Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show();
        } else if (Password.equals(RePassword) && !RePassword.isEmpty() && !Password.isEmpty() && !Email.isEmpty() && !Name.isEmpty()) {
            showWaitingDialog();
            String uri = "http://47.93.5.144/niceo2o/user_Androidregister";
//            String uri = "http://192.168.31.249  :8080/niceo2o/user_Androidregister";
            HttpUtils utils = new HttpUtils();
            RequestParams params = new RequestParams("utf-8");
            params.addBodyParameter("username", Name);
            params.addBodyParameter("email", Email);
            params.addBodyParameter("password", Password);
            utils.send(HttpMethod.POST, uri, params, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    Gson gson = new Gson();
                    register = gson.fromJson(responseInfo.result, UserInfo.class);
                    Log.e("返回码是", String.valueOf(register));
                    String Register = register.getRegister();
                    if (Register == "1") {
                        Toast.makeText(RegisteredActivity.this, "邮箱已被使用", Toast.LENGTH_SHORT).show();
                    } else if (Register == "2") {
                        Toast.makeText(RegisteredActivity.this, "用户名已被使用", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisteredActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    waitingDialog.dismiss();

                }

                @Override
                public void onFailure(HttpException error, String msg) {
                    Toast.makeText(RegisteredActivity.this, "联网注册失败", Toast.LENGTH_SHORT).show();
                    Log.e("失败", msg);
                    waitingDialog.dismiss();


                }
            });


        } else {
            Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
        }

    }

    private void showWaitingDialog() {
        waitingDialog = new ProgressDialog(RegisteredActivity.this);
//        waitingDialog.setTitle("");
        waitingDialog.setMessage("请稍后...");
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(false);
        waitingDialog.show();

    }

}
