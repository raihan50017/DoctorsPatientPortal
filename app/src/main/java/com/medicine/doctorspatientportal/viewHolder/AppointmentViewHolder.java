package com.medicine.doctorspatientportal.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medicine.doctorspatientportal.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AppointmentViewHolder extends RecyclerView.ViewHolder {


    public TextView appointment_data, appointment_type, appointment_doctor_name, appointment_serial, appointment_status;

    public AppointmentViewHolder(@NonNull View itemView) {
        super(itemView);
        appointment_data=itemView.findViewById(R.id.appointment_date);
        appointment_type=itemView.findViewById(R.id.appointment_type);
        appointment_doctor_name = itemView.findViewById(R.id.appointment_doctor_name);
        appointment_serial = itemView.findViewById(R.id.appointment_serial);
        appointment_status = itemView.findViewById(R.id.appointment_status);

    }

}
