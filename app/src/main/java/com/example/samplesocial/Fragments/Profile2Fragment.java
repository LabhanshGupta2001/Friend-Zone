package com.example.samplesocial.Fragments;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.samplesocial.Adapter.FriendsAdapter;
import com.example.samplesocial.Models.FriendsModel;
import com.example.samplesocial.Models.UploadPostModel;
import com.example.samplesocial.R;
import com.example.samplesocial.activity.LoginActivity;
import com.example.samplesocial.activity.YourPosts;
import com.example.samplesocial.databinding.AlertDailogBinding;
import com.example.samplesocial.databinding.BottomSheetaddphotoBinding;
import com.example.samplesocial.databinding.FragmentProfile2Binding;
import com.example.samplesocial.databinding.SeeProfileBinding;
import com.example.samplesocial.databinding.UploadProfleBottomsheetBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class Profile2Fragment extends Fragment implements View.OnClickListener {
    FragmentProfile2Binding binding;
    SeeProfileBinding seeProfileBinding;
    BottomSheetaddphotoBinding binding1;
    UploadProfleBottomsheetBinding binding2;
    BottomSheetDialog bottomSheetDialog;
    ArrayList<FriendsModel> list;
    SharedPreferences sharedPreferences;
    DatabaseReference myref;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;

    String cover;
    String profile;
    private String Name;
    private String userid;
    private boolean isCoverSelected = false;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentProfile2Binding.inflate(getLayoutInflater(), container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        myref = firebaseDatabase.getReference().child("Posts");


        bottomSheetDialog = new BottomSheetDialog(getActivity());
        binding1 = BottomSheetaddphotoBinding.inflate(getLayoutInflater());
        binding2 = UploadProfleBottomsheetBinding.inflate(getLayoutInflater());
        seeProfileBinding = SeeProfileBinding.inflate(getLayoutInflater());

        binding.addCover.setOnClickListener(this);
        binding.ivLogout.setOnClickListener(this);
        binding.profileImage.setOnClickListener(this);
        binding.message.setOnClickListener(this);
        sharedPreferences = getActivity().getSharedPreferences("Detail", Context.MODE_PRIVATE);

        Name = sharedPreferences.getString("name", "");
        userid = sharedPreferences.getString("userid", "");
        profile = sharedPreferences.getString("profile", "");
        cover = sharedPreferences.getString("cover", "");
        if (!profile.equals("")) {
            Picasso.get().load(profile).error(R.drawable.dummy).into(binding.profileImage);
        }
        Picasso.get().load(cover).error(R.drawable.dummy).into(binding.cover);

        binding.textView2.setText(Name);
        list = new ArrayList<>();
        list.add(new FriendsModel(R.drawable.profile));

        list.add(new FriendsModel(R.drawable.nature_dordogne));
        list.add(new FriendsModel(R.drawable.nature));
        list.add(new FriendsModel(R.drawable.cover));
        list.add(new FriendsModel(R.drawable.deaf));
        list.add(new FriendsModel(R.drawable.original));
        list.add(new FriendsModel(R.drawable.profile1));
        list.add(new FriendsModel(R.drawable.nature1));

        FriendsAdapter adapter = new FriendsAdapter(list, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        binding.friendsRV.setLayoutManager(layoutManager);
        binding.friendsRV.setAdapter(adapter);
        getPosts();

        return binding.getRoot();
    }

    private void bottomSheeTask() {
        bottomSheetDialog.setContentView(binding1.getRoot());
        binding1.camera.setOnClickListener(this::onClick);
        binding1.gallary.setOnClickListener(this::onClick);
        binding1.cancel.setOnClickListener(this::onClick);
        bottomSheetDialog.show();

    }

    private void bottomSheeTaskUpdateProfile() {
        bottomSheetDialog.setContentView(binding2.getRoot());
        binding2.cvPost.setOnClickListener(this::onClick);
        bottomSheetDialog.show();

    }

    private void botomSheetTaskSeeProfile() {
        if (isCoverSelected) {
            Picasso.get().load(cover).into(seeProfileBinding.image);

        } else {

            Picasso.get().load(profile).into(seeProfileBinding.image);
        }
        bottomSheetDialog.setContentView(seeProfileBinding.getRoot());
        bottomSheetDialog.show();

    }

    @Override
    public void onClick(View view) {
        if (view == binding2.cvPost) {
            if (isCoverSelected) {
                updateCover();
            } else {
                UpdateProfile();

            }
        }
        if (view == binding.message) {
            Intent intent = new Intent(view.getContext(), YourPosts.class);
            startActivity(intent);

        }

        if (view == binding.ivLogout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setMessage("Are you sure you want to Log Out?").setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Detail", Context.MODE_PRIVATE);
                            sharedPreferences.edit().clear().apply();
                            Intent intent = new Intent(view.getContext(), LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    })
                    .setNegativeButton("No", null);
            AlertDialog dialog = builder.create();

            //setting background to defauldt alert dailog

            dialog.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.alert));
            dialog.show();


        }

        if (view == binding1.cancel) {
            bottomSheetDialog.dismiss();
        }
        if (view == binding1.camera) {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                startActivityForResult(takePictureIntent, 200);
            } catch (ActivityNotFoundException e) {
                // display error state to the user
            }


        }

        if (view == binding1.gallary) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
            bottomSheetDialog.dismiss();

        }
        if (view == binding.profileImage) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyDialogTheme);
            // set the custom layout
            AlertDailogBinding binding = AlertDailogBinding.inflate(getLayoutInflater());
            builder.setView(binding.getRoot());
            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            binding.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            binding.llViewProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    botomSheetTaskSeeProfile();
                }
            });
            binding.llSelectProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setMessage("Are you sure you want to update profile?").setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    bottomSheeTask();
                                }
                            })
                            .setNegativeButton("No", null);
                    AlertDialog dialog = builder.create();

                    //setting background to defauldt alert dailog

                    dialog.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.alert));
                    dialog.show();
                }
            });
            dialog.show();

        }
        if (view == binding.addCover) {
            isCoverSelected = true;
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyDialogTheme);
            // set the custom layout
            AlertDailogBinding binding = AlertDailogBinding.inflate(getLayoutInflater());
            builder.setView(binding.getRoot());
            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            binding.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            binding.llViewProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    botomSheetTaskSeeProfile();
                }
            });
            binding.llSelectProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setMessage("Are you sure you want to update cover?").setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    bottomSheeTask();
                                }
                            })
                            .setNegativeButton("No", null);
                    AlertDialog dialog = builder.create();

                    //setting background to defauldt alert dailog

                    dialog.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.alert));
                    dialog.show();
                }
            });
            dialog.show();

        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                Uri selectedImageUri = data.getData();
                binding2.image.setImageURI(selectedImageUri);
                bottomSheeTaskUpdateProfile();

                //imageBitmap= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImageUri);

            }
            if (resultCode == RESULT_OK && requestCode == 200) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                binding2.image.setImageBitmap(imageBitmap);
                bottomSheeTaskUpdateProfile();


            }
            if (requestCode == 300 && resultCode == RESULT_OK && data != null) {
                // When result code is okay
                // Initialize uri
                Uri uri = data.getData();
                // Initialize intent
                binding2.video.setVideoURI(uri);
                binding2.video.start();
                //   vodeoUri = uri;
                // Start activity
                //   isVideoAdded = true;

                binding2.image.setVisibility(View.GONE);
                binding2.video.setVisibility(View.VISIBLE);
                bottomSheeTaskUpdateProfile();
            }
        }
    }

    void UpdateProfile() {
        binding2.progressBar.setVisibility(View.VISIBLE);

        Drawable drawable = binding2.image.getDrawable();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] byteArray = stream.toByteArray();

        StorageReference reference = firebaseStorage.getReference().child("UserProfiles").child(userid);
        reference.putBytes(byteArray).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {


                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        firebaseDatabase.getReference().child("users").child(userid).child("profile").setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                Toast.makeText(getContext(), "Image Uploaed", Toast.LENGTH_SHORT).show();
                                sharedPreferences = getActivity().getSharedPreferences("Detail", Context.MODE_PRIVATE);

                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putString("profile", uri.toString());

                                Picasso.get().load(uri).into(binding.profileImage);

                                binding2.progressBar.setVisibility(View.GONE);

                                bottomSheetDialog.dismiss();
                                firebaseDatabase.getReference().child("Posts").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            if (dataSnapshot.child("userId").getValue(String.class).equals(userid)) {
                                                dataSnapshot.getRef().child("userProfile").setValue(uri.toString());
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            }
                        });

                    }
                });
            }
        });

    }

    void updateCover() {
        binding2.progressBar.setVisibility(View.VISIBLE);

        Drawable drawable = binding2.image.getDrawable();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] byteArray = stream.toByteArray();

        StorageReference reference = firebaseStorage.getReference().child("UserCover").child(userid);
        reference.putBytes(byteArray).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {


                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        firebaseDatabase.getReference().child("users").child(userid).child("UserCover").setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                Toast.makeText(getContext(), "Image Uploaed", Toast.LENGTH_SHORT).show();
                                sharedPreferences = getActivity().getSharedPreferences("Detail", Context.MODE_PRIVATE);

                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putString("cover", uri.toString());

                                Picasso.get().load(uri).into(binding.cover);

                                binding2.progressBar.setVisibility(View.GONE);

                                bottomSheetDialog.dismiss();
                                firebaseDatabase.getReference().child("Posts").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            if (dataSnapshot.child("userId").getValue(String.class).equals(userid)) {
                                                dataSnapshot.getRef().child("userProfile").setValue(uri.toString());
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            }
                        });

                    }
                });
            }
        });

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
                        model.key = dataSnapshot.getKey();
                        arrayList.add(model);
                    }
                    com.example.samplesocial.Adapter.YourPosts dashboardAdapter = new com.example.samplesocial.Adapter.YourPosts(arrayList, getContext());
                    binding.rvPosts.setNestedScrollingEnabled(false);
                    //  binding.rvPosts.setLayoutManager(new LinearLayoutManager(getContext()()));
                    binding.rvPosts.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    binding.rvPosts.setAdapter(dashboardAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}