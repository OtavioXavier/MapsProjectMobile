package com.example.mapsproject;

import android.location.Location;

public interface LocationConsumer {
    void currentLocation(Location location);
}
