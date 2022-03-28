package com.medicine.doctorspatientportal;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medicine.doctorspatientportal.adapter.PostAdapter;
import com.medicine.doctorspatientportal.model.Post;
import com.medicine.doctorspatientportal.model.User;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    MaterialCardView emergency_doctor;
    MaterialCardView online_appointment;
    MaterialCardView chamber_appointment;
    MaterialCardView others_services;
    CardView create_post_button;
    RecyclerView post_view;
    PostAdapter adapter;
    ArrayList<Post> postList;
    DatabaseReference databaseReference;
    LinearLayout doctor_category_layout;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        emergency_doctor = view.findViewById(R.id.emergency_doctor);
        online_appointment = view.findViewById(R.id.online_appointment);
        chamber_appointment = view.findViewById(R.id.chamber_appointment);
        others_services = view.findViewById(R.id.others_services);
        create_post_button = view.findViewById(R.id.create_post_button);
        doctor_category_layout = view.findViewById(R.id.doctor_category_layout);
        postList=new ArrayList<>();

        // HIDE CATEGORY LIST FOR DOCTOR


        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               User user = dataSnapshot.getValue(User.class);

               if(user.getMemberType().equals("Doctor")){
                   doctor_category_layout.setVisibility(View.GONE);
               }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        create_post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreatingPostActivity.class);
                startActivity(intent);
            }
        });

        emergency_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DoctorsCategoryListActivity.class);
                startActivity(intent);
            }
        });

        online_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DoctorsCategoryListActivity.class);
                startActivity(intent);
            }
        });

        chamber_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DoctorsCategoryListActivity.class);
                startActivity(intent);
            }
        });

        others_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DoctorsCategoryListActivity.class);
                startActivity(intent);
            }
        });


        //SHOW POST

        databaseReference= FirebaseDatabase.getInstance().getReference("posts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Post post=snapshot.getValue(Post.class);
                    postList.add(post);
                }
                post_view = view.findViewById(R.id.post_view);
                adapter = new PostAdapter(postList, getContext());
                LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
                post_view.setLayoutManager(layoutManager);
                post_view.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }
}