package ua.in.zeusapps.snapsafari.activities;

import android.content.Intent;
import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ua.in.zeusapps.snapsafari.App;
import ua.in.zeusapps.snapsafari.R;
import ua.in.zeusapps.snapsafari.common.Layout;

@Layout(R.layout.activity_splash)
public class SplashActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isRegistered = getSharedPreferences(App.TAG, MODE_APPEND)
                .getBoolean(App.REGISTERED, false);

        final Class cls = isRegistered
                ? MenuActivity.class
                : WelcomeActivity.class;

        Single.timer(4, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                Intent intent = new Intent(SplashActivity.this, cls);
                startActivity(intent);
                finish();
            }
        });
    }
}
