package com.guguxiaoyuan.nice.activity;

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

    }

    @Override
    public void onClick(View v) {

    }


}
