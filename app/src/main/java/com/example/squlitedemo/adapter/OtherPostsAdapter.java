package com.example.squlitedemo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.squlitedemo.R;
import com.example.squlitedemo.database.DBHelper;
import com.example.squlitedemo.fragment.UpdateDialog;
import com.example.squlitedemo.models.Posts;

import java.util.ArrayList;

public class OtherPostsAdapter extends RecyclerView.Adapter<OtherPostsAdapter.ViewHolder> {

    private Context context;
    public ArrayList<Posts> arrayList;
    private DBHelper dbHelper;

    public OtherPostsAdapter(Context context, ArrayList<Posts> arrayList) {
        dbHelper = new DBHelper(context);
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.otherpostitemalyout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    @SuppressLint({"RecyclerView","NotifyDataSetChanged"})
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.post.setText(arrayList.get(position).getPost());
        holder.date.setText(arrayList.get(position).getDate());
        holder.createdBy.setText(arrayList.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView post, date, createdBy;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            post = itemView.findViewById(R.id.post);
            date = itemView.findViewById(R.id.postDate);
            createdBy = itemView.findViewById(R.id.postCreatedBy);
        }
    }
}
