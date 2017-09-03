package ua.in.zeusapps.snapsafari.services;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ua.in.zeusapps.snapsafari.models.Card;
import ua.in.zeusapps.snapsafari.models.Event;
import ua.in.zeusapps.snapsafari.models.EventRequest;
import ua.in.zeusapps.snapsafari.models.Login;
import ua.in.zeusapps.snapsafari.models.SnapRequest;
import ua.in.zeusapps.snapsafari.models.SnappedCard;
import ua.in.zeusapps.snapsafari.models.Token;

public interface SnapSafariService {
    String AUTH_HEADER = "Authorization";


    @POST("authorization/login/")
    Flowable<Token> getToken(@Body Login login);

    @GET("cards/cards_list/ ")
    Flowable<List<Card>> getCardsList(@Header(AUTH_HEADER) String token, @Query("page") int page);
    @GET("cards/cards_list/ ")
    Flowable<List<Card>> getCardsList(@Header(AUTH_HEADER) String token);

    @GET("cards/my_cards/ ")
    Flowable<List<SnappedCard>> getMyCards(@Header(AUTH_HEADER) String token, @Query("page") int page);
    @GET("cards/my_cards/ ")
    Flowable<List<SnappedCard>> getMyCards(@Header(AUTH_HEADER) String token);

    @POST("cards/catch_card/")
    Flowable<SnappedCard> snapCard(@Header(AUTH_HEADER)String token, @Body SnapRequest request);

    @POST("events/events_with_filter/")
    Flowable<List<Event>> getEvents(@Header(AUTH_HEADER)String header, @Body EventRequest request);
}
