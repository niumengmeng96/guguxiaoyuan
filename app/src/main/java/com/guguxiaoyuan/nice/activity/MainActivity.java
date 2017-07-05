package com.guguxiaoyuan.nice.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guguxiaoyuan.nice.Adapter.BannerPagerAdapter;
import com.guguxiaoyuan.nice.Adapter.RmAdapter;
import com.guguxiaoyuan.nice.Adapter.ThreeAdapter;
import com.guguxiaoyuan.nice.R;
import com.guguxiaoyuan.nice.bean.BannerTimerTask;
import com.guguxiaoyuan.nice.bean.Post;
import com.guguxiaoyuan.nice.bean.PushResult;
import com.guguxiaoyuan.nice.bean.ReleasePool;
import com.guguxiaoyuan.nice.bean.Three;
import com.guguxiaoyuan.nice.utils.ActivityCollector;
import com.guguxiaoyuan.nice.utils.IndicatorView;
import com.kevin.wraprecyclerview.WrapRecyclerView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;
import com.nanchen.compresshelper.CompressHelper;
import com.nanchen.compresshelper.FileUtil;
import com.squareup.picasso.Picasso;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.guguxiaoyuan.nice.R.layout.three;

public class MainActivity extends BaseActivity {

    private com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;//主页面Viewpager
    private List<View> views;//主页面viewpager添加页面
    private List<ReleasePool> releasePoolList = new ArrayList<>();//listview中添加数据

    private View view1;
    private View view3;
    private View view4;
    private FloatingActionButton fab;//发布信息
    private ListView listView;//单子池listview
    private RmAdapter adapter;//单子池适配器
    private SwipeRefreshLayout swiperereshlayout;//下拉刷新
    private ViewPager titleVP;//one页面viewpager

//    private Toolbar toolbar;
    /**
     * Viewpager轮播
     */
    private ViewPager mViewPager;//轮播图
    private IndicatorView mIndicatorView;//指示器
    private BannerPagerAdapter mBannerPagerAdapter;//适配器
    private List<Integer> pictureList = new ArrayList<>();//图片资源
    public static final int AUTOBANNER_CODE = 0X1001;//轮播图自动轮播消息
    private int mBannerPosition;//当前轮播图位置
    private Timer timer = new Timer();//自动轮播计时器
    private BannerTimerTask mBannerTimerTask;//自动轮播任务
    private boolean mIsUserTouched = false;//用户当前是否点击轮播图


    private boolean initview = true;


    private IndicatorView indicatorView;

    //从服务器获取的信息
    private List<Post> appList;
    private String noteusername;
    private String notedetail;
    private String time;
    private String notehead;
    private String noteemail;
    private String reachrname;
    private String reachemail;
    private String uid;//帖子发送人ID
    private String id;//帖子ID
    private String notesate;
    private Intent intent;
    private String image;
    private Post pt;
    private Post post;
    private String noteusername1;
    private String noteemail1;
    private String uid1;//登录用户ID
    private String noteimage;
    private String userimage;
    private ProgressDialog waitingDialog;
    private CircleImageView fourHp;

    private File TxImage;
    private File newFile;

    /**
     * three
     */
    private DrawerLayout mDrawerLayout;
    private List<Three> threeList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DrawerLayout mDrawerLayout1;
    private SwipeRefreshLayout srlthree;
    private ThreeAdapter threeAdapter;
    private WrapRecyclerView rvOne;
    private View headerView;
    private View viewById;
    private CircleImageView Menutouxiang;
    private View viewById1;
    private TextView menuName;
    private LinearLayout onerun;
    private LinearLayout twocar;
    private String id1;//心情贴ID

    private ActionBar actionBar;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case '1':

                    listView.setAdapter(adapter);
                    break;
                case '2':
                    waitingDialog.dismiss();
                    threeInitView();
                    break;

            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showWaitingDialog();
        initViewpager();//主页面viewpager
        initBottomNavigationView();//主页面页卡
        initFloatingActionButton();
        initMenu();
        onesendRequestWithOKHttp();//向服务器发送数据
        oneInitView();
        threesendRequestWithOKHttp();
        fourInitView();
        // 判断是否从推送通知栏打开的
        XGPushClickedResult click = XGPushManager.onActivityStarted(this);
        if (click != null) {
            //从推送通知栏打开-Service打开Activity会重新执行Laucher流程
            //查看是不是全新打开的面板
            if (isTaskRoot()) {
                return;
            }
            //如果有面板存在则关闭当前的面板
            finish();
        }
        ActivityCollector.addActivity(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        XGPushClickedResult click = XGPushManager.onActivityStarted(this);
        if (click != null) {
            String cusContent = click.getCustomContent();
            if (cusContent != null) {
                Gson gson = new Gson();
                PushResult pushResult = gson.fromJson(cusContent, PushResult.class);
                String state = (String) pushResult.getState();
                if (state.equals("1")) {
                    Intent intent1 = new Intent("com.exaple.broadcastbestpractice.FORCE_OFFLINE");
                    Log.e("发送自定义广播", state);
                    sendBroadcast(intent1);
                }
                Log.e("Main推送接收的Gson数据", state);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        XGPushManager.onActivityStoped(this);
    }

    private void initMenu() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mDrawerLayout1 = (DrawerLayout) findViewById(R.id.dl_main);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.mainmunu);
        }


        navigationView.setCheckedItem(R.id.main);
        headerView = navigationView.getHeaderView(0);
        viewById = headerView.findViewById(R.id.menu_touxiang);
        viewById1 = headerView.findViewById(R.id.menu_name);
        Menutouxiang = (CircleImageView) viewById;
        menuName = (TextView) viewById1;
        menuName.setText(noteusername1);
        Glide.with(this).load("http://47.93.5.144/niceo2o/userImage/" + image).into(Menutouxiang);
        Menutouxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(Intent.ACTION_GET_CONTENT);
                intent5.setType("image/*");
                startActivityForResult(intent5, 2);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_exitlogin:
                        ActivityCollector.finishAll();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));

                        break;
                    case R.id.nav_fankui:
                        startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
                        navigationView.setCheckedItem(R.id.main);
                        break;
                    case R.id.nav_guanyu:
                        startActivity(new Intent(MainActivity.this, AboutusActivity.class));
                        navigationView.setCheckedItem(R.id.main);
                        break;
                    case R.id.nav_shoucang:
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, CollectActivity.class);
                        intent.putExtra("uid", uid1);
                        startActivity(intent);
                        navigationView.setCheckedItem(R.id.main);
                        break;


                }
                mDrawerLayout1.closeDrawers();
                return true;
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout1.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    private void threeInitView() {
        srlthree = (SwipeRefreshLayout) view3.findViewById(R.id.srl_three);
        recyclerView = (RecyclerView) view3.findViewById(R.id.three_recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        threeAdapter = new ThreeAdapter(threeList);
        recyclerView.setAdapter(threeAdapter);
        srlthree.setColorSchemeResources(R.color.firstColor, R.color.secondColor, R.color.thirdColor, R.color.fourthColor);
        srlthree.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                //设置2秒的时间来执行以下事件
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (threeAdapter != null) {
                            threeAdapter.clear();
                            threesendRequestWithOKHttp();
                            threeAdapter.notifyDataSetChanged();
                            srlthree.setRefreshing(false);
                        }

                    }
                }, 2000);
            }
        });
    }

    Handler bannerHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            // 当用户点击时,不进行轮播
            if (!mIsUserTouched) {
                // 获取当前的位置
                mBannerPosition = mViewPager.getCurrentItem();
                // 更换轮播图
                mBannerPosition = (mBannerPosition + 1) % mBannerPagerAdapter.FAKE_BANNER_SIZE;
                mViewPager.setCurrentItem(mBannerPosition);
            }
            return true;
        }
    });


    /**
     * 开始轮播
     */
    private void startBannerTimer() {
        if (timer == null) {
            timer = new Timer();
        }
        if (mBannerTimerTask != null) {
            mBannerTimerTask.cancel();
        }
        mBannerTimerTask = new BannerTimerTask(bannerHandler);
        if (timer != null && mBannerTimerTask != null) {
            // 循环5秒执行
            timer.schedule(mBannerTimerTask, 4000, 4000);
        }
    }


    /**
     * 销毁时,关闭任务,防止异常
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mBannerTimerTask) {
            mBannerTimerTask = null;
        }
    }


    /**
     * 发送请求
     */
    private void onesendRequestWithOKHttp() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
//                            .url("http://192.168.31.249:8080/niceo2o/user_AndroidGetAllNote")
                            .url("http://47.93.5.144/niceo2o/user_AndroidGetAllNote")
                            .build();
                    Response response = client.newCall(request).execute();
                    //得到json数据
                    String responseData = response.body().string();
                    Log.e("服务器获取ListView数据:", responseData);
                    //用Gson解析json数据
                    oneparseJSONWithGSON(responseData);
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
     * 发送请求
     */
    private void threesendRequestWithOKHttp() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
//                            .url("http://192.168.31.249:8080/niceo2o/user_AndroidGetAllNote")
                            .url("http://47.93.5.144/niceo2o/android_AndroidSelectAllMoodAadComment?uid=" + uid1)
                            .build();
                    Response response = client.newCall(request).execute();
                    //得到json数据
                    String responseData = response.body().string();
                    Log.e("服务器获取RecyclerView数据:", responseData);
                    //用Gson解析json数据
                    threeparseJSONWithGSON(responseData);
                    Message msg=new Message();
                    msg.what='2';
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
    private void oneparseJSONWithGSON(String jsonData) {
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


//            Log.e("得到内容图片=========", noteimage);
//            Log.e("得到头像图片=========", userimage);
//            Log.e("MainActivity", "用户名是----" + noteusername);
            pt = new Post();
            pt.setNoteusername(noteusername);
            pt.setNotedetail(notedetail);
            pt.setTime(time);
            pt.setNotehead(notehead);
            pt.setNoteimage(noteimage);
            pt.setUserimage(userimage);
            pt.setId(id);
            pt.setUid(uid);
            initListView(pt);
        }


    }

    /**
     * 解析Gson
     *
     * @param jsonData
     */
    private void threeparseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        appList = gson.fromJson(jsonData, new TypeToken<List<Post>>() {
        }.getType());

        for (Post post : appList) {
            String detail = post.getDetail();
            String head = post.getDetail();
            String aimage = post.getAimage();
            String ausername = post.getAusername();
            String time = post.getTime();
            String keep = post.getKeep();
            //心情帖子的ID
            id1 = post.getId();

            pt = new Post();
            pt.setDetail(detail);
            pt.setHead(head);
            pt.setAimage(aimage);
            pt.setAusername(ausername);
            pt.setTime(time);
            pt.setId(id1);
            pt.setKeep(keep);

            initRecyclerView(post);

        }


    }

    private void oneInitView() {
        listView = (ListView) view1.findViewById(R.id.lv_one);
        swiperereshlayout = (SwipeRefreshLayout) view1.findViewById(R.id.srl_one);
        adapter = new RmAdapter(MainActivity.this, R.layout.releasepool_item, releasePoolList);


        //把viewpager加入listview头部
        View vHead = View.inflate(this, R.layout.lv_header, null);
        titleVP = (ViewPager) vHead.findViewById(R.id.vp_one);
        indicatorView = (IndicatorView) vHead.findViewById(R.id.idv_banner);
        //跑腿点击事件
        onerun = (LinearLayout) vHead.findViewById(R.id.ll_one1);
        onerun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(MainActivity.this, ReleaseActivity.class);
                intent1.putExtra("noteusername", noteusername1);
                intent1.putExtra("noteemail", noteemail1);
                intent1.putExtra("uid", uid1);
//                Log.e("====================",noteusername);
//                Log.e("====================",noteemail);
//                Log.e("====================",noteusername);
                startActivityForResult(intent1, 1);
            }
        });
        //拼车点击事件
        twocar = (LinearLayout) vHead.findViewById(R.id.ll_one2);
        twocar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(MainActivity.this, CarpoolingActivity.class);
                intent1.putExtra("noteusername", noteusername1);
                intent1.putExtra("noteemail", noteemail1);
                intent1.putExtra("uid", uid1);
//                Log.e("====================",noteusername);
//                Log.e("====================",noteemail);
//                Log.e("====================",noteusername);
                startActivityForResult(intent1, 1);
            }
        });


        listView.addHeaderView(vHead);

//        Log.e("++++++++++++++++++", String.valueOf(initview));
        mViewPager = (ViewPager) view1.findViewById(R.id.vp_one);
        mIndicatorView = (IndicatorView) view1.findViewById(R.id.idv_banner);
        mBannerPagerAdapter = new BannerPagerAdapter(this, pictureList);
        mViewPager.setAdapter(mBannerPagerAdapter);
        //添加Viewpager图片
        pictureList.add(R.mipmap.vp_44);
        pictureList.add(R.mipmap.vp_11);
        pictureList.add(R.mipmap.vp_22);
        pictureList.add(R.mipmap.vp_33);
        pictureList.add(R.mipmap.vp_55);
//        pictureList.add(R.mipmap.vp_three);
        //添加指导图标
        mIndicatorView.setViewPager(pictureList.size(), mViewPager);
        //设置默认起始位置, 使开始可以向左边滑动
        mViewPager.setCurrentItem(pictureList.size() * 100);
        mIndicatorView.setOnPageChangeListener(new IndicatorView.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN
                        || action == MotionEvent.ACTION_MOVE) {
                    mIsUserTouched = true;
                } else if (action == MotionEvent.ACTION_UP) {
                    mIsUserTouched = false;
                }
                return false;
            }
        });
        startBannerTimer();


        swiperereshlayout.setColorSchemeResources(R.color.firstColor, R.color.secondColor, R.color.thirdColor, R.color.fourthColor);
        swiperereshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //设置2秒的时间来执行以下事件
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (adapter != null) {
                            adapter.clear();
                            onesendRequestWithOKHttp();
                            adapter.notifyDataSetChanged();
                            swiperereshlayout.setRefreshing(false);
                        }

                    }
                }, 2000);
            }
        });
    }


    /**
     * 加载单子池Listview页面
     *
     * @param post
     */
    public void initListView(Post post) {
        String uri = "http://47.93.5.144/niceo2o/userImage/" + post.getNoteimage();
        String url = "http://47.93.5.144/niceo2o/userImage/" + post.getUserimage();
//        String uri = "http://192.168.31.249:8080/niceo2o/userImage/" + post.getNoteimage();
//        String url = "http://192.168.31.249:8080/niceo2o/userImage/" + post.getUserimage();
        Log.e("Main --uri", uri);
        ReleasePool msg = new ReleasePool(post.getNoteusername(), post.getNotedetail(), post.getTime(), post.getNotehead(), uri, url, post.getId(), post.getUid(), uid1);
        releasePoolList.add(msg);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReleasePool releasePool = releasePoolList.get(position - 1);
                intent = new Intent();
                intent.setClass(MainActivity.this, ListActivity.class);
                intent.putExtra("noteusername", releasePool.getUsername());
                intent.putExtra("notedetail", releasePool.getRlcontent());
                intent.putExtra("notehead", releasePool.getDetail());
                intent.putExtra("time", releasePool.getTime());
                intent.putExtra("uri", releasePool.getImagecontenturl());
                intent.putExtra("url", releasePool.getImageuserurl());
                intent.putExtra("nid", releasePool.getNid());
                intent.putExtra("uid", uid1);
                intent.putExtra("nuid", releasePool.getNuid());
                startActivityForResult(intent, 2);
            }
        });


    }

    /**
     * 加载Card池RecyclerView页面
     *
     * @param post
     */
    public void initRecyclerView(Post post) {
        String url = "http://47.93.5.144/niceo2o/userImage/" + post.getAimage();
        Log.e("Main --", url);
        Three three = new Three(post.getId(), post.getDetail(), post.getAimage(), post.getAusername(), post.getHead(), post.getTime(), url, id1, uid1, post.getKeep(), "2");
        threeList.add(three);
    }

    public void initViewpager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        views = new ArrayList<View>();
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.one, null);
        view3 = inflater.inflate(three, null);
        view4 = inflater.inflate(R.layout.four, null);
        views.add(view1);
        views.add(view3);
        views.add(view4);
        viewPager.setAdapter(new MyViewPagerAdapter(views));
        viewPager.setCurrentItem(0);

        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

//            Toast.makeText(MainActivity.this, "您选择了"+ viewPager.getCurrentItem()+"页卡", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    public void initBottomNavigationView() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);

        int[] image = {R.drawable.ic_mic_black_24dp, R.drawable.ic_favorite_black_24dp,
                R.drawable.ic_book_black_24dp, R.drawable.github_circle,R.mipmap.mainhome};
        int[] color = {ContextCompat.getColor(this, R.color.firstColor), ContextCompat.getColor(this, R.color.secondColor),
                ContextCompat.getColor(this, R.color.thirdColor), ContextCompat.getColor(this, R.color.fourthColor)};

        if (bottomNavigationView != null) {
            bottomNavigationView.isWithText(false);

            // bottomNavigationView.activateTabletMode();
            bottomNavigationView.isColoredBackground(true);
            bottomNavigationView.setTextActiveSize(getResources().getDimension(R.dimen.text_active));
            bottomNavigationView.setTextInactiveSize(getResources().getDimension(R.dimen.text_inactive));
            bottomNavigationView.setItemActiveColorWithoutColoredBackground(ContextCompat.getColor(this, R.color.firstColor));
//            bottomNavigationView.setFont(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Noh_normal.ttf"));
        }

        BottomNavigationItem bottomNavigationItem = new BottomNavigationItem
                ("主页", color[0], image[4]);

        BottomNavigationItem bottomNavigationItem2 = new BottomNavigationItem
                ("趣文", color[0], image[1]);
        BottomNavigationItem bottomNavigationItem3 = new BottomNavigationItem
                ("个人", color[0], image[3]);


        bottomNavigationView.addTab(bottomNavigationItem);
        bottomNavigationView.addTab(bottomNavigationItem2);
        bottomNavigationView.addTab(bottomNavigationItem3);


        bottomNavigationView.setOnBottomNavigationItemClickListener(new OnBottomNavigationItemClickListener() {
            @Override
            public void onNavigationItemClick(int index) {
                switch (index) {
                    case 0:
                        viewPager.setCurrentItem(0);
                        break;
                    case 1:
                        viewPager.setCurrentItem(1);

                        break;
                    case 2:
                        viewPager.setCurrentItem(2);

                        break;

                }
            }
        });
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private List<View> mListViews;

        public MyViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;//构造方法，参数是我们的页卡，这样比较方便。
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListViews.get(position));//删除页卡
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
            container.addView(mListViews.get(position), 0);//添加页卡
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return mListViews.size();//返回页卡的数量
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;//官方提示这样写
        }
    }


    public void initFloatingActionButton() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        noteusername1 = bundle.getString("noteusername");
        noteemail1 = bundle.getString("noteemail");
        uid1 = bundle.getString("uid");
        image = bundle.getString("image");
        fab = (FloatingActionButton) view1.findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(MainActivity.this, ReleaseActivity.class);
                intent1.putExtra("noteusername", noteusername1);
                intent1.putExtra("noteemail", noteemail1);
                intent1.putExtra("uid", uid1);
//                Log.e("====================",noteusername);
//                Log.e("====================",noteemail);
//                Log.e("====================",noteusername);
                startActivityForResult(intent1, 1);

            }
        });
        FloatingActionButton threefab = (FloatingActionButton) view3.findViewById(R.id.three_fab);
        threefab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent();
                intent3.setClass(MainActivity.this, ArticleActivity.class);
                intent3.putExtra("noteusername", noteusername1);
                startActivityForResult(intent3, 3);
            }
        });
    }

    private void fourInitView() {
        LinearLayout llMyInfo = (LinearLayout) view4.findViewById(R.id.llMyInfo);
        LinearLayout shoudan = (LinearLayout) view4.findViewById(R.id.ll_shoudan);
        LinearLayout fadan = (LinearLayout) view4.findViewById(R.id.ll_fadan);
        LinearLayout shoucang = (LinearLayout) view4.findViewById(R.id.ll_shoucang);
//        String uri = "http://192.168.31.249:8080/niceo2o/userImage/" + image;
        String uri = "http://47.93.5.144/niceo2o/userImage/" + image;
        fourHp = (CircleImageView) view4.findViewById(R.id.ivHeader);
        Picasso.with(MainActivity.this).load(uri).error(R.mipmap.logo).into(fourHp);
        TextView FourName = (TextView) view4.findViewById(R.id.tvName);
        TextView FourEmail = (TextView) view4.findViewById(R.id.tvEmail);
        FourName.setText(noteusername1);
        FourEmail.setText(noteemail1);
        llMyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(Intent.ACTION_GET_CONTENT);
                intent4.setType("image/*");
                startActivityForResult(intent4, 2);

            }
        });
        shoudan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Accept = new Intent(MainActivity.this, AcceptActivity.class);
                Accept.putExtra("uid1", uid1);
                startActivity(Accept);
            }
        });
        fadan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Send = new Intent(MainActivity.this, SendActivity.class);
                Send.putExtra("uid1", uid1);
                startActivity(Send);
            }
        });
        shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shoucang = new Intent(MainActivity.this, CollectActivity.class);
                shoucang.putExtra("uid", uid1);
                startActivity(shoucang);
            }
        });


    }


    /**
     * 返回刷新
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1://viewpager1返回刷新
                if (resultCode == RESULT_OK) {


                    //设置2秒的时间来执行以下事件
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (adapter != null) {
                                adapter.clear();
                                onesendRequestWithOKHttp();
                                adapter.notifyDataSetChanged();
                                swiperereshlayout.setRefreshing(false);
                            }

                        }
                    }, 2000);


                    Toast.makeText(MainActivity.this, "刷新啦", Toast.LENGTH_SHORT).show();
                }

                break;

            case 2://上传头像
                if (resultCode == RESULT_OK) {


                    try {
                        TxImage = FileUtil.getTempFile(this, data.getData());
                        compress(TxImage);
                        Log.e("++++++++++++++++", String.valueOf(TxImage));
                        Glide.with(MainActivity.this).load(newFile).into(Menutouxiang);
                        Glide.with(MainActivity.this).load(newFile).into(fourHp);
                        String url = "http://47.93.5.144/niceo2o/user_AndroidChangeImage";
                        HttpUtils utils = new HttpUtils();
                        RequestParams params = new RequestParams("utf-8");
                        params.addBodyParameter("file", newFile, "image/jpg");
                        params.addBodyParameter("uid", uid1);
                        utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                Toast.makeText(MainActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(HttpException error, String msg) {
                                Toast.makeText(MainActivity.this,"网络异常上传失败",Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                break;
            case 3://viewpager2返回刷新
                if (resultCode == RESULT_OK) {


                    //设置2秒的时间来执行以下事件
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (threeAdapter != null) {
                                threeAdapter.clear();
                                threesendRequestWithOKHttp();
                                threeAdapter.notifyDataSetChanged();
                                srlthree.setRefreshing(false);
                            }
                        }
                    }, 2000);


                    Toast.makeText(MainActivity.this, "刷新啦", Toast.LENGTH_SHORT).show();
                }
                break;



        }
    }

    private void compress(File file) {
        File oldfile = file;
        newFile = CompressHelper.getDefault(this).compressToFile(oldfile);
    }


    private void showWaitingDialog() {
        waitingDialog = new ProgressDialog(MainActivity.this);
//        waitingDialog.setTitle("");
        waitingDialog.setMessage("亲,咕咕正在飞来...");
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

