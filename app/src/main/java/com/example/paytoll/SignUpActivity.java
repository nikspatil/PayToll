package com.example.paytoll;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private EditText emailSignUp , usernameSignUp , passwordSignUp, vehicleSignUp;
    private Button signUpButton;
    private DataBaseHelper myDB;
    //private DBHandler myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailSignUp = findViewById(R.id.signupemail);
        usernameSignUp = findViewById(R.id.signupusername);
        passwordSignUp = findViewById(R.id.siguppassword);
        vehicleSignUp = findViewById(R.id.signup_vehicle_number);
        signUpButton = findViewById(R.id.signupbutton);

        myDB = new DataBaseHelper(this);
        insertUser();
    }

    private void insertUser(){
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validateFirst = validate();
                if(validateFirst){
                    boolean var = myDB.registerUser(usernameSignUp.getText().toString() , emailSignUp.getText().toString() , passwordSignUp.getText().toString());
                    if(var){
                        Toast.makeText(SignUpActivity.this, "User Registered Successfully !!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this , LoginActivity.class));
                    }
                    else {
                        Toast.makeText(SignUpActivity.this, "Registration Error !!", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
        //finish();
    }

    public boolean validate() {
        boolean valid = true;
        Pattern pattern = Pattern.compile("^[A-Z]{2}[ -][0-9]{1,2}(?: [A-Z])?(?: [A-Z]*)? [0-9]{4}$");

        String email = emailSignUp.getText().toString();
        String username = usernameSignUp.getText().toString();
        String password = passwordSignUp.getText().toString();
        String vehicleNumber = vehicleSignUp.getText().toString();
        Matcher result = pattern.matcher(vehicleNumber);

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailSignUp.setError("enter a valid email address");
            valid = false;
        } else {
            emailSignUp.setError(null);
        }
        if(username.isEmpty()){
            usernameSignUp.setError("Enter a username");
            valid = false;
        }else{
            usernameSignUp.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordSignUp.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordSignUp.setError(null);
        }
        if(vehicleNumber.isEmpty() || vehicleNumber.length() != 13){
            vehicleSignUp.setError("Enter valid vehicle number format -> MH-09-AB-0000");
            valid= false;
        }else{
            vehicleSignUp.setError(null);
        }
        return  valid;
    }
}
