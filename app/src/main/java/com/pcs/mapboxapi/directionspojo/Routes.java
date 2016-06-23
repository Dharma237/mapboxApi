package com.pcs.mapboxapi.directionspojo;

/**
 * Created by Dharma Sai on 12/1/16.
 */
public class Routes {
    private String summary;

    private String duration;

    private String distance;

    private Steps[] steps;

    private Geometry geometry;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public Steps[] getSteps() {
        return steps;
    }

    public void setSteps(Steps[] steps) {
        this.steps = steps;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    @Override
    public String toString() {
        return "ClassPojo [summary = " + summary + ", duration = " + duration + ", distance = " + distance + ", steps = " + steps + ", geometry = " + geometry + "]";
    }
}
