package com.pcs.mapboxapi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.pcs.mapboxapi.api.MapboxApi;
import com.pcs.mapboxapi.directionspojo.Steps;
import com.pcs.mapboxapi.utils.MapBoxUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    @Bind(R.id.toolbar)
    protected Toolbar toolbar;

    @Bind(R.id.main_layout)
    protected RelativeLayout mainLayout;

    protected MapView mapView;

    protected MapboxApi mapboxApi;

    protected MapboxMap mapboxMap;

    public static final LatLng SOURCECOORDINATES = new LatLng(17.686815, 83.218483);
    public static final LatLng DESTINATIONCOORDINATES = new LatLng(17.387140, 78.491684);
    public static final LatLng CUSTOM_MARKER_LATLANG = new LatLng(16.5062, 80.648);
    private MapBoxUtils mapboxUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        mapboxApi = new MapboxApi();
        mapboxUtils = new MapBoxUtils();

        mapView = (MapView) mainLayout.findViewById(R.id.mapview);
        mainLayout.findViewById(R.id.distance_btn).setOnClickListener(this);
        mainLayout.findViewById(R.id.duration_btn).setOnClickListener(this);
        mainLayout.findViewById(R.id.steps_btn).setOnClickListener(this);
        mainLayout.findViewById(R.id.custom_marker_btn).setOnClickListener(this);

        mapView.onCreate(savedInstanceState);
    }

    private void loadDirections() {
        mapboxApi.setPathAttributes(R.drawable.default_marker, R.drawable.attribution_logo, null, 0);
        mapboxApi.startDirectionsServiceCall(MainActivity.this, mapboxMap, mapView,
                MainActivity.this.getResources().getString(R.string.acessToken),
                SOURCECOORDINATES, DESTINATIONCOORDINATES);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.distance_btn:
                showDistance(v);
                break;

            case R.id.duration_btn:
                showDuration(v);
                break;

            case R.id.steps_btn:
                List<Steps> stepsList = mapboxApi.getStepsBetweenSourceAndDestination();
                break;

            case R.id.custom_marker_btn:
                createCustomMarker();
                break;

            default:
                break;
        }

    }

    private void createCustomMarker() {
        mapboxApi.createMarker(MainActivity.this, mapboxMap, CUSTOM_MARKER_LATLANG,
                "Vijayawada", R.mipmap.ic_launcher);
    }

    private void showDistance(View v) {
        float distance = mapboxApi.getDistanceInMetres();
        if (distance != -1) {
            showSnackBar(v, String.valueOf(distance));
        }
    }

    private void showDuration(View v) {
        float duration = mapboxApi.getDurationInSeconds();
        if (duration != -1) {
            showSnackBar(v, String.valueOf(duration));
        }
    }

    protected void showSnackBar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mapView.getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        mapView.onDestroy();
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        loadDirections();
        mapboxUtils.initializeMap(mapboxMap, MapboxApi.MAPBOX_ACCESS_TOKEN);
    }
}
