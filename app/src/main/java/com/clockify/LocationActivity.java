package com.clockify;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.EditText;

import com.clockify.Adapter.LocationAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity {

    private List<String> listLocation = new ArrayList<>();
    private LocationAdapter adapter;
    EditText searchLocation;
    private LocationManager locationManager;
    private double latitude;
    private double longitude;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private static final int REQUEST_LOCATION = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);


        adapter = new LocationAdapter(this, listLocation);
        listLocation.add("Bandung");
        listLocation.add("jakarta");
        listLocation.add("Bandung");
        listLocation.add("Bandung");
        listLocation.add("Bandung");

        searchLocation = findViewById(R.id.search_location);
        RecyclerView recyclerView = findViewById(R.id.location);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(LocationActivity.this);
        statusCheck();
        //LatLng Mylocation = new LatLng(location.getLatitude(),location.getLongitude());


    }

    @Override
    protected void onStart() {
        super.onStart();
        searchLocation.setText(0 + ", " + 0);
        try {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                searchLocation.setText(latitude + ", " + longitude);
                            }
                        }
                    });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        checkPermission();
    }

    private void checkPermission() {
        ArrayList<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (permissionList.size() > 0) {
            searchLocation.setText(0 + ", " + 0);
            String[] permissionArray = permissionList.toArray(new String[]{});
            ActivityCompat.requestPermissions(LocationActivity.this, permissionArray, REQUEST_LOCATION);
        } else {
            try {
                fusedLocationProviderClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                    searchLocation.setText(latitude + ", " + longitude);
                                }
                            }
                        });
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    public void statusCheck() {
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            buildAlertMessageNoGps();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Check Location")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}