package com.medicine.doctorspatientportal.model;

public class Doctor {
    String address, category, college, degree, fullName, gender, mobile, workPlace, image, id;


    public Doctor() {
    }

    public Doctor(String address, String image, String category, String college, String degree, String fullName, String gender, String mobile, String workPlace, String id) {
        this.address = address;
        this.category = category;
        this.college = college;
        this.degree = degree;
        this.fullName = fullName;
        this.gender = gender;
        this.mobile = mobile;
        this.workPlace = workPlace;
        this.image = image;
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public String getCategory() {
        return category;
    }

    public String getCollege() {
        return college;
    }

    public String getDegree() {
        return degree;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public String getMobile() {
        return mobile;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }
}
