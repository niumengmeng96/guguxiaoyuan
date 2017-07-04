package com.guguxiaoyuan.nice.bean;

/**
 * Created by 萌 on 2017/5/21.
 */

public class PushResult {
    public String getUid() {
        return uid;
    }

    public String  getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public void setUid(String uid) {
        this.uid = uid;
    }

    String uid;

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    private Object state;
}
