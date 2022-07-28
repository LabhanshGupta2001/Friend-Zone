package com.example.samplesocial.Adapter;

import static com.example.samplesocial.R.drawable.ic_play;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samplesocial.DoubleClickListener;
import com.example.samplesocial.Models.UploadPostModel;
import com.example.samplesocial.R;
import com.example.samplesocial.UtilityTools.Utils;
import com.example.samplesocial.activity.UserProfile;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.viewHolder> {

    ArrayList<UploadPostModel> list;
    Context context;
    boolean clicked = false;
    int likeCount;

    public DashboardAdapter(ArrayList<UploadPostModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dashboard_rv_design, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        UploadPostModel model = list.get(position);
        if (model.media_text.equals("1")) {
            Picasso.get().load(model.uri).into(holder.dashImg);
            holder.dashImg.setVisibility(View.VISIBLE);
            Picasso.get().load(model.userProfile).into(holder.profile);
            holder.name.setText(model.user_name);
            holder.bio.setText(model.content);
            holder.like.setText(model.like);
            holder.comment.setText(model.comment);
            holder.share.setText(model.share);
        } else {
            Picasso.get().load(model.userProfile).into(holder.profile);
//            holder.videoView.setVisibility(View.VISIBLE);
            holder.videoView.setVideoPath(model.uri);
            holder.dashImg.setImageResource(ic_play);
            holder.dashImg.setBackgroundResource(ic_play);
            holder.dashImg.setScaleType(ImageView.ScaleType.FIT_XY);
            holder.name.setText(model.user_name);
            holder.bio.setText(model.content);
            holder.like.setText(model.like);
            holder.comment.setText(model.comment);
            holder.share.setText(model.share);
        }
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("name", model.user_name);
                bundle.putString("userId", model.userId);
                bundle.putString("profile", model.userProfile);
                Utils.I(context, UserProfile.class, bundle);
            }
        });
        holder.bio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("name", model.user_name);
                bundle.putString("userId", model.userId);
                bundle.putString("profile", model.userProfile);
                Utils.I(context, UserProfile.class, bundle);
            }
        });
        holder.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("name", model.user_name);
                bundle.putString("userId", model.userId);
                bundle.putString("profile", model.userProfile);
                Utils.I(context, UserProfile.class, bundle);
            }
        });
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!clicked) {
                    holder.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like, 0, 0, 0);
                    likeCount = Integer.parseInt(holder.like.getText() + "");
                    //holder.like.setText(likeCount++);
                    clicked = true;
                } else {
                    holder.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like_red, 0, 0, 0);
                    likeCount = Integer.parseInt(holder.like.getText() + "");
                    // holder.like.setText(Integer.parseInt(model.getLike())-1);
                    clicked = false;
                }

            }
        });
        holder.dashImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        holder.videoView.setOnClickListener(new DoubleClickListener() {
            @Override
            public void onSingleClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
                bottomSheetDialog.setContentView(R.layout.see_profile);
                VideoView video = (VideoView) bottomSheetDialog.findViewById(R.id.vv_video);
                video.setVideoPath(model.uri);
                video.start();
                video.setVisibility(View.VISIBLE);
                bottomSheetDialog.show();

            }

            @Override
            public void onDoubleClick(View v) {
                if (!clicked) {
                    holder.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like, 0, 0, 0);
                    likeCount = Integer.parseInt(holder.like.getText() + "");
                    //holder.like.setText(likeCount++);
                    clicked = true;
                } else {
                    holder.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like_red, 0, 0, 0);
                    likeCount = Integer.parseInt(holder.like.getText() + "");
                    // holder.like.setText(Integer.parseInt(model.getLike())-1);
                    clicked = false;
                }

            }
        });
        holder.dashImg.setOnClickListener(new DoubleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (model.media_text.equals("1")) {

                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
                    bottomSheetDialog.setContentView(R.layout.see_profile);
                    Picasso.get().load(model.uri).into((ImageView) bottomSheetDialog.findViewById(R.id.image));
                    bottomSheetDialog.show();
                } else {
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
                    bottomSheetDialog.setContentView(R.layout.see_profile);
                    VideoView video = (VideoView) bottomSheetDialog.findViewById(R.id.vv_video);
                    video.setVideoPath(model.uri);
                    video.start();
                    video.setVisibility(View.VISIBLE);
                    bottomSheetDialog.show();
                }
            }

            @Override
            public void onDoubleClick(View v) {
                if (!clicked) {
                    holder.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like, 0, 0, 0);
                    likeCount = Integer.parseInt(holder.like.getText() + "");
                    //holder.like.setText(likeCount++);
                    clicked = true;
                } else {
                    holder.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like_red, 0, 0, 0);
                    likeCount = Integer.parseInt(holder.like.getText() + "");
                    // holder.like.setText(Integer.parseInt(model.getLike())-1);
                    clicked = false;
                }

            }
        });

        holder.saveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!clicked) {
                    holder.saveImg.setImageResource(R.drawable.saved);
                    clicked = true;
                } else {
                    holder.saveImg.setImageResource(R.drawable.ic_bookmark);
                    clicked = false;
                }

            }


        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView profile, dashImg, saveImg;
        TextView name, bio, like, comment, share;
        VideoView videoView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            videoView = itemView.findViewById(R.id.vv_video);
            profile = itemView.findViewById(R.id.profile_image);
            dashImg = itemView.findViewById(R.id.addStoryImg);
            saveImg = itemView.findViewById(R.id.saveImg);
            name = itemView.findViewById(R.id.userName);
            bio = itemView.findViewById(R.id.bio);
            like = itemView.findViewById(R.id.like);
            comment = itemView.findViewById(R.id.comment);
            share = itemView.findViewById(R.id.share);
        }
    }
}
