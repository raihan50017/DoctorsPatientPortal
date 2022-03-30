package com.medicine.doctorspatientportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medicine.doctorspatientportal.adapter.AppointmentAdapter;
import com.medicine.doctorspatientportal.adapter.DoctorAdapter;
import com.medicine.doctorspatientportal.model.Appointment;
import com.medicine.doctorspatientportal.model.Doctor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserAllAppointmentActivity extends AppCompatActivity {

    RecyclerView my_appointment_recycler_view;
    List<Appointment> appointmentList;
    AppointmentAdapter adapter;

    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_all_appointment);
        toolbar = findViewById(R.id.toolbar);

        appointmentList = new ArrayList<>();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("appointment");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointmentList.clear();
                for(DataSnapshot value: dataSnapshot.getChildren()){
                    Appointment appointment=value.getValue(Appointment.class);

                    if(appointment.getU_id().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        appointmentList.add(appointment);
                    }
                }

                Collections.reverse(appointmentList);

                my_appointment_recycler_view = findViewById(R.id.my_appointment_recycler_view);
                adapter=new AppointmentAdapter(appointmentList, UserAllAppointmentActivity.this);
                LinearLayoutManager layoutManager=new LinearLayoutManager(UserAllAppointmentActivity.this);
                my_appointment_recycler_view.setLayoutManager(layoutManager);
                my_appointment_recycler_view.setAdapter(adapter);
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