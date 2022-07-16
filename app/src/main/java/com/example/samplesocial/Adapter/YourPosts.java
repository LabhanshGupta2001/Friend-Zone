package com.example.samplesocial.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samplesocial.Models.UploadPostModel;
import com.example.samplesocial.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class YourPosts extends RecyclerView.Adapter<YourPosts.viewHolder> {

    ArrayList<UploadPostModel> list;
    Context context;
    boolean clicked = false;
    int likeCount;
    DatabaseReference myRef;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    StorageReference reference;

    public YourPosts(ArrayList<UploadPostModel> list, Context context) {
        this.list = list;
        this.context = context;
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().child("Posts");
        firebaseStorage = FirebaseStorage.getInstance();
        reference = firebaseStorage.getReference().child("Posts");
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.your_post, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        UploadPostModel model = list.get(position);
        if (model.media_text.equals("1")) {
            Picasso.get().load(model.uri).into(holder.dashImg);
            holder.dashImg.setVisibility(View.VISIBLE);
        } else {
            holder.videoView.setVisibility(View.VISIBLE);
            holder.videoView.setVideoPath(model.uri);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
                bottomSheetDialog.setContentView(R.layout.see_profile);
                if (model.media_text.equals("1")){
                Picasso.get().load(model.uri).into((ImageView) bottomSheetDialog.findViewById(R.id.image));}
                else {
                   VideoView videoView= (VideoView)bottomSheetDialog.findViewById(R.id.vv_video);
                   videoView.setVideoPath(model.uri);
                   videoView.start();
                   videoView.setVisibility(View.VISIBLE);
                }
                bottomSheetDialog.show();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context).setMessage("Are you sure you want to Delete Post profile?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                myRef.child(model.key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        reference.child(model.key).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", null);
                AlertDialog dialog = builder.create();

                //setting background to defauldt alert dailog

                dialog.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.alert));
                dialog.show();

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView dashImg;
        VideoView videoView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            videoView = itemView.findViewById(R.id.vv_video);

            dashImg = itemView.findViewById(R.id.addStoryImg);
        }
    }

}
