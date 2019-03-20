package com.onion.community.di.module;


import javax.inject.Singleton;

import com.onion.community.api.Api;
import com.onion.community.api.AuthApi;
import com.onion.community.di.DataManager;
import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangqi on 2017/12/20.
 * e-mail : ${email}
 * desc :
 */
@Module
public class DataModule {

    @Provides
    @Singleton
    DataManager prodiveDataManager(Api api, AuthApi authApi){
        return new DataManager(api,authApi);
    }
}
