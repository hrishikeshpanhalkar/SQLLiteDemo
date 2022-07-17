package com.example.squlitedemo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.squlitedemo.R;
import com.example.squlitedemo.database.DBHelper;
import com.google.android.material.textfield.TextInputLayout;

public class RegistrationActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs" ;
    private Button btnLogin, btnRegister;
    private TextInputLayout username, password, email, phone;
    private AutoCompleteTextView role;
    private DBHelper dbHelper;
    private SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        role = findViewById(R.id.role);
        dbHelper = new DBHelper(RegistrationActivity.this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameValue = username.getEditText().getText().toString();
                String passValue = password.getEditText().getText().toString();
                String emailValue = email.getEditText().getText().toString();
                String phoneValue = phone.getEditText().getText().toString();
                String roleValue = role.getText().toString();

                if(usernameValue.isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "Fill All Details!!", Toast.LENGTH_SHORT).show();
                }else if(passValue.isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "Fill All Details!!", Toast.LENGTH_SHORT).show();
                }else if(emailValue.isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "Fill All Details!!", Toast.LENGTH_SHORT).show();
                }else if(phoneValue.isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "Fill All Details!!", Toast.LENGTH_SHORT).show();
                }else if(roleValue.isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "Fill All Details!!", Toast.LENGTH_SHORT).show();
                }else{
                    boolean checkInsertData = dbHelper.insertUserData(usernameValue, passValue, emailValue, phoneValue, roleValue);
                    if(checkInsertData){
                        Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("username", usernameValue);
                        intent.putExtras(bundle);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.putString("username", usernameValue);
                        editor.apply();
                        Toast.makeText(RegistrationActivity.this, "User Created Successfully!!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }else{
                        Toast.makeText(RegistrationActivity.this, "Unable to Create!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}