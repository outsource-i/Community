package com.onion.community.di.module;

import android.app.Application;

import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import com.onion.community.api.Api;
import com.onion.community.api.AuthApi;
import com.onion.community.api.HttpBuilder;
import com.onion.community.api.Interceptor.HeaderInterceptor;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by zhangqi on 2017/12/20.
 * e-mail : ${email}
 * desc : HttpModule层  统一由HttpBuilder负责构建对象
 */
@Module
public class HttpModule {

    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        return HttpBuilder.getCache(application);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return HttpBuilder.getGson();
    }

    /**
     * 建造Httplogger
     */
    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLogger(){
        return HttpBuilder.getHttplogger();
    }

    @Provides
    @Singleton
    HeaderInterceptor provideHeaderInterceptor(){
        return HttpBuilder.getHeaderInterceptor();
    }

    @Provides
    @Singleton
    OkHttpClient.Builder provideOkhttpBuilder(HttpLoggingInterceptor interceptor,HeaderInterceptor headerInterceptor){
        return HttpBuilder.getOkhttpBuilder(interceptor,headerInterceptor);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient(OkHttpClient.Builder builder){
        return HttpBuilder.getOkhttpClient(builder);
    }

    @Provides
    @Singleton
    @Named("normal_retrofitbuilder")
    Retrofit.Builder provideRetrofitBuilder(Gson gson,OkHttpClient client){
        return HttpBuilder.getRetrofitBuilder(gson,client, Api.BASE_URL);
    }

    @Provides
    @Singleton
    @Named("normal_retrofit")
    Retrofit provideRetrofit(@Named("normal_retrofitbuilder") Retrofit.Builder builder){
        return HttpBuilder.getRetrofit(builder);
    }

    @Provides
    @Singleton
    Api provideApi(@Named("normal_retrofit") Retrofit retrofit){
        return HttpBuilder.getApi(retrofit);
    }

    @Provides
    @Singleton
    @Named("auth_retrofitbuilder")
    Retrofit.Builder provideRetrofitBuilderAuth(Gson gson,OkHttpClient client){
        return HttpBuilder.getRetrofitBuilder(gson,client, AuthApi.BASE_URL);
    }

    @Provides
    @Singleton
    @Named("auth_retrofit")
    Retrofit provideRetrofitAuth(@Named("auth_retrofitbuilder") Retrofit.Builder builder){
        return HttpBuilder.getRetrofit(builder);
    }

    @Provides
    @Singleton
    AuthApi provideApiAuth(@Named("auth_retrofit") Retrofit retrofit){
        return HttpBuilder.getAuthApi(retrofit);
    }
}
