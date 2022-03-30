package com.medicine.doctorspatientportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medicine.doctorspatientportal.adapter.DoctorAdapter;
import com.medicine.doctorspatientportal.model.Doctor;

import java.util.ArrayList;
import java.util.List;

public class DoctorsListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Doctor> doctorList;
    Intent intent;
    DoctorAdapter adapter;
    String category;
    MaterialToolbar toolbar;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);

        doctorList=new ArrayList<>();
        intent=getIntent();
        category=intent.getStringExtra("category");
        Toast.makeText(DoctorsListActivity.this, category, Toast.LENGTH_LONG).show();
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        databaseReference= FirebaseDatabase.getInstance().getReference("doctors");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                doctorList.clear();
                for(DataSnapshot value: dataSnapshot.getChildren()){
                    Doctor doctor=value.getValue(Doctor.class);

                    assert doctor != null;

                    if(doctor.getCategory().equals(category)){
                        doctorList.add(doctor);
                    }
                }

                recyclerView=findViewById(R.id.recyclerView);
                adapter=new DoctorAdapter(doctorList, DoctorsListActivity.this);
                LinearLayoutManager layoutManager=new LinearLayoutManager(DoctorsListActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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