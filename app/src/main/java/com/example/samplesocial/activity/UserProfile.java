package com.example.samplesocial.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.samplesocial.Models.UploadPostModel;
import com.example.samplesocial.R;
import com.example.samplesocial.databinding.ActivityUserProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserProfile extends AppCompatActivity implements View.OnClickListener {
    ActivityUserProfileBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
    DatabaseReference myref;
    FirebaseDatabase firebaseDatabase;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        id = bundle.getString("userId");
        binding.textView2.setText(bundle.getString("name"));
        getPosts();
        binding.messageView.setOnClickListener(this);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot = snapshot.child(id);
                Picasso.get().load(snapshot.child("profile").getValue(String.class)).into(binding.profileImage);
                Picasso.get().load(snapshot.child("UserCover").getValue(String.class)).error(R.drawable.dummy).into(binding.cover);

                  /*  editor.putString("email", (snapshot.child("email").getValue(String.class)));
                    editor.putString("mobile", (snapshot.child("mobile").getValue(String.class)));
                    editor.putString("name", (snapshot.child("name").getValue(String.class)));
                    editor.putString("userid", snapshot.getKey());
                    editor.putString("profile", snapshot.child("profile").getValue(String.class));*/
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getPosts() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        myref = firebaseDatabase.getReference().child("Posts");
        myref.addValueEventListener(new ValueEventListener() {
            private ArrayList<UploadPostModel> arrayList;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList = new ArrayList();
                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("userId").getValue(String.class).equals(id)) {
                        UploadPostModel model = dataSnapshot.getValue(UploadPostModel.class);
                        model.key = dataSnapshot.getKey();
                        arrayList.add(model);
                    }
                    com.example.samplesocial.Adapter.YourPosts dashboardAdapter = new com.example.samplesocial.Adapter.YourPosts(arrayList, getApplicationContext());
                    binding.rvPosts.setNestedScrollingEnabled(false);
                    //  binding.rvPosts.setLayoutManager(new LinearLayoutManager(getApplicationContext()()()));
                    binding.rvPosts.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                    binding.rvPosts.setAdapter(dashboardAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view== binding.messageView){
        }

    }
}