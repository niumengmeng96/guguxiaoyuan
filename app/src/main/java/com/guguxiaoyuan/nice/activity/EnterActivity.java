package com.guguxiaoyuan.nice.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


    }


    public void enter(View v) {


        startActivity(new Intent(this, LoginActivity.class));
        finish();

    }

    public void registered(View v) {

        startActivity(new Intent(this, RegisteredActivity.class));


    }

}
