package com.onion.community.engine.main;

import com.onion.community.bean.Article;
import com.onion.community.bean.Banners;
import com.onion.community.bean.Community;
import com.onion.community.bean.HttpWrapper;
import com.onion.community.di.DataManager;
import com.onion.community.manager.Result;
import com.onion.community.manager.T;
import com.onion.community.mvp.RxPresenter;

import javax.inject.Inject;
import java.util.List;

public class MainPresenter extends RxPresenter<MainContract.View> implements  MainContract.Presenter{


    @Inject
    public MainPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getBanner() {
        mDataManager.getApi().getBanner()
                .compose(T.D())
                .subscribe(new Result<HttpWrapper<List<Banners>>>() {
                    @Override
                    protected void onSuccess(HttpWrapper<List<Banners>> listHttpWrapper) {
                        mView.getBannerSuccess(listHttpWrapper);
                    }
                });
    }

    @Override
    public void getNews() {
        mDataManager.getApi().getNews()
                .compose(T.D())
                .subscribe(new Result<HttpWrapper<List<Article>>>() {
                    @Override
                    protected void onSuccess(HttpWrapper<List<Article>> listHttpWrapper) {
                        mView.getNewsSuccess(listHttpWrapper);
                    }
                });
    }

    @Override
    public void getFollowCommunity(String id) {

        mDataManager
                .getApi()
                .getFollowCommunity(id)
                .compose(T.D())
                .subscribeWith(new Result<HttpWrapper<List<Community>>>() {
                    @Override
                    protected void onSuccess(HttpWrapper<List<Community>> listHttpWrapper) {
                        mView.getFollowCommunityOk(listHttpWrapper);
                    }
                });

    }
}
