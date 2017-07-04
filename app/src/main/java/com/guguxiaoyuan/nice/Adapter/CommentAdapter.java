package com.guguxiaoyuan.nice.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.guguxiaoyuan.nice.R;
import com.guguxiaoyuan.nice.bean.Comment;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 萌 on 2017/5/16.
 */


public class CommentAdapter extends ArrayAdapter<Comment> {

    private int resourceId;
    private View view;
    private ViewHolder viewHolder;


    private Context mContext;
    private Comment comment;
    private LocalBroadcastManager localBroadcastManager;
    private String uid;
    private String aid;
    private String pid;
    private List<String> zanListuid = new ArrayList<>();
    private List<String> zanListaid = new ArrayList<>();
    private List<String> zanListpid = new ArrayList<>();


    public CommentAdapter(Context context, int textViewResourceId, List<Comment> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        mContext = context;

    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        comment = getItem(position);
        final int pos = position;
        if (convertView == null ) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.Touxiang = (CircleImageView) view.findViewById(R.id.cv_comment);
            viewHolder.Name = (TextView) view.findViewById(R.id.tv_comment_name);
            viewHolder.detail = (TextView) view.findViewById(R.id.tv_comment);
            viewHolder.llzan = (LinearLayout) view.findViewById(R.id.ll_comment_zan);
            viewHolder.zancout = (TextView) view.findViewById(R.id.tv_comment_zan);
            viewHolder.zan = (ImageView) view.findViewById(R.id.im_comment_zan);
            viewHolder.time = (TextView) view.findViewById(R.id.tv_conment_time);
            view.setTag(viewHolder);//将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();//重新获取ViewHolder

        }

        if (comment.getZanflag().equals("1")) {
            Glide.with(mContext).load(R.mipmap.zan2).into(viewHolder.zan);
            viewHolder.llzan.setEnabled(false);
        }
        viewHolder.Name.setText(comment.getUname());
        viewHolder.detail.setText(comment.getDetail());
        viewHolder.time.setText(comment.getTime());
        viewHolder.zancout.setText(String.valueOf(comment.getZancount()));
        Glide.with(mContext).load(comment.getUrl()).into(viewHolder.Touxiang);
        uid = comment.getUid();
        aid = comment.getAid();
        pid = comment.getId();
        zanListuid.add(pos, uid);
        zanListaid.add(pos, aid);
        zanListpid.add(pos, pid);
        Log.e("评论uid-----------", uid);
        Log.e("评论aid-----------", aid);
        Log.e("评论pid-----------", pid);
        //获取广播实例
        localBroadcastManager = LocalBroadcastManager.getInstance(mContext);

        viewHolder.llzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(mContext, String.valueOf(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("com.guguxiaoyuan.nice.ZAN_BROADCAST");
                intent.putExtra("uid", zanListuid.get(pos));
                intent.putExtra("aid", zanListaid.get(pos));
                intent.putExtra("pid", zanListpid.get(pos));
                localBroadcastManager.sendBroadcast(intent);
            }
        });


        Log.e("CommentAdapter", comment.getId());
        Log.e("CommentAdapter", comment.getZanflag());
        return view;
    }

    public void clearList() {
        zanListuid.clear();
        zanListaid.clear();
        zanListpid.clear();
    }

    class ViewHolder {

        CircleImageView Touxiang;
        ImageView zan;
        TextView Name;
        TextView detail;
        TextView time;
        LinearLayout llzan;
        TextView zancout;


    }

}

