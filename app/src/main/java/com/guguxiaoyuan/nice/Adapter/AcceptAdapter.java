package com.guguxiaoyuan.nice.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guguxiaoyuan.nice.R;
import com.guguxiaoyuan.nice.bean.SendPool;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 萌 on 2017/5/19.
 */

public class AcceptAdapter extends ArrayAdapter<SendPool> {

    private int resourceId;
    private View view;
    private ViewHolder viewHolder;

    private final Context mContext2;


    public AcceptAdapter(Context context, int textViewResourceId, List<SendPool> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        mContext2 = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final SendPool accept_item = getItem(position);
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.usertouxiang = (CircleImageView) view.findViewById(R.id.Accept_item_iv_head_list);
            viewHolder.contentImage = (ImageView) view.findViewById(R.id.Accept_tv_rpim_content);
            viewHolder.userName = (TextView) view.findViewById(R.id.Accept_dynamic_tv_user_name);

            viewHolder.content = (TextView) view.findViewById(R.id.Accept_tv_rp_content);
            viewHolder.time = (TextView) view.findViewById(R.id.Accept_dynamic_tv_time);
            viewHolder.topic = (TextView) view.findViewById(R.id.Accept_tv_rp_topic);
            view.setTag(viewHolder);//将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();//重新获取ViewHolder
        }
        viewHolder.userName.setText(accept_item.getUsername());
        viewHolder.content.setText(accept_item.getRlcontent());
        viewHolder.time.setText(accept_item.getTime());
        viewHolder.topic.setText(accept_item.getDetail());
        Glide.with(mContext2).load(accept_item.getImageuserurl()).into(viewHolder.usertouxiang);
        Glide.with(mContext2).load(accept_item.getImagecontenturl()).into(viewHolder.contentImage);
        return view;
    }

    private class ViewHolder {

        CircleImageView usertouxiang;
        TextView userName;
        ImageView contentImage;
        TextView content;
        TextView time;
        TextView topic;


    }
}