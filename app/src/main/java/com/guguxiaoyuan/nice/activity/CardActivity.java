package com.guguxiaoyuan.nice.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guguxiaoyuan.nice.Adapter.CommentAdapter;
import com.guguxiaoyuan.nice.R;
import com.guguxiaoyuan.nice.bean.Comment;
import com.guguxiaoyuan.nice.bean.Post1;
import com.guguxiaoyuan.nice.bean.PushResult;
import com.guguxiaoyuan.nice.csviewpager.SwipeBackLayout;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 萌 on 2017/5/6.
 */

public class CardActivity extends AppCompatActivity {


    private String name;
    private String imageurl;
    private String content;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView imageView;
    private TextView textView;
    private FloatingActionButton cardcoment;
    private LinearLayout linearLayout;
    private EditText et;
    private String uid;//登录用户人ID
    private String aid;//发布心情帖子ID
    private ListView lv;
    private List<Post1> post1List;
    private List<Comment> comments = new ArrayList<>();//listview添加数据
    private String result;
    private String time;
    private String uid1;
    private String aid1;
    private String detail;
    private String mark;
    private String uname;
    private String uimage;
    private CommentAdapter adapter;
    private boolean cc = false;
    private ImageView zan;
    private TextView zanshu;
    private ImageView shoucang;
    private LinearLayout llshoucang;
    private String keep;
    private String zanflag;
    private String id;
    private String zancount;
    private SwipeRefreshLayout swipeRefreshLayout;


    private String a="0";


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case '1':
                    initview();
                    zan = (ImageView) findViewById(R.id.im_comment_zan);
                    zanshu = (TextView) findViewById(R.id.tv_comment_zan);
                    lv = (ListView) findViewById(R.id.lv_card);
                    adapter = new CommentAdapter(CardActivity.this, R.layout.comment_item, comments);
                    lv.setAdapter(adapter);
                    a="1";
                    swipeRefreshLayout.setColorSchemeResources(R.color.firstColor, R.color.secondColor, R.color.thirdColor, R.color.fourthColor);
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
                    waitingDialog.dismiss();
                    break;
                case '2':

                    initview();
                    waitingDialog.dismiss();
                    break;

            }
        }
    };
    private ProgressDialog waitingDialog;
    private InputMethodManager imm;

    private LocalBroadcastManager localBroadcastManager;
    private MyBroadcastReceiver myBroadcastReceiver;
    private IntentFilter intentFilter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackLayout layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
                R.layout.swipeback, null);
        layout.attachToActivity(this);
        showWaitingDialog();
        initData();
        sendRequestWithOKHttp();
        LocalBroadCast();
    }
    public void LocalBroadCast(){
        localBroadcastManager=LocalBroadcastManager.getInstance(this);//获取广播实例
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.guguxiaoyuan.nice.ZAN_BROADCAST");
        myBroadcastReceiver=new MyBroadcastReceiver();
        localBroadcastManager.registerReceiver(myBroadcastReceiver, intentFilter);//注册本地监听器
    }



    public void initview() {
        setContentView(R.layout.activity_three);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        toolbar = (Toolbar) findViewById(R.id.three_item_toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.three_collapsing_toolbar);
        imageView = (ImageView) findViewById(R.id.three_content);
        textView = (TextView) findViewById(R.id.three_content_text);
        cardcoment = (FloatingActionButton) findViewById(R.id.fab_cardcomment);
        linearLayout = (LinearLayout) findViewById(R.id.ll_card);
        et = (EditText) findViewById(R.id.et_card_comment);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_comment);
        shoucang = (ImageView) findViewById(R.id.iv_three_shoucang);
        llshoucang = (LinearLayout) findViewById(R.id.ll_three_shoucang);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(name);
        Glide.with(this).load(imageurl).into(imageView);
        textView.setText(content);
        cardcoment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.VISIBLE);
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                cc = true;
                String string = "  ";
                et.setText(string);
                et.requestFocus();
                et.setSelection(string.length());

            }
        });
        if (keep.equals("1")) {
            Glide.with(CardActivity.this).load(R.mipmap.collect1).into(shoucang);
            llshoucang.setEnabled(false);
        }

    }

    public void initData() {
        Intent intent = getIntent();
        name = intent.getStringExtra("cardname");
        imageurl = intent.getStringExtra("iamgeurl");
        content = intent.getStringExtra("content");
        uid = intent.getStringExtra("uid");
        aid = intent.getStringExtra("aid");
        keep = intent.getStringExtra("keep");


    }

    public void shoucang(View view) {
        String url = "http://47.93.5.144/niceo2o/android_AndroidKeepMyLoveNote";
        HttpUtils utils = new HttpUtils();
        RequestParams params = new RequestParams("utf-8");
        params.addBodyParameter("uid", uid);
        params.addBodyParameter("aid", aid);
        utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> resposeInfo) {
                Toast.makeText(CardActivity.this, "已收藏", Toast.LENGTH_SHORT).show();
                Glide.with(CardActivity.this).load(R.mipmap.collect1).into(shoucang);
                llshoucang.setEnabled(false);

            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(CardActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sendComment(View view) {

        String detail = et.getText().toString().trim();
        if (detail.isEmpty()) {
            Toast.makeText(CardActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
        } else {
            String url = "http://47.93.5.144/niceo2o/android_AndroidSendComment";
            HttpUtils utils = new HttpUtils();
            RequestParams params = new RequestParams("utf-8");
            Log.e("aid---", aid);
            Log.e("uid----", uid);
            params.addBodyParameter("uid", uid);
            params.addBodyParameter("aid", aid);
            params.addBodyParameter("detail", detail);
            utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    Toast.makeText(CardActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                    linearLayout.setVisibility(View.GONE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    if (adapter != null) {
                        adapter.clear();
                        sendRequestWithOKHttp();
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }


                }

                @Override
                public void onFailure(HttpException error, String msg) {
                    Toast.makeText(CardActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    public void sendRequestWithOKHttp() {
        new Thread(new Runnable() {


            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://47.93.5.144/niceo2o/android_AndroidSelectAllComment?aid=" + aid + "&uid=" + uid)
                            .build();
                    Response response = client.newCall(request).execute();
                    //得到json数据
                    String responseData = response.body().string();
                    Log.e("服务器获取listview数据:", responseData);
                    //用Gson解析json数据
                    Log.e("返回Json数据长度", String.valueOf(responseData.length()));
                    if (responseData.length() == 14) {
                        Message msg = new Message();
                        msg.what = '2';
                        handler.sendMessage(msg);
                    } else {
                        parseJSONWithGSON(responseData);
                        Message msg = new Message();
                        msg.what = '1';
                        handler.sendMessage(msg);

                    }


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
        post1List = gson.fromJson(jsonData, new TypeToken<List<Post1>>() {
        }.getType());
        for (Post1 post1 : post1List) {
            result = post1.getResult();
            time = post1.getTime();
            uid1 = post1.getUid();
            aid1 = post1.getAid();
            detail = post1.getDetail();
            mark = post1.getMark();
            uname = post1.getUname();
            uimage = post1.getUimage();
            zanflag = post1.getZanflag();
            id = post1.getId();
            zancount = post1.getZancount();

            Log.e("zan------", zancount);
            Post1 p = new Post1();
            p.setDetail(detail);
            p.setAid(aid1);
            p.setMark(mark);
            p.setResult(result);
            p.setUimage(uimage);
            p.setTime(time);
            p.setUid(uid1);
            p.setUname(uname);
            p.setZanflag(zanflag);
            p.setId(id);
            p.setZancount(zancount);
            initListView(p);
        }

    }

    public void initListView(Post1 p) {
        int count = Integer.parseInt(p.getZancount());
        String url = "http://47.93.5.144/niceo2o/userImage/" + p.getUimage();
        Comment msg = new Comment(p.getAid(), p.getUid(), p.getUname(), p.getDetail(), p.getTime(), url, p.getZanflag(), p.getId(), count);
        comments.add(msg);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 退出
     *
     * @param keyCode
     * @param event
     * @return
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (cc) {
                linearLayout.setVisibility(View.GONE);
                cc = false;
                ExitApp();
            } else
                this.finish();
        }
        return false;
    }

    private long exitTime = 0;

    public void ExitApp() {

        if ((System.currentTimeMillis() - exitTime) > 2000) {
            exitTime = System.currentTimeMillis();
        } else {
            this.finish();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        XGPushClickedResult click = XGPushManager.onActivityStarted(this);
        if (click != null) {
            String cusContent = click.getCustomContent();
            Gson gson = new Gson();
            PushResult result = gson.fromJson(cusContent, PushResult.class);
            String uid = result.getUid();
            String id = result.getId();

            Log.e("AboutasActivity", cusContent);
            Log.e("AboutasActivity", String.valueOf(uid));
            Log.e("AboutasActivity", id);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        XGPushManager.onActivityStoped(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (a.equals("1")){
            adapter.clearList();
        }
        localBroadcastManager.unregisterReceiver(myBroadcastReceiver);
    }

    private void showWaitingDialog() {
        waitingDialog = new ProgressDialog(this);
//        waitingDialog.setTitle("");
        waitingDialog.setMessage("正在加载...");
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(false);
        waitingDialog.show();
    }

    class MyBroadcastReceiver extends BroadcastReceiver {
        private Context mContext;

//        private Context mContext;

        @Override
        public void onReceive(Context context, Intent intent) {
            mContext = context;
//            Toast.makeText(context, "广播被接受了", Toast.LENGTH_SHORT).show();
            String uid=intent.getStringExtra("uid");
            String aid=intent.getStringExtra("aid");
            String pid=intent.getStringExtra("pid");
            Log.e("评论uid",uid);
            Log.e("评论aid",aid);
            Log.e("评论pid",pid);
            String url = "http://47.93.5.144/niceo2o/android_AndroidAddNoteZan";
            HttpUtils utils = new HttpUtils();
            RequestParams params = new RequestParams("utf-8");
            params.addBodyParameter("uid",uid);
            params.addBodyParameter("aid",aid);
            params.addBodyParameter("pid", pid);
            utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> resposeInfo) {
//                    Toast.makeText(mContext, "点赞成功", Toast.LENGTH_SHORT).show();
                    if (adapter != null) {
                        adapter.clear();
                        sendRequestWithOKHttp();
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onFailure(HttpException error, String msg) {
                    Toast.makeText(mContext, "点赞失败", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


}
