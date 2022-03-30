package com.medicine.doctorspatientportal.adapter;

import static android.view.View.GONE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medicine.doctorspatientportal.R;
import com.medicine.doctorspatientportal.model.Post;
import com.medicine.doctorspatientportal.model.User;
import com.medicine.doctorspatientportal.viewHolder.PostViewHolder;

import java.util.ArrayList;
import java.util.Objects;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    ArrayList<Post> posts;
    Context context;
    DatabaseReference database;

    PostAdapter(){

    }

    public PostAdapter(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_view_item,parent,false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        Post post=posts.get(position);
        holder.card_post_text.setText(post.getPostText());
        holder.card_post_time.setText(post.getPostTime());

        //holder.card_post_like.setText(post.getLike());
        //holder.card_post_comment.setText(post.getComment());
//        if(post.getImgUrl().equals("default")){
//            holder.card_post_image.setVisibility(GONE);
//        }
//        else{
            Glide.with(context).load(post.getImgUrl()).placeholder(R.drawable.loader).into(holder.card_post_image);
        //}

        
        database=FirebaseDatabase.getInstance().getReference("users").child(post.getUid());
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                Objects.requireNonNull(holder).card_post_user_name.setText( user.getFullName());
                Glide.with(context).load(user.getImgUrl()).placeholder(R.drawable.avatar).into(holder.card_post_user_image);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
