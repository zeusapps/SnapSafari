package ua.in.zeusapps.snapsafari.services;

import java.io.File;
import java.util.List;
import java.util.Observable;

import io.reactivex.Flowable;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import ua.in.zeusapps.snapsafari.models.CardAnimation;
import ua.in.zeusapps.snapsafari.models.Card;
import ua.in.zeusapps.snapsafari.models.Event;
import ua.in.zeusapps.snapsafari.models.EventRequest;
import ua.in.zeusapps.snapsafari.models.Login;
import ua.in.zeusapps.snapsafari.models.SnapRequest;
import ua.in.zeusapps.snapsafari.models.Token;

public interface SnapSafariService {
    String AUTH_HEADER = "Authorization";


    @POST("authorization/registration/")
    Flowable<Token>register(@Body Login credentials);

    @POST("authorization/login/")
    Flowable<Token> getToken(@Body Login login);

    @GET("cards/cards_list/")
    Flowable<List<Card>> getCardsList(@Header(AUTH_HEADER) String token, @Query("page") int page);
    @GET("cards/cards_list/")
    Flowable<List<Card>> getCardsList(@Header(AUTH_HEADER) String token);

    @GET("cards/my_cards/")
    Flowable<List<Card>> getMyCards(@Header(AUTH_HEADER) String token, @Query("page") int page);
    @GET("cards/my_cards/")
    Flowable<List<Card>> getMyCards(@Header(AUTH_HEADER) String token);

    @POST("cards/catch_card/")
    Flowable<Card> snapCard(@Header(AUTH_HEADER)String token, @Body SnapRequest request);

    @POST("events/events_with_filter/")
    Flowable<List<Event>> getEvents(@Header(AUTH_HEADER)String header, @Body EventRequest request);

    @GET
    Flowable<CardAnimation> getAnimation(@Header(AUTH_HEADER) String token, @Url String url);
}
