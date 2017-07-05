package com.guguxiaoyuan.nice.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.guguxiaoyuan.nice.R;
import com.guguxiaoyuan.nice.utils.SpUtils;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;


/**
 * ================================
 * 版权:Nice工作室 版权所有(C)2017
 * 作者 萌 on 2017/3/20.
 * 版本:1.0
 * 创建日期:2017/3/20 21:05
 * 描述:
 * 修改历史:
 * ================================
 */

public class SplashActivity extends Activity {
    private RelativeLayout rlRoot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        rlRoot= (RelativeLayout) findViewById(R.id.rl_root);
        startAnim();
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

    }

    /**
     * 开启动画
     */
    private void startAnim() {
//        //动画集合
//        AnimationSet set = new AnimationSet(false);
//        //旋转动画
//        RotateAnimation rotate = new RotateAnimation(0, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
//        rotate.setDuration(1000);//动画时间
//        rotate.setFillAfter(true);//保持动画状态
//
//
//        //缩放动画
//        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
//        scale.setDuration(1000);//动画时间
//        scale.setFillAfter(true);//保持动画状态

        //渐变动画
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(1500);//动画时间
        alpha.setFillAfter(true);//保持动画状态

//
//        set.addAnimation(rotate);
//        set.addAnimation(scale);
//        set.addAnimation(alpha);

        alpha.setAnimationListener(new Animation.AnimationListener() {
            //动画开始
            @Override
            public void onAnimationStart(Animation animation) {

            }

            //动画结束
            @Override
            public void onAnimationEnd(Animation animation) {
                jumpNextPage();
            }


            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        rlRoot.startAnimation(alpha);


    }
    private void jumpNextPage() {
        //判断之前有没有显示过新手引导页

        boolean userGuide = SpUtils.getBoolean(this,"is_user_guide_showed",false);
        if (!userGuide) {
            //跳转新手引导页
            startActivity(new Intent(SplashActivity.this, GuideActivity.class));

        } else {
            startActivity(new Intent(SplashActivity.this, EnterActivity.class));

        }
        finish();

    }
}
