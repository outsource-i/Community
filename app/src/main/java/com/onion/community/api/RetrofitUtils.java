package com.onion.community.api;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 */
public class RetrofitUtils {

    private static Retrofit sRetrofit;
    private static OkHttpClient mOkHttpClient;

    private RetrofitUtils(){}

    public static class Hide{
        static RetrofitUtils sRetrofitUtil = new RetrofitUtils();
    }

    public static RetrofitUtils getInstance() {
        return Hide.sRetrofitUtil;
    }

    public Api build(){
        if(mOkHttpClient == null){
            mOkHttpClient = HttpBuilder.getOkhttpClient(HttpBuilder.getOkhttpBuilder(HttpBuilder.getHttplogger(),HttpBuilder.getHeaderInterceptor()));
        }

        if(sRetrofit == null){
            sRetrofit = HttpBuilder.getRetrofit(HttpBuilder.getRetrofitBuilder(HttpBuilder.getGson(),mOkHttpClient, Api.BASE_URL));
        }
        return HttpBuilder.getApi(sRetrofit);
    }
}
