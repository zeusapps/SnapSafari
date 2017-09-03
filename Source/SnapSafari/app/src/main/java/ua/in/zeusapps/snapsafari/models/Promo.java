package ua.in.zeusapps.snapsafari.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ProgressBar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Promo implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("term")
    @Expose
    private String term;
    @SerializedName("promo_code")
    @Expose
    private String promoCode;
    @SerializedName("expiry_date")
    @Expose
    private String expiryDate;
    @SerializedName("image")
    @Expose
    private String image;

    public Promo(){

    }

    public int getId() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(term);
        dest.writeString(promoCode);
        dest.writeString(expiryDate);
        dest.writeString(image);
    }

    private Promo(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        term = in.readString();
        promoCode = in.readString();
        expiryDate = in.readString();
        image = in.readString();
    }

    public static final Creator<Promo> CREATOR = new Creator<Promo>() {
        @Override
        public Promo createFromParcel(Parcel in) {
            return new Promo(in);
        }

        @Override
        public Promo[] newArray(int size) {
            return new Promo[size];
        }
    };
}