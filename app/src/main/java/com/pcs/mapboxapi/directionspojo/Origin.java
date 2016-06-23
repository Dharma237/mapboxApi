package com.pcs.mapboxapi.directionspojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dharma Sai on 12/1/16.
 */
public class Origin {
    private Properties properties;

    @SerializedName("type")
    private String originType;

    @SerializedName("geometry")
    private GeometryResponse originGeometry;

    public Origin() {
        properties = new Properties();
        originGeometry = new GeometryResponse();
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getType() {
        return originType;
    }

    public void setType(String type) {
        this.originType = type;
    }

    public GeometryResponse getGeometry() {
        return originGeometry;
    }

    public void setGeometry(GeometryResponse geometry) {
        this.originGeometry = geometry;
    }

    @Override
    public String toString() {
        return "ClassPojo [properties = " + properties + ", type = " + originType + ", geometry = " + originGeometry + "]";
    }


}
