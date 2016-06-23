package com.pcs.mapboxapi.directionspojo;


/**
 * Created by Dharma Sai on 12/1/16.
 */
public class DirectionsResponse {

    private Routes[] routes;

    private Origin origin;

    private String[] waypoints;

    private Destination destination;

    public DirectionsResponse() {
        origin = new Origin();
        destination = new Destination();
    }

    public Routes[] getRoutes() {
        return routes;
    }

    public void setRoutes(Routes[] routes) {
        this.routes = routes;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public String[] getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(String[] waypoints) {
        this.waypoints = waypoints;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "ClassPojo [routes = " + routes + ", origin = " + origin + ", waypoints = " + waypoints + ", destination = " + destination + "]";
    }


}
