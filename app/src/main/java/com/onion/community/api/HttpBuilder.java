package com.onion.community.api;

import android.app.Application;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.onion.community.api.Interceptor.HeaderInterceptor;
import com.onion.community.api.Interceptor.HttpLogger;
import com.onion.community.constant.Constant;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.concurrent.TimeUnit;


public class HttpBuilder {

    /**
     * 构建Gson
     * @return
     */
    public static Gson getGson(){
//        GsonBuilder gsonBuilder = new GsonBuilder();
        GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")  ;
        return gsonBuilder.create();
    }

    /**
     * 构建缓存
     * @param application
     * @return
     */
    public static Cache getCache(Application application){
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    /**
     * 构建 okhttp 日志
     * @return
     */
    public static HttpLoggingInterceptor getHttplogger(){
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logInterceptor;
    }

    /**
     * 构建header 拦截器
     * @return
     */
    public static HeaderInterceptor getHeaderInterceptor(){
        return new HeaderInterceptor();
    }

    /**
     * 构建okhttpbuilder
     * @param interceptor
     * @param headerInterceptor
     * @return
     */
    public static OkHttpClient.Builder getOkhttpBuilder(HttpLoggingInterceptor interceptor,HeaderInterceptor headerInterceptor){
        return new OkHttpClient.Builder()
                .connectTimeout(Constant.TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constant.TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constant.TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(interceptor)
                .addInterceptor(headerInterceptor);
    }

    /**
     * 构建okhttpclient
     * @param builder
     * @return
     */
    public static OkHttpClient getOkhttpClient(OkHttpClient.Builder builder){
        return builder.build();
    }

    /**
     * 构建retrofitbuilder
     * @param gson
     * @param client
     * @return
     */
    public static Retrofit.Builder getRetrofitBuilder(Gson gson,OkHttpClient client,String baseurl){
        return new Retrofit.Builder()
                .baseUrl(baseurl)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    /**
     * 构建retrofit
     * @param builder
     * @return
     */
    public static Retrofit getRetrofit(Retrofit.Builder builder){
        return builder.build();
    }

    /**
     * 构建Api
     * @param retrofit
     * @return
     */
    public static Api getApi(Retrofit retrofit){
        return retrofit.create(Api.class);
    }

    public static AuthApi getAuthApi(Retrofit retrofit){
        return retrofit.create(AuthApi.class);
    }
}
