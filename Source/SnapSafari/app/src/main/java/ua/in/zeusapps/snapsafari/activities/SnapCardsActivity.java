package ua.in.zeusapps.snapsafari.activities;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.in.zeusapps.snapsafari.R;
import ua.in.zeusapps.snapsafari.fragments.ElephantFragment;

public class SnapCardsActivity extends AppCompatActivity {
    @BindView(R.id.activity_snap_cards_tab_layout)
    TabLayout _tabLayout;
    @BindView(R.id.activity_snap_cards_view_pager)
    ViewPager _viewPager;
    TextView _snappedAnimalsDescription;
    TextView _boostCardsDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap_cards);

        ButterKnife.bind(this);
        init();
    }

    private void init(){
        _viewPager.setAdapter(new Adapter(getSupportFragmentManager()));
        _tabLayout.setupWithViewPager(_viewPager);

        for (int i = 0; i < _tabLayout.getTabCount(); i++){
            View tabView = getLayoutInflater().inflate(R.layout.tab, null, false);

            TextView title = (TextView) tabView.findViewById(R.id.tab_title);
            TextView description = (TextView) tabView.findViewById(R.id.tab_description);

            title.setText(getTitle(i));
            description.setText(getDescription(i));

            _tabLayout.getTabAt(i).setCustomView(tabView);
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
            return new ElephantFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
