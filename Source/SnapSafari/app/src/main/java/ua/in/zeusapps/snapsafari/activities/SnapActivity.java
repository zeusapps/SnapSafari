package ua.in.zeusapps.snapsafari.activities;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ua.in.zeusapps.snapsafari.R;
import ua.in.zeusapps.snapsafari.common.Layout;
import ua.in.zeusapps.snapsafari.models.Card;
import ua.in.zeusapps.snapsafari.models.SnappedCard;

@Layout(R.layout.activity_snap)
public class SnapActivity extends ActivityBase {
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
    @BindView(R.id.activity_snap_snap_it_button)
    Button _snapButton;

    @Override
    protected void onResume() {
        super.onResume();

        getApp()
                .getService()
                .getMyCards(getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SnappedCard>>() {
                    @Override
                    public void accept(@NonNull List<SnappedCard> cards) throws Exception {
                        if (cards.size() > 0) {
                            Random r = new Random();
                            SnappedCard card = cards.get(r.nextInt(cards.size()));
                            showCard(card);
                        } else {
                            showNoContent();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.d(ElephantActivity.class.getSimpleName(), throwable.getMessage());
                        showLoadingError();
                    }
                });
    }

    @OnClick(R.id.activity_snap_snap_it_button)
    public void onClick(){
        Intent intent = new Intent(this, ElephantDisabledActivity.class);

        startActivity(intent);
    }

    private void showCard(SnappedCard card){
        Uri image = getApp().getUri(card.getCard().getImage());
        Picasso
                .with(this)
                .load(image)
                .placeholder(R.drawable.elephant)
                .into(_imageImageView);
        Picasso
                .with(this)
                .load(getElementResource(card.getCard()))
                .into(_iconImageView);

        _labelTextView.setText(card.getCard().getTitle());
        _descriptionTextView.setText(card.getCard().getDescription());
        _levelTextView.setText(card.getLevel());
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

    private void showLoadingError(){

    }

    private void showNoContent(){

    }
}
