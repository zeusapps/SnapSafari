package ua.in.zeusapps.snapsafari.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ua.in.zeusapps.snapsafari.R;
import ua.in.zeusapps.snapsafari.common.Layout;
import ua.in.zeusapps.snapsafari.fragments.ElephantFragment;
import ua.in.zeusapps.snapsafari.fragments.PromoFragment;
import ua.in.zeusapps.snapsafari.models.SnappedCard;

@Layout(R.layout.activity_snap_cards)
public class SnapCardsActivity extends ActivityBase {

    private String _selectedFilter = "E";

    private List<SnappedCard> _snappedCards;
    private ElephantFragment _elephantFragment = new ElephantFragment();
    private PromoFragment _promoFragment = new PromoFragment();


    @BindView(R.id.activity_snap_cards_tab_layout)
    TabLayout _tabLayout;
    @BindView(R.id.activity_snap_cards_view_pager)
    ViewPager _viewPager;
    @BindView(R.id.activity_snap_cards_filter_earth)
    Button _earthButton;
    @BindView(R.id.activity_snap_cards_filter_air)
    Button _airButton;
    @BindView(R.id.activity_snap_cards_filter_fire)
    Button _fireButton;
    @BindView(R.id.activity_snap_cards_filter_water)
    Button _waterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        _viewPager.setAdapter(new Adapter(getSupportFragmentManager()));
        _tabLayout.setupWithViewPager(_viewPager);

        getApp().getService().getMyCards(getToken())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<SnappedCard>>() {
                    @Override
                    public void accept(@NonNull List<SnappedCard> snappedCards) throws Exception {
                        _snappedCards = snappedCards;
                        _elephantFragment.addCards(snappedCards);
                        _promoFragment.addCards(snappedCards);
                        update();
                    }
                });
    }

    private void update(){
        for (int i = 0; i < _tabLayout.getTabCount(); i++){
            View tabView = getLayoutInflater().inflate(R.layout.tab, null, false);

            TextView title = (TextView) tabView.findViewById(R.id.tab_title);
            TextView description = (TextView) tabView.findViewById(R.id.tab_description);

            title.setText(getTitle(i));
            description.setText(getDescription(i));

            _tabLayout.getTabAt(i).setCustomView(tabView);
        }
    }

    @OnClick({
            R.id.activity_snap_cards_filter_earth,
            R.id.activity_snap_cards_filter_air,
            R.id.activity_snap_cards_filter_fire,
            R.id.activity_snap_cards_filter_water,
    })
    public void onFilter(Button btn){
        int id = btn.getId();

        _earthButton.setBackgroundColor(Color.TRANSPARENT);
        _airButton.setBackgroundColor(Color.TRANSPARENT);
        _fireButton.setBackgroundColor(Color.TRANSPARENT);
        _waterButton.setBackgroundColor(Color.TRANSPARENT);


        int color = ContextCompat.getColor(this, R.color.colorFilterSelected);

        switch (id){
            case R.id.activity_snap_cards_filter_earth:
                _selectedFilter = "E";
                _earthButton.setBackgroundColor(color);
                break;
            case R.id.activity_snap_cards_filter_air:
                _selectedFilter = "A";
                _earthButton.setBackgroundColor(color);
                break;
            case R.id.activity_snap_cards_filter_fire:
                _selectedFilter = "F";
                _earthButton.setBackgroundColor(color);
                break;
            case R.id.activity_snap_cards_filter_water:
                _selectedFilter = "W";
                _earthButton.setBackgroundColor(color);
                break;
        }
    }

    private String getDescription(int i) {



        if (i == 0){
            return getString(R.string.activity_snap_cards_snapped_animals_count);
        }

        return getString(R.string.activity_snap_cards_boost_cards_count);
    }

    private String getTitle(int i) {
        if (i == 0){
            return getString(R.string.activity_snap_cards_snapped_animals);
        }

        return getString(R.string.activity_snap_cards_boost_cards);
    }


    class Adapter extends FragmentPagerAdapter{

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0){
                return _elephantFragment;
            }

            return _promoFragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
