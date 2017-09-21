package ua.in.zeusapps.snapsafari.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kovalskiy on 9/20/17.
 */

public class Coordinate {
    @SerializedName("latitude") @Expose private double latitude;
    @SerializedName("longitude") @Expose private double longitude;

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
