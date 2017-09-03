package ua.in.zeusapps.snapsafari.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
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

@Layout(R.layout.activity_snap)
public class SnapActivity extends ActivityBase {

    public static final String CARD_EXTRA = "card";

    private int _cardId = -1;
    @BindView(R.id.activity_snap_image)
    ImageView _imageImageView;
    @BindView(R.id.activity_snap_icon)
    ImageView _iconImageView;
    @BindView(R.id.activity_snap_label)
    TextView _labelTextView;
    @BindView(R.id.activity_snap_level)
    TextView _levelTextView;
    @BindView(R.id.activity_snap_description)
    TextView _descriptionTextView;
    @BindView(R.id.activity_snap_redeem_button)
    Button _redeemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Card card = getIntent().getParcelableExtra(CARD_EXTRA);
        showCard(card);
    }

    @OnClick(R.id.activity_snap_snap_it_button)
    public void onSnap(){
        if (_cardId == -1){
            return;
        }

        getApp()
                .getService()
                .snapCard(getToken(), new SnapRequest(_cardId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SnappedCard>() {
                    @Override
                    public void accept(@NonNull SnappedCard snappedCard) throws Exception {
                        String message = _labelTextView.getText().toString() + " snapped!";
                        Toast.makeText(SnapActivity.this, message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SnapActivity.this, SnapCardsActivity.class);
                        startActivity(intent);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Toast.makeText(SnapActivity.this, "Failed to snap. Try again later.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @OnClick(R.id.activity_snap_redeem_button)
    public void onRedeem(){
        Toast.makeText(this, "Redeem now!", Toast.LENGTH_SHORT).show();
    }

    private void showCard(Card card){
        _cardId = card.getId();
        if (card.getPromo() == null) {
            _redeemButton.setVisibility(View.GONE);
        }

        Uri image = getApp().getUri(card.getImage());
        Picasso
                .with(this)
                .load(image)
                .placeholder(R.drawable.elephant)
                .into(_imageImageView);
        Picasso
                .with(this)
                .load(getElementResource(card))
                .into(_iconImageView);

        _labelTextView.setText(card.getTitle());
        _descriptionTextView.setText(card.getDescription());
        //TODO update level
        _levelTextView.setText("level 1");
    }

    private int getElementResource(Card card){
        switch (card.getElement()){
            case "E":
                return R.drawable.earth;
            case "F":
                return R.drawable.fire;
            case "W":
                return R.drawable.water;
            case "A":
                return R.drawable.wind;
        }

        return 0;
    }
}
