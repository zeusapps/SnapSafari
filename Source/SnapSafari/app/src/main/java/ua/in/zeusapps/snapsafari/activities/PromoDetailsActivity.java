package ua.in.zeusapps.snapsafari.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ua.in.zeusapps.snapsafari.R;
import ua.in.zeusapps.snapsafari.common.Layout;
import ua.in.zeusapps.snapsafari.models.Card;
import ua.in.zeusapps.snapsafari.models.SnapRequest;
import ua.in.zeusapps.snapsafari.models.SnappedCard;

@Layout(R.layout.activity_promo_details)
public class PromoDetailsActivity extends ActivityBase {
    public static final String CARD_EXTRA = "card";

    private Card _card;

    @BindView(R.id.activity_promo_details_element)
    ImageView _element;
    @BindView(R.id.activity_promo_details_promo_image)
    ImageView _promoImage;
    @BindView(R.id.activity_promo_details_qr_code)
    ImageView _qrCode;
    @BindView(R.id.activity_promo_details_title)
    TextView _title;
    @BindView(R.id.activity_promo_details_promo_code)
    TextView _promoCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _card = getIntent().getParcelableExtra(CARD_EXTRA);
        Picasso.with(this).load(getElementResource(_card)).into(_element);
        Picasso.with(this).load(getApp().getUri(_card.getPromo().getImage())).into(_promoImage);

        _title.setText(_card.getTitle());
        String promoCode = getString(
                R.string.activity_promo_details_promo_code_suffix,
                _card.getPromo().getPromoCode());
        _promoCode.setText(promoCode);
    }

    @OnClick(R.id.activity_promo_details_redeem_button)
    public void onRedeem(){
//        SnapRequest request = new SnapRequest(_card.getId());
//        getApp().getService().snapCard(getToken(), request)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Consumer<SnappedCard>() {
//                    @Override
//                    public void accept(@NonNull SnappedCard snappedCard) throws Exception {
//                        Toast.makeText(PromoDetailsActivity.this, "Card snapped!", Toast.LENGTH_SHORT).show();
//
//                        Intent intent = new Intent(PromoDetailsActivity.this, SnapCardsActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(@NonNull Throwable throwable) throws Exception {
//                        Toast.makeText(PromoDetailsActivity.this, "Failed to snap card. Try later", Toast.LENGTH_SHORT).show();
//                    }
//                });
        Toast.makeText(this, "Redeem now!", Toast.LENGTH_SHORT).show();
    }

    private int getElementResource(Card card){
        switch (card.getElement()){
            case "W":
                return R.drawable.water;
            case "A":
                return R.drawable.wind;
            case "F":
                return R.drawable.fire;
            case "E":
                return R.drawable.earth;
        }

        return 0;
    }
}
