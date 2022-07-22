package com.example.squlitedemo.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.squlitedemo.R;
import com.example.squlitedemo.adapter.AdminPostAdapter;
import com.example.squlitedemo.database.DBHelper;
import com.example.squlitedemo.models.Posts;

import java.util.ArrayList;

public class AdminHomeActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    private RecyclerView recyclerView;
    private AdminPostAdapter adminPostAdapter;
    private ArrayList<Posts> arrayList;
    private ImageButton btnLogOut;
    private DBHelper dbHelper;
    private AlertDialog.Builder builder;
    private SharedPreferences sharedpreferences;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        recyclerView = findViewById(R.id.recyclerview);
        btnLogOut = findViewById(R.id.btnLogOut);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        dbHelper = new DBHelper(AdminHomeActivity.this);
        Cursor res = dbHelper.getUnapprovedPosts();
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminHomeActivity.this));
        arrayList = new ArrayList<>();
        while (res.moveToNext()){
            Posts posts = new Posts();
            posts.setPostNo(res.getInt(0));
            posts.setPost(res.getString(1));
            posts.setDate(res.getString(2));
            posts.setUsername(res.getString(3));
            posts.setApprovedStatus(false);
            arrayList.add(posts);
        }
        adminPostAdapter = new AdminPostAdapter(AdminHomeActivity.this, arrayList);
        recyclerView.setAdapter(adminPostAdapter);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressLint("CommitPrefEdits")
            public void onClick(View view) {
                builder = new AlertDialog.Builder(AdminHomeActivity.this);
                builder.setTitle("LOGOUT DIALOG");

                builder.setMessage("Are you want to Logout ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.clear();
                                editor.apply();
                                Toast.makeText(AdminHomeActivity.this, "Logout Successfully!!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AdminHomeActivity.this, LoginActivity.class));
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}