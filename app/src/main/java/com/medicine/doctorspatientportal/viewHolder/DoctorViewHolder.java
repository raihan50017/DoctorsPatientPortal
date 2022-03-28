package com.medicine.doctorspatientportal.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medicine.doctorspatientportal.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView doctorImage;
    public TextView doctorName, doctorCollege;

    public DoctorViewHolder(@NonNull View itemView) {
        super(itemView);

        doctorImage=itemView.findViewById(R.id.doctorImage);
        doctorName=itemView.findViewById(R.id.doctorName);
        doctorCollege=itemView.findViewById(R.id.doctorCollege);

    }
}
