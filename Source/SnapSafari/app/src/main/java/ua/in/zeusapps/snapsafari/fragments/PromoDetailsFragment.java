package ua.in.zeusapps.snapsafari.fragments;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import ua.in.zeusapps.snapsafari.R;
import ua.in.zeusapps.snapsafari.common.Layout;

@Layout(R.layout.fragment_promo_details)
public class PromoDetailsFragment extends FragmentBase {

    private final static String MESSAGE_EXTRA = "message";
    private final static String PROMO_CODE_EXTRA = "promo_code";
    private final static String QR_CODE_EXTRA = "qr_code";



    @BindView(R.id.fragment_promo_details_message)
    TextView _messageTextView;
    @BindView(R.id.fragment_promo_details_promo_code)
    TextView _promoCodeTextView;
    @BindView(R.id.fragment_promo_details_qr_code)
    ImageView _qrCodeImageView;

    @Override
    protected void init() {
        _messageTextView.setText(getArguments().getString(MESSAGE_EXTRA));
        _promoCodeTextView.setText(getArguments().getString(PROMO_CODE_EXTRA));
        Picasso
                .with(getContext())
                .load(getArguments().getInt(QR_CODE_EXTRA))
                .into(_qrCodeImageView);
    }

    public static PromoDetailsFragment newInstance(String message, String promoCode, int qrCode){
        PromoDetailsFragment fragment = new PromoDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MESSAGE_EXTRA, message);
        bundle.putString(PROMO_CODE_EXTRA, promoCode);
        bundle.putInt(QR_CODE_EXTRA, qrCode);
        fragment.setArguments(bundle);
        return fragment;
    }
}