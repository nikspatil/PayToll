package com.example.paytoll;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class start_tracing_route extends AppCompatActivity {

    EditText Etsource, Etdestination;
    String stype;
    double sourceLat = 0, sourceLong = 0, destinationLat = 0, destinationLong = 0;
    int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_tracing_route);

        Etsource = (EditText) findViewById(R.id.et_source);
        Etdestination = (EditText) findViewById(R.id.et_destination);
        if (!Places.isInitialized()) {
            //Places.initialize(getApplicationContext(), getString(R.string.api_key), Locale.US);
            Places.initialize(getApplicationContext(), getString(R.string.google_maps_key), Locale.US);
        }
        //Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        Etsource.setFocusable(false);
        Etsource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stype = "source";
                System.out.println("inside source on click");
                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS,
                        Place.Field.LAT_LNG);
                System.out.println("after List");
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(
                        start_tracing_route.this);
                System.out.println("calling startactivity");
                startActivityForResult(intent, 100);
            }
        });

        Etdestination.setFocusable(false);
        Etdestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stype = "destination";
                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS,
                        Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(
                        start_tracing_route.this);
                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            if (stype.equals("source")) {
                flag++;
                Etsource.setText(place.getAddress());
                //Get Lat and Logitute of source
                String sSource = String.valueOf(place.getLatLng());
                sSource = sSource.replaceAll("lat/lng:", "");
                sSource = sSource.replace("(", "");
                sSource = sSource.replace(")", "");

                String[] split = sSource.split(",");
                sourceLat = Double.parseDouble(split[0]);
                sourceLong = Double.parseDouble(split[1]);
                System.out.println(sSource);
            } else {
                flag++;
                Etdestination.setText(place.getAddress());
                //Get Lat and Logitute of source
                String sDestination = String.valueOf(place.getLatLng());
                sDestination = sDestination.replaceAll("lat/lng:", "");
                sDestination = sDestination.replace("(", "");
                sDestination = sDestination.replace(")", "");

                String[] split = sDestination.split(",");
                destinationLat = Double.parseDouble(split[0]);
                destinationLong = Double.parseDouble(split[1]);
                System.out.println(sDestination);
            }
        }
    }
}