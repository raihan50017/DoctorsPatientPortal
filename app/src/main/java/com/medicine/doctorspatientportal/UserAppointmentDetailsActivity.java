package com.medicine.doctorspatientportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medicine.doctorspatientportal.model.Appointment;
import com.medicine.doctorspatientportal.model.User;

import java.util.ResourceBundle;

public class UserAppointmentDetailsActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    TextView appointment_patient_name;
    TextView appointment_date;
    TextView appointment_status;
    TextView appointment_serial;
    Button cancel_appointment;
    Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_appointment_details);

        if(getIntent().getStringExtra("id")==null){
            finish();
        }

        toolbar = findViewById(R.id.toolbar);

        appointment_patient_name = findViewById(R.id.appointment_patient_name);
        appointment_date = findViewById(R.id.appointment_date);
        appointment_status = findViewById(R.id.appointment_status);
        appointment_serial = findViewById(R.id.appointment_serial);
        cancel_appointment = findViewById(R.id.cancel_appointment);
        cancel_appointment = findViewById(R.id.cancel_appointment);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



        cancel_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(UserAppointmentDetailsActivity.this);

                ResourceBundle resources;
                materialAlertDialogBuilder.setTitle("Are sure want to delete this appointment?")
                        .setNegativeButton("DECLINE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("appointment").child(getIntent().getStringExtra("id"));

                                databaseReference.removeValue();
                                finish();
                                flag = true;
                                Toast.makeText(UserAppointmentDetailsActivity.this, "Appointment deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                .show();
            }
        });


        //startActivity(new Intent(UserAppointmentDetailsActivity.this, UserAllAppointmentActivity.class));

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("appointment").child(getIntent().getStringExtra("id"));

            if(databaseReference!=null){
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Appointment appointment = dataSnapshot.getValue(Appointment.class);

                        if (appointment==null){
                            startActivity(new Intent(UserAppointmentDetailsActivity.this, UserAllAppointmentActivity.class));
                        }

                        //assert appointment !=null;
                        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("users").child(appointment.getU_id());
                        if(databaseReference1!=null){
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
                        }else {
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }else {
                finish();
            }

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