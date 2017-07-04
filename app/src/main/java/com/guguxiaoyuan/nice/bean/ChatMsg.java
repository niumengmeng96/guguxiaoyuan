package com.guguxiaoyuan.nice.bean;

/**
 * Created by 萌 on 2017/3/27.
 */

public class ChatMsg {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;
    private String content;
    private int type;

    public String getContent() {
        return content;
    }

    public ChatMsg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public int getType() {
        return type;
    }


}
