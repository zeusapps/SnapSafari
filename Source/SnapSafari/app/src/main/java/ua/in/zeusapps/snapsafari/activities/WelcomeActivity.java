package ua.in.zeusapps.snapsafari.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import ua.in.zeusapps.snapsafari.R;
import ua.in.zeusapps.snapsafari.common.Layout;

@Layout(R.layout.activity_welcome)
public class WelcomeActivity extends ActivityBase {
    @BindView(R.id.activity_welcome_register_button)
    Button _registerButton;
    @BindView(R.id.activity_welcome_login_button)
    Button _loginButton;
    @BindView(R.id.activity_welcome_try_it_login)
    Button _letMeTryButton;


    @OnClick(R.id.activity_welcome_login_button)
    public void onLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
