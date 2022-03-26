package com.medicine.doctorspatientportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuPopupHelper;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

public class UserSignUpActivity extends AppCompatActivity {

    LinearLayout birthdate;
    TextView dateshow;
    AutoCompleteTextView genderselect;
    AutoCompleteTextView membertypeselect;
    ArrayAdapter<String> adapterItems;
    ArrayAdapter<String> membertypes;
    String[] items = {"Male", "Female", "Others"};
    String[] membertypsemenu = {"Patient", "Doctor"};
    TextInputEditText fullnameinput;
    TextInputEditText mobileinput;
    TextInputEditText emailinput;
    TextInputEditText passwordinput;
    String item;
    TextView signin;
    String birth_date;
    Button signup;
    String membertype;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

        birthdate = findViewById(R.id.birthdate);
        dateshow = findViewById(R.id.dateshow);
        genderselect = findViewById(R.id.genderselect);
        fullnameinput = findViewById(R.id.fullnameinput);
        mobileinput = findViewById(R.id.mobileinput);
        emailinput = findViewById(R.id.emailinput);
        passwordinput = findViewById(R.id.passwordinput);
        item = genderselect.getText().toString();
        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);
        membertypeselect = findViewById(R.id.member_type_select);
        membertype = membertypeselect.getText().toString();


//EXTRACT TEXT STRING
        


//USER SIGN UP



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullname_input = fullnameinput.getText().toString();
                String mobile_input = mobileinput.getText().toString();
                String email_input = emailinput.getText().toString();
                String password_input = passwordinput.getText().toString();


                if(fullname_input.isEmpty()||mobile_input.isEmpty()||email_input.isEmpty()||password_input.isEmpty()||birth_date.isEmpty()){

                    if(fullname_input.isEmpty()){
                        fullnameinput.setError("Full Name Required");
                    }
                    if(mobile_input.isEmpty()) {
                        mobileinput.setError("Mobile Required");
                    }
                    if(email_input.isEmpty()){
                        emailinput.setError("Email Required");
                    }
                    if(password_input.isEmpty()){
                        passwordinput.setError("Password Required");
                    }
                    if (birth_date==null){
                        Toast.makeText(UserSignUpActivity.this, "Birth Date Input Required", Toast.LENGTH_SHORT).show();
                    }

                }
                else {


                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    String reference;
                    DatabaseReference databaseref = database.getReference("users");

                    auth.createUserWithEmailAndPassword(email_input, password_input).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(UserSignUpActivity.this, "Signup successfull", Toast.LENGTH_LONG).show();
                                firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                                Map<String, Object> user = new HashMap<String, Object>();
                                user.put("fullName",fullname_input);
                                user.put("mobile", mobile_input);
                                user.put("email", email_input);
                                user.put("birthDate", birth_date);
                                user.put("gender", item);
                                user.put("memberType", membertype);
                                databaseref.child(firebaseUser.getUid()).setValue(user);
                                Intent intent = new Intent(UserSignUpActivity.this, UserSignInActivity.class);
                                startActivity(intent);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserSignUpActivity.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
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


//SIGN IN TEXT SETUP

        SpannableString content = new SpannableString("Sign In");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        signin.setText(content);

//SIGN IN PAGE REDIRECT

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserSignUpActivity.this, UserSignInActivity.class);
                startActivity(intent);
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

//MEMBER TYPE SELECT MENU

        membertypes = new ArrayAdapter<String>(this,R.layout.menu_adapter,membertypsemenu);
        membertypeselect.setAdapter(membertypes);
        membertypeselect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                membertype = (String) parent.getItemAtPosition(position);
                //Toast.makeText(UserSignUpActivity.this, membertype, Toast.LENGTH_LONG).show();
            }
        });


//HIDE ACTIONBAR
        //getSupportActionBar().hide();
    }
}