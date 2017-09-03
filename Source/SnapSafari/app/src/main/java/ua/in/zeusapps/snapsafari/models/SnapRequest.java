package ua.in.zeusapps.snapsafari.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SnapRequest {
    @Expose
    @SerializedName("catch_cart_id")
    private int _id;

    public SnapRequest(int id) {
        _id = id;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }
}
