package com.medicine.doctorspatientportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.card.MaterialCardView;

public class DoctorsCategoryListActivity extends AppCompatActivity {

    LinearLayout banner;
    MaterialCardView cardiology_doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_category_list);

        banner = findViewById(R.id.banner);

//DOCTORS CATEGORY

        cardiology_doctor = findViewById(R.id.cardiology_doctor);

        cardiology_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorsCategoryListActivity.this, CardiologyDoctorActivity.class);
                startActivity(intent);
            }
        });


        CountDownTimer timer = new CountDownTimer(99999999,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
               // banner.setBackground(Drawable.createFromPath("@drawable/carousel2"));
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }
}