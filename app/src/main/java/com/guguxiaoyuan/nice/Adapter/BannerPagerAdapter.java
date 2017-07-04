package com.guguxiaoyuan.nice.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.guguxiaoyuan.nice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 萌 on 2017/3/30.
 */

public class BannerPagerAdapter extends PagerAdapter {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 图像列表
     */
    private List<Integer> pictureList = new ArrayList<>();
    /**
     * 默认轮播个数
     */
    public static final int FAKE_BANNER_SIZE = 10000;

    public BannerPagerAdapter(Context context, List<Integer> pictureList) {
        this.mContext = context;
        this.pictureList = pictureList;
    }

    @Override
    public int getCount() {
        return FAKE_BANNER_SIZE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.banner_item, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_banner_item);
        // 获取当前显示位置
        position %= pictureList.size();
        Glide.with(mContext).load(pictureList.get(position)).into(imageView);
//        imageView.setImageResource(pictureList.get(position));
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
