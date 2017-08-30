package ua.in.zeusapps.snapsafari.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.in.zeusapps.snapsafari.R;
import ua.in.zeusapps.snapsafari.controls.Menu;

public class PromoActivity extends AppCompatActivity {

    @BindView(R.id.activity_promo_title)
    TextView title;
    @BindView(R.id.activity_promo_description)
    TextView description;
    @BindView(R.id.activity_promo_valid_for)
    TextView validFor;
    @BindView(R.id.activity_promo_menu)
    Menu menu;

    @BindView(R.id.activity_promo_elephant)
    ImageView elephantImage;
    @BindView(R.id.activity_promo_promo_image)
    ImageView promoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);

        ButterKnife.bind(this);
    }
}
