package com.onion.community.engine.self.collection;

import com.onion.community.bean.Article;
import com.onion.community.bean.Collection;
import com.onion.community.bean.HttpWrapper;
import com.onion.community.di.DataManager;
import com.onion.community.manager.Result;
import com.onion.community.manager.T;
import com.onion.community.mvp.RxPresenter;

import javax.inject.Inject;
import java.util.List;

public class CollectionPresenter extends RxPresenter<CollectionContract.View> implements CollectionContract.Presenter {

    @Inject
    public CollectionPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getMyCollection(String userId) {
        mDataManager
                .getApi()
                .getMyCollection(userId)
                .compose(T.D())
                .subscribeWith(new Result<HttpWrapper<List<Article>>>() {
                    @Override
                    protected void onSuccess(HttpWrapper<List<Article>> wrapper) {
                        mView.getCollectionOk(wrapper);
                    }
                });
    }
}


