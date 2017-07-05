package com.guguxiaoyuan.nice.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.guguxiaoyuan.nice.R;
import com.guguxiaoyuan.nice.utils.SpUtils;

import java.util.ArrayList;



/**
 * ================================
 * 版权:Nice工作室 版权所有(C)2017
 * 作者 萌 on 2017/3/20.
 * 版本:1.0
 * 创建日期:2017/3/20 21:23
 * 描述:
 * 修改历史:
 * ================================
 */
public class GuideActivity extends AppCompatActivity {
    private static final int[] mImageIds = new int[]{R.mipmap.vp_one, R.mipmap.vp_four, R.mipmap.vp_two};
    private ArrayList<ImageView> mImageViews;
    private ViewPager vpGuide;
    private Button btnStart;
    private LinearLayout llPoint;
    private int mPointWidth;
    private View redPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        vpGuide = (ViewPager) findViewById(R.id.vp_guide);
        btnStart = (Button) findViewById(R.id.btn_start);
        llPoint = (LinearLayout) findViewById(R.id.ll_point_group);
        redPoint = findViewById(R.id.view_red_point);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpUtils.setBoolean(GuideActivity.this, "is_user_guide_showed", true);
                startActivity(new Intent(GuideActivity.this,EnterActivity.class));
                finish();
            }
        });
        initView();
        vpGuide.setAdapter(new GuideAdapter());
        vpGuide.setOnPageChangeListener(new GuidePageListener());
    }

    /**
     * 初始化界面
     */

    private void initView() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        mImageViews = new ArrayList<>();
        //初始化引导页
        for (int i = 0; i < mImageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(mImageIds[i]);
            mImageViews.add(imageView);
        }
        //初始化引导页小圆点
        for (int i = 0; i < mImageIds.length; i++) {
            View point = new View(this);
            point.setBackgroundResource(R.drawable.shape_point_gray);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30, 30);
            if (i > 0) {
                params.leftMargin = 30;
            }
            point.setLayoutParams(params);
            llPoint.addView(point);
        }
        llPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                mPointWidth = llPoint.getChildAt(1).getLeft() - llPoint.getChildAt(0).getLeft();
                Log.d("圆点距离", "" + mPointWidth);
            }
        });

    }

    /**
     * ViewPager数据适配器
     */
    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViews.get(position));
            return mImageViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    class GuidePageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int len = (int) ((position * mPointWidth) + (positionOffset * mPointWidth));
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(redPoint.getLayoutParams());
            params.leftMargin = len;
            redPoint.setLayoutParams(params);


        }

        @Override
        public void onPageSelected(int position) {
            if (position == mImageIds.length - 1) {
                btnStart.setVisibility(View.VISIBLE);
            } else {
                btnStart.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
