package ua.in.zeusapps.snapsafari.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import ua.in.zeusapps.snapsafari.App;
import ua.in.zeusapps.snapsafari.common.Layout;

public abstract class ActivityBase extends AppCompatActivity {

    private final static String APP_NAME = App.class.getName();
    private final static String TOKEN_NAME = "token";
    private final static String TOKEN_SUFFIX = "Token ";

    private App _app;
    public App getApp(){
        if (_app != null){
            return _app;
        }

        _app = (App) getApplication();
        return _app;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Class cls = getClass();
        if (!cls.isAnnotationPresent(Layout.class)){
            return;
        }

        Layout layout = (Layout) cls.getAnnotation(Layout.class);
        setContentView(layout.value());

        ButterKnife.bind(this);
    }

    protected void saveToken(String token){
        token = TOKEN_SUFFIX + token;

        getSharedPreferences(APP_NAME, MODE_APPEND)
                .edit()
                .putString(TOKEN_NAME, token)
                .apply();
    }

    protected String getToken(){
        return getSharedPreferences(APP_NAME, MODE_PRIVATE)
                .getString(TOKEN_NAME, null);
    }
}
