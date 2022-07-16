package com.example.samplesocial.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samplesocial.Models.ChatsModel;
import com.example.samplesocial.R;
import com.example.samplesocial.activity.chatScreen;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.MyViewHolder> {
    Context context;
    ArrayList<ChatsModel> arrayList;
    int i = 0;


    public ChatsAdapter(Context context, ArrayList<ChatsModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chats, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatsModel postModel = arrayList.get(position);
        holder.name.setText(postModel.name);
        if (!postModel.profile.equals("")) {
            Picasso.get().load(postModel.profile).into(holder.profile);
        }
        holder.comment.setText(postModel.comment);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, chatScreen.class);
                intent.putExtra("key", postModel.key);
                intent.putExtra("name", postModel.name);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, comment;
        ImageView profile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            profile = itemView.findViewById(R.id.cv_profile);
            comment = itemView.findViewById(R.id.tvEmail);


        }
    }
}
