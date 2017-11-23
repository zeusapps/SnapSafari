package ua.in.zeusapps.snapsafari.activities;

import android.content.Intent;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ua.in.zeusapps.snapsafari.App;
import ua.in.zeusapps.snapsafari.R;
import ua.in.zeusapps.snapsafari.common.Layout;

@Layout(R.layout.activity_main_menu)
public class MenuActivity extends ActivityBase {
    @BindView(R.id.activity_main_menu_profile_image)
    CircleImageView _profileImage;
    @BindView(R.id.activity_main_menu_progress_bar)
    ProgressBar _progressBar;
    @BindView(R.id.activity_main_menu_info)
    TextView _infoTextView;
    @BindView(R.id.activity_main_menu_notification_text)
    TextView _notificationTextView;
    @BindView(R.id.activity_main_menu_play_button)
    Button _playButton;
    @BindView(R.id.activity_main_menu_battle_button)
    Button _buttleButton;
    @BindView(R.id.activity_main_menu_marketplace_button)
    Button _marketplaceButton;
    @BindView(R.id.activity_main_menu_snap_cards_button)
    Button _snapCardsButton;
    @BindView(R.id.activity_main_menu_settings_button)
    Button _settingsButton;
    @BindView(R.id.activity_main_menu_notification_container)
    FrameLayout _notificationButton;

    @OnClick(R.id.activity_main_menu_play_button)
    public void onPlay(){
        Intent intent = new Intent(this, BlankActivity.class);

        startActivity(intent);
    }

    @OnClick(R.id.activity_main_menu_snap_cards_button)
    public void onSnapCards(){
        Intent intent = new Intent(this, SnapCardsActivity.class);

        startActivity(intent);
    }

    @OnClick(R.id.activity_main_menu_settings_button)
    public void onSettings() {

        getSharedPreferences(App.TAG, MODE_APPEND)
                .edit()
                .putBoolean(App.REGISTERED, false)
                .apply();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
