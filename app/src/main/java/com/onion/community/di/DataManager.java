package com.onion.community.di;


import com.onion.community.api.Api;
import com.onion.community.api.AuthApi;

/**
 * Created by zhangqi on 2017/12/20.
 * e-mail : ${email}
 * desc :
 * 数据管理中心
 *  api  网络层
 *  本地层数据
 *
 */

public class DataManager {

    Api mApi;

    AuthApi mAuthApi;

    public DataManager(Api api,AuthApi authApi) {
       this.mApi = api;
       this.mAuthApi = authApi;
    }

    public Api getApi() {
        return mApi;
    }

    public AuthApi getAuthApi() {
        return mAuthApi;
    }
}
