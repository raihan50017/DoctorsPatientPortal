package com.medicine.doctorspatientportal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreatingPostActivity extends AppCompatActivity {

    LinearLayout postimageupload;
    ImageView postimageview;
    StorageReference storageReference;
    String imgUrl=null;
    TextInputEditText posttext;
    Button post;
    FirebaseUser firebaseUser;
    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_post);

        postimageupload = findViewById(R.id.post_image_upload);
        postimageview = findViewById(R.id.post_image_view);
        posttext = findViewById(R.id.post_text_input);
        post = findViewById(R.id.post);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        postimageupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String post_text = posttext.getText().toString().trim();

                if(post_text.equals("") && imgUrl==null){
                    Toast.makeText(CreatingPostActivity.this, "At least one field required", Toast.LENGTH_SHORT).show();
                }else {

                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference databaseref = database.getReference("posts");

                                firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                                assert firebaseUser!=null;
                                Map<String, Object> user = new HashMap<String, Object>();
                                user.put("postText", post_text);
                                if(imgUrl==null){
                                    user.put("imgUrl", "default");
                                }
                                else{
                                    user.put("imgUrl", imgUrl);
                                }

                                user.put("like", 0);
                                user.put("comment", 0);
                                user.put("uid", firebaseUser.getUid());
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                                String currentDateandTime = sdf.format(new Date());
                                user.put("postTime",currentDateandTime);
                                String id = ""+System.currentTimeMillis()+"";
                                user.put("id",id);
                    databaseref.child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(CreatingPostActivity.this,"Post updated successfully", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(CreatingPostActivity.this, HomeActivity.class));
                                        }
                                    }});
                }
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

                    postimageview.setImageURI(null);
                    postimageview.setImageURI(uri);

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
                                        Toast.makeText(CreatingPostActivity.this, imgUrl, Toast.LENGTH_LONG);
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