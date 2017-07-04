package com.guguxiaoyuan.nice.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guguxiaoyuan.nice.R;
import com.guguxiaoyuan.nice.activity.ListActivity;
import com.guguxiaoyuan.nice.bean.SendPool;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 萌 on 2017/5/5.
 */
public class SendAdapter extends RecyclerView.Adapter<SendAdapter.ViewHolder> {
    private List<SendPool> mSendPool;
    private Context mcontext;
    private ViewHolder holder;


    static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView usertouxiang;
        TextView userName;
        ImageView contentImage;
        TextView content;
        TextView time;
        TextView topic;
        LinearLayout lv;


        public ViewHolder(View view) {
            super(view);
            lv = (LinearLayout) view.findViewById(R.id.lv_send_item);

            usertouxiang = (CircleImageView) view.findViewById(R.id.Send_item_iv_head_list);
            userName = (TextView) view.findViewById(R.id.Send_dynamic_tv_user_name);
            contentImage = (ImageView) view.findViewById(R.id.Send_tv_rpim_content);
            content = (TextView) view.findViewById(R.id.Send_tv_rp_content);
            time = (TextView) view.findViewById(R.id.Send_dynamic_tv_time);
            topic = (TextView) view.findViewById(R.id.Send_tv_rp_topic);

        }
    }

    public SendAdapter(List<SendPool> sendPool) {
        mSendPool = sendPool;
        Log.e("mSendPool--", String.valueOf(sendPool.size()));
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mcontext == null) {
            mcontext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.send_item, parent, false);
        holder = new ViewHolder(view);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SendPool sendPool = mSendPool.get(position);

        holder.userName.setText(sendPool.getUsername());
        holder.content.setText(sendPool.getRlcontent());
        holder.time.setText(sendPool.getTime());
        holder.topic.setText(sendPool.getDetail());

        Glide.with(mcontext).load(sendPool.getImagecontenturl()).into(holder.contentImage);
        Glide.with(mcontext).load(sendPool.getImageuserurl()).into(holder.usertouxiang);

        holder.lv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                SendPool sendPool = mSendPool.get(position);
                Intent intent = new Intent(mcontext, ListActivity.class);
                intent.putExtra("noteusername", sendPool.getUsername());
                intent.putExtra("notedetail", sendPool.getRlcontent());
                intent.putExtra("notehead", sendPool.getDetail());
                intent.putExtra("time", sendPool.getTime());
                intent.putExtra("uri", sendPool.getImagecontenturl());
                intent.putExtra("url", sendPool.getImageuserurl());
                intent.putExtra("nid", sendPool.getNid());
                intent.putExtra("uid", sendPool.getUid());
                intent.putExtra("nuid", sendPool.getNuid());
                mcontext.startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        Log.e("mSendPool", String.valueOf(mSendPool.size()));
        return mSendPool.size();
    }

    public void clear() {
        mSendPool.clear();
    }
}


