package com.medicine.doctorspatientportal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medicine.doctorspatientportal.adapter.UserAdapter;
import com.medicine.doctorspatientportal.model.Chat;
import com.medicine.doctorspatientportal.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class DoctorMessageFragment extends Fragment {



    RecyclerView recyclerView;
    UserAdapter userAdapter;
    List<User> userList;


    public DoctorMessageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_message, container, false);

        recyclerView=view.findViewById(R.id.recyclerView);
        userList=new ArrayList<>();
        readUsers();

        return  view;
    }
    private void readUsers() {

        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        final DatabaseReference chatsReference= FirebaseDatabase.getInstance().getReference("Chats");
        final DatabaseReference userReference=FirebaseDatabase.getInstance().getReference("users");

        final Set<String> setList=new HashSet<>();

        chatsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                userList.clear();
                setList.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    final Chat chat=snapshot.getValue(Chat.class);

                    assert chat != null;
                    assert firebaseUser != null;

                    if(chat.getSender().equals(firebaseUser.getUid())){

                        setList.add(chat.getReceiver());
                    }

                    else if(chat.getReceiver().equals(firebaseUser.getUid())){

                        setList.add(chat.getSender());
                    }
                }

                userReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                            User user=dataSnapshot1.getValue(User.class);
                            for(String string: setList){
                                assert user != null;
                                if(string.equals(user.getId()))
                                {
                                    userList.add(user);
                                    break;
                                }
                            }
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        linearLayoutManager.setReverseLayout(true);
                        userAdapter=new UserAdapter(getContext(),userList);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(userAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}