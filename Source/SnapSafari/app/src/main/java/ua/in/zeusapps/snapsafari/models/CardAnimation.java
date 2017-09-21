package ua.in.zeusapps.snapsafari.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kovalskiy on 9/21/17.
 */

public class CardAnimation {
    @SerializedName("animation_path") @Expose private String animationURL;

    public String getAnimationURL(){
        return animationURL;
    };
    public void setAnimationURL(String animationURL){
        this.animationURL = animationURL;
    };
}