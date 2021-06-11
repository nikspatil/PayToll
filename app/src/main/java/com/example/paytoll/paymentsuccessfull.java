package com.example.paytoll;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class paymentsuccessfull extends AppCompatActivity {
    Button success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_successfull);
        success = (Button)findViewById(R.id.continuetrip);
        success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(paymentsuccessfull.this , point_tollnaka.class));
//                finish();
                Intent intent = new Intent(paymentsuccessfull.this, point_tollnaka.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }
}
