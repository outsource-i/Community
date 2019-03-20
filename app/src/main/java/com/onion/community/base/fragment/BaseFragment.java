package com.onion.community.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.onion.community.AppCenter;
import com.onion.community.di.component.ActivityComponent;
import com.onion.community.di.component.DaggerActivityComponent;
import com.onion.community.di.module.ActivityModule;
import com.onion.community.mvp.BasePresenter;
import com.onion.community.mvp.BaseView;

import javax.inject.Inject;

/**
 * Created by codeest on 2016/8/2.
 * MVP Fragment基类
 */
public abstract class BaseFragment<T extends BasePresenter> extends SimpleFragment implements BaseView {

    @Inject
    protected T mPresenter;

    protected ActivityComponent getFragmentComponent(){
        return DaggerActivityComponent.builder()
                .appComponent(AppCenter.getAppComponent())
                .activityModule(getFragmentModule())
                .build();
    }

    protected ActivityModule getFragmentModule(){
        return new ActivityModule(mActivity);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initInject();
        mPresenter.attachView(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) mPresenter.detachView();
        super.onDestroyView();
    }

    protected abstract void initInject();

}