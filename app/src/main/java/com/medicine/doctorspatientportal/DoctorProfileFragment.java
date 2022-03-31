package com.medicine.doctorspatientportal;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medicine.doctorspatientportal.model.User;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorProfileFragment extends Fragment {

    DatabaseReference databaseReference;
    TextView profile_name;
    ImageView cover_photo;
    CircleImageView profile_image;
    MaterialButton view_more_info;

    public DoctorProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_doctor_profile, container, false);

        profile_name = view.findViewById(R.id.profie_name);
        cover_photo = view.findViewById(R.id.cover_photo);
        profile_image = view.findViewById(R.id.profile_image);
        view_more_info = view.findViewById(R.id.view_more_info);


        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                profile_name.setText(user.getFullName());
                Glide.with(getContext()).load(user.getImgUrl()).placeholder(R.drawable.avatar).into(profile_image);
                Glide.with(getContext()).load(user.getImgUrl()).placeholder(R.drawable.avatar).into(cover_photo);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        view_more_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),DoctorDetailInfoActivity.class);
                startActivity(intent);
            }
        });

        return  view;
    }
}