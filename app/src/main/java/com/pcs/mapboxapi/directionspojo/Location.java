package com.pcs.mapboxapi.directionspojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dharma Sai on 12/1/16.
 */
public class Location {

    @SerializedName("type")
    private String locationType;

    private String[] coordinates;

    public String getType() {
        return locationType;
    }

    public void setType(String type) {
        this.locationType = type;
    }

    public String[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String[] coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "ClassPojo [type = " + locationType + ", coordinates = " + coordinates + "]";
    }
}
