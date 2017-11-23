package ua.in.zeusapps.snapsafari.activities;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ua.in.zeusapps.snapsafari.App;
import ua.in.zeusapps.snapsafari.R;
import ua.in.zeusapps.snapsafari.common.Layout;
import ua.in.zeusapps.snapsafari.models.Login;
import ua.in.zeusapps.snapsafari.models.Token;

/**
 * Created by kovalskiy on 11/23/17.
 */

@Layout(R.layout.activity_email_login)
public class EmailLoginActivity extends ActivityBase {
    @BindView(R.id.activity_login_name)
    EditText name;
    @BindView(R.id.activity_login_email)
    EditText email;
    @BindView(R.id.activity_login_password)
    EditText password;
    @BindView(R.id.activity_login_login)
    Button loginButton;

    @OnClick(R.id.activity_login_login)
    public void onLogin() {

        Login login = new Login(name.getText().toString(), password.getText().toString(), email.getText().toString());

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

                                getSharedPreferences(App.TAG, MODE_APPEND)
                                        .edit()
                                        .putBoolean(App.REGISTERED, true)
                                        .apply();

                                Intent intent = new Intent(EmailLoginActivity.this, MenuActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(EmailLoginActivity.this, "Login failed.", Toast.LENGTH_SHORT).show();
                            }
                        });

    }
}
