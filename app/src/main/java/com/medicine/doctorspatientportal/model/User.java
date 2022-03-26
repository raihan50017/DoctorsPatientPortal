package com.medicine.doctorspatientportal.model;

public class User {
    String fullName;
    String memberType;
    public User(){

    };

    public User(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
    public String getMemberType() {
        return memberType;
    }
}
