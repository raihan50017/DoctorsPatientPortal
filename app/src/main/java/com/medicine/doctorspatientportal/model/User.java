package com.medicine.doctorspatientportal.model;

public class User {
    String fullName;
    String memberType, mobile, birthDate;
    public User(){

    };

    public User(String fullName, String memberType, String mobile) {
        this.fullName = fullName;
        this.memberType = memberType;
        this.mobile = mobile;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMemberType() {
        return memberType;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getMobile() {
        return mobile;
    }
}
