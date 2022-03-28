package com.medicine.doctorspatientportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AppointTakingActivity extends AppCompatActivity {

    LinearLayout appointmentdate;
    TextView dateshow;
    String appointment_date;
    Button appointment_taking;
    String[] appointment_type_menu = {"Online", "Ofline"};
    ArrayAdapter<String> appoint_type_menu_adapter;
    AutoCompleteTextView appoiment_type_select;
    String appointment_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_taking);

        appointmentdate = findViewById(R.id.appointmentdate);
        dateshow = findViewById(R.id.dateshow);
        appoiment_type_select = findViewById(R.id.appointment_type_select);
        appointment_type = appoiment_type_select.getText().toString();
        appointment_taking = findViewById(R.id.appointment_taking);


        appointment_taking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(appointment_date.isEmpty()){
                    Toast.makeText(AppointTakingActivity.this, "Date must required", Toast.LENGTH_SHORT).show();
                }else {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("appointment");
                    Map<String, Object> appointment = new HashMap<String, Object>();
                    Intent intent = getIntent();
                    appointment.put("u_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    appointment.put("date",appointment_date);
                    appointment.put("type",appointment_type);
                    appointment.put("d_id",intent.getStringExtra("d_id"));
                    appointment.put("status","Not seen");
                    appointment.put("serial","0");

                    databaseReference.child(String.valueOf(System.currentTimeMillis())).setValue(appointment);

                    Toast.makeText(AppointTakingActivity.this, "Appoint taken successfully", Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(AppointTakingActivity.this, UserAllAppointmentActivity.class);
                    startActivity(intent1);

                }
            }
        });



        appoint_type_menu_adapter = new ArrayAdapter<String>(this, R.layout.gender_menu_adapter,appointment_type_menu);
        appoiment_type_select.setAdapter(appoint_type_menu_adapter);


        appoiment_type_select.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                appointment_type = (String) parent.getItemAtPosition(position);
                //Toast.makeText(UserSignUpActivity.this, item, Toast.LENGTH_LONG).show();
            }
        });


        //DATE PICKER
        CalendarConstraints.Builder constraintsBuilder =
                new CalendarConstraints.Builder()
                        .setValidator(DateValidatorPointForward.now());

        MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds()).setCalendarConstraints(constraintsBuilder.build())
                .build();

        appointmentdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        appointment_date = datePicker.getHeaderText().toString();
                        dateshow.setText(datePicker.getHeaderText().toString());
                    }
                });
            }
        });

    }
}