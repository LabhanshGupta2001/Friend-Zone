package com.example.samplesocial.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.samplesocial.Models.userHelperClass;
import com.example.samplesocial.R;
import com.example.samplesocial.databinding.ActivitySignupBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    ActivitySignupBinding binding;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.llLogin.setOnClickListener(this);
        binding.cvSignup.setOnClickListener(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        //  Setting Status Bar Color

        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }

    @Override
    public void onClick(View view) {
        if (view == binding.llLogin) {

            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        }

        if (view == binding.cvSignup) {
            if (binding.etName.getText().toString().isEmpty()) {
                binding.etName.requestFocus();
                binding.etName.setError("required");
            } else if (binding.etemail.getText().toString().isEmpty()) {
                binding.etemail.requestFocus();
                binding.etemail.setError("required");
            } else if (binding.etMobile.getText().toString().isEmpty()) {
                binding.etMobile.requestFocus();
                binding.etMobile.setError("required");
            } else if (binding.etPassword.getText().toString().isEmpty()) {
                binding.etPassword.requestFocus();
                binding.etPassword.setError("required");
            } else {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(binding.etMobile.getText().toString())) {
                            Toast.makeText(SignupActivity.this, "Mobile Already exist", Toast.LENGTH_SHORT).show();
                        } else {


                            userHelperClass userHelperClass = new userHelperClass(binding.etName.getText().toString(), binding.etemail.getText().toString(), binding.etMobile.getText().toString(), binding.etPassword.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/social-media-d5e1b.appspot.com/o/dummy.webp?alt=media&token=8e54d865-d591-4df2-bb38-e88da55fb1eb");
                            databaseReference.child(binding.etMobile.getText().toString()).setValue((userHelperClass));


                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        }
    }


}