package com.guguxiaoyuan.nice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.guguxiaoyuan.nice.R;
import com.guguxiaoyuan.nice.bean.UserInfo;
import com.guguxiaoyuan.nice.csviewpager.SwipeBackLayout;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 萌 on 2017/4/5.
 */

public class ListActivity extends AppCompatActivity {

    private TextView detail;
    private TextView topic;
    private TextView username;
    private TextView time;
    private Button accept;
    private String nuid;
    private String uid;
    private String nid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackLayout layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
                R.layout.swipeback, null);
        layout.attachToActivity(this);
        initView();
        initData();
    }

    public void initView(){
        setContentView(R.layout.activity_list);
        detail = (TextView) findViewById(R.id.tv_rp_content);
        topic = (TextView) findViewById(R.id.tv_rp_topic);
        username = (TextView) findViewById(R.id.dynamic_tv_user_name);
        time = (TextView) findViewById(R.id.dynamic_tv_time);
        accept = (Button) findViewById(R.id.btn_accept);
    }
    public void initData(){
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String Noteusername = bundle.getString("noteusername");
        String Notedetail = bundle.getString("notedetail");
        String Notehead = bundle.getString("notehead");
        String Time = bundle.getString("time");
        String uri = bundle.getString("uri");
        String url = bundle.getString("url");
        nuid = bundle.getString("nuid");
        uid = bundle.getString("uid");
        nid = bundle.getString("nid");
        Log.e("uri", uri);
        Log.e("url", url);
        Log.e("nuid++++++++", nuid);
        Log.e("uid========", uid);
        Log.e("nid========", nid);
        ImageView OneContentImage = (ImageView) findViewById(R.id.tv_rpim_content);
//        Picasso.with(ListActivity.this).load(uri).error(R.mipmap.logo).into(OneContentImage);
        Picasso
                .with(ListActivity.this)
                .load(uri)
                .centerInside()
                .fit()
                .into(OneContentImage);

        CircleImageView OneUserImage = (CircleImageView) findViewById(R.id.item_iv_head);
        Picasso.with(ListActivity.this).load(url).error(R.mipmap.logo).into(OneUserImage);

        detail.setText(Notedetail);
        topic.setText(Notehead);
        username.setText(Noteusername);
        time.setText(Time);
        if (nuid.equals(uid)) {
            accept.setVisibility(View.INVISIBLE);
        } else {
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String url = "http://47.93.5.144/niceo2o/user_AndroidReachNote";
                    HttpUtils utils = new HttpUtils();
                    RequestParams params = new RequestParams("utf-8");
                    params.addBodyParameter("uid", uid);
                    params.addBodyParameter("nid", nid);
                    utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            Gson gson = new Gson();
                            UserInfo result = gson.fromJson(responseInfo.result, UserInfo.class);
                            if (result.equals("1")) {
                                Toast.makeText(ListActivity.this, "接受失败", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ListActivity.this, "已接单", Toast.LENGTH_SHORT).show();
                                Intent intent1=new Intent();
                                intent1.putExtra("data_return1","1");
                                setResult(RESULT_OK);

                                finish();

                            }

                        }

                        @Override
                        public void onFailure(HttpException error, String msg) {
                            Toast.makeText(ListActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });

        }

    }

}
