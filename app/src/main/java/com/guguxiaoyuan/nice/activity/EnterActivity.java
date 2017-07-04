package com.guguxiaoyuan.nice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.guguxiaoyuan.nice.R;
import com.guguxiaoyuan.nice.utils.ActivityCollector;

/**
 * Created by 萌 on 2017/4/26.
 */

public class EnterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_enter);


    }


    public void enter(View v) {


        startActivity(new Intent(this, LoginActivity.class));
        finish();

    }

    public void registered(View v) {

        startActivity(new Intent(this, RegisteredActivity.class));


    }

}
