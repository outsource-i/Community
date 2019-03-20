package com.onion.community.di.component;

import android.app.Activity;


import com.onion.community.AppCenter;
import com.onion.community.di.DataManager;
import com.onion.community.di.module.ActivityModule;
import com.onion.community.di.scope.ActivityScope;
import com.onion.community.engine.HomeActivity;
import com.onion.community.engine.community.CommunityFragment;
import com.onion.community.engine.login.LoginActivity;
import com.onion.community.engine.main.MainFragment;
import com.onion.community.engine.self.SelfFragment;
import dagger.Component;

/**
 * Created by zhangqi on 16/8/7.
 * activity 管理层
 * <p>
 * 注入app组件  ActivityModule
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    AppCenter getContext();

    DataManager getDataManager();


    void inject(HomeActivity homeActivity);

    void inject(SelfFragment selfFragment);

    void inject(LoginActivity loginActivity);

    void inject(MainFragment mainActivity);

    void inject(CommunityFragment communityActivity);

}
