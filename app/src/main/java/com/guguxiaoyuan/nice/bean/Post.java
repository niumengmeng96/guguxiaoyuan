package com.guguxiaoyuan.nice.bean;

/**
 * Created by 萌 on 2017/4/7.
 */

public class Post {
    private String notehead;
    private String notedetail;
    private String time;
    private String noteusername;
    private String notesate;
    private String reachrname;
    private String reachemail;
    private String uid;
    private String noteemail;
    private String id;
    private String detail;
    private String aimage;
    private String ausername;
    private String head;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private String result;

    public String getKeep() {
        return keep;
    }

    public void setKeep(String keep) {
        this.keep = keep;
    }

    String keep;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAimage() {
        return aimage;
    }

    public void setAimage(String aimage) {
        this.aimage = aimage;
    }

    public String getAusername() {
        return ausername;
    }

    public void setAusername(String ausername) {
        this.ausername = ausername;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    @Override
    public String toString() {
        return "Post{" +
                "notehead='" + notehead + '\'' +
                ", notedetail='" + notedetail + '\'' +
                ", time='" + time + '\'' +
                ", noteusername='" + noteusername + '\'' +
                ", notesate='" + notesate + '\'' +
                ", reachrname='" + reachrname + '\'' +
                ", reachemail='" + reachemail + '\'' +
                ", uid='" + uid + '\'' +
                ", noteemail='" + noteemail + '\'' +
                ", id='" + id + '\'' +
                ", noteimage='" + noteimage + '\'' +
                ", userimage='" + userimage + '\'' +
                '}';
    }

    private String noteimage;

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    private String userimage;


    public String getNoteimage() {
        return noteimage;
    }

    public void setNoteimage(String noteimage) {
        this.noteimage = noteimage;
    }


    public String getNotesate() {
        return notesate;
    }

    public void setNotesate(String notesate) {
        this.notesate = notesate;
    }

    public String getNotehead() {
        return notehead;
    }

    public void setNotehead(String notehead) {
        this.notehead = notehead;
    }

    public String getNotedetail() {
        return notedetail;
    }

    public void setNotedetail(String notedetail) {
        this.notedetail = notedetail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNoteusername() {
        return noteusername;
    }

    public void setNoteusername(String noteusername) {
        this.noteusername = noteusername;
    }

    public String getNoteemail() {
        return noteemail;
    }

    public void setNoteemail(String noteemail) {
        this.noteemail = noteemail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReachrname() {
        return reachrname;
    }

    public void setReachrname(String reachrname) {
        this.reachrname = reachrname;
    }

    public String getReachemail() {
        return reachemail;
    }

    public void setReachemail(String reachemail) {
        this.reachemail = reachemail;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


}
