package ua.in.zeusapps.snapsafari.models;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Card implements Parcelable {

    @SerializedName("id") @Expose private int id;
    @SerializedName("promo") @Expose private Promo promo;
    @SerializedName("element") @Expose private String element;
    @SerializedName("value") @Expose private int value;
    @SerializedName("title") @Expose private String title;
    @SerializedName("second_title") @Expose private String secondTitle;
    @SerializedName("description") @Expose private String description;
    @SerializedName("power_capability") @Expose private String powerCapability;
    @SerializedName("kind_id") @Expose private String kindID;
    @SerializedName("level") @Expose private int level;

    public int getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Promo getPromo() {
        return promo;
    }
    public void setPromo(Promo promo) {
        this.promo = promo;
    }

    public String getElement() {
        return element;
    }
    public void setElement(String element) {
        this.element = element;
    }

    public int getValue() {
        return value;
    }
    public void setValue(Integer value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getSecondTitle() {
        return secondTitle;
    }
    public void setSecondTitle(String secondTitle) {
        this.secondTitle = secondTitle;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getPowerCapability() {
        return powerCapability;
    }
    public void setPowerCapability(String powerCapability) {
        this.powerCapability = powerCapability;
    }

    public String getKindID() {
        return kindID;
    }
    public void setKindID(String kindID) {
        this.kindID = kindID;
    }

    public int getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(value);
        dest.writeInt(level);
        dest.writeParcelable(promo, flags);
        dest.writeString(element);
        dest.writeString(title);
        dest.writeString(secondTitle);
        dest.writeString(description);
        dest.writeString(powerCapability);
        dest.writeString(kindID);
    }

    private Card(Parcel in) {
        id = in.readInt();
        value = in.readInt();
        level = in.readInt();
        promo = in.readParcelable(Promo.class.getClassLoader());
        element = in.readString();
        title = in.readString();
        secondTitle = in.readString();
        description = in.readString();
        powerCapability = in.readString();
        kindID = in.readString();
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    public String getImage() {
        return "test";
    }
}