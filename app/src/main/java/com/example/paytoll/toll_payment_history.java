package com.example.paytoll;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class toll_payment_history extends AppCompatActivity {
    Button fetchpayments;
    List<Payment> payments;
    RazorpayClient razorpay;
    JSONObject paymentRequest;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    RecyclerView fetcheduserpayemts;
    //private AdapterFish mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toll_payment_history);
        fetchpayments = (Button) findViewById(R.id.fetch);
        fetchpayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchPaymentDetails();
            }
        });
    }


    protected void fetchPaymentDetails() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    razorpay = new RazorpayClient("rzp_live_JZZQIX2Lfoqvek", "5GCftPDXCs7ZHOkuBDW7qkDT");
                    paymentRequest= new JSONObject();
                    paymentRequest.put("count", 100);
                    List<Payment> payments = razorpay.Payments.fetchAll(paymentRequest);
                    System.out.println("All payemnts:"+payments);
                } catch (RazorpayException | JSONException e) {
                    // Handle Exception
                    System.out.println("execpetion in razor" + e.getMessage());
                }
            }
        });
        thread.start();
    }

}