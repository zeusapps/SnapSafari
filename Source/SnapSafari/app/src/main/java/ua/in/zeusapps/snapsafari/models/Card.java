package ua.in.zeusapps.snapsafari.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Card implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("promo")
    @Expose
    private Promo promo;
    @SerializedName("element")
    @Expose
    private String element;
    @SerializedName("value")
    @Expose
    private int value;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("second_title")
    @Expose
    private String secondTitle;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("power_capability")
    @Expose
    private String powerCapability;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("constant_card")
    @Expose
    private int constantCard;

    public Card(){

    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getConstantCard() {
        return constantCard;
    }

    public void setConstantCard(Integer constantCard) {
        this.constantCard = constantCard;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(value);
        dest.writeInt(constantCard);
        dest.writeParcelable(promo, flags);
        dest.writeString(element);
        dest.writeString(title);
        dest.writeString(secondTitle);
        dest.writeString(description);
        dest.writeString(powerCapability);
        dest.writeString(image);
    }

    private Card(Parcel in) {
        id = in.readInt();
        value = in.readInt();
        constantCard = in.readInt();
        promo = in.readParcelable(Promo.class.getClassLoader());
        element = in.readString();
        title = in.readString();
        secondTitle = in.readString();
        description = in.readString();
        powerCapability = in.readString();
        image = in.readString();
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
}