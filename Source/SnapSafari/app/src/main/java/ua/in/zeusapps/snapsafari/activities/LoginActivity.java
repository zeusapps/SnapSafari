package ua.in.zeusapps.snapsafari.activities;

import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ua.in.zeusapps.snapsafari.R;
import ua.in.zeusapps.snapsafari.common.Layout;
import ua.in.zeusapps.snapsafari.models.Login;
import ua.in.zeusapps.snapsafari.models.Token;

@Layout(R.layout.activity_login)
public class LoginActivity extends ActivityBase {

    private final static String USERNAME = "test_user";
    private final static String PASSWORD = "dstudio2017";

    @BindView(R.id.activity_login_twitter_login)
    Button _twitterButton;
    @BindView(R.id.activity_login_facebook_login)
    Button _facebookButton;
    @BindView(R.id.activity_login_gmail_login)
    Button _gmailButton;
    @BindView(R.id.activity_login_try_it_login)
    Button _tryItButton;

    @OnClick({
            R.id.activity_login_twitter_login,
            R.id.activity_login_facebook_login,
            R.id.activity_login_gmail_login,
            R.id.activity_login_try_it_login})
    public void onLogin(){
        Login login = new Login(USERNAME, PASSWORD);

        getApp()
                .getService()
                .getToken(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                new Consumer<Token>() {
                    @Override
                    public void accept(Token token) throws Exception {
                        saveToken(token.getKey());

                        Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },
                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(LoginActivity.this, "Login failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
