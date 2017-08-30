package ua.in.zeusapps.snapsafari.services;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ua.in.zeusapps.snapsafari.models.Login;
import ua.in.zeusapps.snapsafari.models.Token;

public interface SnapSafariService {
    @POST("authorization/login/")
    Flowable<Token> getToken(@Body Login login);
}
