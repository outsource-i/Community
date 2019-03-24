package com.onion.community.engine.self.mycommunity;

import com.onion.community.di.DataManager;
import com.onion.community.mvp.BasePresenter;
import com.onion.community.mvp.BaseView;
import com.onion.community.mvp.RxPresenter;

import javax.inject.Inject;

public class MyCommunityPresenter extends RxPresenter<MyCommunityContract.View> implements MyCommunityContract.Presenter {


    @Inject
    public MyCommunityPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
