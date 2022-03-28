package com.medicine.doctorspatientportal.model;

public class User {
    String fullName;
    String memberType, mobile, birthDate;
    public User(){

    };

    public User(String fullName, String memberType, String mobile, String birthDate) {
        this.fullName = fullName;
        this.memberType = memberType;
        this.mobile = mobile;
        this.birthDate = birthDate;
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
}
