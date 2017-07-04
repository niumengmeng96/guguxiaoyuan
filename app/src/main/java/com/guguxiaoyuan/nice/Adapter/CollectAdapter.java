package com.guguxiaoyuan.nice.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guguxiaoyuan.nice.R;
import com.guguxiaoyuan.nice.activity.CardActivity;
import com.guguxiaoyuan.nice.bean.Three;

import java.util.List;

/**
 * Created by 萌 on 2017/5/6.
 */

public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.ViewHolder> {
    private List<Three> mThree;
    private Context mContext;
    private ViewHolder holder;
    private String keep;


    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageView;
        TextView textView;
        ImageView shoucang;



        public ViewHolder(View view) {
            super(view);
            cardView= (CardView) view;
            imageView= (ImageView) view.findViewById(R.id.three_image);
            textView= (TextView) view.findViewById(R.id.three_text);
            shoucang=(ImageView)view.findViewById(R.id.iv_card_shoucang);
        }
    }

    public CollectAdapter(List<Three> threes) {
        mThree = threes;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext==null){
            mContext=parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.three_item, parent, false);
        holder = new ViewHolder(view);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Three three=mThree.get(position);
        Glide.with(mContext).load(R.mipmap.collect1).into(holder.shoucang);
        holder.textView.setText(three.getDetail());
        Log.e("url---",three.getUrl());
        Glide.with(mContext).load(three.getUrl()).into(holder.imageView);
        keep = "1";
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position= holder.getAdapterPosition();
                Three three1=mThree.get(position);
                Intent intent=new Intent(mContext, CardActivity.class);
                intent.putExtra("cardname",three1.getHead());
                intent.putExtra("iamgeurl",three1.getUrl());
                intent.putExtra("content",three1.getDetail());
                intent.putExtra("aid",three1.getAid());
                intent.putExtra("uid",three1.getUid());
                intent.putExtra("keep", keep);
                Log.e("aid---",three1.getAid());
                Log.e("uid----",three1.getUid());

                mContext.startActivity(intent);

            }
        });

    }




    @Override
    public int getItemCount() {
        return mThree.size();
    }
    public void clear(){
        mThree.clear();
    }
}

