package com.onion.community.engine.community;

import com.onion.community.di.DataManager;
import com.onion.community.mvp.RxPresenter;

import javax.inject.Inject;

public class CommunityPresenter extends RxPresenter<CommunityContract.View> implements  CommunityContract.Presenter{


    @Inject
    public CommunityPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
