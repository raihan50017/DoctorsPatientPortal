package com.medicine.doctorspatientportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
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
import com.google.firebase.auth.FirebaseUser;

public class EditDoctorProfileActivity extends AppCompatActivity {

    Button update_profile;
    LinearLayout birthdate;
    String item;
    String birth_date;
    String categoryitem;
    FirebaseUser firebaseUser;
    TextView dateshow;
    String[] items = {"Male", "Female", "Others"};
    String[] category = {"Cardiologist", "Dentist"};
    ArrayAdapter<String> adapterItems;
    ArrayAdapter<String> categoryadapter;
    AutoCompleteTextView genderselect;
    AutoCompleteTextView categoryselect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doctor_profile);

        update_profile = findViewById(R.id.update_profile);
        birthdate = findViewById(R.id.birthdate);
        dateshow = findViewById(R.id.dateshow);
        genderselect = findViewById(R.id.genderselect);
        categoryselect = findViewById(R.id.category_select);
        item = genderselect.getText().toString();
        categoryitem = categoryselect.getText().toString();




        //DATE PICKER
        CalendarConstraints.Builder constraintsBuilder =
                new CalendarConstraints.Builder()
                        .setValidator(DateValidatorPointForward.now());

        MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        birth_date = datePicker.getHeaderText();
                        dateshow.setText(datePicker.getHeaderText());
                    }
                });
            }
        });



//SIGN IN PAGE REDIRECT

        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(UserSignUpActivity.this, UserSignInActivity.class);
//                startActivity(intent);
            }
        });

//GENDER MENU

        adapterItems = new ArrayAdapter<String>(this, R.layout.gender_menu_adapter,items);
        genderselect.setAdapter(adapterItems);


        genderselect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = (String) parent.getItemAtPosition(position);
                //Toast.makeText(UserSignUpActivity.this, item, Toast.LENGTH_LONG).show();
            }
        });


        //CATEGORY MENU

        categoryadapter = new ArrayAdapter<String>(this, R.layout.gender_menu_adapter,category);
        categoryselect.setAdapter(categoryadapter);


        categoryselect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoryitem = (String) parent.getItemAtPosition(position);
                Toast.makeText(EditDoctorProfileActivity.this, categoryitem, Toast.LENGTH_LONG).show();
            }
        });



    }
}