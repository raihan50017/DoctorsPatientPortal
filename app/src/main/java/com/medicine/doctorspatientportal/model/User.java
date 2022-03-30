package com.medicine.doctorspatientportal.model;

public class User {
    String fullName;
    String memberType, mobile, birthDate, imgUrl, gender;
    public User(){

    };

    public User(String fullName, String memberType, String mobile, String birthDate, String imgUrl, String gender) {
        this.fullName = fullName;
        this.memberType = memberType;
        this.mobile = mobile;
        this.birthDate = birthDate;
        this.imgUrl = imgUrl;
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMemberType() {
        return memberType;
    }

    public String getMobile() {
        return mobile;
    }
    public String getBirthDate() {
        return birthDate;
    }
    public String getGender() {
        return gender;
    }
    public String getImgUrl() {
        return imgUrl;
    }
}
