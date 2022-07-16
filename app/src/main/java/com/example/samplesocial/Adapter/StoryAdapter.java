package com.example.samplesocial.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samplesocial.R;
import com.example.samplesocial.Models.StoryModel;
import com.example.samplesocial.activity.StoryActivity;

import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.viewHolder> {

    ArrayList<StoryModel> list;
    Context context;

    public StoryAdapter(ArrayList<StoryModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.story_rv_design, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        StoryModel model = list.get(position);
        holder.stroyImg.setImageResource(model.getStroy());
        holder.profile.setImageResource(model.getProfile());
        holder.storyType.setImageResource(model.getStoryType());
        holder.name.setText(model.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, StoryActivity.class);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView stroyImg, profile, storyType;
        TextView name;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            stroyImg = itemView.findViewById(R.id.addStoryImg);
            profile = itemView.findViewById(R.id.profile_image);
            storyType = itemView.findViewById(R.id.storyType);
            name = itemView.findViewById(R.id.name);
        }
    }
}
