package com.onion.community.di.component;

import android.app.Activity;


import com.onion.community.AppCenter;
import com.onion.community.di.DataManager;
import com.onion.community.di.module.FragmentModule;
import com.onion.community.di.scope.FragmentScope;
import dagger.Component;

/**
 * Created by zhangqi on 16/8/7.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    AppCenter getContext();

    DataManager getDataManager();


}
