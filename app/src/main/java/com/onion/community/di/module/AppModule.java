package com.onion.community.di.module;


import javax.inject.Singleton;

import com.onion.community.AppCenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangqi on 2017/12/20.
 * e-mail : ${email}
 * desc :
 */
@Module
public class AppModule {

    private final AppCenter mAppCenter;

    public AppModule(AppCenter application) {
        this.mAppCenter = application;
    }

    @Provides
    @Singleton
    AppCenter provideApplicationContext() {
        return mAppCenter;
    }

}
