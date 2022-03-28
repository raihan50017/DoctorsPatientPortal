package com.medicine.doctorspatientportal.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medicine.doctorspatientportal.R;

public class DoctorAppointmentViewHolder extends RecyclerView.ViewHolder {

    public TextView appointment_date, appointment_type, appointment_patient_name, appointment_serial, appointment_status;

    public DoctorAppointmentViewHolder(@NonNull View itemView) {
        super(itemView);
        appointment_date=itemView.findViewById(R.id.appointment_type);
        appointment_type=itemView.findViewById(R.id.appointment_type);
        appointment_patient_name = itemView.findViewById(R.id.appointment_patient_name);
        appointment_serial = itemView.findViewById(R.id.appointment_serial);
        appointment_status = itemView.findViewById(R.id.appointment_status);

    }
}
