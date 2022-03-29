package com.medicine.doctorspatientportal.model;

public class Post {

    String uid;
    String postTime;
    String postText;
    String imgUrl;
    Integer comment;
    Integer like;
    String id;

    Post(){

    }

    public Post(String uid, String postText, String imgUrl, Integer comment, Integer like, String postTime, String id) {
        this.uid = uid;
        this.postText = postText;
        this.imgUrl = imgUrl;
        this.comment = comment;
        this.like = like;
        this.postTime = postTime;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public Integer getLike() {
        return like;
    }
}
