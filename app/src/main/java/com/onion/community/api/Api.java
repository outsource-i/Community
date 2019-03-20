package com.onion.community.api;


import com.onion.community.bean.Article;
import com.onion.community.bean.Banners;
import com.onion.community.bean.HttpWrapper;
import com.onion.community.bean.User;
import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import java.util.List;

/**
 * Created by zhangqi on 2016/9/12.
 */
public interface Api {

    String BASE_URL = "http://118.25.14.84:8050/";


    @FormUrlEncoded
    @POST("user/login")
    Flowable<HttpWrapper<User>> login(@Field("phone") String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/register")
    Flowable<HttpWrapper<User>> register(@Field("phone") String phone, @Field("password") String password);

    @POST("user/getBanner")
    Flowable<HttpWrapper<List<Banners>>> getBanner();

    @FormUrlEncoded
    @POST("user/getFriend")
    Flowable<HttpWrapper<User>> getFriend(@Field("userId") String id);

    @POST("community/getNews")
    Flowable<HttpWrapper<List<Article>>> getNews();

}

