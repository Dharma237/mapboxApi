package com.pcs.mapboxapi.directionspojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dharma Sai on 12/1/16.
 */
public class Steps {
    private String duration;

    private String distance;

    private String direction;

    @SerializedName("way_name")
    private String wayName;

    private Maneuver maneuver;

    private String heading;

    private String mode;

    public Steps() {
        maneuver = new Maneuver();
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Maneuver getManeuver() {
        return maneuver;
    }

    public void setManeuver(Maneuver maneuver) {
        this.maneuver = maneuver;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getWayName() {
        return wayName;
    }

    public void setWayName(String wayName) {
        this.wayName = wayName;
    }

    @Override
    public String toString() {
        return "ClassPojo [duration = " + duration + ", distance = " + distance + ", direction = " + direction + ", way_name = " + wayName + ", maneuver = " + maneuver + ", heading = " + heading + ", mode = " + mode + "]";
    }
}
