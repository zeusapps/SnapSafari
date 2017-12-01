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

@Layout(R.layout.activity_register)
public class RegisterActivity extends ActivityBase {
    @BindView(R.id.activity_register_profile_photo)
    ImageView profilePhoto;
    @BindView(R.id.activity_register_name)
    EditText name;
    @BindView(R.id.activity_register_email)
    EditText email;
    @BindView(R.id.activity_register_password)
    EditText password;
    @BindView(R.id.activity_register_register)
    Button registerButton;

    @OnClick(R.id.activity_register_register)
    public void onRegister(){

        Login login = new Login(name.getText().toString(), password.getText().toString(), email.getText().toString());

        getApp()
                .getService()
                .register(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<Token>() {
                            @Override
                            public void accept(Token token) throws Exception {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(RegisterActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                            }
                        });

    }
}
