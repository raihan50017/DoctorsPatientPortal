package com.medicine.doctorspatientportal.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medicine.doctorspatientportal.PostDetailsActivity;
import com.medicine.doctorspatientportal.R;
import com.medicine.doctorspatientportal.model.ModelComment;
import com.medicine.doctorspatientportal.model.User;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.MyHolder> {


    Context context;
    List<ModelComment> list;

    public AdapterComment(Context context, List<ModelComment> list, String myuid, String postid) {
        this.context = context;
        this.list = list;
        this.myuid = myuid;
        this.postid = postid;
    }

    String myuid;
    String postid;


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_comments, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        final String uid = list.get(position).getUid();
        String name = list.get(position).getUname();
        String email = list.get(position).getUemail();
        String image = list.get(position).getUdp();
        final String cid = list.get(position).getcId();
        String comment = list.get(position).getComment();
        String timestamp = list.get(position).getPtime();
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(Long.parseLong(timestamp));
        String timedate = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                holder.name.setText(user.getFullName());
                Glide.with(context).load(user.getImgUrl()).placeholder(R.drawable.loader).into(holder.imagea);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.time.setText(timedate);
        holder.comment.setText(comment);
        try {
            Glide.with(context).load(image).into(holder.imagea);
        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        ImageView imagea;
        TextView name, comment, time;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imagea = itemView.findViewById(R.id.loadcomment);
            name = itemView.findViewById(R.id.commentname);
            comment = itemView.findViewById(R.id.commenttext);
            time = itemView.findViewById(R.id.commenttime);
        }
    }

}
