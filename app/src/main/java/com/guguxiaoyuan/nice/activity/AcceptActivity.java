package com.guguxiaoyuan.nice.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guguxiaoyuan.nice.Adapter.AcceptAdapter;
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

public class AcceptActivity extends AppCompatActivity {
    private List<SendPool> acceptlist = new ArrayList<>();//listview中添加数据
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
    private String noteimage;
    private String userimage;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog waitingDialog;
    private ListView listView;
    private AcceptAdapter adapter;
    private String uid1;
    private Post p;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case '1':
                    initView();

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            SendPool sendPool = acceptlist.get(position);
                            Intent intent = new Intent();
                            intent.setClass(AcceptActivity.this, ListActivity.class);
                            intent.putExtra("noteusername", sendPool.getUsername());
                            intent.putExtra("notedetail", sendPool.getRlcontent());
                            intent.putExtra("notehead", sendPool.getDetail());
                            intent.putExtra("time", sendPool.getTime());
                            intent.putExtra("uri", sendPool.getImagecontenturl());
                            intent.putExtra("url", sendPool.getImageuserurl());
                            intent.putExtra("nid", sendPool.getNid());
                            intent.putExtra("uid", sendPool.getUid());
                            intent.putExtra("nuid", sendPool.getNuid());
                            startActivityForResult(intent, 2);
                        }
                    });
                    waitingDialog.dismiss();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackLayout layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
                R.layout.swipeback, null);
        layout.attachToActivity(this);
        showWaitingDialog();
        initData();
        sendRequestWithOKHttp();
    }


    private void initView() {
        setContentView(R.layout.activity_accept);
        listView = (ListView) findViewById(R.id.lv_accept);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_accept);
        adapter = new AcceptAdapter(AcceptActivity.this, R.layout.accept_item, acceptlist);
        listView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //设置2秒的时间来执行以下事件
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (adapter != null) {
                            adapter.clear();
                            adapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                        }

                    }
                }, 2000);
            }
        });

    }

    private void initData() {
        Intent intent1 = getIntent();
        uid1 = intent1.getStringExtra("uid1");
        Log.e("AcceptActivity---uid", uid1);
    }


//


    /**
     * 发送请求
     */
    private void sendRequestWithOKHttp() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
//                            .url("http://192.168.31.249:8080/niceo2o/user_AndroidGetAllNote")
                            .url("http://47.93.5.144/niceo2o/user_AndroidSelectReachNote?uid=" + uid1)
                            .build();
                    Response response = client.newCall(request).execute();
                    //得到json数据
                    String responseData = response.body().string();

                    Log.e("AcceptActivity-initView", responseData);
                    //用Gson解析json数据
                    parseJSONWithGSON(responseData);
                    Message msg = new Message();
                    msg.what = '1';
                    handler.sendMessage(msg);
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
//            Log.e("AcceptActivity", "用户名是" + post.getNoteusername());
//            Log.e("AcceptActivity", "主题是" + post.getNotedetail());
//            Log.e("AcceptActivity", "时间是" + post.getTime());
//            Log.e("AcceptActivity", "内容是" + post.getNotehead());
//            Log.e("AcceptActivity", "邮箱是" + post.getNoteemail());
//            Log.e("AcceptActivity", "读者邮箱是" + post.getReachemail());
//            Log.e("AcceptActivity", "读者名字是" + post.getReachrname());
//            Log.e("AcceptActivity", " uid是" +post.getUid());
//            Log.e("AcceptActivity", " id是" +  post.getId());
//            Log.e("AcceptActivity", " notesate是" +post.getNotesate());
//            Log.e("AcceptActivity", " noteimage是" +post.getNoteimage());
//            Log.e("AcceptActivity", " userimage是" + post.getUserimage());
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
//                    Log.e("AcceptActivity", "用户名是----" + noteusername);
            p = new Post();
            p.setNoteusername(noteusername);
            p.setNotedetail(notedetail);
            p.setTime(time);
            p.setNotehead(notehead);
            p.setNoteimage(noteimage);
            p.setUserimage(userimage);
            p.setId(id);
            p.setUid(uid);
            initlistview(p);
        }


    }

    private void initlistview(Post post) {

//        Log.e("AcceptActivity", "用户名是" + post.getNoteusername());
//        Log.e("AcceptActivity", "主题是" + post.getNotedetail());
//        Log.e("AcceptActivity", "时间是" + post.getTime());
//        Log.e("AcceptActivity", "内容是" + post.getNotehead());
//        Log.e("AcceptActivity", "邮箱是" + post.getNoteemail());
//        Log.e("AcceptActivity", "读者邮箱是" + post.getReachemail());
//        Log.e("AcceptActivity", "读者名字是" + post.getReachrname());
//        Log.e("AcceptActivity", " uid是" +post.getUid());
//        Log.e("AcceptActivity", " id是" +  post.getId());
//        Log.e("AcceptActivity", " notesate是" +post.getNotesate());
//        Log.e("AcceptActivity", " noteimage是" +post.getNoteimage());
//        Log.e("AcceptActivity", " userimage是" + post.getUserimage());
//        String uri = "http://10.212.28.111:8080/niceo2o/userImage/" + post.getNoteimage();
//        String url = "http://10.212.28.111:8080/niceo2o/userImage/" + post.getUserimage();
        String uri = "http://47.93.5.144/niceo2o/userImage/" + post.getNoteimage();
        String url = "http://47.93.5.144/niceo2o/userImage/" + post.getUserimage();

        SendPool msg = new SendPool(post.getNoteusername(), post.getNotedetail(), post.getTime(), post.getNotehead(), uri, url, post.getId(), post.getUid(), uid1);
        acceptlist.add(msg);



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
