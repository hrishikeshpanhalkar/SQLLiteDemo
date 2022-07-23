package com.example.squlitedemo.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.squlitedemo.R;
import com.example.squlitedemo.adapter.MyPostAdapter;
import com.example.squlitedemo.database.DBHelper;
import com.example.squlitedemo.models.Posts;

import java.util.ArrayList;

public class MyPostsActivity extends AppCompatActivity {

    private String userName;
    private DBHelper dbHelper;
    public static ArrayList<Posts> arrayList;
    private RecyclerView recyclerView;
    @SuppressLint("StaticFieldLeak")
    public static MyPostAdapter myPostAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        userName = getIntent().getExtras().getString("username");
        System.out.println("Data: " + userName);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DBHelper(MyPostsActivity.this);
        arrayList = new ArrayList<>();
        Cursor res = dbHelper.getMyPostsData(userName);
        if(res.getCount() == 0){
            Toast.makeText(MyPostsActivity.this, "No data found!!", Toast.LENGTH_SHORT).show();
        }else {
            while (res.moveToNext()){
                Posts posts = new Posts();
                posts.setPostNo(res.getInt(0));
                posts.setPost(res.getString(1));
                posts.setDate(res.getString(2));
                posts.setUsername(res.getString(3));
                posts.setApprovedStatus(true);
                arrayList.add(posts);
            }
            myPostAdapter = new MyPostAdapter(MyPostsActivity.this, arrayList);
            recyclerView.setAdapter(myPostAdapter);
        }
    }
}