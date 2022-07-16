package com.example.samplesocial.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.samplesocial.Adapter.DashboardAdapter;
import com.example.samplesocial.Models.UploadPostModel;
import com.example.samplesocial.databinding.ActivityYourPostsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class YourPosts extends AppCompatActivity {
    ActivityYourPostsBinding binding;
    DatabaseReference myref;
    FirebaseDatabase firebaseDatabase;
    SharedPreferences sharedPreferences;
    FirebaseStorage firebaseStorage;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityYourPostsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        sharedPreferences = getSharedPreferences("Detail", Context.MODE_PRIVATE);

        userid = sharedPreferences.getString("userid", "");


        myref = firebaseDatabase.getReference().child("Posts");
        getPosts();
    }

    private void getPosts() {

        myref.addValueEventListener(new ValueEventListener() {
            private ArrayList<UploadPostModel> arrayList;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList = new ArrayList();
                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("userId").getValue(String.class).equals(userid)) {
                        UploadPostModel model = dataSnapshot.getValue(UploadPostModel.class);
                        arrayList.add(model);
                    }
                    DashboardAdapter dashboardAdapter = new DashboardAdapter(arrayList, getApplicationContext());
                    binding.rvPosts.setNestedScrollingEnabled(false);
                    //  binding.rvPosts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    binding.rvPosts.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                    binding.rvPosts.setAdapter(dashboardAdapter);
                    Toast.makeText(YourPosts.this, "" + arrayList.size(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}