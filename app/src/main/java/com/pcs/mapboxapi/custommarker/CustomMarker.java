package com.pcs.mapboxapi.custommarker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcs.mapboxapi.R;

public class CustomMarker {

    private View snapView;
    private ImageView markerImage;
    private TextView priceOfParkingSpaceTxt;

    public View getSnapView() {
        return snapView;
    }

    public ImageView getMarkerImage() {
        return markerImage;
    }

    public TextView getPriceOfParkingSpaceTxt() {
        return priceOfParkingSpaceTxt;
    }

    public CustomMarker invoke(Context mContext) {
        snapView = LayoutInflater.from(mContext).inflate(R.layout.custommarker, null);
        markerImage = (ImageView) snapView.findViewById(R.id.parking_icon);
        priceOfParkingSpaceTxt = (TextView) snapView.findViewById(R.id.parking_cost);
        return this;
    }
}