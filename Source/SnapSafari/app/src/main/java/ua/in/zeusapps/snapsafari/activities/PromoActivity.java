package ua.in.zeusapps.snapsafari.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ua.in.zeusapps.snapsafari.R;
import ua.in.zeusapps.snapsafari.common.Layout;
import ua.in.zeusapps.snapsafari.controls.Menu;
import ua.in.zeusapps.snapsafari.models.Card;
import ua.in.zeusapps.snapsafari.models.SnapRequest;
import ua.in.zeusapps.snapsafari.models.SnappedCard;

@Layout(R.layout.activity_promo)
public class PromoActivity extends ActivityBase {

    public static final String CARD_EXTRA = "card";

    private Card _card;

    @BindView(R.id.activity_promo_title)
    TextView title;
    @BindView(R.id.activity_promo_description)
    TextView description;
    @BindView(R.id.activity_promo_valid_for)
    TextView validFor;
    @BindView(R.id.activity_promo_term)
    TextView term;
    @BindView(R.id.activity_promo_menu)
    Menu menu;

    @BindView(R.id.activity_promo_elephant)
    ImageView elephantImage;
    @BindView(R.id.activity_promo_promo_image)
    ImageView promoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _card = getIntent().getParcelableExtra(CARD_EXTRA);
        showCard(_card);
    }

    private void showCard(Card card){
        title.setText(card.getTitle());
        description.setText(card.getPromo().getTitle());
        String message = "Valid for " + getDaysBetween(card) + " days";
        validFor.setText(message);
        term.setText(card.getPromo().getTerm());

        Picasso.with(this).load(getApp().getUri(card.getImage())).into(elephantImage);
        Picasso.with(this).load(getApp().getUri(card.getPromo().getImage())).into(promoImage);
    }

    @OnClick(R.id.activity_promo_snap_button)
    public void onSnap(){
        if (_card == null){
            return;
        }

        SnapRequest request = new SnapRequest(_card.getId());
        getApp().getService().snapCard(getToken(), request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<SnappedCard>() {
                    @Override
                    public void accept(@NonNull SnappedCard snappedCard) throws Exception {
                        Toast.makeText(PromoActivity.this, "Card snapped!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(PromoActivity.this, SnapCardsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Toast.makeText(PromoActivity.this, "Failed to snap card. Try later", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @OnClick(R.id.activity_promo_redeem_button)
    public void onRedeem(){
        Intent intent = new Intent(this, PromoDetailsActivity.class);
        intent.putExtra(PromoDetailsActivity.CARD_EXTRA, _card);

        startActivity(intent);
    }

    @OnClick(R.id.activity_promo_back)
    public void onBack(){
        finish();
    }

    private int getIconRes(Card card){
        switch (card.getElement()){
            case "A":
                return R.drawable.wind;
            case "E":
                return R.drawable.earth;
            case "W":
                return R.drawable.water;
            case "F":
                return R.drawable.fire;
        }

        return 0;
    }

    private int getDaysBetween(Card card){
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss",
                Locale.getDefault());

        Date expire = new Date();
        Date today = new Date();
        try
        {
            expire = dateFormat.parse(card.getPromo().getExpiryDate());
            long diff = expire.getTime() - today.getTime();
            if (diff < 0){
                return 0;
            }

            return (int) (diff / (24 * 60 * 60 * 1000));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return 0;
        }
    }
}
