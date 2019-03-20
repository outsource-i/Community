package com.onion.community.api;



import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by OnionMac on 2018/1/2.
 */

public interface AuthApi {

    String BASE_URL = "http://www.baidu.com/";
    String AUTH_BASE_URL = "http://www.baidu.com/";

    @GET("environment/air/cityair")
    Flowable<String> get(@Query("city") String city, @Query("key") String key);
}
