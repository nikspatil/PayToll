package com.example.paytoll;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText loginEmail, loginPassword;
    private Button loginButton;
    private DataBaseHelper myDb;
    TextView signuphinttv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail= findViewById(R.id.loginemail);
        loginPassword = findViewById(R.id.loginpassword);
        loginButton = findViewById(R.id.loginbutton);
        signuphinttv = findViewById(R.id.signuphint);
        myDb = new DataBaseHelper(this);

        loginUser();

        signuphinttv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this , SignUpActivity.class));
            }
        });

    }
    private void loginUser(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validateFirst = validate();
                if(validateFirst) {
                    boolean var = myDb.checkUser(loginEmail.getText().toString(), loginPassword.getText().toString());
                    if (var) {
                        String Email = loginEmail.getText().toString();
                        String getUsername = myDb.getUserName(loginEmail.getText().toString());
                        System.out.println("Got Username as "+getUsername);
                        Toast.makeText(LoginActivity.this, "Welcome " +getUsername, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), tollpay_user_dashboard.class);
                        intent.putExtra("Username",getUsername);
                        intent.putExtra("Email", Email);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Failed !!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Incorrect Email and Password!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public boolean validate() {
        boolean valid = true;
        String email = loginEmail.getText().toString();
        String password = loginPassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginEmail.setError("enter a valid email address");
            valid = false;
        } else {
            loginEmail.setError(null);
        }
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            loginPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            loginPassword.setError(null);
        }
        return  valid;
    }

}
