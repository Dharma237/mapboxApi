package com.pcs.mapboxapi.directionspojo;


import com.google.gson.annotations.SerializedName;

public class Destination {

    private Properties properties;

    @SerializedName("type")
    private String destinationType;

    private GeometryResponse geometry;

    public Destination() {
        properties = new Properties();
        geometry = new GeometryResponse();
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getType() {
        return destinationType;
    }

    public void setType(String type) {
        this.destinationType = type;
    }

    public GeometryResponse getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryResponse geometry) {
        this.geometry = geometry;
    }

    @Override
    public String toString() {
        return "ClassPojo [properties = " + properties + ", type = " + destinationType + ", geometry = " + geometry + "]";
    }

}