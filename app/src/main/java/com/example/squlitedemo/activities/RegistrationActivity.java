package com.example.squlitedemo.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
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
    String[] roles ={"Admin","User"};

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        role = findViewById(R.id.role);
        dbHelper = new DBHelper(RegistrationActivity.this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_dropdown_item_1line,roles);
        role.setThreshold(1);
        role.setAdapter(adapter);

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
                        Intent intent = null;
                        if(roleValue.equals("Admin")){
                            intent = new Intent(RegistrationActivity.this, AdminHomeActivity.class);
                        }else {
                            intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("username", usernameValue);
                        intent.putExtras(bundle);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.putString("username", usernameValue);
                        editor.putString("role", roleValue);
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

        inputChange();
    }

    private void inputChange() {
        username.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                usernameCheck();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        email.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailCheck();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordCheck();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        phone.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phoneCheck();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        role.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                roleCheck();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @SuppressLint("ResourceType")
    private boolean passwordCheck() {
        String Password = password.getEditText().getText().toString();

        if (Password.length() == 0) {
            password.setError("Password is Empty");
            return false;
        } else if (Password.length() < 8) {
            password.setError("Password is too weak!");
            return false;
        } else if (!Password.matches("(.*[A-Z].*)")) {
            password.setError("Password must one Uppercase letter!");
            return false;
        } else if (!Password.matches("(.*[0-9].*)")) {
            password.setError("Password must one Number!");
            return false;
        } else if (!Password.matches("(.*[!@#$%^&].*)")) {
            password.setError("Password must one Special Character!");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    @SuppressLint("ResourceType")
    private boolean usernameCheck() {
        String name = username.getEditText().getText().toString();

        if (name.length() == 0) {
            username.setError("Name is Empty");
            return false;
        } else {
            username.setError(null);
            return true;
        }
    }

    @SuppressLint("ResourceType")
    private boolean emailCheck() {
        String Email = email.getEditText().getText().toString();

        if (Email.length() == 0) {
            email.setError("Email is Empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Invalid Email Address!");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    @SuppressLint("ResourceType")
    private boolean phoneCheck() {
        String Phone = phone.getEditText().getText().toString();

        if (Phone.length() == 0) {
            phone.setError("Phone is Empty");
            return false;
        } else if (Phone.length()<10) {
            phone.setError("Invalid Phone number!");
            return false;
        } else {
            phone.setError(null);
            return true;
        }
    }

    @SuppressLint("ResourceType")
    private boolean roleCheck() {
        String Role = role.getText().toString();

        if (Role.length() == 0) {
            role.setError("Role is Empty");
            return false;
        }  else {
            role.setError(null);
            return true;
        }
    }
}