package com.medicine.doctorspatientportal;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medicine.doctorspatientportal.model.User;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserProfileFragment extends Fragment {

    CardView my_appointment;
    CircleImageView profile_image;
    TextView user_name;


    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
         my_appointment = view.findViewById(R.id.my_appointment);
         profile_image = view.findViewById(R.id.profile_image);
         user_name = view.findViewById(R.id.user_name);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                assert user != null;
                user_name.setText(user.getFullName());
                Glide.with(getContext()).load(user.getImgUrl()).placeholder(R.drawable.avatar).into(profile_image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

         my_appointment.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getContext(), UserAllAppointmentActivity.class);
                 startActivity(intent);
             }
         });

         profile_image.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getContext(), EditUserProfileActivity.class);
                 startActivity(intent);
             }
         });

        return view;
    }
}