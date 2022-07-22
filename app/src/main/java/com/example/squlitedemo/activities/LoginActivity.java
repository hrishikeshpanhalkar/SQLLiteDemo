package com.example.squlitedemo.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.squlitedemo.R;
import com.example.squlitedemo.database.DBHelper;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    
    public static final String MyPREFERENCES = "MyPrefs" ;
    private Button btnLogin, btnRegister;
    private TextInputLayout username, password;
    private DBHelper dbHelper;
    private SharedPreferences sharedpreferences;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        dbHelper = new DBHelper(LoginActivity.this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedpreferences.getBoolean("isLoggedIn", false);
        if(isLoggedIn){
            Intent intent = null;
            if(sharedpreferences.getString("role", "").equals("Admin")){
                intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
            }else {
                intent = new Intent(LoginActivity.this, HomeActivity.class);
            }
            Bundle bundle = new Bundle();
            bundle.putString("username", sharedpreferences.getString("username", ""));
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isLoginSuccessful = false;
                String usernameValue = username.getEditText().getText().toString();
                String passValue = password.getEditText().getText().toString();
                if(usernameValue.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Fill All Details!!", Toast.LENGTH_SHORT).show();
                }else if(passValue.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                }else {
                    Cursor res = dbHelper.getUserData();
                    if(res.getCount() == 0){
                        Toast.makeText(LoginActivity.this, "Unable to Login", Toast.LENGTH_SHORT).show();
                    }else {
                        while (res.moveToNext()){
                            if(usernameValue.equals(res.getString(0)) && passValue.equals(res.getString(1))){
                                Intent intent = null;
                                if(res.getString(4).equals("Admin")){
                                    intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                                }else {
                                    intent = new Intent(LoginActivity.this, HomeActivity.class);
                                }
                                Bundle bundle = new Bundle();
                                bundle.putString("username", usernameValue);
                                intent.putExtras(bundle);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putBoolean("isLoggedIn", true);
                                editor.putString("username", usernameValue);
                                editor.putString("role", res.getString(4));
                                editor.apply();
                                Toast.makeText(LoginActivity.this, "Login Successfully!!", Toast.LENGTH_SHORT).show();
                                isLoginSuccessful = true;
                                startActivity(intent);
                                finish();
                                break;
                            }
                        }
                        if(!isLoginSuccessful){
                            Toast.makeText(LoginActivity.this, "Unable to Login", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}