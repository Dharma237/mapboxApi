package com.pcs.mapboxapi.directionspojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dharma Sai on 12/1/16.
 */
public class Geometry {

    @SerializedName("type")
    private String geometryType;

    private String[][] coordinates;

    public String getType() {
        return geometryType;
    }

    public void setType(String type) {
        this.geometryType = type;
    }

    public String[][] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String[][] coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "ClassPojo [type = " + geometryType + ", coordinates = " + coordinates + "]";
    }
}