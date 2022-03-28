package com.medicine.doctorspatientportal;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;


public class UserProfileFragment extends Fragment {

    CardView my_appointment;


    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
         my_appointment = view.findViewById(R.id.my_appointment);

         my_appointment.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getContext(), UserAllAppointmentActivity.class);
                 startActivity(intent);
             }
         });

        return view;
    }
}