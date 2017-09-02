package ua.in.zeusapps.snapsafari.activities;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import ua.in.zeusapps.snapsafari.controls.BottomMenu;
import ua.in.zeusapps.snapsafari.controls.Menu;
import ua.in.zeusapps.snapsafari.models.Card;


@Layout(R.layout.activity_elephant)
public class ElephantActivity extends ActivityBase {

    @BindView(R.id.activity_elephant_menu)
    Menu _menu;
    @BindView(R.id.activity_elephant_bottom_menu)
    BottomMenu _bottomMenu;
    @BindView(R.id.activity_elephant_content)
    LinearLayout _linearLayout;
    @BindView(R.id.activity_elephant_image)
    ImageView _imageView;
    @BindView(R.id.activity_elephant_label)
    TextView _labelTextView;

    @Override
    protected void onResume() {
        super.onResume();

        getApp()
                .getService()
                .getCardsList(getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Card>>() {
            @Override
            public void accept(@NonNull List<Card> cards) throws Exception {
                if (cards.size() > 0) {
                    Random r = new Random();
                    Card card = cards.get(r.nextInt(cards.size()));
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

    @OnClick({
            R.id.activity_elephant_menu,
            R.id.activity_elephant_bottom_menu
    })
    public void onClick(View view){
        Intent intent = new Intent(this, SnapActivity.class);

        startActivity(intent);
    }

    private void showCard(Card card){
        Uri image = getApp().getUri(card.getImage());
        Picasso
                .with(this)
                .load(image)
                .placeholder(R.drawable.elephant)
                .into(_imageView);

        _labelTextView.setText(card.getTitle());
    }

    private void showLoadingError(){

    }

    private void showNoContent(){

    }
}
