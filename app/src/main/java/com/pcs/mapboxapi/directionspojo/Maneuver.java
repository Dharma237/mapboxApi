package com.pcs.mapboxapi.directionspojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dharma Sai on 12/1/16.
 */
public class Maneuver {
    private Location location;

    private String instruction;

    @SerializedName("type")
    private String maneuverType;

    public Maneuver() {
        location = new Location();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getType() {
        return maneuverType;
    }

    public void setType(String type) {
        this.maneuverType = type;
    }

    @Override
    public String toString() {
        return "ClassPojo [location = " + location + ", instruction = " + instruction + ", type = " + maneuverType + "]";
    }

}