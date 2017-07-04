package com.guguxiaoyuan.nice.bean;

/**
 * Created by 萌 on 2017/5/5.
 */

public class SendPool {
    private String username;
    private String rlcontent;
    private String detail;

    public String getImagecontenturl() {
        return imagecontenturl;
    }



    private String imagecontenturl;

    public String getImageuserurl() {
        return imageuserurl;
    }

    private String imageuserurl;


    public String getTime() {
        return time;
    }

    private String time;

    public String getNid() {
        return nid;
    }

    public String getNuid() {
        return nuid;
    }


    private String nuid;

    private String nid;

    public String getUid() {
        return uid;
    }

    private String uid;

    public SendPool(String username, String rlcontent, String time, String detail, String imagecontenturl, String imageuserurl, String nid, String nuid,String uid) {
        this.username = username;
        this.rlcontent = rlcontent;
        this.time = time;
        this.detail = detail;
        this.imagecontenturl = imagecontenturl;
        this.imageuserurl = imageuserurl;
        this.nid = nid;
        this.nuid = nuid;
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public String getRlcontent() {
        return rlcontent;
    }

    public String getDetail() {
        return detail;
    }
}
