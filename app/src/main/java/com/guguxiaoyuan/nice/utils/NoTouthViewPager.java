package com.guguxiaoyuan.nice.utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 萌 on 2017/4/4.
 */

public class NoTouthViewPager extends ViewPager {
    public NoTouthViewPager(Context context) {
        super(context);
    }

    public NoTouthViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override//父控件不去拦截子ViewPager的事件
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    //取消ViewPager的滑动事件
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
