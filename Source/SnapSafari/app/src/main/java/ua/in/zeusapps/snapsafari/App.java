package ua.in.zeusapps.snapsafari;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.in.zeusapps.snapsafari.services.SnapSafariService;

public class App extends Application {

    private static final String BASE_URL = "http://46.101.73.111:7000/";

    private SnapSafariService _service;

    public App() {
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        _service = retrofit.create(SnapSafariService.class);
    }

    public SnapSafariService getService() {
        return _service;
    }
}
