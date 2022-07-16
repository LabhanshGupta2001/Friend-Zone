package com.example.samplesocial.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.samplesocial.Adapter.ChatsAdapter;
import com.example.samplesocial.Models.ChatsModel;
import com.example.samplesocial.R;
import com.example.samplesocial.databinding.FragmentChatsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChatsFragment extends Fragment {
    FragmentChatsBinding binding;
    ArrayList<ChatsModel> chatsModels = new ArrayList<>();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
    String userid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentChatsBinding.inflate(getLayoutInflater(), container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Detail", Context.MODE_PRIVATE);
        userid = sharedPreferences.getString("userid", "");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (!dataSnapshot.getKey().equals(userid)) {
                        ChatsModel model = new ChatsModel();
                        model.key = dataSnapshot.getKey();
                        model.name = dataSnapshot.child("name").getValue(String.class);
                        model.comment = dataSnapshot.child("email").getValue(String.class);
                        model.profile = dataSnapshot.child("profile").getValue(String.class);
                        chatsModels.add(model);
                    }
                    binding.rvChats.setLayoutManager(new LinearLayoutManager(getActivity()));
                    binding.rvChats.setAdapter(new ChatsAdapter(getActivity(), chatsModels));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return binding.getRoot();
    }
}