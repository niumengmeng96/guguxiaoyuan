package com.guguxiaoyuan.nice.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.guguxiaoyuan.nice.R;
import com.guguxiaoyuan.nice.csviewpager.SwipeBackLayout;

/**
 * Created by 萌 on 2017/5/13.
 */


public class AboutusActivity extends AppCompatActivity implements View.OnClickListener {




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackLayout layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
                R.layout.swipeback, null);
        layout.attachToActivity(this);
        setContentView(R.layout.activity_aboutus);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

    }

    @Override
    public void onClick(View v) {

    }


}
