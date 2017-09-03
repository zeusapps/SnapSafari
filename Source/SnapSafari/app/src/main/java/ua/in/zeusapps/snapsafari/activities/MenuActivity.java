package ua.in.zeusapps.snapsafari.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ua.in.zeusapps.snapsafari.R;

public class MenuActivity extends AppCompatActivity {
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
}
