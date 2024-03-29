package com.example.samplesocial.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.samplesocial.Adapter.ChatsMessageAdapter;
import com.example.samplesocial.Models.ChatsMessageModel;
import com.example.samplesocial.databinding.ActivityChatScreenBinding;

import java.util.ArrayList;

public class chatScreen extends AppCompatActivity implements View.OnClickListener {
    ActivityChatScreenBinding binding;
    ArrayList<ChatsMessageModel> arrayList = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference myRef;
    String receiverId;
    String userId;
    String senderRoom;
    String receiverRoom;
    String room;
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.send.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);

        SharedPreferences sharedPreferences = getSharedPreferences("Detail", MODE_PRIVATE);
        receiverId = getIntent().getStringExtra("key");
        name = getIntent().getStringExtra("name");
        userId = sharedPreferences.getString("userid", "");
        binding.tvName.setText(name);
        senderRoom = userId + receiverId;
        receiverRoom = receiverId + userId;

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("chats");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(senderRoom)) {
                    room = senderRoom;
                    getMessages();
                } else {
                    room = receiverRoom;
                    getMessages();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == binding.send) {
            String message = binding.message.getText().toString().trim();
            if (!message.isEmpty()) {
                binding.message.setText("");
                ChatsMessageModel model = new ChatsMessageModel();
                model.SendId = userId;
                model.message = message;

                myRef.child(room).push().setValue(model);


            }
        }
        if (binding.ivBack == view) {
            finish();
        }
    }

    void getMessages() {
        myRef.child(room).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ChatsMessageModel model = dataSnapshot.getValue(ChatsMessageModel.class);
                    arrayList.add(model);
                }

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setStackFromEnd(true);
                binding.rvChats.setLayoutManager(linearLayoutManager);
                binding.rvChats.setAdapter(new ChatsMessageAdapter(getApplicationContext(), arrayList, receiverId));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}