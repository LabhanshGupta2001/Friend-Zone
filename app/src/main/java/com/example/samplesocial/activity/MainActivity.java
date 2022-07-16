package com.example.samplesocial.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.samplesocial.Fragments.ChatsFragment;
import com.example.samplesocial.Fragments.HomeFragment;
import com.example.samplesocial.Fragments.NotificationFragment;
import com.example.samplesocial.Fragments.Profile2Fragment;
import com.example.samplesocial.Fragments.addPost;
import com.example.samplesocial.R;
import com.example.samplesocial.databinding.ActivityMainBinding;
import com.iammert.library.readablebottombar.ReadableBottomBar;


public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Hide Toolbar manually
        //getSupportActionBar().hide();

        setSupportActionBar(binding.toolbar);
        MainActivity.this.setTitle("My Profile");

        //Transparent Action Bar
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }*/


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        binding.toolbar.setVisibility(View.GONE);
        transaction.replace(R.id.content, new HomeFragment());
        transaction.commit();


        binding.readableBottomBar.setOnItemSelectListener(new ReadableBottomBar.ItemSelectListener() {
            @Override
            public void onItemSelected(int i) {

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (i) {
                    case 0:
                        binding.toolbar.setVisibility(View.GONE);
                        transaction.replace(R.id.content, new HomeFragment());
                        break;
                    case 1:
                        binding.toolbar.setVisibility(View.GONE);
                        transaction.replace(R.id.content, new NotificationFragment());
                        break;
                    case 2:
                        binding.toolbar.setVisibility(View.GONE);
                        transaction.replace(R.id.content, new addPost());

                        break;
                    case 3:
                        transaction.replace(R.id.content, new ChatsFragment());
                        binding.toolbar.setVisibility(View.GONE);
                        break;
                    case 4:
                        transaction.replace(R.id.content, new Profile2Fragment());
                        binding.toolbar.setVisibility(View.VISIBLE);
                        break;

                }
                transaction.commit();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Toast.makeText(this, "Setting Clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        /*=============================================++++++++++++++++++++++++++++++++++++++++++=============================================================*/
        //Both are working and both are best

        //on first we are adding custom layout


    /*    AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.bottom_sheetaddphoto, null);
        builder.setView(customLayout);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();*/
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme).setMessage("Are you sure you want to exit?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null);
        AlertDialog dialog = builder.create();

        //setting background to defauldt alert dailog

        dialog.getWindow().setBackgroundDrawable((getDrawable(R.drawable.alert)));
        dialog.show();

    }
}