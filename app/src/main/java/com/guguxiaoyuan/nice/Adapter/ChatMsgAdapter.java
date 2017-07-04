package com.guguxiaoyuan.nice.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guguxiaoyuan.nice.R;
import com.guguxiaoyuan.nice.bean.ChatMsg;

import java.util.List;


/**
 * Created by 萌 on 2017/3/27.
 */

public class ChatMsgAdapter extends RecyclerView.Adapter<ChatMsgAdapter.ViewHolder> {
    private List<ChatMsg> mChatMsgList;


    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView leftMsg;
        TextView rightMsg;
        private final LinearLayout leftLayout;
        private final LinearLayout rightLayout;


        public ViewHolder(View view) {
            super(view);
            leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
            rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
            leftMsg = (TextView) view.findViewById(R.id.tv_left_msg);
            rightMsg = (TextView) view.findViewById(R.id.tv_right_msg);
        }
    }

    public ChatMsgAdapter(List<ChatMsg> chatMsgList) {
        mChatMsgList = chatMsgList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_msg_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatMsg chatMsg = mChatMsgList.get(position);
        if (chatMsg.getType() == ChatMsg.TYPE_RECEIVED) {
            //如果是收到的消息,显示左边的消息布局,将右边消息布局异常
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(chatMsg.getContent());
        } else if (chatMsg.getType() == ChatMsg.TYPE_SENT) {
            //如果是发送的消息,显示右边的消息布局,将左边消息布局异常
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.rightMsg.setText(chatMsg.getContent());
        }

    }

    @Override
    public int getItemCount() {
        return mChatMsgList.size();
    }
}
