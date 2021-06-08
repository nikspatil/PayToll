package com.example.paytoll;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.paytoll.DirectionModules.DirectionFinderListener;
import com.example.paytoll.DirectionModules.Route;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import androidx.core.app.ActivityCompat;

import com.example.paytoll.DirectionModules.DirectionFinder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class point_tollnaka extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener {
    EditText Etsource, Etdestination, Ettollplaza, etTOLL;
    String stype;
    GoogleMap mMap;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    final int REQUEST_CODE = 101;
    int flag = 0;
    Button showRoute;
    List<Marker> originMarkers = new ArrayList<>();
    List<Marker> destinationMarkers = new ArrayList<>();
    List<Polyline> polylinePaths = new ArrayList<>();
    ProgressDialog progressDialog;
    FloatingActionButton fabbtn;

    ArrayList<LatLng> tollMarkers =  new ArrayList<LatLng>();
    List<LatLng> sourceMarkers = new ArrayList<LatLng>();
    List<LatLng> destiMarkers = new ArrayList<LatLng>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_tollnaka);
        //MapsInitializer.initialize(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Etsource = (EditText) findViewById(R.id.et_source);
        Etdestination = (EditText) findViewById(R.id.et_destination);
        showRoute = (Button)findViewById(R.id.showdirection);
        fabbtn = (FloatingActionButton) findViewById(R.id.fab);
        etTOLL = (EditText)findViewById(R.id.et_tollplaza);


        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));

        Etsource.setFocusable(false);
        Etsource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tollMarkers.clear();
                mMap.clear();
                stype = "source";
                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS,
                        Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(
                        point_tollnaka.this);
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
                        point_tollnaka.this);
                startActivityForResult(intent, 100);
            }
        });

        etTOLL.setFocusable(false);
        etTOLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stype = "toll";
                System.out.println("Inside etoll edittext");
                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS,
                        Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(
                        point_tollnaka.this);
                startActivityForResult(intent, 100);
                System.out.println("got the details");

            }
        });
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();

        showRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest();
            }
        });

        fabbtn.setOnClickListener(new View.OnClickListener() {
            String origin = Etsource.getText().toString();
            String destination = Etdestination.getText().toString();
            String tollplaza  = etTOLL.getText().toString();
            @Override
            public void onClick(View view) {
//                if(origin.isEmpty()){
//                    Toast.makeText(point_tollnaka.this, "Please enter source location", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                if(destination.isEmpty()){
//                    Toast.makeText(point_tollnaka.this, "Please enter destination location", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                if(tollplaza.isEmpty()){
//                    Toast.makeText(point_tollnaka.this,"Please enter toll plaza point", Toast.LENGTH_LONG).show();
//                }else{
                startActivity(new Intent(point_tollnaka.this , scanqrCode.class));
//                }

            }
        });

    }

    private void showToll() {
        String origin = Etsource.getText().toString();
        String destination = Etdestination.getText().toString();
        String tollplaza  = etTOLL.getText().toString();
        if(origin.isEmpty()){
            Toast.makeText(this, "Please enter source location", Toast.LENGTH_LONG).show();
            return;
        }
        if(destination.isEmpty()){
            Toast.makeText(this, "Please enter destination location", Toast.LENGTH_LONG).show();
            return;
        }
        if(tollplaza.isEmpty()){
            Toast.makeText(this,"Please enter toll plaza point", Toast.LENGTH_LONG).show();
        }

        MarkerOptions tolloptions = new MarkerOptions();
        for(int i=0;i<tollMarkers.size();i++)
        {
            tolloptions.position(tollMarkers.get(i));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tollMarkers.get(i), 10));
            mMap.addMarker(tolloptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.toll_paid_icon)));
        }
    }




    private void sendRequest() {
        String origin = Etsource.getText().toString();
        String destination = Etdestination.getText().toString();
        if(origin.isEmpty()){
            Toast.makeText(this, "Please enter source location", Toast.LENGTH_LONG).show();
            return;
        }
        if(destination.isEmpty()){
            Toast.makeText(this, "Please enter destination location", Toast.LENGTH_LONG).show();
            return;
        }
        try{
            System.out.println("Inside direction finder");
            new DirectionFinder(point_tollnaka.this, origin, destination).execute();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }
    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);
        System.out.println("Inside direction finder start");
        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        System.out.println("Inside direction finder succes");
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            System.out.println("inside route for loop");
            System.out.println("Routes:"+route);
            System.out.println(route.duration);

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 10));
            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(15);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            if (stype.equals("source")) {
                flag++;
                Etsource.setText(place.getAddress());
                sourceMarkers.clear();
                sourceMarkers.add(place.getLatLng());

            } else if(stype.equals("destination"))  {
                flag++;
                Etdestination.setText(place.getAddress());
                destiMarkers.clear();
                destiMarkers.add(place.getLatLng());
            }else{
                System.out.println("Inside toll plaza on activity result");
                flag++;
                etTOLL.setText(place.getAddress());
                //tollMarkers.clear();
                tollMarkers.add(place.getLatLng());

                System.out.println("Toll markers list"+tollMarkers);
                showToll();
            }
        }
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(point_tollnaka.this);
                }
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng latlong = new LatLng(16.691307, 74.244865);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong, 10));
        originMarkers.add(mMap.addMarker(new MarkerOptions()
                .title("Yor are here")
                .position(latlong)));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                }
                break;
        }
    }



}