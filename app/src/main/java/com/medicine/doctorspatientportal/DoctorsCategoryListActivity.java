package com.medicine.doctorspatientportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

public class DoctorsCategoryListActivity extends AppCompatActivity {

    LinearLayout banner;
    MaterialCardView cardiology_doctor;
    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_category_list);

        banner = findViewById(R.id.banner);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

//DOCTORS CATEGORY

//        cardiology_doctor = findViewById(R.id.cardiology_doctor);
//
//        cardiology_doctor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DoctorsCategoryListActivity.this, CardiologyDoctorActivity.class);
//                startActivity(intent);
//            }
//        });


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

    public void criticalMedicine(View view) {
        Intent intent= new Intent(DoctorsCategoryListActivity.this, DoctorsListActivity.class);
        intent.putExtra("category", "ICU");
        startActivity(intent);
    }

    public void cardiology(View view) {
        Intent intent= new Intent(DoctorsCategoryListActivity.this, DoctorsListActivity.class);
        intent.putExtra("category", "Cardiologist");
        startActivity(intent);
    }

    public void dentistry(View view) {
        Intent intent= new Intent(DoctorsCategoryListActivity.this, DoctorsListActivity.class);
        intent.putExtra("category", "Dentistry");
        startActivity(intent);
    }

    public void dermatology(View view) {
        Intent intent= new Intent(DoctorsCategoryListActivity.this, DoctorsListActivity.class);
        intent.putExtra("category", "Dermatology");
        startActivity(intent);
    }

    public void endrocinology(View view) {
        Intent intent= new Intent(DoctorsCategoryListActivity.this, DoctorsListActivity.class);
        intent.putExtra("category", "Endrocinology");
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}