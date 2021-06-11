package com.example.paytoll;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.navigation.NavigationView;

public class tollpay_user_dashboard extends AppCompatActivity{

    TextView loginusername;
    CardView profileId,quickpayId,tollplazarouteId,historyId,demoId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_tollpay_user_dashboard);
        profileId =(CardView)findViewById(R.id.userprofileId);
        quickpayId =(CardView)findViewById(R.id.quickpayId);
        tollplazarouteId =(CardView)findViewById(R.id.tollplzaenroute);
        historyId =(CardView)findViewById(R.id.tollpayhistory);
        loginusername= (TextView)findViewById(R.id.username);
        demoId =(CardView)findViewById(R.id.demoId);

        Intent intent = getIntent();
        String getUserName = intent.getStringExtra("Username");
        String getEmail = intent.getStringExtra("Email");
        loginusername.setText(getUserName);
        profileId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(tollpay_user_dashboard.this, EditProfile.class));
            }
        });

        quickpayId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(tollpay_user_dashboard.this,scanqrCode.class));
            }
        });
        tollplazarouteId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(tollpay_user_dashboard.this,point_tollnaka.class));
            }
        });
        historyId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), toll_payment_history.class);
                intent.putExtra("Email", getEmail);
                startActivity(intent);
                //startActivity(new Intent(tollpay_user_dashboard.this,toll_payment_history.class));
            }
        });

    }

}
