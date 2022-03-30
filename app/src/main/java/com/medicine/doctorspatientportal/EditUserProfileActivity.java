package com.medicine.doctorspatientportal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.service.autofill.DateTransformation;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.medicine.doctorspatientportal.model.User;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditUserProfileActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    CircleImageView profile_image;
    ImageView change_profile_image;
    StorageReference storageReference;
    String imgUrl=null;
    TextView user_name;
    TextView user_birth_date;
    TextView gender;
    TextView user_mobile;
    ImageView edit_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        toolbar = findViewById(R.id.toolbar);
        profile_image = findViewById(R.id.profile_image);
        change_profile_image = findViewById(R.id.change_profile_image);
        user_name = findViewById(R.id.user_name);
        user_birth_date = findViewById(R.id.user_birth_date);
        gender = findViewById(R.id.gender);
        user_mobile = findViewById(R.id.user_mobile);
        edit_info = findViewById(R.id.edit_info);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        change_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });


        edit_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditUserProfileActivity.this, EditUserMoreInfoActivity.class);
                startActivity(intent);
            }
        });


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                assert user != null;
                user_name.setText(user.getFullName());
                user_birth_date.setText(user.getBirthDate());
                user_mobile.setText(user.getMobile());
                gender.setText(user.getGender());
                Glide.with(getApplicationContext()).load(user.getImgUrl()).placeholder(R.drawable.avatar).into(profile_image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //IMAGE UPLOAD

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 50017);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==50017){
            if(resultCode==RESULT_OK){
                if (data!=null){
                    Uri uri=data.getData();

                    profile_image.setImageURI(null);
                    profile_image.setImageURI(uri);

                    storageReference= FirebaseStorage.getInstance().getReference("post");

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    assert  user != null;

                    StorageReference storage = storageReference.child(user.getUid());

                    storage.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storage.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if(task.isSuccessful()){
                                        imgUrl=task.getResult().toString();
                                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        databaseReference.child("imgUrl").setValue(imgUrl);
                                        Toast.makeText(EditUserProfileActivity.this, imgUrl, Toast.LENGTH_LONG);
                                    }
                                }
                            });
                        }
                    });

                }
            }
        }
        else{

        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}