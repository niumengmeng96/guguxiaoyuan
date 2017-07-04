package com.guguxiaoyuan.nice.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guguxiaoyuan.nice.Adapter.CollectAdapter;
import com.guguxiaoyuan.nice.R;
import com.guguxiaoyuan.nice.bean.Post;
import com.guguxiaoyuan.nice.bean.Three;
import com.guguxiaoyuan.nice.csviewpager.SwipeBackLayout;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 萌 on 2017/5/18.
 */

public class CollectActivity extends AppCompatActivity {


    private List<Three> list = new ArrayList<>();
    private RecyclerView collect;
    private CollectAdapter adapter;
    private ProgressDialog waitingDialog;

    private String uid;
    private List<Post> postList;
    private Post pt;
    private String id1;
    private String detail;
    private String head;
    private String aimage;
    private String ausername;
    private String time;
    private String keep;
    private String result;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case '1':
                    waitingDialog.dismiss();
                    initView();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackLayout layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
                R.layout.swipeback, null);
        layout.attachToActivity(this);
        setContentView(R.layout.activity_collect);
        showWaitingDialog();
        initData();
        sendRequestWithOKHttp();

    }

    private void initView() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_collect);
        collect = (RecyclerView) findViewById(R.id.rv_collect);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        collect.setLayoutManager(layoutManager);
        adapter = new CollectAdapter(list);
        collect.setAdapter(adapter);
        swipeRefreshLayout.setColorSchemeResources(R.color.firstColor, R.color.secondColor, R.color.thirdColor, R.color.fourthColor);
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

    private void initData() {
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        Log.e("uid-----",uid);
    }

    private void sendRequestWithOKHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
//                            .url("http://192.168.31.249:8080/niceo2o/user_AndroidGetAllNote")
                            .url("http://47.93.5.144/niceo2o/android_AndroidSelectMyKeep?uid=" + uid)
                            .build();
                    Response response = client.newCall(request).execute();
                    //得到json数据
                    String responseData = response.body().string();
                    Log.e("收藏服务器获取RecyclerView数据:", responseData);
                    //用Gson解析json数据
                    parseJSONWitGSON(responseData);
                    Message msg=new Message();
                    msg.what='1';//
                    handler.sendMessage(msg);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void parseJSONWitGSON(String jsonData) {
        Gson gson = new Gson();
        postList = gson.fromJson(jsonData, new TypeToken<List<Post>>() {
        }.getType());
        for (Post post : postList) {
            detail = post.getDetail();
            head = post.getDetail();
            aimage = post.getAimage();
            ausername = post.getAusername();
            time = post.getTime();
            result = post.getResult();


            id1 = post.getId();

            pt = new Post();
            pt.setDetail(detail);
            pt.setHead(head);
            pt.setAimage(aimage);
            pt.setAusername(ausername);
            pt.setTime(time);
            pt.setId(id1);
            pt.setResult(result);

            Log.e("CollectActivity", ausername);

            initRecylerView(pt);
        }



    }

    private void initRecylerView(Post post) {

        String url = "http://47.93.5.144/niceo2o/userImage/" + post.getAimage();
        Log.e("CollectActivity", url);
        Three three = new Three(post.getId(), post.getDetail(), post.getAimage(), post.getAusername(), post.getHead(), post.getTime(), url, id1, uid, post.getKeep(),post.getResult());
        list.add(three);
        Log.e("CollectActivity", String.valueOf(list));
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