package com.example.mapsproject;

import androidx.fragment.app.FragmentActivity;

import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mapsproject.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationConsumer {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    LocationFactory locationFactory;

    private Marker marker;
    private LatLng mCurrentLocation =  new LatLng(0,0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationFactory = new LocationFactory(this, this,  this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mCurrentLocation = mCurrentLocation.equals(new LatLng(0,0)) ? new LatLng(-34, 151) : mCurrentLocation;
        marker = mMap.addMarker(new MarkerOptions().position(mCurrentLocation).title("Minha Localização"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLocation, 15));
    }

    @Override
    public void currentLocation(Location location) {
        mCurrentLocation = new LatLng(location.getLatitude(), location.getLongitude());

        if (mMap != null) {
            marker.setPosition(mCurrentLocation);
            float currentZoom = mMap.getCameraPosition().zoom;

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLocation, currentZoom), 1000, null);

        }
    }
}