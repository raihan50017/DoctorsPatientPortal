package com.medicine.doctorspatientportal.model;

public class Post {

    String uid;
    String postTime;
    String postText;
    String imgUrl;
    Integer comment;
    Integer like;

    Post(){

    }

    public Post(String uid, String postText, String imgUrl, Integer comment, Integer like, String postTime) {
        this.uid = uid;
        this.postText = postText;
        this.imgUrl = imgUrl;
        this.comment = comment;
        this.like = like;
        this.postTime = postTime;
    }

    public String getUid() {
        return uid;
    }

    public String getPostTime() {
        return postTime;
    }

    public String getPostText() {
        return postText;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Integer getComment() {
        return comment;
    }

    public Integer getLike() {
        return like;
    }
}
