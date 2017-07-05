package com.guguxiaoyuan.nice.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guguxiaoyuan.nice.Adapter.SendAdapter;
import com.guguxiaoyuan.nice.R;
import com.guguxiaoyuan.nice.bean.Post;
import com.guguxiaoyuan.nice.bean.SendPool;
import com.guguxiaoyuan.nice.csviewpager.SwipeBackLayout;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 萌 on 2017/4/25.
 */

public class SendActivity extends AppCompatActivity {
    private List<SendPool> send_item = new ArrayList<>();//listview中添加数据
    private List<Post> appList;
    private String noteusername;
    private String notedetail;
    private String time;
    private String notehead;
    private String noteemail;
    private String reachrname;
    private String reachemail;
    private String uid;
    private String id;
    private String notesate;
    private Intent intent;

    private Post post;
    private String noteimage;
    private String userimage;
    private ListView lv;

    private String url1;
    private String uid1;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog waitingDialog;
    private RecyclerView rl;
    private SendAdapter adapter;


    private Handler handler =  new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case '1':

                    waitingDialog.dismiss();
                    initView();
                    Toast.makeText(getBaseContext(),"text",Toast.LENGTH_LONG).show();
                    break;
            }

            super.handleMessage(msg);
        }
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackLayout layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
                R.layout.swipeback, null);
        layout.attachToActivity(this);
        setContentView(R.layout.activity_send);
        showWaitingDialog();
        sendRequestWithOKHttp();



    }




    private void initView() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_send);
        rl = (RecyclerView) findViewById(R.id.rl_send);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rl.setLayoutManager(layoutManager);
        adapter = new SendAdapter(send_item);
        rl.setAdapter(adapter);
        Log.e("initView", "==========");
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //设置2秒的时间来执行以下事件
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (adapter != null) {
                            adapter.clear();
                            sendRequestWithOKHttp();
                            adapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                        }

                    }
                }, 2000);
            }
        });

    }

//



    /**
     * 发送请求
     */
    private void sendRequestWithOKHttp() {
//        Log.e("initData", "==========");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        uid1 = bundle.getString("uid1");
        url1 = "http://47.93.5.144/niceo2o/user_AndroidSelectMyNote?ssid=" + uid1;
//        Log.e("SendActivity---uid", uid1);
//        Log.e("SendActivity---url1", url1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url(url1)
                            .build();
                    Response response = client.newCall(request).execute();
                    //得到json数据
                    String responseData = response.body().string();
                    Log.e("服务器获取ListView数据:", responseData);
                    //用Gson解析json数据
                    parseJSONWithGSON(responseData);


                    Message message = new Message();
                    message.what='1';
                    handler.sendMessage(message);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    /**
     * 解析Gson
     *
     * @param jsonData
     */
    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        appList = gson.fromJson(jsonData, new TypeToken<List<Post>>() {
        }.getType());

        for (Post post : appList) {
//            Log.e("MainActivity", "用户名是" + post.getNoteusername());
//            Log.e("MainActivity", "主题是" + post.getNotedetail());
//            Log.e("MainActivity", "时间是" + post.getTime());
//            Log.e("MainActivity", "内容是" + post.getNotehead());
//            Log.e("MainActivity", "邮箱是" + post.getNoteemail());
            noteusername = post.getNoteusername();
            notedetail = post.getNotedetail();
            time = post.getTime();
            notehead = post.getNotehead();
            noteemail = post.getNoteemail();
            reachrname = post.getReachrname();
            reachemail = post.getReachemail();
            uid = post.getUid();//帖子发送人的ID
            id = post.getId();//帖子的ID
            notesate = post.getNotesate();
            noteimage = post.getNoteimage();
            userimage = post.getUserimage();


//                    Log.e("得到内容图片=========", noteimage);
//                    Log.e("得到头像图片=========", userimage);
//                    Log.e("SendActivity", "用户名是----" + noteusername);

            post = new Post();
            post.setNoteusername(noteusername);
            post.setNotedetail(notedetail);
            post.setTime(time);
            post.setNotehead(notehead);
            post.setNoteimage(noteimage);
            post.setUserimage(userimage);
            post.setId(id);
            post.setUid(uid);
            initlistview(post);

        }




    }



    private void initlistview(Post post) {
//        Log.e("initlistview", post.getNoteusername());
//        Log.e("initlistview", post.getNotedetail());

        String uri = "http://47.93.5.144/niceo2o/userImage/" + post.getNoteimage();
        String url = "http://47.93.5.144/niceo2o/userImage/" + post.getUserimage();
//        Log.e("initlistview", uri);
//        Log.e("initlistview", url);

        SendPool sendPool = new SendPool(post.getNoteusername(), post.getNotedetail(), post.getTime(), post.getNotehead(), uri, url, post.getId(), post.getUid(), uid1);
        send_item.add(sendPool);

        Log.e("SendActivity",send_item.toString());

    }





    private void showWaitingDialog() {
        waitingDialog = new ProgressDialog(this);
//        waitingDialog.setTitle("");
        waitingDialog.setMessage("正在加载...");
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(false);
        waitingDialog.show();
    }


}
