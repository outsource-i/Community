package com.onion.community.engine.community.detail;

import com.onion.community.bean.Article;
import com.onion.community.bean.Community;
import com.onion.community.bean.HttpWrapper;
import com.onion.community.di.DataManager;
import com.onion.community.manager.Result;
import com.onion.community.manager.T;
import com.onion.community.mvp.RxPresenter;

import javax.inject.Inject;
import java.util.List;

public class CommunityPresenter extends RxPresenter<CommunityContract.View> implements CommunityContract.Presenter{

    @Inject
    public CommunityPresenter(DataManager dataManager) {
        super(dataManager);
    }


    @Override
    public void getCommunityInfo(String communityId) {
        addSubscribe(mDataManager
                .getApi()
                .getCommunityInfo(communityId)
                .compose(T.D())
                .subscribeWith(new Result<HttpWrapper<Community>>() {
                    @Override
                    protected void onSuccess(HttpWrapper<Community> communityHttpWrapper) {
                        mView.getOk(communityHttpWrapper);
                    }
                }));
    }

    @Override
    public void getCommunityArticle(int type, String communityId, int page, int pageSize,boolean flag) {
        get(type,communityId,page,pageSize,flag);
    }

    public void get(int type, String communityId, int page, int pageSize,boolean loadMore){
        addSubscribe(mDataManager
                .getApi()
                .getCommunityArticle(type,communityId,page,pageSize)
                .compose(T.D())
                .subscribeWith(new Result<HttpWrapper<List<Article>>>() {
                    @Override
                    protected void onSuccess(HttpWrapper<List<Article>> wrapper) {
                        mView.getArticleOk(wrapper,loadMore);
                    }
                }));
    }
}
