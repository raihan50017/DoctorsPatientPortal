package com.medicine.doctorspatientportal.model;

public class Post {


    String description;
    String id;
    String imgUrl;
    String pcomments;
    String plike;
    String ptime;
    String title;
    String udp;
    String uemail;
    String uid;
    String uimage;
    String uname;



    Post(){

    }

    public Post(String uid, String uname, String uemail, String udp, String title, String uimage, String ptime, String description, String imgUrl, String pcomments, String plike, String id) {
        this.uid = uid;
        this.uname = uname;
        this.uemail = uemail;
        this.udp = udp;
        this.title = title;
        this.uimage = uimage;
        this.ptime = ptime;
        this.description = description;
        this.imgUrl = imgUrl;
        this.pcomments = pcomments;
        this.plike = plike;
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public String getUname() {
        return uname;
    }

    public String getUemail() {
        return uemail;
    }

    public String getUdp() {
        return udp;
    }

    public String getTitle() {
        return title;
    }

    public String getUimage() {
        return uimage;
    }

    public String getPtime() {
        return ptime;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getPcomments() {
        return pcomments;
    }

    public String getPlike() {
        return plike;
    }

    public String getId() {
        return id;
    }
}
