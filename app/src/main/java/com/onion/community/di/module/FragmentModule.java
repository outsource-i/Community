package com.onion.community.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;


import com.onion.community.di.scope.FragmentScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangqi on 16/8/7.
 */

@Module
public class FragmentModule {

    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return fragment.getActivity();
    }
}
