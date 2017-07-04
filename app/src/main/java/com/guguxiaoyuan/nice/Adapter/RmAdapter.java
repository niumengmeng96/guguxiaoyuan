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
import com.guguxiaoyuan.nice.bean.ReleasePool;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 萌 on 2017/4/4.
 */

public class RmAdapter extends ArrayAdapter<ReleasePool> {

    private int resourceId;
    private View view;
    private ViewHolder viewHolder;

    private final Context mContext;


    public RmAdapter(Context context, int textViewResourceId, List<ReleasePool> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ReleasePool releasePool = getItem(position);
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.usertouxiang = (CircleImageView) view.findViewById(R.id.item_iv_head_list);
            viewHolder.userName = (TextView) view.findViewById(R.id.dynamic_tv_user_name);
            viewHolder.contentImage = (ImageView) view.findViewById(R.id.tv_rpim_content);
            viewHolder.content = (TextView) view.findViewById(R.id.tv_rp_content);
            viewHolder.time = (TextView) view.findViewById(R.id.dynamic_tv_time);
            viewHolder.topic = (TextView) view.findViewById(R.id.tv_rp_topic);
            view.setTag(viewHolder);//将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();//重新获取ViewHolder

        }
        viewHolder.userName.setText(releasePool.getUsername());
        viewHolder.content.setText(releasePool.getRlcontent());
        viewHolder.time.setText(releasePool.getTime());
        viewHolder.topic.setText(releasePool.getDetail());

        Glide.with(mContext).load(releasePool.getImagecontenturl()).into(viewHolder.contentImage);
        Glide.with(mContext).load(releasePool.getImageuserurl()).into(viewHolder.usertouxiang);

        return view;
    }

    public class ViewHolder {

        CircleImageView usertouxiang;
        TextView userName;
        ImageView contentImage;
        TextView content;
        TextView time;
        TextView topic;

    }
}
