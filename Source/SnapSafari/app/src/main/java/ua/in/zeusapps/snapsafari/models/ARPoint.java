package ua.in.zeusapps.snapsafari.models;

import android.content.Intent;
import android.location.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ARPoint {
    @SerializedName("altitude") @Expose private double altitude;
    @SerializedName("floor") @Expose private int floor;
    @SerializedName("description") @Expose private String description;
    @SerializedName("coordinate") @Expose private Coordinate coordinate;

    public double getAltitude() {
        return altitude;
    }
    public void setAltitude(double alt) {
        this.altitude = altitude;
    }

    public int getFloor() {
        return floor;
    }
    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getDescription() {
        return description;
    }
    public void setFloor(String description) {
        this.description = description;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

}
