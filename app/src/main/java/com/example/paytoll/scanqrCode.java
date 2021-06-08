package com.example.paytoll;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.paytoll.util.BarcodeCaptureActivity;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class scanqrCode extends AppCompatActivity {
    public static final int RC_BARCODE_CAPTURE = 9001;
    public static final int REQUEST_CODE_PERMISSION = 1001;

    private Button scanQRButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanqr_code);

        scanQRButton = (Button) findViewById(R.id.scan_btn);

        scanQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openScannerIfCameraPermissionAvailable();
            }
        });
    }

    private void openScannerIfCameraPermissionAvailable() {
        if (ContextCompat.checkSelfPermission(scanqrCode.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(scanqrCode.this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CODE_PERMISSION);
        } else initiateScan();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initiateScan();
                }
            }
        }
    }

    public void initiateScan() {
        Intent intent = new Intent(scanqrCode.this, BarcodeCaptureActivity.class);
        startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == CommonStatusCodes.SUCCESS && requestCode == RC_BARCODE_CAPTURE) {
            if (data == null) return;
            Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
            String scanResult = barcode.displayValue;
            if (scanResult == null) return;
            try {
                scanResult = URLDecoder.decode(scanResult, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String getUPIid = substringBetween(scanResult, "pa=", "&pn");
            System.out.println(getUPIid);
            String getMerchantName = substringBetween(scanResult, "&pn=", "&mc");
            System.out.println(getMerchantName);
            Intent intent1 = new Intent(getApplicationContext(), getPayeeDetails.class);
            intent1.putExtra("UPIid",getUPIid);
            intent1.putExtra("Merchant Name", getMerchantName);
            startActivity(intent1);

        }
    }

    private String substringBetween(String str, String open, String close) {
        if (str == null || open == null || close == null) {
            return null;
        }
        int start = str.indexOf(open);
        if (start != -1) {
            int end = str.indexOf(close, start + open.length());
            if (end != -1) {
                return str.substring(start + open.length(), end);
            }
        }
        return null;
    }
}