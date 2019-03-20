package com.onion.community.di.module;

import android.app.Activity;


import com.onion.community.di.scope.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangqi on 16/8/7.
 */

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }
}
