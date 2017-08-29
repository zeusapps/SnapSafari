package ua.in.zeusapps.snapsafari.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.in.zeusapps.snapsafari.MainActivity;
import ua.in.zeusapps.snapsafari.R;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.activity_login_twitter_login)
    Button _twitterButton;
    @BindView(R.id.activity_login_facebook_login)
    Button _facebookButton;
    @BindView(R.id.activity_login_gmail_login)
    Button _gmailButton;
    @BindView(R.id.activity_login_try_it_login)
    Button _tryItButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.activity_login_twitter_login,
            R.id.activity_login_facebook_login,
            R.id.activity_login_gmail_login,
            R.id.activity_login_try_it_login})
    public void onLogin(){
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }
}
