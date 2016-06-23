package com.pcs.mapboxapi.directionspojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dharma Sai on 13/1/16.
 */
public class GeometryResponse {

    @SerializedName("type")
    private String geometryResponseType;

    private String[] coordinates;

    public String getType() {
        return geometryResponseType;
    }

    public void setType(String type) {
        this.geometryResponseType = type;
    }

    public String[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String[] coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "ClassPojo [type = " + geometryResponseType + ", coordinates = " + coordinates + "]";
    }

}
