package com.onion.community.base;

import com.onion.community.AppCenter;
import com.onion.community.di.component.ActivityComponent;
import com.onion.community.di.component.DaggerActivityComponent;
import com.onion.community.di.module.ActivityModule;
import com.onion.community.mvp.BasePresenter;
import com.onion.community.mvp.BaseView;
import io.reactivex.disposables.CompositeDisposable;

import javax.inject.Inject;

/**
 * Created by codeest on 2016/8/2.
 * MVP activity基类
 */
public abstract class BaseActivity<T extends BasePresenter> extends SimpleActivity implements BaseView {

    @Inject
    protected T mPresenter;

    protected ActivityComponent getActivityComponent(){
        return  DaggerActivityComponent.builder()
                .appComponent(AppCenter.getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    protected ActivityModule getActivityModule(){
        return new ActivityModule(this);
    }

    @Override
    protected void initView() {
        initInject();
        if (mPresenter != null){
            mPresenter.attachView(this);
        }

        mProgressDialog.setOnCancelListener(dialog -> {
            CompositeDisposable compositeDisposable = mPresenter.getCompositeDisposable();
            if(compositeDisposable != null && compositeDisposable.isDisposed()){
                compositeDisposable.clear();
            }

            if(mCompositeSubscription != null){
                mCompositeSubscription.clear();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null)
            mPresenter.detachView();
        super.onDestroy();
    }

    protected abstract void initInject();


}