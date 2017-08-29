package ua.in.zeusapps.snapsafari.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import ua.in.zeusapps.snapsafari.R;

public class MainMenuActivity extends AppCompatActivity {
    @BindView(R.id.activity_main_menu_profile_image)
    CircleImageView _profileImage;
    @BindView(R.id.activity_main_menu_progress_bar)
    ProgressBar _progressBar;
    @BindView(R.id.activity_main_menu_info)
    TextView _infoTextView;
    @BindView(R.id.activity_main_menu_play_button)
    Button _playButton;
    @BindView(R.id.activity_main_menu_snap_cards_button)
    Button _snapCardsButton;
    @BindView(R.id.activity_main_menu_settings_button)
    Button _settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ButterKnife.bind(this);
    }
}
