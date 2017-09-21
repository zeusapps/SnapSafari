package ua.in.zeusapps.snapsafari.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("id") @Expose private Integer id;
    @SerializedName("title") @Expose private String title;
    @SerializedName("location") @Expose private ARPoint arPoint;
    @SerializedName("start_date") @Expose private String startDate;
    @SerializedName("finish_date") @Expose private String finishDate;
    @SerializedName("card") @Expose private Card card;
    @SerializedName("description") @Expose private String description;

    private String _animationURL;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public ARPoint getArPoint() {
    return arPoint;
}
    public void setArPoint(ARPoint arPoint) {
        this.arPoint= arPoint;
    }

    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }
    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public Card getCard() {
        return card;
    }
    public void setCards(Card card) {
        this.card = card;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnimationURL() {
        return _animationURL;
    };
    public void setAnimationURL(String url) {
        _animationURL = url;
    }

}
