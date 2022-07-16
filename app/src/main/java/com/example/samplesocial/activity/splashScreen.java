package com.example.samplesocial.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.samplesocial.R;

public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences sharedPreferences = getSharedPreferences("Detail", MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPreferences.contains("name")) {
                    if (sharedPreferences.contains("email")) {
                        if (sharedPreferences.contains("mobile")) {

                            Intent intent = new Intent(splashScreen.this, MainActivity.class);
                            startActivity(intent);
                            finish();


                        }
                    }

                } else {

                    Intent i = new Intent(splashScreen.this,
                            LoginActivity.class);
                    //Intent is used to switch from one activity to another.

                    startActivity(i);
                    //invoke the SecondActivity.

                    finish();

                }
                //the current activity will get finished.
            }
        }, 500);
    }
}