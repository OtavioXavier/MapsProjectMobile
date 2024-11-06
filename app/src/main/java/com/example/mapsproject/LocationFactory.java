package com.example.mapsproject;

import android.app.Activity;
import android.content.Context;
import android.Manifest;
import android.content.pm.PackageManager;

import android.location.Location;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationFactory {
    private static final int REQUEST_LOCATION_UPDATES = 1;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private Context context;
    private Activity activity;
    private LocationConsumer locationConsumer;

    public LocationFactory(Context context, Activity activity, LocationConsumer locationConsumer) {
        this.context = context;
        this.activity = activity;
        this.locationConsumer = locationConsumer;
        if(checkPermission()) {
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
            mLocationRequest = new LocationRequest.Builder(5*1000).build();
            mLocationCallback = new LocationCallback() {

                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {

                    super.onLocationResult(locationResult);
                    Location location = locationResult.getLastLocation();
                    locationConsumer.currentLocation(location);
                }
            };

            mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_UPDATES);
        }
    }

    private boolean checkPermission() {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

}
