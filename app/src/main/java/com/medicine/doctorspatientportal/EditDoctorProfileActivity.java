package com.medicine.doctorspatientportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medicine.doctorspatientportal.model.User;

import java.util.HashMap;
import java.util.Map;

public class EditDoctorProfileActivity extends AppCompatActivity {

    Button update_profile;
    LinearLayout birthDateLayout;
    String genderItem;
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
    TextInputEditText fullnameinput, degreeinput, addressinput, collegeinput, workingplaceinput, mobileinput;

    DatabaseReference databaseReference;
    ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doctor_profile);

        fullnameinput=findViewById(R.id.fullnameinput);
        degreeinput=findViewById(R.id.degreeinput);
        addressinput=findViewById(R.id.addressinput);
        collegeinput=findViewById(R.id.collegeinput);
        workingplaceinput=findViewById(R.id.workingplaceinput);
        mobileinput=findViewById(R.id.EditMobileinput);
        update_profile = findViewById(R.id.update_profile);
        birthDateLayout = findViewById(R.id.editBirthdate);
        dateshow = findViewById(R.id.editDateshow);
        genderselect = findViewById(R.id.editGenderselect);
        categoryselect = findViewById(R.id.category_select);

        genderItem = genderselect.getText().toString();
        categoryitem = categoryselect.getText().toString();

        databaseReference= FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user= dataSnapshot.getValue(User.class);
                fullnameinput.setText(user.getFullName());
                mobileinput.setText(user.getMobile());
                birth_date = user.getBirthDate();
                dateshow.setText(user.getBirthDate());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //DATE PICKER
        CalendarConstraints.Builder constraintsBuilder =
                new CalendarConstraints.Builder()
                        .setValidator(DateValidatorPointForward.now());

        MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        birthDateLayout.setOnClickListener(new View.OnClickListener() {
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
                genderItem = (String) parent.getItemAtPosition(position);
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
                //Toast.makeText(EditDoctorProfileActivity.this, categoryitem, Toast.LENGTH_LONG).show();
            }
        });


        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, mobile, degree, address, college, workPlace, birthDate, gender, category;
                name=fullnameinput.getText().toString().trim();
                mobile=mobileinput.getText().toString().trim();
                degree=degreeinput.getText().toString().trim();
                address=addressinput.getText().toString().trim();
                college=collegeinput.getText().toString().trim();
                workPlace=workingplaceinput.getText().toString().trim();
                birthDate=birth_date;
                gender=genderItem;
                category=categoryitem;

                if(checkError(name, mobile, degree, address, college, workPlace, birthDate, gender, category)){
                    Toast.makeText(EditDoctorProfileActivity.this, "There is a errors", Toast.LENGTH_LONG).show();
                }
                else{
                    progressDialog=new ProgressDialog(EditDoctorProfileActivity.this);
                    progressDialog.setTitle("Info Updating");
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                    databaseReference=FirebaseDatabase.getInstance().getReference("doctors").child(firebaseUser.getUid());
                    Map<String, Object> map= new HashMap<>();
                    map.put("fullName", name);
                    map.put("mobile", mobile);
                    map.put("degree", degree);
                    map.put("address", address);
                    map.put("college", college);
                    map.put("workPlace", workPlace);
                    map.put("birthDate", birthDate);
                    map.put("gender", gender);
                    map.put("category", category);

                    databaseReference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(EditDoctorProfileActivity.this, "Update Successfull", Toast.LENGTH_LONG).show();
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(EditDoctorProfileActivity.this, "Update not Successfull", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });



    }

    private boolean checkError(String name, String mobile, String degree, String address, String college, String workPlace, String birthDate, String gender, String category) {

        if(name.isEmpty() || mobile.isEmpty() || degree.isEmpty() || address.isEmpty() || college.isEmpty() || workPlace.isEmpty() || birthDate==null|| gender.isEmpty()||category.isEmpty()){
            if (name.isEmpty()){
                fullnameinput.setError("Full name required");
            }
            if (mobile.isEmpty()){
                mobileinput.setError("Full name required");
            }
            if (degree.isEmpty()){
                degreeinput.setError("Full name required");
            }
            if (address.isEmpty()){
                addressinput.setError("Full name required");
            }
            if (college.isEmpty()){
                collegeinput.setError("Full name required");
            }
            if (workPlace.isEmpty()){
                workingplaceinput.setError("Full name required");
            }
            if (birthDate==null){
                Toast.makeText(EditDoctorProfileActivity.this, "Birth Date Input Required", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return false;
    }


}