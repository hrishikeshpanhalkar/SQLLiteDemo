package com.example.squlitedemo.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.squlitedemo.R;

public class HomeActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    private TextView welcomeText;
    private ImageButton btnLogOut;
    private AlertDialog.Builder builder;
    private SharedPreferences sharedpreferences;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        welcomeText = findViewById(R.id.welcomeText);
        btnLogOut = findViewById(R.id.btnLogOut);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if(getIntent() != null){
            String welcomeTextValue = "Welcome " + getIntent().getExtras().getString("username");
            welcomeText.setText(welcomeTextValue);
        }

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressLint("CommitPrefEdits")
            public void onClick(View view) {
                builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("LOGOUT DIALOG");

                builder.setMessage("Are you want to Logout ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.clear();
                                editor.apply();
                                Toast.makeText(HomeActivity.this, "Logout Successfully!!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
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