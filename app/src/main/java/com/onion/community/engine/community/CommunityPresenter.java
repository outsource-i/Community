package com.onion.community.engine.community;

import com.onion.community.bean.Community;
import com.onion.community.bean.CommunityType;
import com.onion.community.bean.HttpWrapper;
import com.onion.community.di.DataManager;
import com.onion.community.manager.Result;
import com.onion.community.manager.T;
import com.onion.community.mvp.RxPresenter;

import javax.inject.Inject;
import java.util.List;

public class CommunityPresenter extends RxPresenter<CommunityContract.View> implements  CommunityContract.Presenter{


    @Inject
    public CommunityPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void loadType() {
        addSubscribe(mDataManager
                .getApi()
                .getCommunityType()
                .compose(T.D())
                .subscribeWith(new Result<HttpWrapper<List<CommunityType>>>() {
                    @Override
                    protected void onSuccess(HttpWrapper<List<CommunityType>> listHttpWrapper) {
                        mView.getTypeOk(listHttpWrapper);
                    }
                }));
    }

    @Override
    public void getCommunity(String id) {
        addSubscribe(mDataManager
                .getApi()
                .getCommunityForId(id)
                .compose(T.D())
                .subscribeWith(new Result<HttpWrapper<List<Community>>>() {
                    @Override
                    protected void onSuccess(HttpWrapper<List<Community>> listHttpWrapper) {
                        mView.getCommunityOk(listHttpWrapper);
                    }
                }));
    }

    @Override
    public void followCommunity(String id, String communityId) {

        mDataManager
                .getApi()
                .followCommunity(id,communityId)
                .compose(T.D())
                .subscribeWith(new Result<HttpWrapper<Community>>() {
                    @Override
                    protected void onSuccess(HttpWrapper<Community> communityHttpWrapper) {
                        mView.followOk(communityHttpWrapper);
                    }
                });
    }
}
