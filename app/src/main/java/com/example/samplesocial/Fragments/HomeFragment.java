package com.example.samplesocial.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samplesocial.Adapter.DashboardAdapter;
import com.example.samplesocial.Adapter.StoryAdapter;
import com.example.samplesocial.Models.DashboardModel;
import com.example.samplesocial.Models.StoryModel;
import com.example.samplesocial.Models.UploadPostModel;
import com.example.samplesocial.R;
import com.example.samplesocial.activity.StoryActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {
    DatabaseReference myRef;
    FirebaseDatabase firebaseDatabase;
    RecyclerView storyRV, dashboardRV;
    ArrayList<StoryModel> storyList;
    ArrayList<DashboardModel> dashboardList;
    ArrayList<UploadPostModel> arrayList;
    ImageView addStory;
    CircleImageView profile_image;
    SharedPreferences sharedPreferences;
    private String userProfile;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().child("Posts");
        sharedPreferences = getActivity().getSharedPreferences("Detail", Context.MODE_PRIVATE);
        profile_image = view.findViewById(R.id.profile_image);
        userProfile = sharedPreferences.getString("profile", "");
        Picasso.get().load(userProfile).into(profile_image);
        dashboardRV = view.findViewById(R.id.dashboardRV);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), StoryActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        getPosts();

        //Story Recycler View
        storyRV = view.findViewById(R.id.storyRV);
        storyList = new ArrayList<>();
        storyList.add(new StoryModel(R.drawable.dennis, R.drawable.deaf, "Darshil", R.drawable.ic_video_camera));
        storyList.add(new StoryModel(R.drawable.nature_dordogne, R.drawable.art, "Bhuva", R.drawable.ic_video_camera));
        storyList.add(new StoryModel(R.drawable.profile1, R.drawable.profile, "Bhuva", R.drawable.ic_live));
        StoryAdapter adapter = new StoryAdapter(storyList, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        storyRV.setLayoutManager(layoutManager);
        storyRV.setNestedScrollingEnabled(false);
        storyRV.setAdapter(adapter);


        addStory = view.findViewById(R.id.addStory);
        addStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void getPosts() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList = new ArrayList();
                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UploadPostModel model = dataSnapshot.getValue(UploadPostModel.class);
                    arrayList.add(model);
                    DashboardAdapter dashboardAdapter = new DashboardAdapter(arrayList, getContext());
                    dashboardRV.setNestedScrollingEnabled(false);
                    dashboardRV.setLayoutManager(new LinearLayoutManager(getContext()));
                    dashboardRV.setAdapter(dashboardAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    void removeData() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("firebase-test").orderByChild("title").equalTo("Apple");

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
/*
    void removedataOfStorage(){
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

// Create a reference to the file to delete
        StorageReference desertRef = storageRef.child("images/desert.jpg");

// Delete the file
        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
            }
        });
    }
*/

}