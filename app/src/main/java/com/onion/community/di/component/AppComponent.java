package com.onion.community.di.component;



import javax.inject.Singleton;

import com.onion.community.AppCenter;
import com.onion.community.di.DataManager;
import com.onion.community.di.module.AppModule;
import com.onion.community.di.module.DataModule;
import com.onion.community.di.module.HttpModule;
import dagger.Component;

/**
 * Created by zhangqi on 2017/12/20.
 * e-mail : ${email}
 * desc :
 *
 * App组件层  自动注入
 * AppModule  app层
 * HttpModule 网络层
 * DataModule 数据层
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class, DataModule.class})
public interface AppComponent {

    AppCenter getContext();

    DataManager getDataManager();

}
