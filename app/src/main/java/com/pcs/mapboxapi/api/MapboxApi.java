package com.pcs.mapboxapi.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.pcs.mapboxapi.R;
import com.pcs.mapboxapi.custommarker.CustomMarker;
import com.pcs.mapboxapi.directionspojo.Destination;
import com.pcs.mapboxapi.directionspojo.DirectionsResponse;
import com.pcs.mapboxapi.directionspojo.Geometry;
import com.pcs.mapboxapi.directionspojo.GeometryResponse;
import com.pcs.mapboxapi.directionspojo.Origin;
import com.pcs.mapboxapi.directionspojo.Routes;
import com.pcs.mapboxapi.directionspojo.Steps;
import com.pcs.mapboxapi.services.RestClient;
import com.pcs.mapboxapi.utils.ImageUtils;
import com.pcs.mapboxapi.utils.MapBoxUtils;
import com.pcs.mapboxapi.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by dharmasai on 9/3/16.
 */
public class MapboxApi {

    private static final String TAG = MapboxApi.class.getSimpleName();
    private static final int PATH_WIDTH = 6;
    private static final int ZOOM_LEVEL = 17;
    private static final int TWO = 2;
    private static final int SIXTY = 60;
    private static final float DISTANCE_MULTIPLIER = 0.00062137F;
    private static final int THOUSAND = 1000;
    private static final int THREE_THOUSAND_SIX_HUNDRED = 3600;
    public static final String MAPBOX_ACCESS_TOKEN = "pk.eyJ1IjoiYW5pdGhhIiwiYSI6ImNpandwZzVsZzBtd3d2Mm01czFvZ2hnb24ifQ.GKDUFD8mPIQwwOALNveP5g";

    protected Marker currentMarker;

    //center coordinate of map
    protected LatLng centerCoordinate = new LatLng();

    //steps array used for finding steps between path
    protected List<Steps> stepsArray = new ArrayList<>();

    protected float distance;
    protected float duration;
    protected String color = null;
    protected int width = 0;
    protected int sourceMarkerImage = -1;
    protected int destinationMarkerImage = -1;

    /****
     * @param sourceMarkerImage      Not zero
     * @param destinationMarkerImage Not zero
     * @param pathColor
     * @param pathWidth
     */
    public void setPathAttributes(final int sourceMarkerImage,
                                  final int destinationMarkerImage,
                                  final String pathColor,
                                  final int pathWidth) {

        checkPathAttributesNullOrNot(sourceMarkerImage, destinationMarkerImage);
        color = pathColor;
        width = pathWidth;
        this.sourceMarkerImage = sourceMarkerImage;
        this.destinationMarkerImage = destinationMarkerImage;
    }

    /***
     * Checks sourceMarker Image or destination Marker Image is null
     * @param sourceMarkerImage
     * @param destinationMarkerImage
     */
    protected void checkPathAttributesNullOrNot(int sourceMarkerImage, int destinationMarkerImage) {
        if (sourceMarkerImage == 0) {
            throw new NullPointerException("Source marker image is null");
        }
        if (destinationMarkerImage == 0) {
            throw new NullPointerException("Destination marker image is null");
        }
    }

    /***
     * creates  and starts direction service call objects
     *
     * @param context Not null
     * @param mapView Not null
     * @param accessToken Not null
     * @param sourceCoordinates Not null
     * @param destinationCoordinates Not null
     */
    public void startDirectionsServiceCall(@NonNull final Context context,
                                           @NonNull final MapboxMap mapboxMap,
                                           @NonNull final MapView mapView,
                                           @NonNull final String accessToken,
                                           @NonNull final LatLng sourceCoordinates,
                                           @NonNull final LatLng destinationCoordinates) {


        checkDirectionServiceCallParametersNotNull(context, mapView, accessToken, sourceCoordinates, destinationCoordinates);

        MapBoxUtils mapBoxUtils = new MapBoxUtils();

        mapBoxUtils.initializeMap(mapboxMap,accessToken);

        if (NetworkUtil.isOnline(context)) {

            RestClient.getInstance().getDirectionsService(context).getDirections(sourceCoordinates.getLongitude(),
                    sourceCoordinates.getLatitude(), destinationCoordinates.getLongitude(), destinationCoordinates.getLatitude(),
                    accessToken, new Callback<DirectionsResponse>() {

                        @Override
                        public void success(DirectionsResponse directionsResponse, Response response) {
                            setSuccess(context, mapboxMap, directionsResponse);
                        }

                        @Override
                        public void failure(RetrofitError error) {

                            setFailure(context, error);
                        }
                    });
        } else {
            Log.e(TAG, "You are in offline. Network is not there");
        }

    }

    /****
     * Checks direction service call parameters are null or not
     * @param context
     * @param mapView
     * @param accessToken
     * @param sourceCoordinates
     * @param destinationCoordinates
     */
    protected void checkDirectionServiceCallParametersNotNull(@NonNull Context context, @NonNull MapView mapView,
                                                            @NonNull String accessToken, @NonNull LatLng sourceCoordinates,
                                                            @NonNull LatLng destinationCoordinates) {

        if (context == null || mapView == null) {
            throw new NullPointerException("context or map view are null");
        }

        if (sourceCoordinates == null || destinationCoordinates == null) {
            throw new NullPointerException("sourceCoordiantes or destinationCoordiantes are null");
        }

        if (accessToken == null) {
            throw new NullPointerException("map view access Token null");
        }
    }

    /****
     * success method after directions service call
     *
     * @param context
     * @param mapboxMap
     * @param directionsResponse
     */
    protected void setSuccess(final Context context, final MapboxMap mapboxMap, final DirectionsResponse directionsResponse) {

        checkSuccessParametersNotNull(context, mapboxMap, directionsResponse);

        Routes[] routesArray = directionsResponse.getRoutes();

        if (color == null) {
            //default path color
            color = "#0000FF";
        }

        if (width <= 1) {
            width = PATH_WIDTH;
        }

        for (final Routes routes : routesArray) {

            if (routes != null) {

                Steps[] steps = routes.getSteps();

                Geometry geometry = routes.getGeometry();

                String[][] listOfCoordiantes = geometry.getCoordinates();

                distance = calculateDistance(routes);

                duration = calculateDuration(routes);

                List<LatLng> pathPointsArray = addPathPointsArray(listOfCoordiantes);

                drawPolyline(context, mapboxMap, pathPointsArray, width, color);

                addStepsArray(steps);
            }

        }

        Origin origin = directionsResponse.getOrigin();

        if (origin != null) {
            generateMarkerForOrigin(context, mapboxMap, origin);
        }


        Destination destination = directionsResponse.getDestination();

        if (destination != null) {
            generateMarkerForDestination(context, mapboxMap, destination);
        }

        moveAndSetZoom(mapboxMap,centerCoordinate,ZOOM_LEVEL);
    }

    public void moveAndSetZoom(MapboxMap mapboxMap, LatLng centerCoordinateLatLang, int minZoom) {
        mapboxMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                .target(centerCoordinateLatLang)
                .zoom(minZoom)
                .build()));

    }

    /****
     * Checks success parameters are null or not
     * @param context
     * @param mapboxMap
     * @param directionsResponse
     */
    protected void checkSuccessParametersNotNull(final Context context, final MapboxMap mapboxMap,
                                                 final DirectionsResponse directionsResponse) {
        if (context == null || mapboxMap == null) {
            throw new NullPointerException("context or map view are getting null");
        }

        if (directionsResponse == null) {
            throw new NullPointerException("directions response is getting null");
        }
    }

    /***
     * @param routes
     * @return distance in string format from source to destination
     */
    protected String setDistance(Routes routes) {
        String distanceToDestination = routes.getDistance();
        if (distanceToDestination != null) {
            return distanceToDestination;

        }
        return "";
    }

    /****
     * generate marker for origin
     *
     * @param context
     * @param mapboxMap
     * @param origin
     */
    protected void generateMarkerForOrigin(Context context, MapboxMap mapboxMap, Origin origin) {

        GeometryResponse response = origin.getGeometry();
        String[] coordinateValues = response.getCoordinates();
        if (coordinateValues != null && coordinateValues.length >= TWO) {
            double longitude = Double.valueOf(coordinateValues[0]);
            double latitude = Double.valueOf(coordinateValues[1]);
            centerCoordinate.setLatitude(latitude);
            centerCoordinate.setLongitude(longitude);

            if (sourceMarkerImage == -1) {
                sourceMarkerImage = R.drawable.ic_current_location;
            }

            createMarker(context, mapboxMap, new LatLng(latitude, longitude),
                    context.getResources().getString(R.string.my_location),
                    sourceMarkerImage);
        } else {
            Log.e(TAG, "coordinates are getting null or size is zero in generateMarkerForOrigin() method");
        }
    }

    /*****
     * Creates Custom Marker
     * Customize your marker by CustomMarker class custommarker xml file
     * @param latlang
     * @param label
     * @return Marker can be null
     */
    public Marker createMarker(final Context context, MapboxMap mapboxMap, LatLng latlang, String label,
                               int markerImageValue) {

        if (context == null || mapboxMap == null || latlang == null) {
            throw new NullPointerException("context or mapview or latlang or markerImage is null");
        }

        CustomMarker customMarker = new CustomMarker().invoke(context);
        ImageView markerImage = customMarker.getMarkerImage();
        TextView priceOfParkingSpaceTxt = customMarker.getPriceOfParkingSpaceTxt();
        View snapView = customMarker.getSnapView();
        priceOfParkingSpaceTxt.setVisibility(View.GONE);
        IconFactory mIconFactory = IconFactory.getInstance(context);
        Drawable mIconDrawable;

        markerImage.setImageResource(markerImageValue);

        ImageUtils utils = new ImageUtils();

        Bitmap markersImages = utils.loadBitmapFromView(snapView);

        mIconDrawable = new BitmapDrawable(context.getResources(), markersImages);

        Icon icon = mIconFactory.fromDrawable(mIconDrawable);

        if (mapboxMap != null) {
            return mapboxMap.addMarker(new MarkerOptions()
                    .snippet(label)
                    .icon(icon)
                    .position(latlang));

        } else {
            Log.e(TAG, "createMarker : mapview is null");
            return null;
        }
    }

    /****
     * sets durartion
     * @param routes
     * @return duration in seconds
     */
    protected String setDuration(Routes routes) {
        String durationToDestination = routes.getDuration();
        if (durationToDestination != null) {
            return durationToDestination;
        }
        return "";

    }

    /***
     * Draws path from current location to parking space location
     *
     * @param context
     * @param mapboxMap
     * @param pathPointsArray
     * @param width
     */
    public void drawPolyline(final Context context, final MapboxMap mapboxMap, final List<LatLng> pathPointsArray,
                             final int width, final String color) {

        if (context == null || mapboxMap == null) {
            throw new NullPointerException("context or map view are getting null");
        }

        mapboxMap.addPolyline(new PolylineOptions()
                .add(pathPointsArray.toArray(new LatLng[pathPointsArray.size()]))
                .color(Color.parseColor(color))
                .width(width));

    }

    /****
     * Set duration from current location to parking space
     *
     * @param routes
     */
    protected float calculateDuration(Routes routes) {
        String durationInSeconds = setDuration(routes);
        if (durationInSeconds != null && !TextUtils.isEmpty(durationInSeconds)) {
            return Float.valueOf(durationInSeconds);
        } else {
            Log.e(TAG, "duration is getting null or empty in getDuration() method");
        }
        return -1;
    }

    /****
     * generates distance from current location to destination location
     *
     * @param routes
     */
    protected float calculateDistance(Routes routes) {

        String distanceInMiles = setDistance(routes);

        if (!TextUtils.isEmpty(distanceInMiles)) {
            return Float.valueOf(distanceInMiles);
        } else {
            Log.e(TAG, "distance is getting null or empty in calculateDistance() method");
        }
        return -1;
    }

    /****
     * Creates steps array between current location to parking space location
     *
     * @param steps
     */
    private void addStepsArray(Steps[] steps) {
        for (Steps step : steps) {
            if (step != null) {
                stepsArray.add(step);
            } else {
                Log.e(TAG, "step is getting null in addStepsArray() method");
            }

        }
    }

    /****
     * Creates path points array from current locaiton to parking space location
     *
     * @param listOfCoordiantes
     * @return List<LatLang></> values
     */
    protected List<LatLng> addPathPointsArray(String[][] listOfCoordiantes) {
        List<LatLng> pathPointsArray = new ArrayList<>();
        for (String[] row : listOfCoordiantes) {
            if (row != null) {
                double longitude = Double.valueOf(row[0]);
                double latitude = Double.valueOf(row[1]);
                pathPointsArray.add(new LatLng(latitude, longitude));
            } else {
                Log.e(TAG, "row is null in addPathPointsArray() method");
            }
        }
        if (!pathPointsArray.isEmpty()) {
            return pathPointsArray;
        }
        return pathPointsArray;
    }


    /***
     * generate marker for destination
     *
     * @param context
     * @param mapboxMap
     * @param destination
     */
    private void generateMarkerForDestination(Context context, MapboxMap mapboxMap, Destination destination) {
        GeometryResponse response = destination.getGeometry();
        String[] listOfCoordinates = response.getCoordinates();
        if (listOfCoordinates.length >= TWO) {
            double longitude = Double.valueOf(listOfCoordinates[0]);
            double latitude = Double.valueOf(listOfCoordinates[1]);
            if (destinationMarkerImage == -1) {
                destinationMarkerImage = R.drawable.ic_destination_location;
            }
            createMarker(context, mapboxMap, new LatLng(latitude, longitude),
                    "Hello", destinationMarkerImage);
        } else {
            Log.e(TAG, "coordinates are getting null or size is zero in generateMarkerForDestination() method");
        }
    }

    /***
     * Failure method while getting user tickets
     *
     * @param error
     */
    private void setFailure(final Context context, final RetrofitError error) {

        if (context != null && error != null && error.getKind() == RetrofitError.Kind.NETWORK) {
            Toast.makeText(context, "Problem with network connection. Please Try again...", Toast.LENGTH_LONG)
                    .show();
        } else {
            Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    /****
     * Get duration in seconds from source to destination
     *
     * @return -1 in float if duration in seconds is not there
     */
    public float getDurationInSeconds() {
        if (duration == -1) {
            return -1;
        }
        return Float.valueOf(duration);
    }

    /****
     * * Get duration in minutes from source to destination
     *
     * @return -1 in float if duration in minutes is not there
     */
    public float getDurationInMinutes() {
        if (duration == -1) {
            return -1;
        }
        return Float.valueOf(duration) / SIXTY;
    }

    /****
     * * Get duration in hours from source to destination
     *
     * @return -1 in float if duration is hours not there
     */
    public float getDurationInHours() {
        if (duration == -1) {
            return -1;
        }
        return (duration % THREE_THOUSAND_SIX_HUNDRED) / SIXTY;
    }

    /****
     * Get distance in meters from source to destination
     *
     * @return -1 in float if distance is not there between source and destination
     */
    public float getDistanceInMetres() {
        if (distance == -1) {
            return -1;
        }
        return Float.valueOf(distance);
    }

    /****
     * Get distance in miles from source to destination
     *
     * @return -1 in float if distance is not there between source and destination
     */
    public float getDistanceInMiles() {
        if (distance == -1) {
            return -1;
        }
        float distanceValue = Float.valueOf(distance);
        return distanceValue * DISTANCE_MULTIPLIER;
    }

    /****
     * Get distance in kilometers from source to destination
     *
     * @return -1 in float if distance is not there between source and destination
     */
    public float getDistanceInKilometres() {
        if (distance == -1) {
            return -1;
        }
        float distanceValue = Float.valueOf(distance);
        return distanceValue / THOUSAND;
    }

    /****
     * Get steps between source and destination
     *
     * @return List<Steps></> if steps are not there between source and destinatioin
     */
    public List<Steps> getStepsBetweenSourceAndDestination() {
        if (stepsArray.isEmpty()) {
            return stepsArray;
        }
        return stepsArray;
    }
}
