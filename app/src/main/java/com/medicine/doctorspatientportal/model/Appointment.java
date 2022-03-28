package com.medicine.doctorspatientportal.model;

public class Appointment {
    String d_id;
    String u_id;
    String type;
    String date;
    String status;
    String serial;
    String id;

    Appointment(){

    }

    public Appointment(String d_id, String u_id, String type, String date, String status, String serial, String id) {
        this.d_id = d_id;
        this.u_id = u_id;
        this.type = type;
        this.date = date;
        this.id = id;
        this.status = status;
        this.serial = serial;
    }

    public String getD_id() {
        return d_id;
    }

    public String getU_id() {
        return u_id;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
    public String getSerial() {
        return serial;
    }

    public String getId() {
        return id;
    }
}
