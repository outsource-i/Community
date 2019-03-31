package com.onion.community.engine.self.activity;

import com.onion.community.bean.Article;
import com.onion.community.bean.HttpWrapper;
import com.onion.community.di.DataManager;
import com.onion.community.manager.Result;
import com.onion.community.manager.T;
import com.onion.community.mvp.BasePresenter;
import com.onion.community.mvp.BaseView;
import com.onion.community.mvp.RxPresenter;

import javax.inject.Inject;
import java.util.List;

public class ActivityPresenter extends RxPresenter<ActivityContract.View> implements ActivityContract.Presenter {


    @Inject
    public ActivityPresenter(DataManager dataManager) {
        super(dataManager);
    }


    @Override
    public void getActivity(String id) {
        mDataManager
                .getApi()
                .getMyActivity(id)
                .compose(T.D())
                .subscribeWith(new Result<HttpWrapper<List<Article>>>() {
                    @Override
                    protected void onSuccess(HttpWrapper<List<Article>> wrapper) {
                        mView.getActivityOk(wrapper);
                    }
                });
    }
}
