package com.guguxiaoyuan.nice.bean;

/**
 * Created by 萌 on 2017/5/16.
 */

public class Post1 {
    String result;
    String detail;

    public String getZancount() {
        return zancount;
    }

    public void setZancount(String zancount) {
        this.zancount = zancount;
    }

    String zancount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;//评论帖子的ID

    public String getZanflag() {
        return zanflag;
    }

    public void setZanflag(String zanflag) {
        this.zanflag = zanflag;
    }

    String zanflag;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUimage() {
        return uimage;
    }

    public void setUimage(String uimage) {
        this.uimage = uimage;
    }

    String uid;//发表评论人的ID
    String url;
    String time;
    String aid;//心情贴ID
    String mark;
    String uname;
    String uimage;
}
