package com.medicine.doctorspatientportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medicine.doctorspatientportal.model.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorProfileActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    TextView profile_name;
    MaterialToolbar toolbar;
    ImageView cover_photo;
    CircleImageView profile_image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        profile_name = findViewById(R.id.profie_name);
        cover_photo = findViewById(R.id.cover_photo);
        profile_image = findViewById(R.id.profile_image);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                profile_name.setText(user.getFullName());
                Glide.with(DoctorProfileActivity.this).load(user.getImgUrl()).placeholder(R.drawable.avatar).into(profile_image);
                Glide.with(DoctorProfileActivity.this).load(user.getImgUrl()).placeholder(R.drawable.avatar).into(cover_photo);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void viewMore(View view) {
        Intent intent = new Intent(DoctorProfileActivity.this,DoctorDetailInfoActivity.class);
        startActivity(intent);
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