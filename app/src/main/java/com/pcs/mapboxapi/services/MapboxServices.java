package com.pcs.mapboxapi.services;


import com.pcs.mapboxapi.directionspojo.DirectionsResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by pcs-05.
 */
public interface MapboxServices {

    @GET("/{sourceLongitude},{sourceLatitude};{destinationLongitude},{destinationLatitude}.json")
    void getDirections(@Path("sourceLongitude") double sourceLongitude,
                       @Path("sourceLatitude") double sourceLatitude,
                       @Path("destinationLongitude") double destinationLongitude,
                       @Path("destinationLatitude") double destinationLatitude,
                       @Query("access_token") String accessToken,
                       Callback<DirectionsResponse> callback);
}
