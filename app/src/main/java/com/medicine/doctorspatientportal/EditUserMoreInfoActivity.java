package com.medicine.doctorspatientportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

import java.util.HashMap;
import java.util.Map;

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
    Button update_info;

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
        update_info = findViewById(R.id.update_info);


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
                birth_date = dateshow.getText().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        update_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, mobile;
                name = fullnameinput.getText().toString().trim();
                mobile = mobileinput.getText().toString().trim();
                if(checkError(name, mobile)){
                    Toast.makeText(EditUserMoreInfoActivity.this, "There is a errors", Toast.LENGTH_LONG).show();
                }else {
                    ProgressDialog progressDialog;
                    progressDialog=new ProgressDialog(EditUserMoreInfoActivity.this);
                    progressDialog.setTitle("Info Updating");
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    databaseReference=FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    Map<String, Object> map= new HashMap<>();
                    map.put("fullName", name);
                    map.put("mobile", mobile);
                    map.put("birthDate", birth_date);
                    map.put("gender", item);

                    databaseReference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(EditUserMoreInfoActivity.this, "Update Successfull", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(EditUserMoreInfoActivity.this, EditUserProfileActivity.class));
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(EditUserMoreInfoActivity.this, "Update not Successfull", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
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
                        birth_date = datePicker.getHeaderText().toString();
                        dateshow.setText(datePicker.getHeaderText().toString());
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
                //Toast.makeText(EditUserMoreInfoActivity.this, item, Toast.LENGTH_LONG).show();
            }
        });



    }

    private boolean checkError(String name, String mobile) {

        if(name.isEmpty() || mobile.isEmpty()){
            if (name.isEmpty()){
                fullnameinput.setError("Full name required");
            }
            if (mobile.isEmpty()){
                mobileinput.setError("Full name required");
            }
            return true;
        }

        return false;
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