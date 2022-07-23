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

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.ViewHolder> {

    private Context context;
    public ArrayList<Posts> arrayList;
    private DBHelper dbHelper;

    public MyPostAdapter(Context context, ArrayList<Posts> arrayList) {
        dbHelper = new DBHelper(context);
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mypostitemalyout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    @SuppressLint({"RecyclerView","NotifyDataSetChanged"})
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.post.setText(arrayList.get(position).getPost());
        holder.date.setText(arrayList.get(position).getDate());
        holder.createdBy.setText("Own");
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(context);
                FragmentManager fm = activity.getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("postNo", String.valueOf(arrayList.get(position).getPostNo()));
                bundle.putString("position", String.valueOf(position));
                bundle.putString("postValue", arrayList.get(position).getPost());
                UpdateDialog updateDialog = new UpdateDialog();
                updateDialog.show(fm, "Dialog Fragment");
                updateDialog.setArguments(bundle);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isSuccessful = dbHelper.deletePostsData(arrayList.get(position).getPostNo());
                if(isSuccessful){
                    arrayList.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Post Deleted Successfully!!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Unable to Delete!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView post, date, createdBy;
        private Button btnDelete, btnUpdate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            post = itemView.findViewById(R.id.post);
            date = itemView.findViewById(R.id.postDate);
            createdBy = itemView.findViewById(R.id.postCreatedBy);
            btnUpdate = itemView.findViewById(R.id.updatePost);
            btnDelete = itemView.findViewById(R.id.deletePost);
        }
    }
}
