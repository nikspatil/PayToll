package com.example.paytoll;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class toll_payment_history extends AppCompatActivity {

    FetchPaymentsModelClass fetchPaymentsModelClass;
    private  List<FetchPaymentsModelClass> paymentsList;
    RecyclerView recyclerView;
    List<Payment> payments;
    RazorpayClient razorpay;
    JSONObject paymentRequest;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    RecyclerView fetcheduserpayemts;
    String getEmail;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toll_payment_history);
        recyclerView = findViewById(R.id.rv_paymentsData);

        Intent intent = getIntent();
        getEmail = intent.getStringExtra("Email");
        pd = new ProgressDialog(toll_payment_history.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Please wait...");
        pd.setMessage("Updating..");
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
        pd.setIndeterminate(false);
        pd.show();
        fetchPaymentDetails();
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
                    System.out.println("Total transaction records:"+payments);
                    paymentsList = new ArrayList<>();
                    for(int i=0; i< payments.size(); i++) {

                        fetchPaymentsModelClass= new FetchPaymentsModelClass();
                        String getEmailFromJson = payments.get(i).get("email");
                        if(getEmail.equals(getEmailFromJson)){
                            String gettransactionId = payments.get(i).get("id");
                            String gettransactionStatus = payments.get(i).get("status");
                            int amountinpaisa= payments.get(i).get("amount");
                            float paisainfloat =Float.parseFloat(String.valueOf(amountinpaisa));
                            Double trasamount = Double.valueOf(paisainfloat /100);
                            Date transactiondate= payments.get(i).get("created_at");
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM");
                            String gettransactionDate = sdf.format(new Date(String.valueOf(transactiondate)));
                            // converting amount to string
                            String gettransactionamount = String.valueOf(trasamount);
                            fetchPaymentsModelClass.setId(gettransactionId);
                            fetchPaymentsModelClass.setStatus(gettransactionStatus);
                            fetchPaymentsModelClass.setAmount(gettransactionamount);
                            fetchPaymentsModelClass.setDate(gettransactionDate);
                            paymentsList.add(fetchPaymentsModelClass);
                        }else{
                            //Toast.makeText(getApplicationContext(), "No records Found!!", Toast.LENGTH_LONG).show();
                            System.out.println("No records found!!");
                        }

                    }
                    System.out.println("All matched records are "+paymentsList.size());
                    putDataInRecyclerView(paymentsList);
                } catch (RazorpayException | JSONException e) {
                    // Handle Exception
                    System.out.println("execpetion in razor" + e.getMessage());
                }
            }
        });
        thread.start();

    }

    private void putDataInRecyclerView(List<FetchPaymentsModelClass> paymentsList) {
        pd.dismiss();
        Adaptery adaptery = new Adaptery(this, paymentsList);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //your code here to update UI
                recyclerView.setLayoutManager(new LinearLayoutManager(toll_payment_history.this));
                recyclerView.setAdapter(adaptery);
            }
        });




    }


}