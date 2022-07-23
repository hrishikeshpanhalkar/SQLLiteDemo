package com.example.squlitedemo.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.squlitedemo.R;
import com.example.squlitedemo.adapter.OtherPostsAdapter;
import com.example.squlitedemo.database.DBHelper;
import com.example.squlitedemo.models.Posts;

import java.util.ArrayList;

public class OthersPostViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OtherPostsAdapter otherPostsAdapter;
    private ArrayList<Posts> arrayList;
    private DBHelper dbHelper;
    private String userName;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_post_view);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        userName = getIntent().getStringExtra("username");
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(OthersPostViewActivity.this));
        arrayList = new ArrayList<>();
        dbHelper = new DBHelper(OthersPostViewActivity.this);
        Cursor res = dbHelper.getOtherPostsData(userName);
        if(res.getCount() == 0){
            Toast.makeText(this, "Data not found!!", Toast.LENGTH_SHORT).show();
        }else{
            while (res.moveToNext()){
                Posts posts = new Posts();
                posts.setPostNo(res.getInt(0));
                posts.setPost(res.getString(1));
                posts.setUsername(res.getString(2));
                posts.setDate(res.getString(3));
                posts.setApprovedStatus(true);
                arrayList.add(posts);
            }
            otherPostsAdapter = new OtherPostsAdapter(OthersPostViewActivity.this, arrayList);
            recyclerView.setAdapter(otherPostsAdapter);
        }


    }
}