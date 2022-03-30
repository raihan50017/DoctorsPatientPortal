package com.medicine.doctorspatientportal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medicine.doctorspatientportal.AppointTakingActivity;
import com.medicine.doctorspatientportal.DoctorAppointmentDetailsActivity;
import com.medicine.doctorspatientportal.DoctorsListActivity;
import com.medicine.doctorspatientportal.R;
import com.medicine.doctorspatientportal.UserAppointmentDetailsActivity;
import com.medicine.doctorspatientportal.model.Appointment;
import com.medicine.doctorspatientportal.model.Doctor;
import com.medicine.doctorspatientportal.viewHolder.AppointmentViewHolder;
import com.medicine.doctorspatientportal.viewHolder.DoctorViewHolder;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentViewHolder> {

    List<Appointment> appointmentList;
    Context context;
    List<Doctor> doctorList;
    Doctor doctor;

    AppointmentAdapter(){
    }

    public AppointmentAdapter(List<Appointment> appointmentList, Context context) {
        this.appointmentList = appointmentList;
        this.context = context;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_appointment_item, parent, false);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        Appointment appointment=appointmentList.get(position);
        assert appointment!=null;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("doctors").child(appointment.getD_id());


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                doctor=dataSnapshot.getValue(Doctor.class);
                holder.appointment_data.setText(appointment.getDate());
                holder.appointment_type.setText(appointment.getType());
                holder.appointment_serial.setText(appointment.getSerial());
                holder.appointment_status.setText(appointment.getStatus());
                holder.appointment_doctor_name.setText(doctor.getFullName());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, UserAppointmentDetailsActivity.class);
                intent.putExtra("id", appointment.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

}
