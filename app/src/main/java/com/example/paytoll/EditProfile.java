package com.example.paytoll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfile extends AppCompatActivity {


    private EditText emailedit , usernameedit , passwordedit, vehicleedit;
    private Button SaveBtn;
    private DataBaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        emailedit = findViewById(R.id.signupemail);
        usernameedit = findViewById(R.id.signupusername);
        passwordedit = findViewById(R.id.siguppassword);

        vehicleedit = findViewById(R.id.signup_vehicle_number);
        SaveBtn = findViewById(R.id.signupbutton);

        myDB = new DataBaseHelper(this);
        updateUser();
    }

    private void updateUser() {
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usernewName = usernameedit.getText().toString();
                String emailnew = emailedit.getText().toString();
                String passnew = passwordedit.getText().toString();
                boolean var = myDB.updateUser(usernewName, passnew, emailnew);
                if (var) {
                    Toast.makeText(EditProfile.this, "Profile Updated  Successfully !!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditProfile.this, tollpay_user_dashboard.class));
                    finish();
                }
                else {
                    Toast.makeText(EditProfile.this, "Update Error !!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}