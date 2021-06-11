package com.example.paytoll;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.PaymentData;

import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class getPayeeDetails extends AppCompatActivity implements PaymentResultListener {

    TextView upiid, merchantName;
    EditText payamount;
    Button pay;
    String getMerchantName;

    public getPayeeDetails() throws RazorpayException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_payee_details);

        upiid = (TextView)findViewById(R.id.upii_id);
        merchantName = (TextView)findViewById(R.id.merchant_name);
        payamount = (EditText) findViewById(R.id.payamount);

        pay = (Button)findViewById(R.id.paybtn);
//        fetchpayments = (Button)findViewById(R.id.fetch);
//
//        fetchpayments.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fetchPaymentDetails();
//            }
//        });


        Intent intent = getIntent();
        String getUPIID = intent.getStringExtra("UPIid");
        getMerchantName = intent.getStringExtra("Merchant Name");
        upiid.setText(getUPIID);
        merchantName.setText("Paying "+getMerchantName);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(payamount.getText().toString().equals("")){
                    Toast.makeText(getPayeeDetails.this, "Amount is empty", Toast.LENGTH_LONG).show();
                }
                else{
                    startPayment();
                }
            }
        });

    }

    private void startPayment() {
        final Activity activity = this;
        final Checkout co = new Checkout();


        try {
            JSONObject options = new JSONObject();
            options.put("name", getMerchantName);
            options.put("description", "Toll Charges");
            options.put("image", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.123rf.com%2Fphoto_100618216_stock-vector-entry-through-the-barrier-which-is-raised-to-pass-car-toll-gate-with-reception-booth-checkpoint-to-r.html&psig=AOvVaw2Le1GtzI3XIVrMlKV6WtPL&ust=1623070589462000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCMid86qHg_ECFQAAAAAdAAAAABAO");
            options.put("currency", "INR");

            String getenteramount = payamount.getText().toString();

            double total = Double.parseDouble(getenteramount);
            total = total * 100;
            options.put("amount", total);
            JSONObject preFill = new JSONObject();
            preFill.put("contact", upiid);
            options.put("prefill", preFill);
            co.open(activity, options);
        }catch (Exception e){
            Log.e("Chekcout error", "checkout Exception:", e);
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        Toast.makeText(this, "Payement successfull", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getPayeeDetails.this , paymentsuccessfull.class));
    }

    @Override
    public void onPaymentError(int code, String response) {
        try {
            //fetchPaymentDetails();
            Toast.makeText(this, "Payment error try again ", Toast.LENGTH_LONG).show();
        }
        catch (Exception ex){
            Log.e("Payment error", "Exception on failed payment", ex);
        }
    }


    /*
    @Override
    public void onPaymentSuccess(String razorpayPaymentID, PaymentData paymentData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment Status");
        builder.setMessage("Successfull");
        System.out.println("payid"+razorpayPaymentID);
        Payment payment = null;
        try {
            payment = razorpay.Payments.fetch("payment_id");
        } catch (RazorpayException e) {
            e.printStackTrace();
        }
// The the Entity.get("attribute_key") method has flexible return types depending on the attribute
        int amount = payment.get("amount");
        String id = payment.get("id");
        Date createdAt = payment.get("created_at");
        System.out.println(amount +id + createdAt);
        startActivity(new Intent(getPayeeDetails.this , toll_payment_history.class));
        finish();
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        System.out.println(s);
        System.out.println(paymentData);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment ID");
        builder.setMessage("Failed");
    }
    */

}