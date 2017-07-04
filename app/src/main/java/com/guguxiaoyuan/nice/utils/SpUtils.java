package com.guguxiaoyuan.nice.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * ================================
 * 版权:Nice工作室 版权所有(C)2017
 * 作者 萌 on 2017/3/13.
 * 版本:1.0
 * 创建日期:2017/3/13 18:04
 * 描述: SharedPreferences封装工具类
 * 修改历史:
 * ================================
 */

public class SpUtils {
    public static final String PREF_NAME = "config";

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        //判断之前有没有显示过新手引导
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);

        return sp.getBoolean(key, defaultValue);
    }

    public static void setBoolean(Context context, String key, boolean Value) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);

       sp.edit().putBoolean(key, Value).commit();
    }
}
