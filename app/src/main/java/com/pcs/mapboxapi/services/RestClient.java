package com.pcs.mapboxapi.services;

import android.content.Context;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by dharmasai.
 */
public class RestClient implements RequestInterceptor {

    private MapboxServices cpaSerVices;

    public static final String TAG = RestClient.class.getSimpleName();
    public static final String BASE_URL_DIRECTIONS = "https://api.mapbox.com/v4/directions/mapbox.driving";

    public static RestClient getInstance() {
        return new RestClient();
    }

    /**
     * Creating rest adapter
     * for MapboxApi
     *
     * @param context
     */
    public MapboxServices getDirectionsService(Context context) {
        final String cpaBaseUrl = BASE_URL_DIRECTIONS;
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(cpaBaseUrl)
                .setLogLevel(RestAdapter.LogLevel.FULL).setRequestInterceptor(this)
                .setClient(new retrofit.client.UrlConnectionClient()).build();
        cpaSerVices = restAdapter.create(MapboxServices.class);
        return cpaSerVices;
    }


    @Override
    public void intercept(RequestFacade request) {
        //do nothing
    }


}
