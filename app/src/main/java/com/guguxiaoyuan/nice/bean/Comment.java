package com.guguxiaoyuan.nice.bean;

/**
 * Created by 萌 on 2017/5/16.
 */

public class Comment {
    String result;
    String detail;


    public int getZancount() {
        return zancount;
    }


    int zancount;

    public String getId() {
        return id;
    }



    String id;//评论的ID

    public String getZanflag() {
        return zanflag;
    }

    String zanflag;

    public String getUid() {
        return uid;
    }

    String uid;//发表评论人的ID
    String url;


    public String getTime() {
        return time;
    }

    String time;

    public String getAid() {
        return aid;
    }
    public String getUrl() {
        return url;
    }

    public Comment(String aid,String uid,String uname,String detail,String time,String url,String zanflag,String id,int zancount) {
        this.aid = aid;
        this.uid = uid;
        this.uname = uname;
        this.detail = detail;
        this.time = time;
        this.url = url;
        this.zanflag=zanflag;
        this.id=id;
        this.zancount = zancount;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDetail() {
        return detail;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
    public void setUimage(String uimage) {
        this.uimage = uimage;
    }

    public String getUname() {
        return uname;
    }



    public String getUimage() {
        return uimage;
    }



    String aid;//心情贴ID
    String mark;
    String uname;
    String uimage;
}
