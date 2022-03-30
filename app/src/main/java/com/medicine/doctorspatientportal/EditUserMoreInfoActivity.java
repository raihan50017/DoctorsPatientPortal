package com.medicine.doctorspatientportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medicine.doctorspatientportal.model.User;

public class EditUserMoreInfoActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    TextInputEditText fullnameinput;
    TextInputEditText mobileinput;
    TextView dateshow;
    AutoCompleteTextView genderselect;
    DatabaseReference databaseReference;
    String[] items = {"Male", "Female", "Others"};
    ArrayAdapter<String> adapterItems;
    String item;
    LinearLayout birthdate;
    String birth_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_more_info);

        toolbar = findViewById(R.id.toolbar);
        fullnameinput = findViewById(R.id.fullnameinput);
        mobileinput = findViewById(R.id.mobileinput);
        dateshow = findViewById(R.id.dateshow);
        genderselect = findViewById(R.id.genderselect);
        birthdate = findViewById(R.id.birthdate);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                fullnameinput.setText(user.getFullName());
                mobileinput.setText(user.getMobile());
                dateshow.setText(user.getBirthDate());
                //genderselect.setText(user.getGender());
                item = user.getGender();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //DATE PICKER
        CalendarConstraints.Builder constraintsBuilder =
                new CalendarConstraints.Builder()
                        .setValidator(DateValidatorPointBackward.now());

        MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds()).setCalendarConstraints(constraintsBuilder.build())
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