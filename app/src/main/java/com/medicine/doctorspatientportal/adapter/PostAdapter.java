package com.medicine.doctorspatientportal.adapter;

import static android.view.View.GONE;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medicine.doctorspatientportal.PostDetailsActivity;
import com.medicine.doctorspatientportal.R;
import com.medicine.doctorspatientportal.model.Like;
import com.medicine.doctorspatientportal.model.Post;
import com.medicine.doctorspatientportal.model.User;
import com.medicine.doctorspatientportal.viewHolder.PostViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    ArrayList<Post> posts;
    Context context;
    DatabaseReference database;
    ArrayList<Like> likes;

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

        likes = new ArrayList<Like>();

        Post post=posts.get(position);
        holder.card_post_text.setText(post.getDescription());
        holder.card_post_time.setText(post.getPtime());
        holder.card_post_like.setText(""+post.getPlike()+"");
        holder.card_post_comment.setText(""+post.getPcomments()+" comments");


        //holder.card_post_comment.setText(post.getComment());

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("likes");

       databaseReference.child(post.getId()).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

//                        Like like = snapshot.getValue(Like.class);
//
//                        likes.add(like);


                }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });


        holder.view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostDetailsActivity.class);
                intent.putExtra("pid", post.getId());
                context.startActivity(intent);
            }
        });

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("likes").child(post.getId());

                Map<String, Object> like = new HashMap<>();

                like.put(FirebaseAuth.getInstance().getCurrentUser().getUid(),"true");

                databaseReference.setValue(like);
            }
        });

        holder.card_post_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostDetailsActivity.class);
                intent.putExtra("pid",post.getId());
                context.startActivity(intent);
            }
        });


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
