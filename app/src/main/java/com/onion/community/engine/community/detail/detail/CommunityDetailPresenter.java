package com.onion.community.engine.community.detail.detail;

import com.onion.community.bean.Article;
import com.onion.community.bean.HttpWrapper;
import com.onion.community.di.DataManager;
import com.onion.community.manager.Result;
import com.onion.community.manager.T;
import com.onion.community.mvp.RxPresenter;

import javax.inject.Inject;

public class CommunityDetailPresenter extends RxPresenter<CommunityDetailContract.View> implements CommunityDetailContract.Presenter {


    @Inject
    public CommunityDetailPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getArticle(String articleId) {
        addSubscribe(mDataManager
                .getApi()
                .getArticle(articleId)
                .compose(T.D())
                .subscribeWith(new Result<HttpWrapper<Article>>() {
                    @Override
                    protected void onSuccess(HttpWrapper<Article> articleHttpWrapper) {
                        mView.getArticleOk(articleHttpWrapper);
                    }
                }));
    }

    @Override
    public void shoucang(String id, String id1) {
        mDataManager
                .getApi()
                .collection(id,id1)
                .compose(T.D())
                .subscribeWith(new Result<HttpWrapper<String>>() {
                    @Override
                    protected void onSuccess(HttpWrapper<String> stringHttpWrapper) {
                        mView.collection(stringHttpWrapper);
                    }
                });
    }

    @Override
    public void huifu(String data, String id,String userId) {
        mView.showDialog("回复中..");
       addSubscribe(mDataManager
                .getApi()
                .huifu(data,id,userId)
                .compose(T.D())
                .subscribeWith(new Result<HttpWrapper<String>>() {
                    @Override
                    protected void onSuccess(HttpWrapper<String> stringHttpWrapper) {
                        mView.huifuOk(stringHttpWrapper);
                    }

                    @Override
                    protected void onFinish() {
                        super.onFinish();
                        mView.dissDialog();
                    }
                }));
    }

    @Override
    public void baoming(String id, String id1) {
        mView.showDialog("报名..");
        addSubscribe(mDataManager
                .getApi()
                .baoming(id,id1)
                .compose(T.D())
                .subscribeWith(new Result<HttpWrapper<String>>() {
                    @Override
                    protected void onSuccess(HttpWrapper<String> stringHttpWrapper) {
                        mView.signupOk(stringHttpWrapper);
                    }

                    @Override
                    protected void onFinish() {
                        super.onFinish();
                        mView.dissDialog();
                    }
                }));
    }
}
