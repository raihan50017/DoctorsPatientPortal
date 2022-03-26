package com.medicine.doctorspatientportal.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medicine.doctorspatientportal.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView card_post_user_image;
    public TextView card_post_user_name;
    public TextView card_post_time;
    public TextView card_post_text, card_post_like, card_post_comment;
    public ImageView card_post_image;
    public LinearLayout like, write_comment, share_post;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);

        card_post_user_image = itemView.findViewById(R.id.card_post_user_image);
        card_post_user_name = itemView.findViewById(R.id.card_post_user_name);
        card_post_time = itemView.findViewById(R.id.card_post_time);
        card_post_text = itemView.findViewById(R.id.card_post_text);
        card_post_like = itemView.findViewById(R.id.card_post_like);
        card_post_comment = itemView.findViewById(R.id.card_post_comment);
        card_post_image = itemView.findViewById(R.id.card_post_image);
        like = itemView.findViewById(R.id.like);
        write_comment = itemView.findViewById(R.id.write_comment);
        share_post = itemView.findViewById(R.id.share_post);

    }
}
