package com.medicine.doctorspatientportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medicine.doctorspatientportal.model.Post;
import com.medicine.doctorspatientportal.model.User;

public class UserSignInActivity extends AppCompatActivity {

    TextView forgetpassword;
    TextView signup;
    TextInputEditText emailinput;
    TextInputEditText passwordinput;
    Button signin;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_in);

        forgetpassword = findViewById(R.id.forgetpassword);
        signup = findViewById(R.id.signup);
        signin = findViewById(R.id.signin);
        emailinput = findViewById(R.id.emailinput);
        passwordinput = findViewById(R.id.passwordinput);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser!=null){
            DatabaseReference datbase = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());

            datbase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    User user=dataSnapshot.getValue(User.class);
                    Intent intent;
                    if(user.getMemberType().equals("Patient")){
                        intent = new Intent(UserSignInActivity.this, HomeActivity.class);
                    }
                    else{
                        intent = new Intent(UserSignInActivity.this, DoctorHomeActivity.class);
                    }
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


//SIGN IN

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_input = emailinput.getText().toString();
                String password_input = passwordinput.getText().toString();

                if(email_input.isEmpty()||password_input.isEmpty()){
                    if(email_input.isEmpty()){
                        emailinput.setError("Email Required");
                    }
                    if(password_input.isEmpty()){
                        passwordinput.setError("Password Required");
                    }
                }
                else {
                    FirebaseAuth auth = FirebaseAuth.getInstance();


                    auth.signInWithEmailAndPassword(email_input, password_input)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(UserSignInActivity.this, "Login Successfull", Toast.LENGTH_LONG).show();
                                        DatabaseReference datbase = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                                        datbase.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                User user=dataSnapshot.getValue(User.class);
                                                Intent intent;
                                                if(user.getMemberType().equals("Patient")){
                                                    intent = new Intent(UserSignInActivity.this, HomeActivity.class);
                                                }
                                                else{
                                                    intent = new Intent(UserSignInActivity.this, DoctorHomeActivity.class);
                                                }
                                                startActivity(intent);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });


                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserSignInActivity.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });

//FORGET PASSWORD TEXT SETUP

        SpannableString content = new SpannableString("Forget Password?");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        forgetpassword.setText(content);

//SIGNUP TEXT SETUP

        SpannableString content2 = new SpannableString("Sign Up");
        content2.setSpan(new UnderlineSpan(), 0, content2.length(), 0);
        signup.setText(content2);


//SIGNUP CLICK

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserSignInActivity.this, UserSignUpActivity.class);
                startActivity(intent);
            }
        });

//HIDING ACTIONBAR
       // getSupportActionBar().hide();
    }
}