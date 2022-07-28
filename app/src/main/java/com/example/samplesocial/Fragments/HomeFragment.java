package com.example.samplesocial.Fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samplesocial.Adapter.DashboardAdapter;
import com.example.samplesocial.Adapter.StoryAdapter;
import com.example.samplesocial.Models.DashboardModel;
import com.example.samplesocial.Models.StoryModel;
import com.example.samplesocial.Models.UploadPostModel;
import com.example.samplesocial.R;
import com.example.samplesocial.UtilityTools.Utils;
import com.example.samplesocial.activity.MainActivity;
import com.example.samplesocial.activity.StoryActivity;
import com.example.samplesocial.databinding.BottomSheetaddphotoBinding;
import com.example.samplesocial.databinding.BottomsheetAddstoryBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment implements View.OnClickListener {
    DatabaseReference myRef;
    FirebaseDatabase firebaseDatabase;
    RecyclerView storyRV, dashboardRV;
    ArrayList<StoryModel> storyList;
    ArrayList<DashboardModel> dashboardList;
    ArrayList<UploadPostModel> arrayList;
    ImageView addStory, image;
    CardView cv_photo, cv_post;
    FirebaseStorage firebaseStorage;
    String userId;
    EditText caption;
    CircleImageView profile_image;
    SharedPreferences sharedPreferences;
    BottomsheetAddstoryBinding binding;
    BottomSheetaddphotoBinding binding1;
    BottomSheetDialog bottomSheetDialog;
    View view;
    String name;
    private Uri vodeoUri;
    private boolean isimageAdded = false;
    private boolean isVideoAdded = false;
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
        view = inflater.inflate(R.layout.fragment_home, container, false);
        binding1 = BottomSheetaddphotoBinding.inflate(getLayoutInflater());
        binding = BottomsheetAddstoryBinding.inflate(getLayoutInflater());
        bottomSheetDialog = new BottomSheetDialog(getContext());
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().child("Posts");
        sharedPreferences = getActivity().getSharedPreferences("Detail", Context.MODE_PRIVATE);
        profile_image = view.findViewById(R.id.profile_image);
        userId = sharedPreferences.getString("userid", "");
        name = sharedPreferences.getString("name", "");
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
                bottomSheetTask();
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

                }
                DashboardAdapter dashboardAdapter = new DashboardAdapter(arrayList, getContext());
                dashboardRV.setNestedScrollingEnabled(false);
                dashboardRV.setLayoutManager(new LinearLayoutManager(getContext()));
                dashboardRV.setAdapter(dashboardAdapter);
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

    @Override
    public void onClick(View view) {
        if (view == cv_photo) {
            bottomSheeTask();
        }
        if (view == binding1.camera) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                startActivityForResult(takePictureIntent, 200);
            } catch (ActivityNotFoundException e) {
                // display error state to the user
            }
            bottomSheetDialog.dismiss();

        }
        if (view == binding1.gallary) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
            bottomSheetDialog.dismiss();

        }
        if (view == cv_post) {
            if (isimageAdded) {
                addStory();
            } else {
                Toast.makeText(getActivity(), "Add something first", Toast.LENGTH_SHORT).show();
            }
        }
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
    void bottomSheetTask() {
        onDestroyView();
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.bottomsheet_addstory);
        cv_photo = bottomSheetDialog.findViewById(R.id.cv_photo);
        cv_post = bottomSheetDialog.findViewById(R.id.cv_post);
        caption = bottomSheetDialog.findViewById(R.id.caption);
        image = bottomSheetDialog.findViewById(R.id.image);
        Utils.setBottomSheetFullHeight(requireActivity(), bottomSheetDialog);
        cv_photo.setOnClickListener(this);
        cv_post.setOnClickListener(this);
        bottomSheetDialog.show();

    }

    void bottomSheeTask() {
        onDestroyView();
        bottomSheetDialog.setContentView(binding1.getRoot());
        binding1.camera.setOnClickListener(this::onClick);
        binding1.gallary.setOnClickListener(this::onClick);
        binding1.cancel.setOnClickListener(this::onClick);
        bottomSheetDialog.show();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                Uri selectedImageUri = data.getData();
                isimageAdded = true;
                image.setImageURI(selectedImageUri);

                //imageBitmap= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImageUri);

            }
            if (resultCode == RESULT_OK && requestCode == 200) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                isimageAdded = true;
                image.setImageBitmap(imageBitmap);
            }
            if (requestCode == 300 && resultCode == RESULT_OK && data != null) {
                // When result code is okay
                // Initialize uri
                Uri uri = data.getData();
                // Initialize intent
                binding.video.setVideoURI(uri);
                binding.video.start();
                vodeoUri = uri;
                isVideoAdded = true;
                image.setVisibility(View.GONE);
                binding.video.setVisibility(View.VISIBLE);
            }
        }
    }

    void addStory() {
        Dialog dialog = new Dialog(getContext());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        if (!caption.getText().toString().isEmpty()) {
            Drawable drawable = image.getDrawable();
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            String postId = (UUID.randomUUID().toString());
            StorageReference reference = firebaseStorage.getReference().child("Stories").child(userId).child(postId);
            reference.putBytes(byteArray).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            UploadPostModel model = new UploadPostModel();
                            model.uri = uri.toString();
                            model.content = binding.caption.getText().toString().trim();
                            model.userId = userId;
                            model.userProfile = userProfile;
                            model.user_name = name;
                            model.like = "0";
                            model.comment = "0";
                            model.share = "0";
                            model.media_text = "1";

                            firebaseDatabase.getReference().child("Stories").child(userId).child(postId).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(), "Image Uploaed", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    bottomSheetDialog.dismiss();
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                            });

                        }
                    });
                }
            });

        } else {
            Toast.makeText(getActivity(), "Write Caption First", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (binding.cvPhoto.getParent() != null) {
            ((ViewGroup) binding.cvPhoto.getParent()).removeView(binding.cvPhoto); // <- fix
        }

    }
}