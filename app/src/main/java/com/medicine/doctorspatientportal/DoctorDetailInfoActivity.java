package com.medicine.doctorspatientportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DoctorDetailInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail_info);
    }

    public void editDoctorProfile(View view) {
        Intent intent = new Intent(DoctorDetailInfoActivity.this, EditDoctorProfileActivity.class);
        startActivity(intent);
    }
}