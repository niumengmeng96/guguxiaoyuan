package com.guguxiaoyuan.nice.bean;

/**
 * Created by 萌 on 2017/3/26.
 */

public class ReleasePool {
    private String username;
    private String rlcontent;
    private String detail;
    private String uid;

    public String getImagecontenturl() {
        return imagecontenturl;
    }


    @Override
    public String toString() {
        return "ReleasePool{" +
                "username='" + username + '\'' +
                ", rlcontent='" + rlcontent + '\'' +
                ", detail='" + detail + '\'' +
                ", imagecontenturl='" + imagecontenturl + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    private String imagecontenturl;

    public String getImageuserurl() {
        return imageuserurl;
    }

    private String imageuserurl;


    public String getTime() {
        return time;
    }
    public String getUid() {
        return uid;
    }

    private String time;

    public String getNid() {
        return nid;
    }

    public String getNuid() {
        return nuid;
    }

    public void setNuid(String nuid) {
        this.nuid = nuid;
    }

    private String nuid;

    private String nid;

    public ReleasePool(String username, String rlcontent, String time, String detail, String imagecontenturl, String imageuserurl, String nid, String nuid,String  uid) {
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

//    public int getCt_imageId() {
//        return ct_imageId;
//    }
//
//    public int getTx_imageId() {
//        return tx_imageId;
//    }
}
