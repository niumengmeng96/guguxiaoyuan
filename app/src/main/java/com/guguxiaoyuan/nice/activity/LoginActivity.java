package com.guguxiaoyuan.nice.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.guguxiaoyuan.nice.R;
import com.guguxiaoyuan.nice.bean.UserInfo;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.common.Constants;

import java.lang.ref.WeakReference;


public class LoginActivity extends BaseActivity {

    private EditText mUsername;
    private EditText mPasswrod;
    private UserInfo userInfo;
    private TextView rmpassword;
    private CheckBox cbrmpassword;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private ProgressDialog waitingDialog;
    private String login;
    /**
     * 获取设备Token
     */
    private String token;
    private Message message = null;
    private static LoginActivity theActivity;
    private static String token1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        XGInit();




    }
    private void XGInit() {
        // TODO Auto-generated method stub
        XGPushConfig.enableDebug(this, true);
        Context context = getApplicationContext();
        XGPushManager.registerPush(context);
        // 如果需要知道注册是否成功，请使用registerPush(getApplicationContext(),
        // XGIOperateCallback)带callback版本
        // 如果需要绑定账号，请使用registerPush(getApplicationContext(),account)版本
        // 具体可参考详细的开发指南
        // 传递的参数为ApplicationContext
        //Context context = getApplicationContext();
        //1.获取设备的Token
        Handler handler = new LoginActivity.HandlerExtension(LoginActivity.this);
        message = handler.obtainMessage();
        XGPushManager.registerPush(getApplicationContext(), new XGIOperateCallback() {

            @Override
            public void onSuccess(Object data, int flag) {
                // TODO Auto-generated method stub
                Log.d("jiejie", "+++ register push sucess. token:" + data + "  " + flag);
                token = data + "";
                message.obj = "+++ register push sucess. token:" + data;
                System.out.println(token);
                message.sendToTarget();
            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
                // TODO Auto-generated method stub
                Log.d("jiejie", "+++ register push fail. token:" + data
                        + ", errCode:" + errCode + ",msg:"
                        + msg);
                message.obj = "+++ register push fail. token:" + data
                        + ", errCode:" + errCode + ",msg:" + msg;
                message.sendToTarget();
            }
        });
    }
    private static class HandlerExtension extends Handler {
        WeakReference<LoginActivity> mActivity;

        HandlerExtension(LoginActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            theActivity = mActivity.get();
            if (theActivity == null) {
                theActivity = new LoginActivity();
            }
            if (msg != null) {
                Log.d(Constants.LogTag, msg.obj.toString());
                System.out.println("ddd" + msg.obj.toString());
                token1 = XGPushConfig.getToken(theActivity);
                Log.d("Token1", token1);
            }
        }
    }

    private void initView(){

        setContentView(R.layout.activity_login);
        mUsername = (EditText) findViewById(R.id.et_username);
        mPasswrod = (EditText) findViewById(R.id.et_password);
        mPasswrod.setTransformationMethod(PasswordTransformationMethod.getInstance());
        rmpassword = (TextView) findViewById(R.id.tv_rmpassword);
        cbrmpassword = (CheckBox) findViewById(R.id.cb_rmpassword);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = sp.getBoolean("remember_password", false);
        if (isRemember) {
            //将账号密码都设置到文本框中
            String username = sp.getString("username", "");
            String password = sp.getString("password", "");
            mUsername.setText(username);
            mPasswrod.setText(password);
            cbrmpassword.setChecked(true);
            mPasswrod.setSelection(password.length());
        }
    }


    public void login(View v) {

        final String username = mUsername.getText().toString().trim();
        final String password = mPasswrod.getText().toString().trim();
        String url = "http://47.93.5.144/niceo2o/user_Androidlogin";
//        String url = "http://192.168.31.249:8080/niceo2o/user_Androidlogin";
        if (!username.isEmpty() && !password.isEmpty()) {
            showWaitingDialog();
            HttpUtils utils = new HttpUtils();
            RequestParams params = new RequestParams("utf-8");
            params.addBodyParameter("username", username);
            params.addBodyParameter("password", password);
            params.addBodyParameter("token", token);


            utils.send(HttpMethod.POST, url, params, new RequestCallBack() {
                @Override
                public void onSuccess(ResponseInfo responseInfo) {


                    Gson gson = new Gson();
                    userInfo = gson.fromJson((String) responseInfo.result, UserInfo.class);
//                    Log.e("成功", String.valueOf(userInfo.toString()));
                    String uid = userInfo.getId();
                    String noteusername = userInfo.getUsername();
                    String noteemail = userInfo.getEmail();
                    String image = userInfo.getImage();
                    login = userInfo.getLogin();
//                    Log.e("图片是------------------", image);

                    if (login.equals("1")) {
                        waitingDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "用户名密码错误,请重新输入!", Toast.LENGTH_SHORT).show();
                    } else if (login.equals("3")) {
                        waitingDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "账号未激活,请前去邮箱激活", Toast.LENGTH_SHORT).show();

                    } else {

                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        intent.putExtra("uid", uid);
                        intent.putExtra("noteusername", noteusername);
                        intent.putExtra("noteemail", noteemail);
                        intent.putExtra("image", image);
                        startActivity(intent);
                        editor = sp.edit();
                        if (cbrmpassword.isChecked()) {
                            editor.putBoolean("remember_password", true);
                            editor.putString("username", username);
                            editor.putString("password", password);
                        } else {
                            editor.clear();
                        }
                        editor.apply();
                        finish();
                        waitingDialog.dismiss();
                    }
                }
                @Override
                public void onFailure(HttpException error, String msg) {
                    waitingDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    Log.e("失败", msg);
                }
            });


        } else {
            Toast.makeText(LoginActivity.this, "账号或密码不能为空!", Toast.LENGTH_SHORT).show();
        }


    }

    private void showWaitingDialog() {

        waitingDialog = new ProgressDialog(LoginActivity.this);
//        waitingDialog.setTitle("");
        waitingDialog.setMessage("正在登陆...");
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(false);
        waitingDialog.show();

    }

    /**
     * 双击退出
     *
     * @param keyCode
     * @param event
     * @return
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ExitApp();
        }
        return false;
    }

    private long exitTime = 0;

    public void ExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            this.finish();
        }
    }
}
