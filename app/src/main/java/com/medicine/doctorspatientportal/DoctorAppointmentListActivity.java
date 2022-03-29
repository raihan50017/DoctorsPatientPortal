package com.medicine.doctorspatientportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medicine.doctorspatientportal.adapter.AppointmentAdapter;
import com.medicine.doctorspatientportal.adapter.DoctorAppointmentAdapter;
import com.medicine.doctorspatientportal.model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class DoctorAppointmentListActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    RecyclerView recyclerView;
    List<Appointment> appointmentList;
    DoctorAppointmentAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointment_list);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        appointmentList = new ArrayList<>();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("appointment");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointmentList.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Appointment appointment = snapshot.getValue(Appointment.class);
                    if(appointment.getD_id().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        appointmentList.add(appointment);
                    }
                }
                recyclerView = findViewById(R.id.recyclerView);
                adapter=new DoctorAppointmentAdapter(appointmentList, DoctorAppointmentListActivity.this);
                LinearLayoutManager layoutManager=new LinearLayoutManager(DoctorAppointmentListActivity.this);
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