package com.guguxiaoyuan.nice.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.guguxiaoyuan.nice.Adapter.ChatMsgAdapter;
import com.guguxiaoyuan.nice.R;
import com.guguxiaoyuan.nice.bean.ChatMsg;
import com.guguxiaoyuan.nice.csviewpager.SwipeBackLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 萌 on 2017/5/13.
 */

public class FeedbackActivity extends AppCompatActivity {
    private List<ChatMsg> msgList = new ArrayList<>();//添加聊天
    private EditText chatInputText;
    private Button chatsend;
    private RecyclerView chatMsgRecyclerView;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackLayout layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
                R.layout.swipeback, null);
        layout.attachToActivity(this);
        setContentView(R.layout.activity_feedback);
        initChatView();
    }
    public void initChatView() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        chatInputText = (EditText) findViewById(R.id.et_inputMsg);
        chatsend = (Button) findViewById(R.id.bt_sendMsg);
        chatMsgRecyclerView = (RecyclerView) findViewById(R.id.rcv_msg_view);
        layoutManager = new LinearLayoutManager(this);
        chatMsgRecyclerView.setLayoutManager(layoutManager);
        final ChatMsgAdapter chatMsgAdapter = new ChatMsgAdapter(msgList);
        chatMsgRecyclerView.setAdapter(chatMsgAdapter);
        initChatsg();
        chatsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = chatInputText.getText().toString();
                if (!"".equals(content)) {
                    ChatMsg chatMsg = new ChatMsg(content, ChatMsg.TYPE_SENT);
                    msgList.add(chatMsg);
                    chatMsgAdapter.notifyItemInserted(msgList.size() - 1);//当有新消息时刷新chatMsgRecyclerView中显示
                    chatMsgRecyclerView.scrollToPosition(msgList.size() - 1);//将chatMsgRecyclerView定位到最后一行
                    chatInputText.setText("");//清空输入框中的内容
                }
            }
        });
    }

    private void initChatsg() {
        ChatMsg msg1 = new ChatMsg("哇,你很皮", ChatMsg.TYPE_RECEIVED);
        msgList.add(msg1);
        ChatMsg msg2 = new ChatMsg("不存在的", ChatMsg.TYPE_SENT);
        msgList.add(msg2);
        ChatMsg msg3 = new ChatMsg("你皮任你皮,你就是个瓜皮", ChatMsg.TYPE_RECEIVED);
        msgList.add(msg3);
    }
}


