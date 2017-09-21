package ua.in.zeusapps.snapsafari.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import butterknife.BindView;
import ua.in.zeusapps.snapsafari.R;
import ua.in.zeusapps.snapsafari.common.Layout;

@Layout(R.layout.fragment_promo_description)
public class PromoDescriptionFragment extends FragmentBase {

    private final static String DESCRIPTION_EXTRA = "description";

    @BindView(R.id.fragment_promo_description_text)
    TextView _textView;

    @Override
    protected void init() {
        Bundle arguments = getArguments();
        _textView.setText(arguments.getString(DESCRIPTION_EXTRA));
    }

    public static PromoDescriptionFragment newInstance(String description){
        PromoDescriptionFragment fragment = new PromoDescriptionFragment();

        Bundle bundle = new Bundle();

        bundle.putString(DESCRIPTION_EXTRA, description);

        fragment.setArguments(bundle);

        return fragment;
    }
}
