package com.medicine.doctorspatientportal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medicine.doctorspatientportal.DoctorAppointmentDetailsActivity;
import com.medicine.doctorspatientportal.R;
import com.medicine.doctorspatientportal.model.Appointment;
import com.medicine.doctorspatientportal.model.Doctor;
import com.medicine.doctorspatientportal.model.User;
import com.medicine.doctorspatientportal.viewHolder.AppointmentViewHolder;
import com.medicine.doctorspatientportal.viewHolder.DoctorAppointmentViewHolder;

import java.util.List;

public class DoctorAppointmentAdapter extends RecyclerView.Adapter <DoctorAppointmentViewHolder>{

    List<Appointment> appointmentList;
    Context context;
    User user;

    DoctorAppointmentAdapter(){
    }

    public DoctorAppointmentAdapter(List<Appointment> appointmentList, Context context) {
        this.appointmentList = appointmentList;
        this.context = context;
    }

    @NonNull
    @Override
    public DoctorAppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.doctor_appointment_item, parent, false);
        return new DoctorAppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAppointmentViewHolder holder, int position) {

        Appointment appointment=appointmentList.get(position);
        assert appointment!=null;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(appointment.getU_id());


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user=dataSnapshot.getValue(User.class);
                holder.appointment_date.setText(appointment.getDate());
                holder.appointment_type.setText(appointment.getType());
                holder.appointment_serial.setText(appointment.getSerial());
                holder.appointment_status.setText(appointment.getStatus());
                holder.appointment_patient_name.setText(user.getFullName());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DoctorAppointmentDetailsActivity.class);
                intent.putExtra("id", appointment.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return  appointmentList.size();
    }

}
