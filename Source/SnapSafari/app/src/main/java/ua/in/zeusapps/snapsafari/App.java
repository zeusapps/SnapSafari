package ua.in.zeusapps.snapsafari;

import android.app.Application;
import android.net.Uri;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.in.zeusapps.snapsafari.services.SnapSafariService;

public class App extends Application {

    public static final String TAG = App.class.getSimpleName();

    public static final String BASE_URL = "http://46.101.73.111:9000/";

    public static final String REGISTERED = "registered";

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

    public Uri getUri(String relativePath){
        return Uri.withAppendedPath(Uri.parse(BASE_URL), relativePath);
    }
}
