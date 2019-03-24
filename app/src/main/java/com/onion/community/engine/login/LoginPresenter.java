package com.onion.community.engine.login;

import com.onion.community.bean.HttpWrapper;
import com.onion.community.bean.User;
import com.onion.community.di.DataManager;
import com.onion.community.manager.Result;
import com.onion.community.manager.T;
import com.onion.community.mvp.RxPresenter;

import javax.inject.Inject;

public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {


    @Inject
    public LoginPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void login(String phone, String password) {
        mView.showDialog("登录...");
        mDataManager.getApi()
                .login(phone,password)
                .compose(T.D())
                .subscribe(new Result<HttpWrapper<User>>() {
                    @Override
                    protected void onSuccess(HttpWrapper<User> userHttpWrapper) {
                        mView.loginSuccess(userHttpWrapper);
                    }

                    @Override
                    protected void onFinish() {
                        mView.dissDialog();
                    }
                });
    }

    @Override
    public void register(String phone, String password) {
        mView.showDialog("注册...");
        mDataManager.getApi()
                .register(phone,password)
                .compose(T.D())
                .subscribe(new Result<HttpWrapper<User>>() {
                    @Override
                    protected void onSuccess(HttpWrapper<User> userHttpWrapper) {
                        mView.registerSuccess(userHttpWrapper);
                    }

                    @Override
                    protected void onFinish() {

                        mView.dissDialog();

                    }
                });
    }
}
