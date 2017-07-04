package com.guguxiaoyuan.nice.bean;

/**
 * Created by 萌 on 2017/5/16.
 */

public class Three {
    public  Three(String id ,String detail,String aimage,String ausername,String head,String time,String url,String aid,String uid,String keep,String result) {
        this.id = id;
        this.detail = detail;
        this.aimage = aimage;
        this.ausername = ausername;
        this.head = head;
        this.time = time;
        this.url = url;
        this.aid = aid;
        this.uid = uid;
        this.keep=keep;
        this.result = result;
    }

    private String id;
    private String detail;
    private String aimage;
    private String ausername;
    private String url;
    private String aid;//帖子ID
    private String uid;//用户ID

    public String getKeep() {
        return keep;
    }

    private String keep;
    public String getAid() {
        return aid;
    }
    public String getUid() {
        return uid;
    }

    public String getHead() {
        return head;
    }
    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public String getDetail() {
        return detail;
    }

    public String getAimage() {
        return aimage;
    }

    public String getAusername() {
        return ausername;
    }

    public String getTime() {
        return time;
    }

    private String head;
    private String time;

    public String getResult() {
        return result;
    }



    private String result;
}
