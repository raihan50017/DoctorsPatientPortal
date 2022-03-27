package com.medicine.doctorspatientportal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

public class AppointTakingActivity extends AppCompatActivity {

    LinearLayout appointmentdate;
    TextView dateshow;
    String appointment_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_taking);

        appointmentdate = findViewById(R.id.appointmentdate);
        dateshow = findViewById(R.id.dateshow);


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
                        appointment_date = datePicker.getHeaderText();
                        dateshow.setText(datePicker.getHeaderText());
                    }
                });
            }
        });



    }
}