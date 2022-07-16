package com.example.samplesocial.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

import com.example.samplesocial.Models.UploadPostModel;
import com.example.samplesocial.activity.MainActivity;
import com.example.samplesocial.databinding.BottomSheetaddphotoBinding;
import com.example.samplesocial.databinding.FragmentAddPostBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


public class addPost extends Fragment implements View.OnClickListener {
    FragmentAddPostBinding binding;
    BottomSheetaddphotoBinding binding1;
    String userId;
    String userProfile;
    String name;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    BottomSheetDialog bottomSheetDialog;
    SharedPreferences sharedPreferences;
    private Uri vodeoUri;
    private boolean isimageAdded = false;
    private boolean isVideoAdded = false;

    public static byte[] getFileDataFromDrawable(Context context, Uri uri) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            InputStream iStream = context.getContentResolver().openInputStream(uri);
            int bufferSize = 2048;
            byte[] buffer = new byte[bufferSize];
            int len = 0;
            if (iStream != null) {
                while ((len = iStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddPostBinding.inflate(getLayoutInflater(), container, false);
        binding.cvPhoto.setOnClickListener(this);
        bottomSheetDialog = new BottomSheetDialog(getActivity());
        binding1 = BottomSheetaddphotoBinding.inflate(getLayoutInflater());
        binding.cvVideo.setOnClickListener(this);
        binding.cvPost.setOnClickListener(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        sharedPreferences = getActivity().getSharedPreferences("Detail", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userid", "");
        name = sharedPreferences.getString("name", "");
        userProfile = sharedPreferences.getString("profile", "");
        Toast.makeText(getActivity(), "" + userId, Toast.LENGTH_SHORT).show();
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if (view == binding.cvPhoto) {
            bottomSheeTask();
        }
        if (view == binding.cvPost) {
            if (isimageAdded) {
                UploadPost();
            } else if (isVideoAdded) {
                uploadVideo();
            } else {
                Toast.makeText(getActivity(), "Add something first", Toast.LENGTH_SHORT).show();
            }
        }
        if (view == binding.cvVideo) {
            selectVideo();
        }
        if (view == binding1.cancel) {
            bottomSheetDialog.dismiss();
        }
        if (view == binding1.gallary) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
            bottomSheetDialog.dismiss();

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

    }

    private void bottomSheeTask() {
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
                binding.image.setImageURI(selectedImageUri);

                //imageBitmap= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImageUri);

            }
            if (resultCode == RESULT_OK && requestCode == 200) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                isimageAdded = true;
                binding.image.setImageBitmap(imageBitmap);


            }
            if (requestCode == 300 && resultCode == RESULT_OK && data != null) {
                // When result code is okay
                // Initialize uri
                Uri uri = data.getData();
                // Initialize intent
                binding.video.setVideoURI(uri);
                binding.video.start();
                vodeoUri = uri;
                // Start activity
                isVideoAdded = true;

                binding.image.setVisibility(View.GONE);
                binding.video.setVisibility(View.VISIBLE);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Check condition
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0]
                == PackageManager.PERMISSION_GRANTED) {
            // When permission is granted
            // Call method
            selectVideo();
        } else {
            // When permission is denied
            // Display toast
            Toast.makeText(getContext()
                    , "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectVideo() {
        // Initialize intent
        Intent intent = new Intent(Intent.ACTION_PICK);
        // set type
        intent.setType("video/*");
        // start activity result
        startActivityForResult(Intent.createChooser(intent, "Select Video"), 300);
    }

    void UploadPost() {
        if (!binding.caption.getText().toString().isEmpty()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            Drawable drawable = binding.image.getDrawable();
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            String postId = (UUID.randomUUID().toString());
            StorageReference reference = firebaseStorage.getReference().child("Posts").child(postId);
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

                            firebaseDatabase.getReference().child("Posts").child(postId).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(), "Image Uploaed", Toast.LENGTH_SHORT).show();

                                    binding.progressBar.setVisibility(View.GONE);

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

    void uploadVideo() {
        if (!binding.caption.getText().toString().isEmpty()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            byte[] byteArray = getFileDataFromDrawable(getContext(), vodeoUri);
            String path = UUID.randomUUID().toString();
            StorageReference reference = firebaseStorage.getReference().child("Posts").child(path);
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
                            model.media_text = "2";


                            firebaseDatabase.getReference().child("Posts").child(path).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(), "Image Uploaed", Toast.LENGTH_SHORT).show();

                                    binding.progressBar.setVisibility(View.GONE);

                                    bottomSheetDialog.dismiss();
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

}