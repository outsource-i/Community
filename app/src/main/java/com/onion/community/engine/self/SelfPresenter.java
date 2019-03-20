package com.onion.community.engine.self;

import com.onion.community.di.DataManager;
import com.onion.community.mvp.BasePresenter;
import com.onion.community.mvp.BaseView;
import com.onion.community.mvp.RxPresenter;

import javax.inject.Inject;

public class SelfPresenter extends RxPresenter<SelfContract.View> implements  SelfContract.Presenter{


    @Inject
    public SelfPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
