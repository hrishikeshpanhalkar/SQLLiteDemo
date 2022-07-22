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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.squlitedemo.R;
import com.example.squlitedemo.database.DBHelper;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    private TextView welcomeText;
    private ImageButton btnLogOut;
    private AlertDialog.Builder builder;
    private SharedPreferences sharedpreferences;
    private TextInputLayout createPostTxt;
    private String userName;
    private Button btnCreatePost, btnViewMyPost, btnViewOtherPost;
    private DBHelper dbHelper;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));
        welcomeText = findViewById(R.id.welcomeText);
        btnLogOut = findViewById(R.id.btnLogOut);
        createPostTxt = findViewById(R.id.yourPostTxt);
        btnCreatePost = findViewById(R.id.btnCreatePost);
        btnViewMyPost = findViewById(R.id.btnViewMyPost);
        btnViewOtherPost = findViewById(R.id.btnViewOthersPost);
        dbHelper = new DBHelper(HomeActivity.this);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if(getIntent() != null){
            userName = getIntent().getExtras().getString("username");
            String welcomeTextValue = "Welcome " + userName;
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

        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String postValue = createPostTxt.getEditText().getText().toString();
                if(postValue.isEmpty()){
                    Toast.makeText(HomeActivity.this, "Fill All Details!!", Toast.LENGTH_SHORT).show();
                }else {
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String formattedDate = df.format(c);
                    boolean checkInsertData = dbHelper.insertPostData(postValue,userName,formattedDate,false);
                    if(checkInsertData){
                        Toast.makeText(HomeActivity.this, "Post Created Successfully!!", Toast.LENGTH_SHORT).show();
                        createPostTxt.getEditText().setText("");
                    }else{
                        Toast.makeText(HomeActivity.this, "Unable to Create!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnViewMyPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MyPostsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", userName);
                intent.putExtras(bundle);
                createPostTxt.getEditText().setText("");
                startActivity(intent);
            }
        });

        btnViewOtherPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, OthersPostViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", userName);
                intent.putExtras(bundle);
                createPostTxt.getEditText().setText("");
                startActivity(intent);
            }
        });


    }
}