package com.medicine.doctorspatientportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medicine.doctorspatientportal.model.Appointment;
import com.medicine.doctorspatientportal.model.User;

public class DoctorAppointmentDetailsActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    TextView appointment_patient_name;
    TextView appointment_date;
    TextView appointment_status;
    TextView appointment_serial;
    Button approve_button;
    Button cancel_button;
    Button chat_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointment_details);

        toolbar = findViewById(R.id.toolbar);

        appointment_patient_name = findViewById(R.id.appointment_patient_name);
        appointment_date = findViewById(R.id.appointment_date);
        appointment_status = findViewById(R.id.appointment_status);
        appointment_serial = findViewById(R.id.appointment_serial);
        approve_button = findViewById(R.id.approve_button);
        cancel_button = findViewById(R.id.cancel_button);
        chat_start = findViewById(R.id.chat_start);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        chat_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("appointment").child(getIntent().getStringExtra("id"));
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Appointment appointment = dataSnapshot.getValue(Appointment.class);
                        Intent intent = new Intent(DoctorAppointmentDetailsActivity.this, ChatActivity.class);
                        intent.putExtra("id",appointment.getU_id());
                        startActivity(intent);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        // Toast.makeText(DoctorAppointmentDetailsActivity.this,getIntent().getStringExtra("id"), Toast.LENGTH_SHORT).show();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("appointment").child(getIntent().getStringExtra("id"));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Appointment appointment = dataSnapshot.getValue(Appointment.class);
                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("users").child(appointment.getU_id());

                databaseReference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        appointment_patient_name.setText(user.getFullName());
                        appointment_date.setText(appointment.getDate());
                        appointment_serial.setText(appointment.getSerial());
                        appointment_status.setText(appointment.getStatus());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        approve_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("appointment").child(getIntent().getStringExtra("id"));

                databaseReference1.child("status").setValue("approved").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(DoctorAppointmentDetailsActivity.this, "Appointment approved", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });


        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("appointment").child(getIntent().getStringExtra("id"));

                databaseReference1.child("status").setValue("canceled").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(DoctorAppointmentDetailsActivity.this, "Appointment canceled", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

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

    public void editDoctorProfile(View view) {
    }
}