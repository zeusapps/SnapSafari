package ua.in.zeusapps.snapsafari.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ua.in.zeusapps.snapsafari.R;
import ua.in.zeusapps.snapsafari.common.Layout;
import ua.in.zeusapps.snapsafari.fragments.PromoDescriptionFragment;
import ua.in.zeusapps.snapsafari.fragments.PromoDetailsFragment;
import ua.in.zeusapps.snapsafari.models.Card;

@Layout(R.layout.activity_promo_details)
public class PromoDetailsActivity extends ActivityBase {
    public static final String CARD_EXTRA = "card";

    private Card _card;
    private PromoDetailsFragment _promoDetailsFragment;
    private PromoDescriptionFragment _promoDescriptionFragment;

    @BindView(R.id.activity_promo_details_element)
    ImageView _element;
    @BindView(R.id.activity_promo_details_promo_image)
    ImageView _promoImage;
    @BindView(R.id.activity_promo_details_title)
    TextView _title;
    @BindView(R.id.activity_promo_details_view_pager)
    ViewPager _viewPager;
    @BindView(R.id.activity_promo_tab_layout)
    TabLayout _tabLayout;

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


        _promoDetailsFragment = PromoDetailsFragment
                .newInstance(
                        _card.getPromo().getTerm(),
                        promoCode,
                        R.drawable.qr_code);
        _promoDescriptionFragment = PromoDescriptionFragment
                .newInstance(_card.getPromo().getDescription());

        Adapter adapter = new Adapter(getSupportFragmentManager());
        _viewPager.setAdapter(adapter);
        _tabLayout.setupWithViewPager(_viewPager);
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

    public class Adapter extends FragmentPagerAdapter {

        private List<Fragment> _fragments = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);

            _fragments.add(_promoDescriptionFragment);
            _fragments.add(_promoDetailsFragment);
        }

        @Override
        public Fragment getItem(int position) {
            return _fragments.get(position);
        }

        @Override
        public int getCount() {
            return _fragments.size();
        }
    }
}
