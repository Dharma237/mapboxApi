package com.pcs.mapboxapi.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.maps.MapboxMap;

/**
 * Created by dharmasai on 9/3/16.
 */
public class MapBoxUtils {

    public static final int PERMISSIONS_LOCATION = 0;

    private static final int BASIC_ZOOM_LEVEL = 11;

    /***
     * @param mapboxMap   Initializes map view
     * @param accessToken
     */
    public void initializeMap(@NonNull final MapboxMap mapboxMap, @NonNull String accessToken) {

        //You can set different styles to map view like Dark, Light, Satellite
        mapboxMap.setStyle(Style.MAPBOX_STREETS);

        mapboxMap.setMinZoom(8);

        //setting maximum zoom to the mapbox
        mapboxMap.setMaxZoom(20);

        mapboxMap.getUiSettings().setZoomControlsEnabled(true);

        mapboxMap.getUiSettings().setCompassEnabled(true);

        mapboxMap.setAccessToken(accessToken);
    }

    /***
     * Checks Location is enabled or not and asks for permission
     */
    public void checkLocationIsEnabledOrNot(final Context context, final MapboxMap mapboxMap) {

        if (context != null && mapboxMap != null) {
            // Show user location (purposely not in follow mode)
            if ((ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) ||
                    (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_LOCATION);
            } else {
                mapboxMap.setMyLocationEnabled(true);
            }
        }

    }
}